package cs3343.battleship.backend;

public interface Backend {
    public Message waitForMessage() throws Exception;
    public void sendMessage(Message message);
    public void close() throws Exception;
}
