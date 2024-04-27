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
     * Listen to a response from the server
     *
     * @return a message from the server
     */
    String listenForResponse();

    /**
     * Disconnect the client from the server
     */
    void disconnect();
}
