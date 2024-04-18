package org.example.data.aes;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AesImplementation
{
    private static final String ALGORITHM = "AES";
    private static final String CIPHER_TRANSFORMATION = "AES/ECB/PKCS5Padding";
    private static final String CHARSET = "UTF-8";

    public String encrypt(String plaintext, byte[] keyBytes) throws Exception {
        // Create AES key spec
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, ALGORITHM);

        // Create AES cipher instance
        Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);

        // Initialize cipher for encryption
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);

        // Encrypt plaintext
        byte[] ciphertextBytes = cipher.doFinal(plaintext.getBytes(CHARSET));

        // Convert ciphertext to Base64 for easier display
        return Base64.getEncoder().encodeToString(ciphertextBytes);
    }

    public String decrypt(String ciphertext, byte[] keyBytes) throws Exception {
        // Create AES key spec
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, ALGORITHM);

        // Create AES cipher instance
        Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);

        // Initialize cipher for decryption
        cipher.init(Cipher.DECRYPT_MODE, keySpec);

        // Decrypt ciphertext
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(ciphertext));

        // Convert decrypted bytes to string
        return new String(decryptedBytes, CHARSET);
    }
}
