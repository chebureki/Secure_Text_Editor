package Builder;

import javax.crypto.Mac;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class MacBuilder {
    private String hash;

    public MacBuilder(String hash){
        setHash(hash);
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Mac build(){
        try {
            return Mac.getInstance(getHash(), "BC");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Mac Algorithm does not exist");
            throw new RuntimeException(e);
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        }
    }
}
