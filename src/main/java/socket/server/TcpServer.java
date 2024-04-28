package socket.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer extends Thread implements SocketServer
{
    private final ServerSocket serverSocket;
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

    public void run()
    {
        try
        {
            System.out.println("Waiting for client...");

            clientSocket = serverSocket.accept();

            System.out.println("Client connected");

            dataInputStream = new DataInputStream(clientSocket.getInputStream());
            dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());

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
        catch(IOException e)
        {
            throw new RuntimeException("Could not start server");
        }
    }

    @Override
    public String receiveMessage()
    {
        try
        {
            sendResponse(dataInputStream.readUTF());
            return dataInputStream.readUTF();
        }
        catch(IOException e)
        {
            throw new RuntimeException("Can not listen to client");
        }
    }

    @Override
    public void sendResponse(String response)
    {
        if(clientSocket == null)
        {
            throw new IllegalStateException("Not connected to client");
        }

        try
        {
            dataOutputStream.writeUTF(response);
        }
        catch(IOException e)
        {
            throw new RuntimeException("Could not write to client");
        }
    }

    @Override
    public void shutdownServer()
    {
        System.out.println("Shutting down server...");

        try
        {
            if(clientSocket.isConnected())
            {
                clientSocket.close();
                dataInputStream.close();
            }

            serverSocket.close();
        }
        catch(IOException e)
        {
            throw new RuntimeException("Encountered an error while shutting down the server");
        }
    }
}