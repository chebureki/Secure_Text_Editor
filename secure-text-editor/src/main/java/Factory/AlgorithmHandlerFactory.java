package Factory;

import Handler.AESAlgorithmHandler;
import Handler.ChaCha20AlgorithmHandler;
import Handler.CryptoAlgorithmHandler;

public class AlgorithmHandlerFactory {
    public static CryptoAlgorithmHandler getHandler(String algorithm) {
        switch (algorithm) {
            case "AES":
                return new AESAlgorithmHandler();
            case "ChaCha7539":
            case "ChaCha20":
                return new ChaCha20AlgorithmHandler();
            default:
                throw new UnsupportedOperationException("Algorithm not supported");
        }
    }
}