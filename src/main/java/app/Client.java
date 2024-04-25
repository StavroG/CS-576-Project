package app;

import app.gui.GenerateReportsPanel;
import socket.client.TcpClient;
import socket.client.UdpClient;

public class Client
{
    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 5000;

    public static void main(String[] args)
    {
        TcpClient tcpClient = new TcpClient(ADDRESS, PORT);
        UdpClient udpClient = new UdpClient(ADDRESS, PORT);

        try
        {
            new GenerateReportsPanel(tcpClient, udpClient);
        }
        catch(Exception e)
        {
            System.out.println("Ran into errors when starting the generate reports panel");
            System.out.println(e.getMessage());
        }
    }
}
