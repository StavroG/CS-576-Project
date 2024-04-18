import data.aes.AES;
import data.camellia.Camellia;
import data.chacha20.CHACHA20;
import data.threedes.ThreeDES;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CiphersTest
{
    @Test
    public void testAes() throws Exception
    {
        AES aes = new AES();
        String testString = "Hello world";

        String encrypted = aes.encrypt(testString);
        String decrypted = aes.decrypt(encrypted);

        assertNotEquals(testString, encrypted);
        assertEquals(testString, decrypted);
    }

    @Test
    public void testThreeDes() throws Exception
    {
        ThreeDES threeDES = new ThreeDES();
        String testString = "Hello world";

        String encrypted = threeDES.encrypt(testString);
        String decrypted = threeDES.decrypt(encrypted);

        assertNotEquals(testString, encrypted);
        assertEquals(testString, decrypted);
    }

    @Test
    public void testChacha20() throws Exception
    {
        CHACHA20 chacha20 = new CHACHA20();
        String testString = "Hello world";

        String encrypted = chacha20.encrypt(testString);
        String decrypted = chacha20.decrypt(encrypted);

        assertNotEquals(testString, encrypted);
        assertEquals(testString, decrypted);
    }

    @Test
    public void testCamellia() throws Exception
    {
        Camellia camellia = new Camellia();
        String testString = "Hello world";

        String encrypted = camellia.encrypt(testString);
        String decrypted = camellia.decrypt(encrypted);

        assertNotEquals(testString, encrypted);
        assertEquals(testString, decrypted);
    }

}
