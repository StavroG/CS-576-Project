package fileGenerator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TextFileGenerator {
    public static void main(String[] args) {

        // Define the file size in bytes
        int fileSize_1mb = 1024 * 1024;
        int fileSize_100mb = 100 * 1024 * 1024;
        int fileSize_1gb = 1024 * 1024 * 1024;

        String oneMB = "1MB.txt";
        String hundredMB = "100MB.txt";
        String oneGB = "1GB.txt";

        // select the .txt name for your needs
        Path outputPath = Paths.get("src/main/resources", oneMB);

        // Generate random text
        // Change the iteration size (filesize_1mb, or 100mb or 1gb)
        StringBuilder randomText = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < fileSize_1mb; i++) {
            char randomChar = (char) ('a' + random.nextInt(26));
            randomText.append(randomChar);
        }

        // Write the random text to a file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath.toFile()))) {
            writer.write(randomText.toString());
            System.out.println("Random text file generated successfully.");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
