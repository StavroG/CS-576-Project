package org.example;

import org.example.network.TcpServer;

public class Main
{
    private static final int PORT_NUMBER = 5000;

    public static void main(String[] args)
    {
        TcpServer tcpServer = new TcpServer();

        try
        {
            tcpServer.startServer(PORT_NUMBER);
            tcpServer.waitForClient();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }

        while(true)
        {
            try
            {
                String input = tcpServer.listenToClient();

                if(input.equals("quit"))
                {
                    tcpServer.disconnectFromClient();
                    tcpServer.shutdownServer();
                    break;
                }
                else
                {
                    tcpServer.sendMessage(input);
                }
            }
            catch(Exception e)
            {
                System.out.println("An error occurred, disconnecting from client");
                System.out.println("Error message: " + e.getMessage());
                tcpServer.disconnectFromClient();
                tcpServer.waitForClient();
            }
        }
    }


}