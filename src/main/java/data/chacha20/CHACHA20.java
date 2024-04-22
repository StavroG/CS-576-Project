package data.chacha20;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.ChaCha20ParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;




public class CHACHA20 implements data.Cipher
{
    //TODO: Need to implement this cipher
    private static final String ALGORITHM = "ChaCha20";
    private final SecretKey secretKey;
    private final byte[] nonce;
    private final int counter;


    public CHACHA20() throws Exception
    {
        secretKey =  generateChaCha20Key();
        nonce = getNonce();
        counter = 1;
    }


    @Override
    public String encrypt(String message) throws Exception
    {
        byte[] messageBytes = message.getBytes();

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        ChaCha20ParameterSpec param = new ChaCha20ParameterSpec(nonce, counter);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, param);
        byte[] encryptedBytes = cipher.doFinal(messageBytes);

        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    @Override
    public String decrypt(String encryptedMessage) throws Exception
    {
        byte[] messageBytes = Base64.getDecoder().decode(encryptedMessage);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        ChaCha20ParameterSpec param = new ChaCha20ParameterSpec(nonce, counter);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, param);

        byte[] decryptedBytes = cipher.doFinal(messageBytes);

        return new String(decryptedBytes);
    }

    // generate 256-bit secret key (32-byte)
    private static SecretKey generateChaCha20Key() throws Exception
    {
        // Use a secure random number generator
        SecureRandom secureRandom = new SecureRandom();

        byte[] key = new byte[32];
        secureRandom.nextBytes(key);

        // Return the key
        return new SecretKeySpec(key, ALGORITHM);
    }

    // 96-bit nonce
    private static byte[] getNonce()
    {
        byte[] newNonce = new byte[12];
        new SecureRandom().nextBytes(newNonce);
        return newNonce;
    }
}
