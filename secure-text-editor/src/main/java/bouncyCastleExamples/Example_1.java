package bouncyCastleExamples;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.*;
public class Example_1{

    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException {
        //important always load bouncycastle provider before starting to use it
        Security.addProvider(
                new BouncyCastleProvider());
        SecureRandom random = SecureRandom.getInstance("DEFAULT", "BC");
    }
}