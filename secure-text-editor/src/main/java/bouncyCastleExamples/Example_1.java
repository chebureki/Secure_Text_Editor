package bouncyCastleExamples;

import java.security.*;

public class Example_1{

    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException {
        //important always load bouncycastle provider before starting to use it
        java.security.Security.addProvider(
                new org.bouncycastle.jce.provider.BouncyCastleProvider());
        SecureRandom random = SecureRandom.getInstance("DEFAULT", "BC");
    }
}