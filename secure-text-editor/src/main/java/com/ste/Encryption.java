package com.ste;
import Builder.CipherBuilder;
import Builder.KeyBuilder;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import models.EncryptionRequest;
import services.EncryptionService;

import java.security.InvalidKeyException;
import java.util.*;
import javax.crypto.*;

@Path("/api/encrypt")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.APPLICATION_JSON)
public class Encryption {
    EncryptionService service = new EncryptionService();
    String json = "";
    @POST
    public String encryptText(EncryptionRequest request) {
        // Extract the text and encryption parameters from the request
        String plainText = request.getText();
        String encryptionType = request.getEncryptionType();
        String keySize = request.getKeyLength().substring(0,3);
        byte[] encryptedText;
        String padding = request.getPadding().split("_")[0];
        String blockMode = request.getBlockMode().split("_")[0];


        if (encryptionType.equals("AES_SYM")){
            final String AES = "AES";
            Cipher c = service.buildCipher(AES, blockMode, padding);
            //key.getEncoded for bytes of key
            SecretKey key = service.buildKey(AES, "BC", Integer.parseInt(keySize));
            byte[] text = plainText.getBytes();
            text =  service.checkInputBlockSize(text, padding);
            encryptedText =  service.encrypt(c, text, key);
            json = service.prepareAndSerializeMetadata(AES,blockMode,padding,key.getEncoded(),null,keySize,encryptedText);
        }


        return json;
    }

    // A simple placeholder method for encryption logic based on the parameters
    private String performEncryption(String text, String encryptionType, String keyLength) {
        // Implement real encryption logic here based on the encryptionType and keyLength
        return "Encrypted: " + text + " | Type: " + encryptionType + " | Key Length: " + keyLength;
    }



}
