package socket.server;

public interface SocketServer
{
    /**
     * Listen for messages from any clients
     */
    String receiveMessage();

    /**
     * Send a response message to the server, confirming you got the message
     *
     * @param response the message to send back to the server
     */
    void sendResponse(String response);

    /**
     * Shutdown the server
     */
    void shutdownServer();
}
