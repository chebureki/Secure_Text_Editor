package Handler;

import Builder.KeyBuilder;
import DTOs.EncryptionMetadata;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.util.Arrays;
import java.util.Objects;

public class ChaCha20AlgorithmHandler implements CryptoAlgorithmHandler{

    private static final Logger logger = LoggerFactory.getLogger(ChaCha20AlgorithmHandler.class);

    @Override
    public String encrypt(byte[] plainText, EncryptionMetadata metadata) {
        final String chaCha = "ChaCha7539";
        Cipher c = service.buildCipher(chaCha);
        return service.encryptAndStore(chaCha,c,plainText, metadata);
    }

    @Override
    public String decrypt(String cipherText, EncryptionMetadata metadata) {
        Cipher c = service.buildCipher(metadata.getAlgorithm());
        return service.decrypt(cipherText, c, metadata);
    }

}
