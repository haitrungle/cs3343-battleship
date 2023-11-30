package cs3343.battleship.test;


import cs3343.battleship.exceptions.*;
import cs3343.battleship.game.Console;
import cs3343.battleship.logic.Board;
import cs3343.battleship.logic.Direction;
import cs3343.battleship.logic.Position;
import cs3343.battleship.logic.ship.*;
import org.junit.Test;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertThrows;
import static org.testng.AssertJUnit.*;
public class test_battleship_logic {
    @Test
    public void test_direction_down_1() throws Exception {
        Direction dir = Direction.decode("D");
        assertEquals(Direction.DOWN, dir);
    }
    @Test
    public void test_direction_down_2() throws Exception {
        Direction dir = Direction.decode("d");
        assertEquals(Direction.DOWN, dir);
    }
    @Test
    public void test_direction_down_3() throws Exception {
        Direction dir = Direction.decode("down");
        assertEquals(Direction.DOWN, dir);
    }
    @Test
    public void test_direction_right_1() throws Exception {
        Direction dir = Direction.decode("R");
        assertEquals(Direction.RIGHT, dir);
    }
    @Test
    public void test_direction_right_2() throws Exception {
        Direction dir = Direction.decode("r");
        assertEquals(Direction.RIGHT, dir);
    }
    @Test
    public void test_direction_right_3() throws Exception {
        Direction dir = Direction.decode("right");
        assertEquals(Direction.RIGHT, dir);
    }
    @Test
    public void test_direction_exception_1() throws Exception {
        Throwable exc = assertThrows(InvalidInputException.class, () -> Direction.decode("other"));
        assertEquals(Console.textColor("Invalid input: ", Console.RED) + "Direction can only be 'd'/'down' or 'r'/'right'.", exc.getMessage());
    }

    @Test
    public void test_direction_random() throws Exception {
        Direction d = Direction.random(new Random());
        assertEquals(d, d);
    }

    @Test
    public void test_position_to_string_1()  {
        Position pos = new Position(4, 5);
        assertEquals("4,5", pos.toString());
    }

    @Test
    public void test_position_to_string_2()  {
        Position pos =  Position.random(new Random(), 1);
        assertEquals("0,0", pos.toString());
    }
    @Test
    public void test_position_equals_1()  {
        Position pos =  Position.random(new Random(), 1);
        boolean b = pos.equals(null);
        assertEquals(false, b);
    }

    @Test
    public void test_position_equals_2()  {
        Position pos =  Position.random(new Random(), 1);
        boolean b = pos.equals( Direction.random(new Random()));
        assertEquals(false, b);
    }

    @Test
    public void test_position_equals_3()  {
        Position pos =  Position.random(new Random(), 1);
        boolean b = pos.equals( Position.random(new Random(), 1));
        assertEquals(true, b);
    }

    @Test
    public void test_ship_get_name_1(){
        Ship s = new AircraftCarrier();
        assertEquals("Aircraft Carrier", s.getName());
    }

    @Test
    public void test_ship_get_name_2() throws InvalidInputException {
        Ship s = new AircraftCarrier(Direction.decode("d"), new Position(1,1));
        assertEquals("Aircraft Carrier", s.getName());
    }

    @Test
    public void test_ship_get_length_1() throws InvalidInputException {
        Ship s = new AircraftCarrier(Direction.decode("d"), new Position(1,1));
        assertEquals(5, s.getLength());
    }

    @Test
    public void test_ship_get_direction_1() throws InvalidInputException {
        Direction dir = Direction.decode("d");
        Ship s = new AircraftCarrier(dir, new Position(1,1));
        assertEquals(dir, s.getDirection());
    }

    @Test
    public void test_ship_get_start_position_1() throws InvalidInputException {
        Direction dir = Direction.decode("d");
        Position p = new Position(1,1);
        Ship s = new AircraftCarrier(dir, p);
        assertEquals(p, s.getStartPosition());
    }

