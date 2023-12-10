package cs3343.battleship.backend;

import java.io.*;

import cs3343.battleship.exceptions.BackendException;

/**
 * This abstract class represents a Backend using socket connection. Currently,
 * the implementation is rather simplistic: all operation is done synchronously.
 * When SocketBackend A send a message to SocketBackend B, if B is did not call
 * waitForMessage() before the message reaches it, the message will be lost.
 */
public abstract class SocketBackend implements Backend {
    protected ObjectInputStream in;
    protected ObjectOutputStream out;
    protected boolean ready = false;

    /**
     * Returns whether the backend is ready to send and receive messages.
     * 
     * @return whether the backend is ready to send and receive messages
     */
    public boolean isReady() {
        return ready;
    }

    /**
     * Closes the backend. This method should be called when the backend is no
     * longer needed. Subclasses should call this method in their own overriding
     * implementation.
     * 
     * Technically, for socket connection, this method is not very needed, as
     * closing the underlying socket connections also closes these input and output
     * streams. However, this matters if, say, we use a BufferedInputStream to wrap
     * the input stream, and we want to release the buffer before closing the
     * connection.
     * 
     * @throws IOException if an I/O error occurs when closing the backend
     */
    public void close() throws IOException {
        if (in != null) in.close();
        if (out != null) out.close();
        ready = false;
    }

    public Message waitForMessage() throws BackendException {
        try {
            Message message = (Message) in.readObject();
            return message;
        } catch (IOException | ClassNotFoundException e) {
            throw new BackendException("Cannot receive message: " + e.getMessage());
        }
    }

    public void sendMessage(Message message) throws BackendException {
        try {
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            throw new BackendException("Cannot send message: " + e.getMessage());
        }
    }
}