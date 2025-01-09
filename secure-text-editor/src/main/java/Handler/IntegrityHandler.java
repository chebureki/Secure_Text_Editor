package Handler;

import DTOs.EncryptionMetadata;

public interface IntegrityHandler {
    String compute(byte[] plainText, EncryptionMetadata metadata);
    boolean verify(byte[] plainText, EncryptionMetadata metadata);
}
