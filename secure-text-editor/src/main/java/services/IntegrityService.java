package services;

import DTOs.EncryptionMetadata;
import org.bouncycastle.util.encoders.Hex;

import java.security.*;

public class IntegrityService {

    public byte[] sign(EncryptionMetadata metadata, byte[] plaintext, KeyPair kp){
        try {
            Signature signature = Signature.getInstance("SHA256withDSA", "BC");
            signature.initSign(kp.getPrivate());
            signature.update(plaintext);
            metadata.setPublicKey(Hex.toHexString(kp.getPublic().getEncoded()));
            metadata.setPrivateKey(Hex.toHexString(kp.getPrivate().getEncoded()));
            return signature.sign();
        }


        catch (SignatureException | NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
}
