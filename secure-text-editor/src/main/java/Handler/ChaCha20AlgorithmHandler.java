package Handler;

import DTOs.EncryptionMetadata;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Objects;

public class ChaCha20AlgorithmHandler implements CryptoAlgorithmHandler{

    private static final Logger logger = LoggerFactory.getLogger(AESAlgorithmHandler.class);

    @Override
    public String encrypt(String plainText, EncryptionMetadata metadata) {
        final String ChaCha = "ChaCha7539";
        Cipher c = service.buildCipher(ChaCha);
        //ToDo: bis hier her gekommen!!!
        SecretKey key;
        if (!Objects.equals(metadata.getKey(), "")) {
            key = new SecretKeySpec(Hex.decode(metadata.getKey()),ChaCha);
        } else {
            key  = service.buildKey(ChaCha, "BC", Integer.parseInt(metadata.getKeySize()));
        }
        byte[] text = plainText.getBytes();
        byte[] encryptedText =  service.encrypt(c, text, key);
        logger.debug("here are the parameters: \n plaintext: " +plainText+" \n padding: "+ metadata.getPadding()+" \n key: " + key.toString());
        metadata.setAlgorithm(ChaCha);
        metadata.setKey(Hex.toHexString(key.getEncoded()));
        metadata.setIv(Hex.toHexString(Objects.requireNonNullElseGet(c.getIV(), "null"::getBytes)));
        metadata.setFileId(java.util.UUID.randomUUID().toString());
        String fileId = service.serializeMetadata(metadata);
        String encEncryptedText = Hex.toHexString(encryptedText);
        return fileId+"."+encEncryptedText;
    }

    @Override
    public String decrypt(String cipherText, EncryptionMetadata metadata) {
        return null;
    }
}
