package cs3343.battleship.backend;

import java.io.Closeable;

public interface Backend extends Closeable {
    public Message waitForMessage() throws Exception;
    public void sendMessage(Message message);
}
