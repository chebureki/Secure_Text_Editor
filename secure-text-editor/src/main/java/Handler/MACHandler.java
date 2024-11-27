package Handler;

import DTOs.EncryptionMetadata;

public interface MACHandler {
    String compute(byte[] plainText, EncryptionMetadata metadata);
    boolean verify(byte[] plainText, EncryptionMetadata metadata);
}
