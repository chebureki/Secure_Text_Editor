package com.ste;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.EncryptionService;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.security.InvalidKeyException;
import java.security.Security;

import static org.junit.jupiter.api.Assertions.*;

public class EncryptionServiceTest {

    private EncryptionService encryptionService;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    @BeforeEach
    public void setUp() {
        encryptionService = new EncryptionService();
        Security.addProvider(new BouncyCastleProvider()); // Ensure BouncyCastle is available for tests
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void testBuildCipher() {
        Cipher cipher = encryptionService.buildCipher("AES", "CBC", "PKCS5Padding");
        assertNotNull(cipher, "Cipher object should not be null");
        assertEquals("AES/CBC/PKCS5Padding", cipher.getAlgorithm(), "The algorithm of the cipher should match");
    }

    @Test
    public void testBuildKey() {
        SecretKey secretKey = encryptionService.buildKey("AES", "BC", 256); // Assuming BC (BouncyCastle) is the provider
        assertNotNull(secretKey, "The generated key should not be null");
        assertEquals("AES", secretKey.getAlgorithm(), "The algorithm of the key should be AES");
    }



    @Test
    public void testDecryptWithInvalidKey() {
        // Setup
        Cipher cipher = encryptionService.buildCipher("AES", "CBC", "PKCS5Padding");
        byte[] encryptedText = "Invalid key test".getBytes();

        // Test decrypt with an invalid key (null)
        byte[]result =   encryptionService.decrypt(cipher, encryptedText, null);
        assertNull(result, "Result should be null when InvalidKeyException is thrown");
        assertTrue(outputStreamCaptor.toString().contains("Invalid key is inserted"),
                "Expected message should be printed when InvalidKeyException is caught");
    }

    @Test
    public void testPrepareAndSerializeMetadata() {
        byte[] key = new byte[32]; // 256-bit AES key
        byte[] iv = new byte[16];  // 128-bit IV
        byte[] encryptedText = "This is encrypted text".getBytes();

        // Test serialization of metadata
        String fileId = encryptionService.prepareAndSerializeMetadata(
                "AES", "CBC", "PKCS5Padding", key, iv, "256", encryptedText
        );
        assertNotNull(fileId, "The file ID should not be null");
        assertFalse(fileId.isEmpty(), "The file ID should not be empty");
    }
}
