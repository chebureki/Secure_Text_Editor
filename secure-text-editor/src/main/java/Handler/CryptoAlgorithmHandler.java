package Handler;

import DTOs.EncryptionMetadata;
import services.EncryptionService;

public interface CryptoAlgorithmHandler {
    EncryptionService service = new EncryptionService();
    String encrypt(String plainText, EncryptionMetadata metadata);
    String decrypt(String cipherText, EncryptionMetadata metadata);
}