    @Test
    public void test_ship_set_direction_1() throws InvalidInputException {
        Direction dir = Direction.decode("r");
        Position p = new Position(1,1);
        Ship s = new AircraftCarrier(Direction.decode("d"), p);
        s.setDirection(dir);
        assertEquals(dir, s.getDirection());
    }

    @Test
    public void test_ship_set_start_position_1() throws InvalidInputException {

        Position p = new Position(1,1);
        Ship s = new AircraftCarrier(Direction.decode("d"), new Position(1,1));
        s.setStartPosition(p);
        assertEquals(p, s.getStartPosition());
    }
    @Test
    public void test_ship_introduce_1() throws InvalidInputException {


        Ship s = new AircraftCarrier(Direction.decode("d"), new Position(1,1));

        assertEquals("Aircraft Carrier" + " (length " + 5 + ")", s.introduce());
    }
    @Test
    public void test_ship_positions_1() throws InvalidInputException {


        Ship s = new AircraftCarrier(Direction.decode("d"), new Position(1,1));
        List<Position> positions = s.positions();
        for(int i = 0; i < 5;i++){
            boolean b = positions.get(i).equals(new Position(1+i,1));
            assertTrue(b);
        }
    }
    @Test
    public void test_ship_positions_2() throws InvalidInputException {

        Ship s = new AircraftCarrier(Direction.decode("r"), new Position(1,1));
        List<Position> positions = s.positions();
        for(int i = 0; i < 5;i++){
            boolean b = positions.get(i).equals(new Position(1,1+i));
            assertTrue(b);
        }
    }

    @Test
    public void test_ship_overlap_position_1() throws InvalidInputException {

        Ship s = new AircraftCarrier(Direction.decode("r"), new Position(1,1));
        Ship s1 = new AircraftCarrier(Direction.decode("r"), new Position(1,1));
        Position p = Ship.overlapPosition(s,s1);
        boolean b = p.equals(new Position(1,1));
        assertTrue(b);
    }
    @Test
    public void test_ship_overlap_position_2() throws InvalidInputException {

        Ship s = new AircraftCarrier(Direction.decode("r"), new Position(1,1));
        Ship s1 = new AircraftCarrier(Direction.decode("r"), new Position(2,1));
        Position p = Ship.overlapPosition(s,s1);
        assertNull(p);
    }

    @Test
    public void test_ship_1() throws InvalidInputException {

        Ship s = new AircraftCarrier(Direction.decode("r"),  new Position(1,1));
        assertEquals("Aircraft Carrier", s.getName());
        assertEquals(5,s.getLength());
    }
    @Test
    public void test_ship_2() throws InvalidInputException {

        Ship s = new Submarine(Direction.decode("r"),  new Position(1,1));
        assertEquals("Submarine", s.getName());
        assertEquals(3,s.getLength());
    }
    @Test
    public void test_ship_3() throws InvalidInputException {

        Ship s = new Submarine();
        assertEquals("Submarine", s.getName());
        assertEquals(3,s.getLength());
    }
    @Test
    public void test_ship_4() throws InvalidInputException {

        Ship s = new Destroyer(Direction.decode("r"),  new Position(1,1));
        assertEquals("Destroyer", s.getName());
        assertEquals(2,s.getLength());
    }
    @Test
    public void test_ship_5() throws InvalidInputException {

        Ship s = new Destroyer();
        assertEquals("Destroyer", s.getName());
        assertEquals(2,s.getLength());
    }
    @Test
    public void test_ship_6() throws InvalidInputException {

        Ship s = new Cruiser(Direction.decode("r"),  new Position(1,1));
        assertEquals("Cruiser", s.getName());
        assertEquals(3,s.getLength());
    }
    @Test
    public void test_ship_7() throws InvalidInputException {

        Ship s = new Cruiser();
        assertEquals("Cruiser", s.getName());
        assertEquals(3,s.getLength());
    }

