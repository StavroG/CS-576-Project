package app.gui;

import data.Cipher;
import data.aes.AES;
import data.camellia.Camellia;
import data.chacha20.CHACHA20;
import data.reporting.Record;
import data.reporting.RecordType;
import data.reporting.Report;
import data.threedes.ThreeDES;
import socket.client.SocketClient;
import socket.client.TcpClient;
import socket.client.UdpClient;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class GenerateReportsPanelHandler
{
    private final TcpClient tcpClient;
    private final UdpClient udpClient;
    private final AES aes;
    private Camellia camellia;
    private ThreeDES threeDES;
    private CHACHA20 chacha20;
    private List<Record> aesRecords;
    private List<Record> camelliaRecords;
    private List<Record> threeDesRecords;
    private List<Record> chacha20Records;

    public GenerateReportsPanelHandler(TcpClient tcpClient, UdpClient udpClient) throws Exception
    {
        this.tcpClient = tcpClient;
        this.udpClient = udpClient;

        aes = new AES();
        camellia = new Camellia();
        threeDES = new ThreeDES();
        chacha20 = new CHACHA20();

        aesRecords = new ArrayList<>();
        camelliaRecords = new ArrayList<>();
        threeDesRecords = new ArrayList<>();
        chacha20Records = new ArrayList<>();
    }

    public void generateReports(File file)
    {
        List<String> fileChunks = readFileAsStrings(file);

        for(String fileChunk : fileChunks)
        {
            aesRecords.add(generateRecords(fileChunk, tcpClient, aes));
            aesRecords.add(generateRecords(fileChunk, udpClient, aes));

            camelliaRecords.add(generateRecords(fileChunk, tcpClient, camellia));
            camelliaRecords.add(generateRecords(fileChunk, udpClient, camellia));

            threeDesRecords.add(generateRecords(fileChunk, tcpClient, threeDES));
            threeDesRecords.add(generateRecords(fileChunk, udpClient, threeDES));

            chacha20Records.add(generateRecords(fileChunk, tcpClient, chacha20));
            chacha20Records.add(generateRecords(fileChunk, udpClient, chacha20));
        }
        System.out.println("Done generating report");
    }

    public Report getAesReport()
    {
        return new Report("AES", aesRecords);
    }

    public Report getCamelliaReport()
    {
        return new Report("Camellia", camelliaRecords);
    }

    public Report getThreeDesReport()
    {
        return new Report("3DES", threeDesRecords);
    }

    public Report getChacha20Report()
    {
        return new Report("ChaCha20", chacha20Records);
    }

    private List<String> readFileAsStrings(File file)
    {
        int chunkSize = 128;
        List<String> fileChunks = new ArrayList<>();
        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file)))
        {
            byte[] buffer = new byte[chunkSize];
            while(bis.read(buffer) != -1)
            {
                String base64Chunk = Base64.getEncoder().encodeToString(buffer);
                fileChunks.add(base64Chunk);
            }
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
        }
        return fileChunks;
    }

    private Record generateRecords(String message, SocketClient socketClient, Cipher cipher)
    {
        long startTimeMs = System.currentTimeMillis();
        long encryptionTimeMs;
        long decryptionTimeMs;
        boolean isError = false;
        RecordType recordType;

        try
        {
            long encryptionTimeStartMs = System.currentTimeMillis();
            String encrypted = cipher.encrypt(message);
            long encryptionTimeStopMs = System.currentTimeMillis();
            encryptionTimeMs = encryptionTimeStopMs - encryptionTimeStartMs;
            socketClient.sendMessage(encrypted);

            String response = socketClient.listenForResponse();
            long decryptionTimeStartMs = System.currentTimeMillis();
            String decryptedMessage = cipher.decrypt(response);
            long decryptionTimeStopMs = System.currentTimeMillis();
            decryptionTimeMs = decryptionTimeStopMs - decryptionTimeStartMs;

            if(!decryptedMessage.equals(message))
            {
                isError = true;
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }

        long endTimeMs = System.currentTimeMillis();
        long tripTimeMs = endTimeMs - startTimeMs;


        if(socketClient instanceof TcpClient)
        {
            recordType = RecordType.TCP;
        }
        else
        {
            recordType = RecordType.UDP;
        }

        return new Record(tripTimeMs, encryptionTimeMs, decryptionTimeMs, isError, recordType);
    }
}
