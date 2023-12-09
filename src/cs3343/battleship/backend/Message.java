package cs3343.battleship.backend;

import java.io.Serializable;
import java.time.Instant;

import cs3343.battleship.logic.Position;

/**
 * This class represents a message sent between two Backends. It contains the
 * neccessary information for the Battleship game to run.
 */
public final class Message implements Serializable {
    private static final long serialVersionUID = 19122003L;

    /**
     * Represents the type of a message. Since there will be different Message types
     * containing different data, it is necessary to distinguish them. using some
     * kind of tag.
     */
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

    /**
     * Returns the type of this message.
     * 
     * @return the type of this message
     */
    public Type getType() {
        return type;
    }

    /**
     * Returns the timestamp of this message.
     * 
     * @return the timestamp of this message
     */
    public Instant getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the shot position of this message, if it is a SHOT message.
     * 
     * @return the shot position of this message
     */
    public Position getShot() {
        assert type == Type.SHOT;
        return shot;
    }

    /**
     * Returns the hit result of this message, if it is a RESULT message.
     * 
     * @return the result of a previous hit
     */
    public boolean getHit() {
        assert type == Type.RESULT;
        return hit;
    }

    /**
     * Returns an initialization message, akin to a TCP handshake.
     * 
     * @return an INIT message
     */
    public static Message InitMsg() {
        return new Message(Type.INIT, null, false);
    }

    /**
     * Returns a message containing a shot position.
     * 
     * @param shot the shot position
     * @return a SHOT message
     */
    public static Message ShotMsg(Position shot) {
        return new Message(Type.SHOT, shot, false);
    }

    /**
     * Returns a message containing the result of a previous shot.
     * 
     * @param result the result of a previous shot
     * @return a RESULT message
     */
    public static Message ResultMsg(boolean result) {
        return new Message(Type.RESULT, null, result);
    }

    /**
     * Returns a message indicating that the sender has lost.
     * 
     * @return a LOST message
     */
    public static Message LostMsg() {
        return new Message(Type.LOST, null, false);
    }

    @Override
    public String toString() {
        String s = "";
        switch (type) {
            case INIT:
                s = "INIT";
                break;
            case SHOT:
                s = "SHOT(" + shot.toString() + ")";
                break;
            case RESULT:
                s = "RESULT(" + hit + ")";
                break;
            case LOST:
                s = "LOST";
                break;
        }
        return "{ timestamp: " + timestamp.toString() + ", type: " + s + " }";
    }

    @Override
    public boolean equals(Object o) {
        System.out.println(this);
        System.out.println(o);
        if (o == this)
            return true;
        if (!(o instanceof Message))
            return false;
        Message m = (Message) o;
        if (m.type != type)
            return false;
        if (!m.timestamp.equals(timestamp))
            return false;
        if (m.type == Type.SHOT && !m.shot.equals(shot))
            return false;
        if (m.type == Type.RESULT && m.hit != hit)
            return false;
        return true;
    }
}