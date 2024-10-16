package services;

import Builder.CipherBuilder;
import Builder.KeyBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.EncryptionMetadata;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import java.security.InvalidKeyException;
import java.security.Security;
import java.util.Base64;
import java.util.Random;

public class EncryptionService {

    public String serializeMetadata(EncryptionMetadata metadata) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(metadata);  // Convert the metadata object to a JSON string
    }

    // Example function to prepare metadata after encryption
    public String prepareAndSerializeMetadata(String algorithm, String mode, String padding,
                                              byte[] key, byte[] iv, String keyLength, byte[] encryptedText) {
        Security.addProvider(new BouncyCastleProvider());
        EncryptionMetadata metadata = new EncryptionMetadata();
        metadata.setAlgorithm(algorithm);
        metadata.setMode(mode);
        metadata.setPadding(padding);
        metadata.setKey(Base64.getEncoder().encodeToString(key));
        if(iv != null) {
            metadata.setIv(Base64.getEncoder().encodeToString(iv));
        }else{
            metadata.setIv("null");
        }
        metadata.setKeyLength(keyLength);
        metadata.setEncryptedText(Base64.getEncoder().encodeToString(encryptedText));

        return serializeMetadata(metadata);  // Return serialized JSON
    }

    public byte[] encrypt(Cipher c, byte[] byteText, SecretKey key){
        try{
            c.init(Cipher.ENCRYPT_MODE, key);
            return c.doFinal(byteText);
        }catch (InvalidKeyException e){
            System.out.println("Invalid key is inserted, someone did an upsi here!");
            System.out.println("-------------------------------");
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            System.out.println("This blocksize is not suitable. Look up!");
            System.out.println("-------------------------------");
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            System.out.println("Bad padding! take a look at the inserted padding");
            System.out.println("-------------------------------");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return null;
    }

    public Cipher buildCipher(String algorithm, String mode, String padding){
        return new CipherBuilder().setAlgorithm(algorithm)//
                .setMode(mode)//
                .setPadding(padding)//
                .build();
    }

    public SecretKey buildKey(String algorithm, String provider, int keySize){
        return new KeyBuilder().setAlgorithm(algorithm)//
                .setProvider(provider)//
                .setKeySize(keySize)
                .build();
    }

    public byte[] checkInputBlockSize(byte[] text, String padding) {
        if (padding.equals("NoPadding")) {
            int blockSize = 16; // AES block size is 16 bytes
            int paddingLength = blockSize - (text.length % blockSize);

            // Only pad if padding is necessary (when the text is not a multiple of the block size)
            if (paddingLength != blockSize) {
                byte[] paddingBytes = new byte[paddingLength];
                new Random().nextBytes(paddingBytes);  // Random padding

                byte[] result = new byte[text.length + paddingLength];


                System.arraycopy(text, 0, result, 0, text.length);
                System.arraycopy(paddingBytes, 0, result, text.length, paddingLength);

                return result;
            }
        }

        return text;
    }
}
