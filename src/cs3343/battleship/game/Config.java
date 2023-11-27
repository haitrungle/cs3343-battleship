package cs3343.battleship.game;

import cs3343.battleship.logic.ship.*;

public final class Config {
    static int BOARD_SIZE = 10;

    static Ship[] defaultFleet() {
        return new Ship[] {
                new AircraftCarrier(), new Battleship(), new Cruiser(), new Submarine(), new Destroyer()
        };
    }
}
