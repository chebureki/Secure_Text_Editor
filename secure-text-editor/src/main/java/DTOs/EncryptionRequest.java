package DTOs;

public class EncryptionRequest {
        private String text;
        private String encryptionType;
        private String keyLength;
        private String passwordAlgorithm;
        private String chaCha20Algorithm;
        private String padding;
        private String blockMode;



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

        public void setEncryptionType(String encryptionType) {
            this.encryptionType = encryptionType;
        }

        public String getKeyLength() {
            return keyLength;
        }

        public void setKeyLength(String keyLength) {
            this.keyLength = keyLength;
        }

        public String getPasswordAlgorithm() {
            return passwordAlgorithm;
        }

        public void setPasswordAlgorithm(String passwordAlgorithm) {
            this.passwordAlgorithm = passwordAlgorithm;
        }

        public String getChaCha20Algorithm() {
            return chaCha20Algorithm;
        }

        public void setChaCha20Algorithm(String chaCha20Algorithm) {
            this.chaCha20Algorithm = chaCha20Algorithm;
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
}
