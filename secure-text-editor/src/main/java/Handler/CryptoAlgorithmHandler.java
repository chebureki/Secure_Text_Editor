package Handler;

import DTOs.EncryptionMetadata;
import com.ste.Encryption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.EncryptionService;

public interface CryptoAlgorithmHandler {
    EncryptionService service = new EncryptionService();
    String encrypt(String plainText, EncryptionMetadata metadata);
    String decrypt(String cipherText, EncryptionMetadata metadata);
}

