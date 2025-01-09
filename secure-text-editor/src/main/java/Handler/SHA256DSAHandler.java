package Handler;

import Builder.KeyPairBuilder;
import DTOs.EncryptionMetadata;
import services.EncryptionService;
import services.IntegrityService;

import java.security.KeyPair;
import java.security.Signature;

public class SHA256DSAHandler implements IntegrityHandler {

    IntegrityService service = new IntegrityService();
    @Override
    public String compute(byte[] plainText, EncryptionMetadata metadata) {
        String keyPairAlgo = "DSA";
        //Signature signature = service.sign();
        return null;
    }

    @Override
    public boolean verify(byte[] plainText, EncryptionMetadata metadata) {
        return false;
    }
}
