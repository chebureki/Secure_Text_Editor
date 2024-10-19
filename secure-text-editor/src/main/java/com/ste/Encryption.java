package com.ste;
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
        System.out.println(request.getKeySize());
        String plainText = request.getText();
        String encryptionType = request.getEncryptionType();
        String keySize = request.getKeySize().substring(0,3);
        String padding = request.getPadding().split("_")[0];
        String blockMode = request.getBlockMode().split("_")[0];

        String encEncryptedText = "";

        if (encryptionType.equals("AES_SYM")){
           logger.info("AES Encryption will be done my lord!...");
            encEncryptedText = encryptAES(blockMode, padding, keySize, plainText);
        }


        return encEncryptedText;
    }


    private String encryptAES(String blockMode, String padding, String keySize, String plainText){
        final String AES = "AES";
        Cipher c = service.buildCipher(AES, blockMode, padding);
        SecretKey key = service.buildKey(AES, "BC", Integer.parseInt(keySize));
        byte[] text = plainText.getBytes();
        //text =  service.checkInputBlockSize(text, padding);
        byte[] encryptedText =  service.encrypt(c, text, key);
        logger.debug("here are the parameters: \n plaintext: " +plainText+" \n padding: "+ padding+" \n key: " + key.toString());
        String fileId = service.prepareAndSerializeMetadata(AES,blockMode,padding,key.getEncoded(),null,keySize,encryptedText);
        String encEncryptedText = Hex.toHexString(encryptedText);
        return fileId+"."+encEncryptedText;
    }


}
