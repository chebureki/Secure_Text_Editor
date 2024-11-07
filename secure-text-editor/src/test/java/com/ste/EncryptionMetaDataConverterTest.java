package com.ste;

import DTOs.EncryptionMetadata;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.EncryptionMetaDataConverter;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EncryptionMetaDataConverterTest {

    private EncryptionMetaDataConverter converter;
    private Path testBaseDir;

    @BeforeEach
    void setUp() {
        converter = new EncryptionMetaDataConverter();
        testBaseDir = Paths.get(System.getProperty("user.home"), "STE", "encryption", "MetaData");
        // Clean up any test files that might be there from previous tests
        if (Files.exists(testBaseDir)) {
            try {
                Files.walk(testBaseDir)
                        .map(Path::toFile)
                        .forEach(File::delete);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void testStoreMetaDataCreatesFileSuccessfully() {
        UUID uuid = UUID.randomUUID();
        String testJson = "{\"testKey\":\"testValue\"}";

        // Store the metadata
        converter.storeMetaData(testJson, uuid);

        Path filePath = testBaseDir.resolve(uuid.toString() + ".json");
        assertTrue(Files.exists(filePath), "The metadata file should be created");

        // Verify the content
        try {
            String storedContent = Files.readString(filePath);
            assertEquals(testJson, storedContent, "The stored JSON should match the input JSON");
        } catch (IOException e) {
            fail("Reading stored file failed");
        }
    }


    @Test
    void testLookUpMetaDataFileDoesNotExist() {
        UUID uuid = UUID.randomUUID();
        EncryptionMetadata metadata = converter.lookUpMetaData(uuid.toString());

        assertNull(metadata, "Metadata should be null if file does not exist");
    }

    @Test
    void testSerializeMetadataProducesCorrectJson() {
        EncryptionMetadata metadata = new EncryptionMetadata.Builder()//
                .setAlgorithm("AES")//
                .setFileId("15a0c428-bc27-4936-82d0-791a481ab6db")//
                .setMode("CBC")//
                .setPadding("CTSPadding")//
                .setKeySize("128")//
                .setIv("317aed0a1f1af70c9b2965a3859e44d0")//
                .setKey("b0bccd28ce6c14af832d904627e4a7da")
                .build();

        String json = converter.serializeMetadata(metadata);
        assertTrue(json.contains("\"key\": \"b0bccd28ce6c14af832d904627e4a7da\""), "Serialized JSON should contain correct data");
    }

    @Test
    void testDeserializeMetadataParsesJsonCorrectly() {
        String json = "{\n" +
                "  \"fileId\": \"4d5100c4-3365-4b07-b6f8-bcd5635feff8\",\n" +
                "  \"algorithm\": \"AES\",\n" +
                "  \"mode\": \"ECB\",\n" +
                "  \"padding\": \"ISO10126Padding\",\n" +
                "  \"keySize\": \"192\",\n" +
                "  \"key\": \"7be94dbf6304f48c0b39aba1390e36062ece2eebd3587449\",\n" +
                "  \"iv\": \"6e756c6c\"\n" +
                "}";

        EncryptionMetadata metadata = converter.deserializeMetadata(json);
        assertNotNull(metadata, "Deserialized metadata should not be null");
        assertEquals("7be94dbf6304f48c0b39aba1390e36062ece2eebd3587449", metadata.getKey(), "Metadata content should match JSON content");
    }


    @AfterEach
    void tearDown() {
        // Clean up files after tests
        if (Files.exists(testBaseDir)) {
            try {
                Files.walk(testBaseDir)
                        .map(Path::toFile)
                        .forEach(File::delete);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
