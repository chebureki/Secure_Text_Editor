package com.ste;

import Builder.KeyBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

import java.security.Security;

import static org.junit.jupiter.api.Assertions.*;

public class KeyBuilderTest {

    private KeyBuilder keyBuilder;

    @BeforeEach
    public void setUp() {
        keyBuilder = new KeyBuilder();
        Security.addProvider(new BouncyCastleProvider());
    }

    @Test
    public void testBuildValidAESKey() {
        SecretKey key = keyBuilder
                .setAlgorithm("AES")
                .setProvider("BC") // Ensure this provider is available in your environment
                .setKeySize(256)
                .build();
        assertNotNull(key, "The generated key should not be null");
        assertEquals("AES", key.getAlgorithm(), "The key algorithm should be AES");
    }

    @Test
    public void testBuildWithInvalidAlgorithm() {
        SecretKey key = keyBuilder
                .setAlgorithm("InvalidAlgorithm")
                .setProvider("BC")
                .setKeySize(256)
                .build();
        assertNull(key, "The key should be null for an invalid algorithm");
    }

    @Test
    public void testBuildWithInvalidProvider() {
        Security.addProvider(new BouncyCastleProvider());
        SecretKey key = keyBuilder
                .setAlgorithm("AES")
                .setProvider("InvalidProvider")
                .setKeySize(256)
                .build();
        assertNull(key, "The key should be null for an invalid provider");
    }

    @Test
    public void testBuildWithNoKeySize() {
        SecretKey key = keyBuilder
                .setAlgorithm("AES")
                .setProvider("BC")
                .setKeySize(0) // Invalid key size
                .build();
        assertNull(key, "The key should be null for an invalid key size");
    }
}
