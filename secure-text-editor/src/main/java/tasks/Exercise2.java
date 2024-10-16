package tasks;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.Arrays;
import java.util.Base64;

public class Exercise2 {
    public static void main(String[] args) {
     try {
         task();
     }
     catch(Exception e){
         e.printStackTrace();
     }
    }

    public static void task2() throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        //8 Bytes
        byte[] keyBytes = Hex.decode("FFFFFFFFFFFFFFFF");

        SecretKeySpec key = new SecretKeySpec(keyBytes, "DES");
         Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding", "BC");
         byte[] input = Hex.decode("a0a1a2a3a4a5a6a7");
         System.out.println("input : " + Hex.toHexString(input));
         cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] output = cipher.doFinal(input);
        System.out.println("encrypted: " + Hex.toHexString(output));

         cipher.init(Cipher.DECRYPT_MODE, key);

         System.out.println("decrypted: " + Hex.toHexString(cipher.doFinal(output)));
    }
    public static void task() throws Exception {

        Security.addProvider(new BouncyCastleProvider());

        String encodedText = "SoL2FA9Q9lGjhJUZjlE0qO1l2DKeeushaERgeJ/FjbYkDMh14vO9JI1NlWlp9tX2";
        String encodedKey = "5PAN+6j7FxfySdmjRlO8pA\\u003d\\u003d";

        byte[] text = Base64.getDecoder().decode(encodedText);
        byte[] keyByte = Base64.getDecoder().decode(encodedKey);

        SecretKeySpec key = new SecretKeySpec(keyByte, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");

        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(text);

        System.out.println("decrypted: " + new String(decryptedBytes));
    }

}
