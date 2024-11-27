package Factory;

import Handler.*;

public class MacHandlerFactory {

    public static MACHandler getHandler(String mac) {
        return switch (mac) {
            case "SHA-256" -> new SHA256Handler();
            case "AESCMAC" -> new AESCMACHandler();
            case "HMACSHA256" -> new HMACSHA256Handler();
            default -> throw new UnsupportedOperationException("Algorithm not supported");
        };
    }
}
