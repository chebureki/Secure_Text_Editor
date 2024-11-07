package Factory;

import Handler.AESAlgorithmHandler;
import Handler.CryptoAlgorithmHandler;

public class AlgorithmHandlerFactory {
    public static CryptoAlgorithmHandler getHandler(String algorithm) {
        switch (algorithm) {
            case "AES":
                return new AESAlgorithmHandler();
            //ToDo: More Handlers!!!
            default:
                throw new UnsupportedOperationException("Algorithm not supported");
        }
    }
}