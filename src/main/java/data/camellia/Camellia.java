package data.camellia;

//import data.Cipher;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Base64;

public class Camellia implements data.Cipher
{
    //https://javadoc.iaik.tugraz.at/iaik_jce/current/iaik/security/cipher/Camellia.html
    //TODO: Need to implement this cipher

    private static final String algorithm = "Camellia";
    private static final String transformation = "Camellia/CBC/PKCS5Padding";
    private static final String provider = "IAIK";

    private SecretKey key;
    private byte[] keyBytes;
    private SecretKeySpec secret;
    private Cipher cipher;
    private byte[] ivBytes;
    private IvParameterSpec iv;

    public Camellia() throws NoSuchAlgorithmException, NoSuchPaddingException, NoSuchProviderException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm, provider);
        key = keyGenerator.generateKey();
        keyBytes = key.getEncoded();

        cipher = Cipher.getInstance(transformation, provider);

        ivBytes = cipher.getIV();
        iv = new IvParameterSpec(ivBytes);

        secret = new SecretKeySpec(keyBytes,"DES");
    }

    @Override
    public String encrypt(String message) throws Exception
    {
        //convert the message to bytes
        byte[] rawMessage = message.getBytes();

        //encrypt the bytes
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] rawEncryptedMessage = cipher.doFinal(rawMessage);

        //return the encrypted message as a string
        return Base64.getEncoder().encodeToString(rawEncryptedMessage);
    }

    @Override
    public String decrypt(String encryptedMessage) throws Exception
    {
        //convert the encrypted message to bytes
        byte[] rawEncryptedMessage = encryptedMessage.getBytes();

        //decrypt the bytes
        cipher.init(Cipher.DECRYPT_MODE,secret,iv);
        byte[] rawDecryptedMessage = cipher.doFinal(rawEncryptedMessage);

        //return the decrypted message as a string
        return Base64.getEncoder().encodeToString(rawDecryptedMessage);
    }
}
