package app;

import app.gui.GenerateReportsPanel;
import socket.client.TcpClient;
import socket.client.UdpClient;
import socket.server.TcpServer;
import socket.server.UdpServer;

public class Main
{
    private static final String ADDRESS = "127.0.0.1";
    private static final int TCP_PORT = 5000;
    private static final int UDP_PORT = 5001;

    public static void main(String[] args)
    {
        TcpServer tcpServer = new TcpServer(TCP_PORT);
        TcpClient tcpClient = new TcpClient(ADDRESS, TCP_PORT);

        UdpServer udpServer = new UdpServer(UDP_PORT);
        UdpClient udpClient = new UdpClient(ADDRESS, UDP_PORT);

        tcpServer.start();
        tcpClient.start();

        udpServer.start();
        udpClient.start();

        try
        {
            new GenerateReportsPanel(tcpClient, udpClient);
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
