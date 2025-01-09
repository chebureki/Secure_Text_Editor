package Builder;

import java.security.*;
import java.security.spec.DSAParameterSpec;
import java.security.spec.InvalidParameterSpecException;

public class KeyPairBuilder {
    String algorithm;

    public KeyPairBuilder setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
        return this;
    }

    public KeyPair build()  {
        KeyPairGenerator keyPair = null;
        try {
            keyPair = KeyPairGenerator.getInstance("DSA", "BC");
            keyPair.initialize(2048);
            AlgorithmParameterGenerator paramGen = AlgorithmParameterGenerator.getInstance("DSA", "BC");
            paramGen.init(2048);
            AlgorithmParameters params = paramGen.generateParameters();
            DSAParameterSpec dsa = params.getParameterSpec(DSAParameterSpec.class);

            KeyFactory keyFactory = KeyFactory.getInstance("DSA");
            //keyFactory.generatePublic();
            return keyPair.generateKeyPair();
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidParameterSpecException e) {
            throw new RuntimeException(e);
        }
    }
}
