package tasks;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

public class Exercise2 {
    public static void main(String[] args) {
     try {
         task2();
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
}
