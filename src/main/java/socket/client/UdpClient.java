package socket.client;

import java.io.IOException;
import java.net.*;

public class UdpClient implements SocketClient
{
    private final DatagramSocket datagramSocket;
    private final int portNumber;
    private final InetAddress address;

    /**
     * Initialize the client with a port number and an address
     *
     * @param address    the address of the server
     * @param portNumber the port number the server is listening on
     */
    public UdpClient(String address, int portNumber)
    {
        try
        {
            System.out.println("Attempting to connect to server...");

            datagramSocket = new DatagramSocket();

            System.out.println("Connected to server at address: " + address + ":" + portNumber);

            this.portNumber = portNumber;
            this.address = InetAddress.getByName(address);
        }
        catch(SocketException | UnknownHostException e)
        {
            throw new RuntimeException("Could not connect to server");
        }
    }

    @Override
    public void sendMessage(String message)
    {
        try
        {
            datagramSocket.send(new DatagramPacket(message.getBytes(), message.getBytes().length, address, portNumber));
        }
        catch(IOException e)
        {
            throw new RuntimeException("Could not send a message to the server");
        }
    }

    @Override
    public void disconnect()
    {
        datagramSocket.close();
    }


}
