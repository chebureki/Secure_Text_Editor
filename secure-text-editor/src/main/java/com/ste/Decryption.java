package com.ste;

import Builder.DecryptionKeyBuilder;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import DTOs.EncryptionMetadata;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.EncryptionMetaDataConverter;
import services.EncryptionService;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.util.Arrays;
import java.util.Collection;

@Path("/api/decrypt")
public class Decryption {
   private static final Logger logger =  LoggerFactory.getLogger(Decryption.class);
    EncryptionService service = new EncryptionService();
    private static final EncryptionMetaDataConverter converter = new EncryptionMetaDataConverter();
    @POST
    public String decryptText(String encryptedTextWithId) {
        logger.info("Received the encrypted Text with id at beginning: "+encryptedTextWithId);

        String[] parts = encryptedTextWithId.split("\\.");  // Split on the first dot
        String fileID = parts[0];
        String encryptedText = parts[1];

        EncryptionMetadata metadata = converter.lookUpMetaData(fileID);
        Cipher c = service.buildCipher(metadata.getAlgorithm(), metadata.getMode(), metadata.getPadding());
        byte[] text = Hex.decode(encryptedText);
        byte[] keyByte = Hex.decode(metadata.getKey());
        SecretKey key = new DecryptionKeyBuilder().setKey(keyByte).setAlgorithm(metadata.getAlgorithm()).build();
        byte[] iv = Hex.decode(metadata.getIv());
        byte[] decryptedByteText;
        if (Arrays.equals(iv, Hex.decode("6e756c6c"))){
            decryptedByteText = service.decrypt(c,text,key);
        }else {
             decryptedByteText = service.decrypt(c, text, key, new IvParameterSpec(iv));
        }
        String decryptedText = new String(decryptedByteText);
        logger.info("Successfully decrypted the text with result: "+decryptedText);
        return decryptedText;
    }
}
