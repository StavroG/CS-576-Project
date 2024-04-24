package socket.client;

public interface SocketClient
{
    /**
     * Send a message to the server
     *
     * @param message the message to send to the server
     */
    void sendMessage(String message);

    /**
     * Disconnect the client from the server
     */
    void disconnect();
}
