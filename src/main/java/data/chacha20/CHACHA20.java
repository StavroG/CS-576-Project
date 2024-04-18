package data.chacha20;

//import data.Cipher;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.ChaCha20ParameterSpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class CHACHA20 implements Cipher
{
    //TODO: Need to implement this cipher
    private static final String ALGORITHM = "ChaCha20";


    @Override
    public String encrypt(String message) throws Exception
    {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        return "";
    }

    @Override
    public String decrypt(String encryptedMessage) throws Exception
    {
        return "";
    }
}
