package socket.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TcpClient extends Thread implements SocketClient
{
    private final Socket serverSocket;
    private final DataInputStream dataInputStream;
    private final DataOutputStream dataOutputStream;

    /**
     * Initialize the client with a given address of the network a server is on and
     * the port that the server is listening to
     *
     * @param address    the network address the server is on
     * @param portNumber the port that the server is listening to
     */
    public TcpClient(String address, int portNumber)
    {
        try
        {
            System.out.println("Attempting to connect to server...");

            serverSocket = new Socket(address, portNumber);

            System.out.println("Connected to server at address: " + address + ":" + portNumber);

            dataOutputStream = new DataOutputStream(serverSocket.getOutputStream());
            dataInputStream = new DataInputStream(serverSocket.getInputStream());
        }
        catch(IOException e)
        {
            throw new RuntimeException("Could not connect to server");
        }
    }

    @Override
    public void sendMessage(String input)
    {
        if(serverSocket == null)
        {
            throw new IllegalStateException("Not connected to server");
        }

        try
        {
            if(serverSocket.isConnected())
            {
                dataOutputStream.writeUTF(input);
            }
            else
            {
                System.out.println("Server socket not open");
            }
        }
        catch(IOException e)
        {
            throw new RuntimeException("Could not write to server");
        }
    }

    @Override
    public String listenForResponse()
    {
        if(serverSocket == null)
        {
            throw new IllegalStateException("Not connected to a server");
        }

        try
        {
            return dataInputStream.readUTF();
        }
        catch(IOException e)
        {
            throw new RuntimeException("Can not listen to server");
        }
    }

    @Override
    public void disconnect()
    {
        System.out.println("Disconnecting from server...");

        try
        {
            dataOutputStream.close();
            serverSocket.close();
        }
        catch(IOException e)
        {
            throw new RuntimeException("Problem disconnecting from server");
        }
    }
}
