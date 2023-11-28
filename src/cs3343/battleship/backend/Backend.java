package cs3343.battleship.backend;

public interface Backend {
    public Message waitForMessage();
    public void sendMessage(Message message);
}
