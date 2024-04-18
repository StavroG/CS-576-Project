package socket.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer
{
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public TcpServer(int port)
    {
        try
        {
            System.out.println("Attempting to start server...");

            serverSocket = new ServerSocket(port);

            System.out.println("Server started on port: " + port);
        }
        catch(IOException e)
        {
            throw new RuntimeException("Could not start server");
        }
    }

    /**
     * Wait for a client to connect to the server
     *
     * @throws IllegalStateException throw exception if waiting for client without server port open
     */
    public void waitForClient() throws IllegalStateException
    {
        try
        {
            System.out.println("Waiting for client...");

            clientSocket = serverSocket.accept();

            System.out.println("Client connected");

            dataInputStream = new DataInputStream(clientSocket.getInputStream());
            dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
        }
        catch(IOException e)
        {
            throw new RuntimeException("Encountered an error while waiting for client to connect");
        }
    }

    /**
     * Listen to the client and wait for a message
     *
     * @return the message from the client
     * @throws IllegalStateException throw exception if trying to listen to client before a client connects
     */
    public String listenToClient() throws IllegalStateException
    {
        if(clientSocket == null)
        {
            throw new IllegalStateException("Client is not connected yet");
        }

        try
        {
            return dataInputStream.readUTF();
        }
        catch(IOException e)
        {
            throw new RuntimeException("Can not listen to client");
        }
    }

    /**
     * Send a message to the client
     *
     * @param input the message to send to the client
     * @throws IllegalStateException throw exception if a client is not connected yet
     */
    public void sendMessage(String input) throws IllegalStateException
    {
        if(clientSocket == null)
        {
            throw new IllegalStateException("Not connected to client");
        }

        try
        {
            dataOutputStream.writeUTF(input);
        }
        catch(IOException e)
        {
            throw new RuntimeException("Could not write to client");
        }
    }

    /**
     * Disconnect the client from the server
     */
    public void disconnectClient()
    {
        System.out.println("Closing connection with client...");

        try
        {
            clientSocket.close();
            dataInputStream.close();
        }
        catch(IOException e)
        {
            throw new RuntimeException("Problem disconnecting from client");
        }
    }

    /**
     * Shutdown the server port
     */
    public void shutdownServer()
    {
        System.out.println("Shutting down server...");

        try
        {
            if(clientSocket.isConnected())
            {
                disconnectClient();
            }

            serverSocket.close();
        }
        catch(IOException e)
        {
            throw new RuntimeException("Encountered an error while closing the server socket");
        }
    }
}