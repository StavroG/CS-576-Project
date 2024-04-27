import org.apache.commons.lang3.RandomStringUtils;
import data.Cipher;
import data.aes.AES;
import data.camellia.Camellia;
import data.chacha20.CHACHA20;
import data.threedes.ThreeDES;

import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.logging.Logger;

public class EncryptionPerformanceEvaluator {

    private static final Logger logger = Logger.getLogger(EncryptionPerformanceEvaluator.class.getName());
    private static final int MAX_MESSAGE_SIZE = 1024; // Maxsize of random message in bytes

    public static void main(String[] args) {
        evaluateAlgorithms();
    }

    private static void evaluateAlgorithms() {
        try {
            Cipher[] algorithms = {new AES(), new Camellia(), new CHACHA20(), new ThreeDES()};
            long totalIterations = 0;

            for (Cipher algorithm : algorithms) {
                int successfulIterations = 0;
                SecureRandom random = new SecureRandom();
                boolean errorOccurred = false;

                // Encryption and decryption loop
                while (!errorOccurred) {
                    try {
                        // Generate random plaintext message size
                        int messageSize = random.nextInt(MAX_MESSAGE_SIZE) + 1; // Random size between 1 and MAX_MESSAGE_SIZE
                        String plaintext = RandomStringUtils.randomAlphanumeric(messageSize);

                        // Encrypt the message
                        String ciphertext = algorithm.encrypt(plaintext);
                        String decryptedText = algorithm.decrypt(ciphertext);

                        if (plaintext.equals(decryptedText)) {
                            successfulIterations++;
                        } else {
                            errorOccurred = true;
                        }

                        totalIterations++;

                        // Log every 1,000,000 iterations
                        if (totalIterations % 1_000_000 == 0) {
                            logResults(algorithm.getClass().getSimpleName(), successfulIterations, totalIterations);
                        }
                    } catch (Exception e) {
                        errorOccurred = true;
                    }
                }

                // Log the final results after an error occurs
                logResults(algorithm.getClass().getSimpleName(), successfulIterations, totalIterations);
            }
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void logResults(String algorithmName, int successfulIterations, long totalIterations) {
        double successRate = totalIterations == 0 ? 0 : (double) successfulIterations / totalIterations * 100;
        logger.info(algorithmName + ": Successful iterations = " + successfulIterations + ", Success rate = " + successRate + "%");
    }
}
