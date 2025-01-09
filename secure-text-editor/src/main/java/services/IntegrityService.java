package services;

import DTOs.EncryptionMetadata;

import java.security.*;

public class IntegrityService {

    public byte[] sign(EncryptionMetadata metadata, byte[] plaintext, KeyPair kp){
        try {
            Signature signature = Signature.getInstance("SHA256withDSA", "BC");
            signature.initSign(kp.getPrivate());
            signature.update(plaintext);
            return signature.sign();
        }


        catch (SignatureException | NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
}
