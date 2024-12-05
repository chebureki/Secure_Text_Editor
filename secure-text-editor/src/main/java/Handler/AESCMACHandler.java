package Handler;

import Builder.DecryptionKeyBuilder;
import DTOs.EncryptionMetadata;
import com.ste.Encryption;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class AESCMACHandler implements MACHandler{
    private static final Logger logger = LoggerFactory.getLogger(AESCMACHandler.class);
    @Override
    public String compute(byte[] plainText, EncryptionMetadata metadata) {
        try {
            //TODO: pls use secure coding guidelines and create new key!
            Mac mac = Mac.getInstance(metadata.getHash(), "BC");
            SecretKey key = new DecryptionKeyBuilder().setKey(Hex.decode(metadata.getMacKey())).setAlgorithm(metadata.getAlgorithm()).build();
            String k = Hex.toHexString(key.getEncoded());
           logger.info(k);
            mac.init(key);
            mac.update(plainText);
            String s = Hex.toHexString(mac.doFinal());
            return s;
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
