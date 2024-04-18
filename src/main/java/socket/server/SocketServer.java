package socket.server;

public interface SocketServer
{
    String receiveMessage();

    void sendMessage(String message);
}
