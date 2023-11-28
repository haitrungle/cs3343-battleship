package cs3343.battleship.backend;

import java.io.Serializable;
import java.time.Instant;

import cs3343.battleship.logic.Position;

public final class Message implements Serializable {
    public enum Type {
        INIT,
        SHOT,
        RESULT,
        LOST;
    }

    private Type type;
    private Instant timestamp;
    private Position shot;
    private boolean hit;

    private Message(Type type, Position shot, boolean hit) {
        this.type = type;
        this.timestamp = Instant.now();
        this.shot = shot;
        this.hit = hit;
    }

    public Type getType() {
        return type;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Position getShot() {
        assert type == Type.SHOT;
        return shot;
    }

    public boolean getHit() {
        assert type == Type.RESULT;
        return hit;
    }

    public static Message InitMsg() {
        return new Message(Type.INIT, null, false);
    }

    public static Message ShotMsg(Position shot) {
        return new Message(Type.SHOT, shot, false);
    }

    public static Message ResultMsg(boolean result) {
        return new Message(Type.RESULT, null, result);
    }

    public static Message LostMsg() {
        return new Message(Type.LOST, null, false);
    }
}