package DTOs;

public class EncryptionMetadata {

    private String fileId;
    private String algorithm;
    private String mode;
    private String padding;
    private String keyLength;
    private String key;  // Hex encoded key
    private String iv;   // Hex encoded IV
    private String hash; // to store a hash or MAC for integrity
    private String encryptedText;

    public String getEncryptedText() {
        return encryptedText;
    }

    public void setEncryptedText(String encryptedText) {
        this.encryptedText = encryptedText;
    }

    // Getters and setters for all fields
    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getPadding() {
        return padding;
    }

    public void setPadding(String padding) {
        this.padding = padding;
    }

    public String getKeyLength() {
        return keyLength;
    }

    public void setKeyLength(String keyLength) {
        this.keyLength = keyLength;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
}