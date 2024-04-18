package data;

public interface Cipher
{
    /**
     * Given a plain text String, return an encrypted String
     *
     * @param message the original message, not encrypted
     * @return the encrypted message
     */
    String encrypt(String message) throws Exception;

    /**
     * Given an encrypted message, return the decrypted message
     *
     * @param encryptedMessage the encrypted message
     * @return the decrypted message
     */
    String decrypt(String encryptedMessage) throws Exception;
}
