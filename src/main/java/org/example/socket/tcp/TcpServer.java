package org.example.socket.tcp;

public class TcpServer
{
    private static final int PORT_NUMBER = 5000;

    public static void main(String[] args)
    {
        org.example.network.TcpServer tcpServer = new org.example.network.TcpServer();

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