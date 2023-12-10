package cs3343.battleship.backend;

import java.io.Closeable;

import cs3343.battleship.exceptions.BackendException;

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
     * Returns the latest received message. This method blocks until a message is
     * received, typically when a message is sent by {@link #sendMessage(Message)}
     * from another Backend.
     * 
     * @return The received message.
     * @throws BackendException if the message cannot be received
     */
    public Message waitForMessage() throws BackendException;

    /**
     * Sends a message. Typically, the message will be received by a blocking call
     * to {@link #waitForMessage()} from another Backend. Otherwise, the message
     * might get lost.
     * 
     * @param message The message to send.
     * @throws BackendException if the message cannot be sent
     */
    public void sendMessage(Message message) throws BackendException;
}
