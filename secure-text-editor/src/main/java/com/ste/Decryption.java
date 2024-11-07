package com.ste;

import Factory.AlgorithmHandlerFactory;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import DTOs.EncryptionMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.EncryptionMetaDataConverter;
import services.EncryptionService;

@Path("/api/decrypt")
public class Decryption {
   private static final Logger logger =  LoggerFactory.getLogger(Decryption.class);
    EncryptionService service = new EncryptionService();
    private static final EncryptionMetaDataConverter converter = new EncryptionMetaDataConverter();
    @POST
    public String decryptText(String encryptedTextWithId) {
        logger.info("Received the encrypted Text with id at beginning: "+encryptedTextWithId);

        String[] parts = encryptedTextWithId.split("\\.");// Split on the first dot
        String fileID = parts[0];
        String encryptedText = parts[1];

        EncryptionMetadata metadata = converter.lookUpMetaData(fileID);
        if (metadata != null){

            return AlgorithmHandlerFactory.getHandler(metadata.getAlgorithm()).decrypt(encryptedText, metadata);
        }
        return "Text does not exists!";
    }
}
