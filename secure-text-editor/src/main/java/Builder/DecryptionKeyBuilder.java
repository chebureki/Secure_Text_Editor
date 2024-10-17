package Builder;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;

public class DecryptionKeyBuilder {
    String algorithm;
    byte[] key = null;

    public DecryptionKeyBuilder setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
        return this;
    }

    public DecryptionKeyBuilder setKey(byte[] key) {
        this.key = key;
        return this;
    }

    public SecretKey build(){
        Security.addProvider(new BouncyCastleProvider());
        if(key != null && algorithm != null) {
            return new SecretKeySpec(key, algorithm);
        }
        return null;
    }

}
