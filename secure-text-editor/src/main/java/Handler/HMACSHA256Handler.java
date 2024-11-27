package Handler;

import DTOs.EncryptionMetadata;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class HMACSHA256Handler implements MACHandler {

    @Override
    public String compute(byte[] plainText, EncryptionMetadata metadata) {
        try {
            // Use the provided key from metadata
            SecretKey key = new SecretKeySpec(Hex.decode(metadata.getKey()), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256", "BC");
            mac.init(key);
            return Hex.toHexString(mac.doFinal(plainText));
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new RuntimeException("Error computing HMAC-SHA256 hash", e);
        }
    }

    @Override
    public boolean verify(byte[] plainText, EncryptionMetadata metadata) {
        try {
            // Recompute the hash and compare it with the stored hash
            String computedHash = compute(plainText, metadata);
            return MessageDigest.isEqual(
                    Hex.decode(computedHash),
                    Hex.decode(metadata.getHashValue())
            );
        } catch (Exception e) {
            throw new RuntimeException("Error verifying HMAC-SHA256 hash", e);
        }
    }
}
