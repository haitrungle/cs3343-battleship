package cs3343.battleship.exceptions;

import cs3343.battleship.backend.Message;

/**
 * This class represents an exception that occurs when a method for getting data
 * from Message is called on a message with the wrong type.
 * 
 */
public class WrongMessageTypeException extends GameException {
    public WrongMessageTypeException(Message.Type expected, Message.Type actual) {
        super("Wrong message type: expected " + expected + " but got " + actual);
    }
}
