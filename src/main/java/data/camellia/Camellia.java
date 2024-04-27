//Copyright (c) 2008 NTT (Nippon Telegraph and Telephone Corporation) .
//All rights reserved.
//
//Redistribution and use in source and binary forms, with or without
//modification, are permitted provided that the following conditions
//are met:
//1. Redistributions of source code must retain the above copyright
//notice, this list of conditions and the following disclaimer as
//the first lines of this file unmodified.
//2. Redistributions in binary form must reproduce the above copyright
//notice, this list of conditions and the following disclaimer in the
//documentation and/or other materials provided with the distribution.
//
//THIS SOFTWARE IS PROVIDED BY NTT ``AS IS'' AND ANY EXPRESS OR
//IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
//OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
//IN NO EVENT SHALL NTT BE LIABLE FOR ANY DIRECT, INDIRECT,
//INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
//NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
//DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
//THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
//(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
//THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

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
import java.security.Security;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.security.SecureRandom;

public class Camellia implements data.Cipher
{
    //https://javadoc.iaik.tugraz.at/iaik_jce/current/iaik/security/cipher/Camellia.html
    //TODO: Need to implement this cipher

    private static final String algorithm = "Camellia";
    private static final String transformation = "Camellia/CBC/PKCS5Padding";
    private static final String provider = "BC";

    private SecretKey key;
    private byte[] keyBytes;
    private SecretKeySpec secret;
    private Cipher cipher;
    private byte[] ivBytes;
    private IvParameterSpec iv;


    public Camellia() throws NoSuchAlgorithmException, NoSuchPaddingException, NoSuchProviderException {
        Security.addProvider(new BouncyCastleProvider());

        // Initialize the cipher object
        cipher = Cipher.getInstance(transformation, provider);

        KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm, provider);
        key = keyGenerator.generateKey();
        keyBytes = key.getEncoded();

        // Generate a random initialization vector (iv)
        SecureRandom random = new SecureRandom();
        ivBytes = new byte[cipher.getBlockSize()];
        random.nextBytes(ivBytes);
        iv = new IvParameterSpec(ivBytes);

        secret = new SecretKeySpec(keyBytes, "DES");
    }

    @Override
    public String encrypt(String message) throws Exception
    {
        // Convert the message to bytes
        byte[] rawMessage = message.getBytes();

        // Encrypt the bytes
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] rawEncryptedMessage = cipher.doFinal(rawMessage);

        // Return the encrypted message as a string
        return Base64.getEncoder().encodeToString(rawEncryptedMessage);
    }

    @Override
    public String decrypt(String encryptedMessage) throws Exception
    {
        // Decode the Base64 string
        byte[] rawEncryptedMessage = Base64.getDecoder().decode(encryptedMessage);

        // Decrypt the bytes
        cipher.init(Cipher.DECRYPT_MODE, secret, iv);
        byte[] rawDecryptedMessage = cipher.doFinal(rawEncryptedMessage);

        // Return the decrypted message as a string
        return new String(rawDecryptedMessage);
    }


}
