package Builder;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class KeyBuilder {

    static String algorithm;
    static String provider;
    static int keySize;

    public KeyBuilder setAlgorithm(String algorithm) {
        KeyBuilder.algorithm = algorithm;
        return this;
    }

    public KeyBuilder setProvider(String provider) {
        KeyBuilder.provider = provider;
        return this;
    }

    public KeyBuilder setKeySize(int keySize) {
        KeyBuilder.keySize = keySize;
        return this;
    }

    public SecretKey build() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm, "BC");
            keyGenerator.init(keySize);
            return keyGenerator.generateKey();
        }catch (NoSuchAlgorithmException e){
            System.out.println("The given Algorithm does not exists. Check the String input with setAlgorithm! ");
            System.out.println("-------------------------------");
            e.printStackTrace();
        }catch (NoSuchProviderException e){
            System.out.println("Bouncy Castle is not available? Check if the dependcy is set in pom.xml!");
            System.out.println("-------------------------------");
            System.out.println("Provider not ");
            e.printStackTrace();
        }
        return null;
    }

}
