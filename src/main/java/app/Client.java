package app;

import app.gui.GenerateReportsPanel;
import socket.client.TcpClient;
import socket.client.UdpClient;

public class Client
{
    private static final String ADDRESS = "127.0.0.1";
    private static final int TCP_PORT = 5000;
    private static final int UDP_PORT = 5001;

    public static void main(String[] args)
    {
        TcpClient tcpClient = new TcpClient(ADDRESS, TCP_PORT);
        UdpClient udpClient = new UdpClient(ADDRESS, UDP_PORT);
//
//        tcpClient.sendMessage("Test");
//        System.out.println(tcpClient.listenForResponse());
//        tcpClient.sendMessage("Test2");
//        System.out.println(tcpClient.listenForResponse());
//
//        udpClient.sendMessage("test udp");
//        System.out.println(udpClient.listenForResponse());
//
//        tcpClient.sendMessage("quit");
//        udpClient.sendMessage("quit");
//
//        tcpClient.disconnect();
//        udpClient.disconnect();

        try
        {
            new GenerateReportsPanel(tcpClient, udpClient);
//            tcpClient.sendMessage("quit");
//            udpClient.sendMessage("quit");
//            tcpClient.disconnect();
//            udpClient.disconnect();
        }
        catch(Exception e)
        {
            System.out.println("Ran into errors when starting the generate reports panel");
            System.out.println(e.getMessage());
        }
    }
}
