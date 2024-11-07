package services;

import Builder.CipherBuilder;
import Builder.KeyBuilder;
import DTOs.EncryptionMetadata;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Security;
import java.util.Objects;
import java.util.UUID;
/**
 * This class provides encryption services.
 * It supports encrypting and decrypting text, building ciphers, and handling encryption metadata.
 *  @author Elias Harb
 */

public class EncryptionService {


    private static final EncryptionMetaDataConverter converter = new EncryptionMetaDataConverter();

    /**
     * serializes the MetaData into Json files and triggers the storing function
     * @param algorithm
     * @param mode
     * @param padding
     * @param key
     * @param iv
     * @param keyLength
     * @param encryptedText
     * @return the UUID of the encrypted file
     */
    public String prepareAndSerializeMetadata(String algorithm, String mode, String padding,
                                              byte[] key, byte[] iv, String keyLength, byte[] encryptedText) {
        Security.addProvider(new BouncyCastleProvider());
        EncryptionMetadata metadata = new EncryptionMetadata.Builder().setAlgorithm(algorithm)//
                .setMode(mode)//
                .setPadding(padding)//
                .setKey(Hex.toHexString(key))//
                .setKeySize(keyLength)//
                .setIv(Hex.toHexString(Objects.requireNonNullElseGet(iv, "null"::getBytes)))//
                .setFileId(java.util.UUID.randomUUID().toString())//
                .build();
        converter.storeMetaData(converter.serializeMetadata(metadata), UUID.fromString(metadata.getFileId()));
        return metadata.getFileId();  // Return serialized JSON
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

    public byte[] decrypt(Cipher c, byte[] encryptedByteText,SecretKey key){
        try {
            c.init(Cipher.DECRYPT_MODE,key);
            return c.doFinal(encryptedByteText);
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

    public byte[] decrypt(Cipher c, byte[] encryptedByteText, SecretKey key, IvParameterSpec iv){
        try {
            c.init(Cipher.DECRYPT_MODE,key, iv);
            return c.doFinal(encryptedByteText);
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
        } catch (InvalidAlgorithmParameterException e) {
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
        return new KeyBuilder().
                setAlgorithm(algorithm)//
                .setKeySize(keySize)//
                .setProvider(provider)//
                .build();
    }


}
