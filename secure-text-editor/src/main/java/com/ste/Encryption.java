package com.ste;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import models.EncryptionRequest;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

@Path("/api/encrypt")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.APPLICATION_JSON)
public class Encryption {

    @POST
    public String encryptText(EncryptionRequest request) {
        // Extract the text and encryption parameters from the request
        String plainText = request.getText();
        String encryptionType = request.getEncryptionType();
        String keyLength = request.getKeyLength();
        String encryptedText = "";
        if (encryptionType.equals("AES_SYM")){
            Cipher c = new CipherBuilder().setAlgorithm("AES").setMode("ECB").setPadding("NoPadding").build();
            System.out.println("I was here!" + c);
            encryptedText = performEncryption(plainText, encryptionType, keyLength);
        }


        return encryptedText;
    }

    // A simple placeholder method for encryption logic based on the parameters
    private String performEncryption(String text, String encryptionType, String keyLength) {
        // Implement real encryption logic here based on the encryptionType and keyLength
        return "Encrypted: " + text + " | Type: " + encryptionType + " | Key Length: " + keyLength;
    }

    public class CipherBuilder {
        static String input = "";
        static String provider = "BC";

        public CipherBuilder setAlgorithm(String algo) {
            algo += "/";
            this.input += algo;
            return this;
        }

        public CipherBuilder setMode(String mode){
            this.input += mode + "/";
            return this;
        }

        public CipherBuilder setPadding(String padding){
            this.input += padding;
            return this;
        }

        public Cipher build() {
            try {

                Security.addProvider(new BouncyCastleProvider());
                return Cipher.getInstance(input, provider);
            }catch (NoSuchPaddingException e){
                System.out.println("The given Padding does not exists. Check the String input with setPadding! ");
                System.out.println("-------------------------------");
                e.printStackTrace();
            }catch (NoSuchAlgorithmException e){
                System.out.println("The given Algorithm does not exists. Check the String input with setAlgorithm! ");
                System.out.println("-------------------------------");
                e.printStackTrace();
            }catch (NoSuchProviderException e){
                System.out.println("Bouncy Castle is not available? Check if the dependcy is set in pom.xml!");
                System.out.println("-------------------------------");
                e.printStackTrace();
            }
            return null;
        }

    }
}
