package Handler;

import DTOs.EncryptionMetadata;
import org.bouncycastle.util.encoders.Hex;
import services.EncryptionService;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class HMACSHA256Handler implements MACHandler {

    private final EncryptionService service = new EncryptionService();
    @Override
    public String compute(byte[] plainText, EncryptionMetadata metadata) {
        try {
            SecretKey key = service.buildKey(Hex.decode(metadata.getMacKey()), "HmacSHA256");
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
