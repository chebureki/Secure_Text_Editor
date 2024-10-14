package com.ste;
import Builder.CipherBuilder;
import Builder.KeyBuilder;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import models.EncryptionRequest;

import java.security.InvalidKeyException;
import java.util.*;
import javax.crypto.*;

@Path("/api/encrypt")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.APPLICATION_JSON)
public class Encryption {

    @POST
    public String encryptText(EncryptionRequest request) {
        // Extract the text and encryption parameters from the request
        String plainText = request.getText();
        String encryptionType = request.getEncryptionType();
        String keySize = request.getKeyLength().substring(0,3);
        String encryptedText = "";
        String padding = "NoPadding";
        if (encryptionType.equals("AES_SYM")){
            Cipher c = buildCipher("AES", "ECB", padding);
            SecretKey key = buildKey("AES", "BC", Integer.parseInt(keySize));
            byte[] text = plainText.getBytes();
            text = checkInputBlockSize(text, padding);
            encryptedText = new String(Objects.requireNonNull(encrypt(c, text, key)));
        }


        return encryptedText;
    }

    // A simple placeholder method for encryption logic based on the parameters
    private String performEncryption(String text, String encryptionType, String keyLength) {
        // Implement real encryption logic here based on the encryptionType and keyLength
        return "Encrypted: " + text + " | Type: " + encryptionType + " | Key Length: " + keyLength;
    }

    private byte[] encrypt(Cipher c, byte[] byteText, SecretKey key){
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
