package socket.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UdpServer
{
    private final DatagramSocket datagramSocket;

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

    /**
     * Listen to a UDP message from the client
     *
     * @return the data packet containing information such as: the message data, client address and client port number
     */
    public DatagramPacket listenForMessage()
    {
        try
        {
            byte[] buffer = new byte[256];
            DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);

            datagramSocket.receive(datagramPacket);

            return datagramPacket;
        }
        catch(IOException e)
        {
            throw new RuntimeException("Could not listen for a message from the client");
        }
    }

    /**
     * Send a response to the client after getting a message
     *
     * @param response   the message to send back to the client
     * @param address    the address of the client
     * @param portNumber the port number the client is listening on
     */
    public void sendResponse(String response, InetAddress address, int portNumber)
    {
        try
        {
            datagramSocket.send(new DatagramPacket(response.getBytes(), response.getBytes().length, address, portNumber));
        }
        catch(IOException e)
        {
            throw new RuntimeException("Could not send a response to the client");
        }
    }

    /**
     * Shutdown the server datagram socket when done
     */
    public void closeServer()
    {
        datagramSocket.close();
    }
}
