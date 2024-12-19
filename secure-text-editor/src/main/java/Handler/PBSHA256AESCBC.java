package Handler;

import DTOs.EncryptionMetadata;
import org.bouncycastle.util.encoders.Hex;

public class PBSHA256AESCBC implements CryptoAlgorithmHandler {
    @Override
    public String encrypt(byte[] plainText, EncryptionMetadata metadata) {
        byte[] derivedKey = service.buildPBEKey(metadata).getEncoded();
        metadata.setKey(Hex.toHexString(derivedKey));
        metadata.setMode("CBC");
        return new AESAlgorithmHandler().encrypt(plainText, metadata);
    }

    @Override
    public String decrypt(String cipherText, EncryptionMetadata metadata) {
        return new AESAlgorithmHandler().decrypt(cipherText, metadata);
    }
}
