package cs3343.battleship.backend;

import java.io.Closeable;

/**
 * This interface defines the methods needed for two instances of the game to
 * communicate with each other. Instead of one `sendMessageAndWairForReply()`
 * method, we decompose it into two methods for greater flexibility.
 * 
 * We intentionally omit any methods for initializing the connection because
 * not all types of backend need to do so, and their initialization mechanism
 * may be so different that it is not possible to define a common interface.
 * Thus, details about initialization, reconnecting, connection pooling, flow
 * control, etc. are left to the implementation.
 * 
 * This interface extends Closeable so that the caller can terminate the backend
 * (useful for testing).
 */
public interface Backend extends Closeable {
    /**
     * Returns whether the backend is ready to send and receive messages.
     * 
     * @return whether the backend is ready to send and receive messages
     */
    public boolean isReady();

    /**
     * Blocks until a message is received. Typically, this is when a message is sent
     * by {@link #sendMessage(Message)} from another Backend.
     * 
     * @return The message received.
     * @throws Exception
     */
    public Message waitForMessage() throws Exception;

    /**
     * Sends a message. Typically, the message is received by
     * {@link #waitForMessage()} from another Backend. Hence, if the other Backend
     * is not running {@link #waitForMessage()}, the message will be lost.
     * 
     * @param message The message to send.
     */
    public void sendMessage(Message message);
}
