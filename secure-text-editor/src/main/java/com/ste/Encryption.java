package com.ste;
import DTOs.EncryptionMetadata;
import Factory.AlgorithmHandlerFactory;
import Factory.MacHandlerFactory;
import Handler.MACHandler;
import Handler.SHA256Handler;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import DTOs.EncryptionRequest;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.EncryptionService;

import javax.crypto.SecretKey;

@Path("/api")
public class Encryption {
   private static final Logger logger = LoggerFactory.getLogger(Encryption.class);

   EncryptionService service = new EncryptionService();
 @POST
 @Path("/encrypt")
 @Produces(MediaType.TEXT_PLAIN)
 @Consumes(MediaType.APPLICATION_JSON)
    public String encryptText(EncryptionRequest request) {
        //TODO: pls first concat the Hashvalue with the plaintext and then encrypt! Output length of Hashvalue is same!
        // Extract the text and encryption parameters from the request
       logger.info("Received Text, ready to encrypt!");
        String plainText = request.getText();
        String encryptionType = request.getEncryptionType().split("_")[0];
        String keySize = request.getKeySize().substring(0,3);
        String padding = request.getPadding().split("_")[0];
        String blockMode = request.getBlockMode().split("_")[0];
        String mac = request.getMAC().split("_")[0];
        byte[] plainText2Bytes = plainText.getBytes();
        EncryptionMetadata metadata = new EncryptionMetadata.Builder()//
                .setKeySize(keySize)//
                .setPadding(padding)//
                .setMode(blockMode)//
                .setHash(mac)
                .setAlgorithm(encryptionType)
                .build();
        if (request.getKey().equals("")){
            SecretKey key  = service.buildKey(encryptionType, "BC", Integer.parseInt(metadata.getKeySize()));
            metadata.setKey(Hex.toHexString(key.getEncoded()));
        }else {
            metadata.setKey(request.getKey());
        }
        if(!mac.equals("NONE")) {
            SecretKey key  = service.buildKey(encryptionType, "BC", Integer.parseInt(metadata.getKeySize()));
            metadata.setMacKey(Hex.toHexString(key.getEncoded()));
            metadata.setHashValue(MacHandlerFactory.getHandler(mac).compute(plainText2Bytes, metadata));
        }

        return AlgorithmHandlerFactory.getHandler(encryptionType).encrypt(plainText2Bytes,metadata);
    }
    @POST
    @Path("/generate-key")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String generateKey(EncryptionRequest request) {
        String encryptionType = request.getEncryptionType().split("_")[0];
        int keySize = Integer.parseInt(request.getKeySize().substring(0, 3));
        return Hex.toHexString(service.buildKey(encryptionType, "BC", keySize).getEncoded());
    }
}
