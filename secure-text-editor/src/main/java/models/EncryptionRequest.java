package models;

public class EncryptionRequest {
        private String text;
        private String encryptionType;
        private String keyLength;
        private String passwordAlgorithm;
        private String chaCha20Algorithm;

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
}
