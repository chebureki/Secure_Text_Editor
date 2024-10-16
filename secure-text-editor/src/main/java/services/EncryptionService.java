package services;

import Builder.CipherBuilder;
import Builder.KeyBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.EncryptionMetadata;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.Security;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

public class EncryptionService {

    @ConfigProperty(name = "file.storage.path")
    String storagePath;
    private String serializeMetadata(EncryptionMetadata metadata) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(metadata);  // Convert the metadata object to a JSON string
    }
    public EncryptionMetadata deserializeMetadata(String jsonMetadata) {
        Gson gson = new Gson();
        return gson.fromJson(jsonMetadata, EncryptionMetadata.class);
    }

    /**
     *
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
        UUID fileId = java.util.UUID.randomUUID();
        metadata.setFileId(fileId.toString());
        storeMetaData(serializeMetadata(metadata), fileId);
        return fileId.toString();  // Return serialized JSON
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

    private void storeMetaData(String json, UUID uiid){
        String fileName = System.getProperty("user.home");
        fileName+= "\\STE\\encryption\\MetaData\\"+uiid.toString()+".json";
        File metaData = new File(fileName);
        System.out.println(metaData.getName());
        try {
            Files.write(metaData.toPath(), json.getBytes());
        }catch (IOException e){
            System.out.println("file could not be stored!");
            e.printStackTrace();
        }
    }

    private String getMetaDataFromSystem(String uiid)  {
        String fileName = System.getProperty("user.home");
        fileName+= "\\STE\\encryption\\MetaData\\"+uiid.toString()+".json";
        Path path = Paths.get(fileName);
        try {
            return Files.readString(path);
        } catch (IOException e) {
            System.out.println("File does not exist!");
            e.printStackTrace();
        }
        return "";
    }

    public EncryptionMetadata lookUpMetaData(String id){
        String json = getMetaDataFromSystem(id);
        EncryptionMetadata metadata = deserializeMetadata(json);
        return metadata;
    }
}