    @Test
    public void test_ship_8() throws InvalidInputException {

        Ship s = new Battleship(Direction.decode("r"),  new Position(1,1));
        assertEquals("Battleship", s.getName());
        assertEquals(4,s.getLength());
    }
    @Test
    public void test_ship_9() throws InvalidInputException {

        Ship s = new Battleship();
        assertEquals("Battleship", s.getName());
        assertEquals(4,s.getLength());
    }


    @Test
    public void test_board_to_string_1() throws Exception {
        Board.State state = Board.State.HIT;
        assertEquals("×", state.toString());
    }
    @Test
    public void test_board_to_string_2() throws Exception {
        Board.State state = Board.State.MISS;
        assertEquals("⋆", state.toString());
    }
    @Test
    public void test_board_to_string_3() throws Exception {
        Board.State state = Board.State.SHIP;
        assertEquals("□", state.toString());
    }
    @Test
    public void test_board_to_string_4() throws Exception {
        Board.State state = Board.State.WATER;
        assertEquals("~", state.toString());
    }
    @Test
    public void test_board_set_state_1() throws Exception {
        Board test = new Board(9);
        test.setState(new Position(1,1), Board.State.MISS);
        assertEquals(Board.State.MISS, test.getState(1,1));
    }

    @Test
    public void test_board_set_state_exception_1() throws Exception {
        Board test = new Board(9);
        Exception e = assertThrows(Exception.class, ()-> test.setState(null, Board.State.MISS));
        assertEquals("Position is not exist.", e.getMessage());
    }

    @Test
    public void test_board_set_state_exception_2() throws Exception {
        Board test = new Board(9);
        Exception e = assertThrows(Exception.class, ()-> test.setState(new Position(1,1), null));
        assertEquals("State is not exist.", e.getMessage());
    }

    @Test
    public void test_board_set_state_exception_3() throws Exception {
        Board test = new Board(1);
        Exception e = assertThrows(Exception.class, ()-> test.setState(new Position(1,1), Board.State.MISS));
        assertEquals(Console.textColor("Invalid input: ", Console.RED) +"Position (" + 1 + "," + 1 + ") is out of bounds. Row and column must be between 0 and " + (1 - 1) + ".", e.getMessage());
    }




    @Test
    public void test_board_add_ship_1() throws Exception {
        Ship s = new Battleship(Direction.decode("r"),  new Position(1,1));
        Board test = new Board(9);
        test.addShip(s);
        List<Position> positions = s.positions();
        for(int i = 0; i < 4; i++){
            boolean b = positions.get(i).equals(new Position(1,1+i));
            assertTrue(b);
        }

    }

    @Test
    public void test_board_add_ship_2() throws Exception {
        Ship s = new Battleship(Direction.decode("d"),  new Position(1,1));
        Board test = new Board(9);
        test.addShip(s);
        List<Position> positions = s.positions();
        for(int i = 0; i < 4; i++){
            boolean b = positions.get(i).equals(new Position(1+i,1));
            assertTrue(b);
        }
    }

    @Test
    public void test_board_add_ship_exception_1() throws Exception {
        Ship s = new Battleship();
        Board test = new Board(1);
        Exception e = assertThrows(Exception.class, ()-> test.addShip(s));
        assertEquals("Direction is not exist", e.getMessage());
    }

    @Test
    public void test_board_add_ship_exception_2() throws Exception {
        Ship s = new Battleship();
        s.setDirection(Direction.DOWN);
        Board test = new Board(1);
        Exception e = assertThrows(Exception.class, ()-> test.addShip(s));
        assertEquals("Position is not exist", e.getMessage());
    }
    @Test
    public void test_board_add_ship_exception_3() throws Exception {
        Ship s = new Battleship(Direction.decode("r"),  new Position(1,1));;
        Board test = new Board(1);
        Exception e = assertThrows(Exception.class, ()-> test.addShip(s));
        assertEquals(Console.textColor("Invalid input: ", Console.RED) +"Position (" + 1 + "," + 1 + ") is out of bounds. Row and column must be between 0 and " + (1 - 1) + ".", e.getMessage());
    }

