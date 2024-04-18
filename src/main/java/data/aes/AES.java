package data.aes;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class AES implements data.Cipher
{
    private static final String ALGORITHM = "AES";
    private static final String CIPHER_TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int KEY_SIZE = 128;
    private static final int TAG_LENGTH = 128;

    private final SecretKey secretKey;
    private final Cipher cipher;

    public AES() throws NoSuchAlgorithmException, NoSuchPaddingException
    {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        keyGenerator.init(KEY_SIZE);

        secretKey = keyGenerator.generateKey();
        cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
    }

    @Override
    public String encrypt(String message) throws Exception
    {
        byte[] messageBytes = message.getBytes();


        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(messageBytes);

        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    @Override
    public String decrypt(String encryptedMessage) throws Exception
    {
        byte[] messageBytes = Base64.getDecoder().decode(encryptedMessage);

        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(TAG_LENGTH, cipher.getIV());
        cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParameterSpec);

        byte[] decryptedBytes = cipher.doFinal(messageBytes);

        return new String(decryptedBytes);
    }
}
