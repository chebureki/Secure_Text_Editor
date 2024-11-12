package DTOs;

public class EncryptionRequest {
    private String text;
    private String encryptionType;
    private String keySize;
    private String padding;
    private String blockMode;
    private String key;


// Getters and Setters

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getEncryptionType() {
        return encryptionType;
    }


    public String getKeySize() {
        return keySize;
    }

    public void setKeySize(String keySize) {
        this.keySize = keySize;
    }


    public String getPadding() {
        return padding;
    }

    public void setPadding(String padding) {
        this.padding = padding;
    }

    public String getBlockMode() {
        return blockMode;
    }

    public void setBlockMode(String blockMode) {
        this.blockMode = blockMode;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setEncryptionType(String encryptionType) {
        this.encryptionType = encryptionType;
    }
}
