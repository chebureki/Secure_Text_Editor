package Handler;

import DTOs.EncryptionMetadata;
import org.bouncycastle.util.encoders.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class SHA256Handler implements MACHandler {
    @Override
    public String compute(byte[] plainText, EncryptionMetadata metadata) {
        try {
            MessageDigest digest = MessageDigest.getInstance(metadata.getHash(), "BC");
            return Hex.toHexString(digest.digest(plainText));
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean verify(byte[] plainText, EncryptionMetadata metadata) {
        byte[] computedHash = Hex.decode(compute(plainText, metadata));
        byte[] storedHash = Hex.decode(metadata.getHashValue());
        return MessageDigest.isEqual(computedHash, storedHash);
    }

}
