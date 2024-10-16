package com.ste;

import Builder.DecryptionKeyBuilder;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import models.EncryptionMetadata;
import services.EncryptionService;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.util.Base64;

@Path("/api/decrypt")
public class Decryption {
    EncryptionService service = new EncryptionService();
    @POST
    public String decryptText(String encryptedTextWithId) {
        System.out.println("Received encrypted text: " + encryptedTextWithId);

        String[] parts = encryptedTextWithId.split("\\.", 2);  // Split on the first dot
        String fileID = parts[0];
        String encryptedText = parts[1];

        EncryptionMetadata metadata = service.lookUpMetaData(fileID);
        Cipher c = service.buildCipher(metadata.getAlgorithm(),metadata.getMode(), metadata.getPadding());
        byte[] text = Base64.getDecoder().decode(encryptedText);
        byte[] keyByte = Base64.getDecoder().decode(metadata.getKey());
        SecretKey key = new DecryptionKeyBuilder().setKey(keyByte).setAlgorithm(metadata.getAlgorithm()).build();
        byte[] decryptedByteText = service.decrypt(c,text,key);
        String decryptedText = new String(decryptedByteText);
        System.out.println(decryptedText);
        return decryptedText;
    }
}
