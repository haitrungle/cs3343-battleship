package cs3343.battleship.backend;

import java.io.*;

/**
 * This abstract class represents a Backend using socket connection. Currently,
 * the implementation is rather simplistic: all operation is done synchronously.
 * When SocketBackend A send a message to SocketBackend B, if B is did not call
 * waitForMessage() before the message reaches it, the message will be lost.
 */
public abstract class SocketBackend implements Backend {
    protected ObjectInputStream in;
    protected ObjectOutputStream out;

    public Message waitForMessage() throws Exception {
        try {
            Message message = (Message) in.readObject();
            return message;
        } catch (IOException e) {
            throw new Exception("Error reading incoming message: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new Exception("Error reading incoming message: " + e.getMessage());
        }
    }

    public void sendMessage(Message message) {
        try {
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            System.out.println("Error sending message to remote host: " + e.getMessage());
        }
    }
}