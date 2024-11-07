package com.ste;
import DTOs.EncryptionMetadata;
import Factory.AlgorithmHandlerFactory;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import DTOs.EncryptionRequest;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.EncryptionService;
import javax.crypto.*;

@Path("/api/encrypt")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.APPLICATION_JSON)
public class Encryption {
   private static final Logger logger = LoggerFactory.getLogger(Encryption.class);
    EncryptionService service = new EncryptionService();
    @POST
    public String encryptText(EncryptionRequest request) {
        // Extract the text and encryption parameters from the request
       logger.info("Received Text, ready to encrypt!");
        String plainText = request.getText();
        String encryptionType = request.getEncryptionType().split("_")[0];
        String keySize = request.getKeySize().substring(0,3);
        String padding = request.getPadding().split("_")[0];
        String blockMode = request.getBlockMode().split("_")[0];
        EncryptionMetadata metadata = new EncryptionMetadata.Builder()//
                .setKeySize(keySize)//
                .setPadding(padding)//
        .setMode(blockMode).build();
        return AlgorithmHandlerFactory.getHandler(encryptionType).encrypt(plainText,metadata);
    }
}