    @Test
    public void test_board_add_ship_exception_4() throws Exception {
        Ship s = new Battleship(Direction.decode("r"),  new Position(1,1));;
        Board test = new Board(3);
        Exception e = assertThrows(Exception.class, ()-> test.addShip(s));
        assertEquals(Console.textColor("Invalid input: ", Console.RED) +"Position (" + 1 + "," + 4 + ") is out of bounds. Row and column must be between 0 and " + (3 - 1) + ".", e.getMessage());
    }

    @Test
    public void test_board_add_ship_exception_5() throws Exception {
        Ship s = new Battleship(Direction.decode("d"),  new Position(1,1));;
        Board test = new Board(3);
        Exception e = assertThrows(Exception.class, ()-> test.addShip(s));
        assertEquals(Console.textColor("Invalid input: ", Console.RED) +"Position (" + 4 + "," + 1 + ") is out of bounds. Row and column must be between 0 and " + (3 - 1) + ".", e.getMessage());
    }
    @Test
    public void test_board_add_ship_exception_6() throws Exception {
        Ship s = new Battleship(Direction.decode("r"),  new Position(1,1));;
        Board test = new Board(9);
        test.addShip(s);
        Exception e = assertThrows(Exception.class, ()-> test.addShip(s));
        assertEquals(Console.textColor("Invalid input: ", Console.RED) +"There is an overlapping ship at " + "1,1" + ". Please choose another location for your ship.", e.getMessage());
    }
    @Test
    public void test_board_add_ship_exception_7() throws Exception {
        Ship s = new Battleship(Direction.decode("d"),  new Position(1,1));;
        Board test = new Board(9);
        test.addShip(s);
        Exception e = assertThrows(Exception.class, ()-> test.addShip(s));
        assertEquals(Console.textColor("Invalid input: ", Console.RED) +"There is an overlapping ship at " + "1,1" + ". Please choose another location for your ship.", e.getMessage());
    }
    @Test
    public void test_board_add_shot_1() throws Exception {
        Position shot = new Position(1,1);
        Board test = new Board(9);
        boolean b = test.addShot(shot);
        assertFalse(b);
        }

    @Test
    public void test_board_add_shot_2() throws Exception {
        Ship s = new Battleship(Direction.decode("d"),  new Position(1,1));;
        Position shot = new Position(1,1);
        Board test = new Board(9);
        test.addShip(s);
        boolean b = test.addShot(shot);
        assertTrue(b);
    }
    @Test
    public void test_board_add_shot_exception_1() throws Exception {
        Ship s = new Battleship(Direction.decode("d"),  new Position(1,1));;
        Position shot = new Position(1,1);
        Board test = new Board(9);
        test.addShip(s);
        test.addShot(shot);
        Exception e = assertThrows(Exception.class, ()-> test.addShot(shot));
        assertEquals(Console.textColor("Invalid input: ", Console.RED) + "You have already shot at " + "1,1" + ". Please select another position.",e.getMessage());
    }

    @Test
    public void test_board_add_shot_exception_2() throws Exception {


        Board test = new Board(9);

        Exception e = assertThrows(Exception.class, ()-> test.addShot(null));
        assertEquals("Shot is not exist.",e.getMessage());
    }



    @Test
    public void test_board_has_alive_ship_1(){
        Board test = new Board(5);
        boolean b = test.hasAliveShip();
        assertFalse(b);
    }

    @Test
    public void test_board_has_alive_ship_2() throws OverlapShipException, DirectionIsNotExistException, PositionOutOfBoundsException, PositionIsNotExistException {
        Board test = new Board(5);
        Ship s = new Submarine(Direction.DOWN, new Position(1,1));
        test.addShip(s);
        boolean b = test.hasAliveShip();
        assertTrue(b);
    }


}
