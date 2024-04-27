package data.threedes;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class ThreeDES implements data.Cipher
{
    private static final String ALGORITHM = "DESede";
    private final SecretKey secretKey;
    private final byte[] iv;
    
    public ThreeDES() throws Exception {
        secretKey = generateTripleDESKey();
        iv = generateRandomIV();
    }
    
    public SecretKey generateTripleDESKey() throws Exception {

        SecureRandom secureRandom = new SecureRandom();
        // Generate a 24-byte (192-bit) random key
        byte[] keyBytes = new byte[24];
        secureRandom.nextBytes(keyBytes);
        DESedeKeySpec keySpec = new DESedeKeySpec(keyBytes);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);

        return keyFactory.generateSecret(keySpec);
    }
    
    public byte[] generateRandomIV() {
        byte[] iv = new byte[8]; // 64-bit IV for TripleDES
        new SecureRandom().nextBytes(iv);
        return iv;
    }

    @Override
    public String encrypt(String message) throws Exception
    {
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), ALGORITHM);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher encryptCipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        encryptCipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec);
        byte[] encryptedBytes = encryptCipher.doFinal(message.getBytes());

        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    @Override
    public String decrypt(String encryptedMessage) throws Exception
    {
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), ALGORITHM);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher decryptCipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        decryptCipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivSpec);
        byte[] decryptedBytes = decryptCipher.doFinal(Base64.getDecoder().decode(encryptedMessage));

        return new String(decryptedBytes);
    }
}
