package DTOs;

public class EncryptionMetadata {

    private String fileId;
    private String algorithm;
    private String mode;
    private String padding;
    private String keySize;
    private String key;  // Hex encoded key
    private String iv;   // Hex encoded IV
    private String hash; // to store a hash or MAC for integrity
    private String hashValue;
    public EncryptionMetadata(Builder builder){
        fileId = builder.fileId;
        algorithm = builder.algorithm;
        mode = builder.mode;
        padding = builder.padding;
        keySize = builder.keySize;
        key = builder.key;
        iv = builder.iv;
        hash = builder.hash;
        hashValue = builder.hashValue;
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

    public String getKeySize() {
        return keySize;
    }

    public void setKeySize(String keySize) {
        this.keySize = keySize;
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

    public String getHashValue() {
        return hashValue;
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
    public void setHashValue(String hash){this.hashValue = hash; }

    public static class Builder {
        private String fileId;
        private String algorithm;
        private String mode;
        private String padding;
        private String keySize;
        private String key;
        private String iv;
        private String hash;
        private String hashValue;
        private String encryptedText;

        // Builder methods for each field
        public Builder setFileId(String fileId) {
            this.fileId = fileId;
            return this;
        }

        public Builder setAlgorithm(String algorithm) {
            this.algorithm = algorithm;
            return this;
        }

        public Builder setMode(String mode) {
            this.mode = mode;
            return this;
        }

        public Builder setPadding(String padding) {
            this.padding = padding;
            return this;
        }

        public Builder setKeySize(String keySize) {
            this.keySize = keySize;
            return this;
        }

        public Builder setKey(String key) {
            this.key = key;
            return this;
        }

        public Builder setIv(String iv) {
            this.iv = iv;
            return this;
        }

        public Builder setHash(String hash) {
            this.hash = hash;
            return this;
        }

        public Builder setHashValue(String hashValue) {
            this.hashValue = hashValue;
            return this;
        }
        public Builder setEncryptedText(String encryptedText) {
            this.encryptedText = encryptedText;
            return this;
        }



        // Build method to create EncryptionMetadata object
        public EncryptionMetadata build() {
            return new EncryptionMetadata(this);
        }
    }
}
