package socket.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UdpServer extends Thread implements SocketServer
{
    private final DatagramSocket datagramSocket;
    private InetAddress lastClientAddress;
    private int lastClientPort;

    /**
     * Start a UDP server on a given port
     *
     * @param port the port to listen for messages on
     */
    public UdpServer(int port)
    {
        try
        {
            System.out.println("Attempting to start server...");

            datagramSocket = new DatagramSocket(port);

            System.out.println("Server started on port: " + port);
        }
        catch(SocketException e)
        {
            throw new RuntimeException("Could not start server");
        }
    }

    public void run()
    {
        while(true)
        {
            String message = receiveMessage();

            if(message.equalsIgnoreCase("quit"))
            {
                shutdownServer();
                break;
            }
            else
            {
                sendResponse(message);
            }
        }
    }

    @Override
    public String receiveMessage()
    {
        try
        {
            byte[] buffer = new byte[256];
            DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);

            datagramSocket.receive(datagramPacket);

            lastClientAddress = datagramPacket.getAddress();
            lastClientPort = datagramPacket.getPort();

            return new String(datagramPacket.getData(), 0, datagramPacket.getLength());
        }
        catch(IOException e)
        {
            throw new RuntimeException("Could not listen for a message from the client");
        }
    }

    @Override
    public void sendResponse(String response)
    {
        if(lastClientAddress == null)
        {
            throw new NullPointerException("Could not send response message");
        }

        try
        {
            datagramSocket.send(new DatagramPacket(response.getBytes(), response.getBytes().length, lastClientAddress, lastClientPort));
        }
        catch(IOException e)
        {
            throw new RuntimeException("Could not send response message");
        }
    }

    @Override
    public void shutdownServer()
    {
        datagramSocket.close();
    }
}
