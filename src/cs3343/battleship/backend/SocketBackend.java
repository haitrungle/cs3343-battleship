package cs3343.battleship.backend;

import java.io.*;

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