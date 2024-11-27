package Handler;

import Builder.DecryptionKeyBuilder;
import DTOs.EncryptionMetadata;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.util.Objects;

public class ChaCha20AlgorithmHandler implements CryptoAlgorithmHandler{

    private static final Logger logger = LoggerFactory.getLogger(ChaCha20AlgorithmHandler.class);

    @Override
    public String encrypt(byte[] plainText, EncryptionMetadata metadata) {
        final String ChaCha = "ChaCha7539";
        Cipher c = service.buildCipher(ChaCha);
        SecretKey key  = service.buildKey(ChaCha, "BC", Integer.parseInt(metadata.getKeySize()));
        byte[] encryptedText =  service.encrypt(c, plainText, key);
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
        Cipher c = service.buildCipher(metadata.getAlgorithm());
        byte[] text = Hex.decode(cipherText);
        byte[] keyByte = Hex.decode(metadata.getKey());
        SecretKey key = new DecryptionKeyBuilder().setKey(keyByte).setAlgorithm(metadata.getAlgorithm()).build();
        byte[] iv = Hex.decode(metadata.getIv());
        byte[] decryptedByteText;
        if (Arrays.equals(iv, Hex.decode("6e756c6c"))){
            decryptedByteText = service.decrypt(c,text,key);
        }else {
            decryptedByteText = service.decrypt(c, text, key, new IvParameterSpec(iv));
        }
        String decryptedText = new String(decryptedByteText);
        logger.info("Successfully decrypted the text with result: "+decryptedText);
        return decryptedText;
    }

}
