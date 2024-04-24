package socket.server;

public interface SocketServer
{
    /**
     * Listen for messages from any clients
     */
    String receiveMessage();

    /**
     * Shutdown the server
     */
    void shutdownServer();
}
