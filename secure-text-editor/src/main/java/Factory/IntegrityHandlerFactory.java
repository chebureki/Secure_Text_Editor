package Factory;

import Handler.*;

public class IntegrityHandlerFactory {

    public static IntegrityHandler getHandler(String algorithm) {
        return switch (algorithm) {
            case "SHA-256" -> new SHA256Handler();
            case "AESCMAC" -> new AESCMACHandler();
            case "HMACSHA256" -> new HMACSHA256Handler();
            case "SHA256withDSA" -> new SHA256DSAHandler();
            default -> throw new UnsupportedOperationException("Algorithm not supported");
        };
    }
}
