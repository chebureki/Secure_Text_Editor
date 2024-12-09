package Factory;

import Handler.AESAlgorithmHandler;
import Handler.ChaCha20AlgorithmHandler;
import Handler.CryptoAlgorithmHandler;

public class AlgorithmHandlerFactory {
    public static CryptoAlgorithmHandler getHandler(String algorithm) {
        return switch (algorithm) {
            case "AES" -> new AESAlgorithmHandler();
            case "ChaCha7539", "ChaCha20" -> new ChaCha20AlgorithmHandler();
            default -> throw new UnsupportedOperationException("Algorithm not supported");
            //e
        };
    }
}