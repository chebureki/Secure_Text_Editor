package com.ste;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import models.EncryptionRequest;
import services.EncryptionService;
import javax.crypto.*;
import java.util.Base64;

@Path("/api/encrypt")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.APPLICATION_JSON)
public class Encryption {
    EncryptionService service = new EncryptionService();
    @POST
    public String encryptText(EncryptionRequest request) {
        // Extract the text and encryption parameters from the request
        String plainText = request.getText();
        String encryptionType = request.getEncryptionType();
        String keySize = request.getKeyLength().substring(0,3);
        byte[] encryptedText;
        String padding = request.getPadding().split("_")[0];
        String blockMode = request.getBlockMode().split("_")[0];
        String fileId= "";
        String encEncryptedText = "";

        if (encryptionType.equals("AES_SYM")){
            final String AES = "AES";
            Cipher c = service.buildCipher(AES, blockMode, padding);
            //key.getEncoded for bytes of key
            SecretKey key = service.buildKey(AES, "BC", Integer.parseInt(keySize));
            byte[] text = plainText.getBytes();
            text =  service.checkInputBlockSize(text, padding);
            encryptedText =  service.encrypt(c, text, key);
            fileId = service.prepareAndSerializeMetadata(AES,blockMode,padding,key.getEncoded(),null,keySize,encryptedText);
            encEncryptedText = Base64.getEncoder().encodeToString(encryptedText);
            encEncryptedText = fileId+"."+encEncryptedText;
        }


        return encEncryptedText;
    }



}
