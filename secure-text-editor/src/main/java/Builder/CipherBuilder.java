package Builder;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

public class CipherBuilder {

    static String algo;
    static String mode;
    static String padding;

    static String provider = "BC";

    public CipherBuilder setAlgorithm(String algo) {
        this.algo = algo;
        return this;
    }

    public CipherBuilder setMode(String mode){
        this.mode = mode;
        return this;
    }

    public CipherBuilder setPadding(String padding){
        this.padding = padding;
        return this;
    }

    public Cipher build() {
        try {
            Security.addProvider(new BouncyCastleProvider());
            String input = algo+"/"+mode+"/"+padding;
            return Cipher.getInstance(input, provider);
        }catch (NoSuchPaddingException e){
            System.out.println("The given Padding does not exists. Check the String input with setPadding! ");
            System.out.println("-------------------------------");
            e.printStackTrace();
        }catch (NoSuchAlgorithmException e){
            System.out.println("The given Algorithm does not exists. Check the String input with setAlgorithm! ");
            System.out.println("-------------------------------");
            e.printStackTrace();
        }catch (NoSuchProviderException e){
            System.out.println("Bouncy Castle is not available? Check if the dependency is set in pom.xml!");
            System.out.println("-------------------------------");
            e.printStackTrace();
        }
        return null;
    }

}
