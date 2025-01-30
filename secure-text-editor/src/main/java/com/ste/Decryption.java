package com.ste;

import Factory.AlgorithmHandlerFactory;
import Factory.IntegrityHandlerFactory;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import DTOs.EncryptionMetadata;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.EncryptionMetaDataConverter;
import services.KeyStoreService;

import java.security.Security;

@Path("/api/decrypt")
public class Decryption {

   private static final Logger logger =  LoggerFactory.getLogger(Decryption.class);
    private static final EncryptionMetaDataConverter converter = new EncryptionMetaDataConverter();
    @POST
    public String decryptText(String encryptedTextWithId) {
        Security.addProvider(new BouncyCastleProvider());
        KeyStoreService ks = new KeyStoreService();
        logger.info("Received the encrypted Text with id at beginning: "+encryptedTextWithId);

        String[] parts = encryptedTextWithId.split("\\.");// Split on the first dot
        String fileID = parts[0];
        String encryptedText;
        if(parts.length > 1) {
            encryptedText = parts[1];
        }else{
            encryptedText = "";
        }


        EncryptionMetadata metadata = converter.lookUpMetaData(fileID);
        metadata.setKey(ks.retrieveKey(metadata));
        // Decrypt the text
        String decryptedText = decryptText(encryptedText, metadata);

        // Verify message integrity if a hash is provided
        if (isMessageCompromised(decryptedText, metadata)) {
            return "MESSAGE COMPROMISED!";
        }

        return decryptedText;
    }

    private String decryptText(String encryptedText, EncryptionMetadata metadata) {
        if (metadata.getAlgorithm() == null || metadata.getAlgorithm().isEmpty()) {
            throw new IllegalArgumentException("Algorithm cannot be null or empty");
        }
        String baseAlgorithm = metadata.getAlgorithm().contains("_")
                ? metadata.getAlgorithm().split("_")[0]
                : metadata.getAlgorithm();
        metadata.setAlgorithm(baseAlgorithm);
        return AlgorithmHandlerFactory.getHandler(metadata.getAlgorithm()).decrypt(encryptedText, metadata);
    }

    private boolean isMessageCompromised(String text, EncryptionMetadata metadata) {
        String hashAlgorithm = metadata.getIntegrityAlgorithm();
        if (hashAlgorithm == null || hashAlgorithm.isEmpty()) {
            return false; // No integrity check required if hash is absent
        }
        return !IntegrityHandlerFactory.getHandler(hashAlgorithm).verify(text.getBytes(), metadata);
    }
}
