package cs3343.battleship.exceptions;

import cs3343.battleship.backend.Message;

public class WrongMessageTypeException extends GameException {
    public WrongMessageTypeException(Message.Type expected, Message.Type actual) {
        super("Wrong message type: expected " + expected + " but got " + actual);
    }
}
