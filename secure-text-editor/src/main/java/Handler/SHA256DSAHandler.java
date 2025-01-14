package Handler;

import Builder.KeyPairBuilder;
import DTOs.EncryptionMetadata;
import org.bouncycastle.util.encoders.Hex;
import services.EncryptionService;
import services.IntegrityService;

import java.security.Key;
import java.security.KeyPair;
import java.security.Signature;

public class SHA256DSAHandler implements IntegrityHandler {

    IntegrityService service = new IntegrityService();
    @Override
    public String compute(byte[] plainText, EncryptionMetadata metadata) {
        String keyPairAlgo = "DSA";
        KeyPair keyPair = new KeyPairBuilder().setAlgorithm(keyPairAlgo).build();
        return Hex.toHexString(service.sign(metadata, plainText, keyPair));
    }

    @Override
    public boolean verify(byte[] plainText, EncryptionMetadata metadata) {
        return false;
    }
}
