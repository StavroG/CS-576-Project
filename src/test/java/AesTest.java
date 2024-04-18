import org.example.data.aes.AesImplementation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Tests the methods from the data package
 *
 * @author Stavro Gorou
 */
public class AesTest
{
    AesImplementation aesImplementation = new AesImplementation();
    @BeforeAll
    public static void before()
    {

    }

    @Test
    public void checkObject()
    {
        assertNotNull(aesImplementation);
    }

    @Test
    public void algorithmTest()
    {
        String testString = "Hello world";
        aesImplementation.encrypt(testString)
    }
}
