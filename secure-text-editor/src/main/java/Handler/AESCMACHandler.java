package Handler;

import Builder.DecryptionKeyBuilder;
import DTOs.EncryptionMetadata;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class AESCMACHandler implements MACHandler{

    @Override
    public String compute(byte[] plainText, EncryptionMetadata metadata) {
        try {
            Mac mac = Mac.getInstance(metadata.getHash(), "BC");
            SecretKey key = new DecryptionKeyBuilder().setKey(Hex.decode(metadata.getKey())).setAlgorithm(metadata.getAlgorithm()).build();
            mac.init(key);
            mac.update(plainText);
            return Hex.toHexString(mac.doFinal());
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean verify(byte[] plainText, EncryptionMetadata metadata) {
            byte[] computedHash = Hex.decode(compute(plainText,metadata)); // Compute the hash of the input text
            byte[] storedHash = Hex.decode(metadata.getHashValue()); // Decode the stored hash from metadata
            return MessageDigest.isEqual(computedHash, storedHash); // Secure comparison of hashes

    }
}
