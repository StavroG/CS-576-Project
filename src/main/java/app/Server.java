package app;

import socket.server.TcpServer;
import socket.server.UdpServer;

public class Server
{
    private static final int TCP_PORT = 5000;
    private static final int UDP_PORT = 5001;

    public static void main(String[] args)
    {
        TcpServer tcpServer = new TcpServer(TCP_PORT);
        UdpServer udpServer = new UdpServer(UDP_PORT);

        boolean tcpActive = true;
        boolean udpActive = true;

        while(tcpActive || udpActive)
        {
            String tcpMessage = tcpServer.receiveMessage();

            if(tcpActive)
            {
                if(tcpMessage.equals("quit"))
                {
                    tcpActive = false;
                }
                else
                {
                    tcpServer.sendResponse(tcpMessage);
                }
            }

            if(udpActive)
            {
                String udpMessage = udpServer.receiveMessage();

                if(udpMessage.equals("quit"))
                {
                    udpActive = false;
                }
                else
                {
                    udpServer.sendResponse(udpMessage);
                }
            }
        }

//        tcpServer.shutdownServer();
//        udpServer.shutdownServer();

//
//        String message = tcpServer.receiveMessage();
//        System.out.println(message);
//        tcpServer.sendResponse();
//        tcpServer.shutdownServer();
//
//        String udpMessage = udpServer.receiveMessage();
//        System.out.println(udpMessage);
//        udpServer.sendResponse();
//        udpServer.shutdownServer();
    }
}
