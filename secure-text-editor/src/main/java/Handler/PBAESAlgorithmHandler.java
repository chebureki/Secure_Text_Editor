package Handler;

import DTOs.EncryptionMetadata;
import org.bouncycastle.jcajce.spec.ScryptKeySpec;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.SecretKeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

public class PBAESAlgorithmHandler implements CryptoAlgorithmHandler{
    @Override
    public String encrypt(byte[] plainText, EncryptionMetadata metadata) {
        byte[] derivedKey = service.buildScryptKey(metadata);
        metadata.setKey(Hex.toHexString(derivedKey));
        return new AESAlgorithmHandler().encrypt(plainText, metadata);

    }

    @Override
    public String decrypt(String cipherText, EncryptionMetadata metadata) {
        return new AESAlgorithmHandler().decrypt(cipherText, metadata);
    }


}
