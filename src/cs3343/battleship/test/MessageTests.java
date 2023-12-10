package cs3343.battleship.test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import cs3343.battleship.backend.Message;
import cs3343.battleship.exceptions.NullObjectException;
import cs3343.battleship.exceptions.WrongMessageTypeException;
import cs3343.battleship.logic.Position;

public class MessageTests {
    @Test
    public void initMsg_shouldHaveTypeINIT() {
        Message m = Message.InitMsg();
        assertEquals(Message.Type.INIT, m.getType());
    }

    @Test
    public void shotMsg_shouldHaveTypeSHOT() throws Exception {
        Message m = Message.ShotMsg(new Position(1, 1));
        assertEquals(Message.Type.SHOT, m.getType());
    }

    @Test
    public void shotMsgNull_shouldThrow() throws Exception {
        assertThrows(NullObjectException.class, () -> Message.ShotMsg(null));
    }

    @Test
    public void resultMsg_shouldHaveTypeResult() {
        Message m = Message.ResultMsg(false);
        assertEquals(Message.Type.RESULT, m.getType());
    }

    @Test
    public void lostMsg_shouldHaveTypeLost() {
        Message m = Message.LostMsg();
        assertEquals(Message.Type.LOST, m.getType());
    }

    static Stream<Message> messageProvider() throws Exception {
        return Stream.of(
                Message.InitMsg(),
                Message.ShotMsg(new Position(0, 0)),
                Message.ShotMsg(new Position(-2, -10)),
                Message.ResultMsg(true),
                Message.ResultMsg(false),
                Message.LostMsg()
        );
    }

    @ParameterizedTest
    @MethodSource("messageProvider")
    public void shouldHaveTimestampWithinCurrentMinute(Message msg) {
        long now = Instant.now().toEpochMilli();
        long msgTime = msg.getTimestamp().toEpochMilli();
        assertTrue(msgTime - now < 2 * 1000);
    }

    @ParameterizedTest
    @MethodSource("messageProvider")
    public void methodOnWrongType_shouldThrow(Message msg) {
        if (msg.getType() != Message.Type.SHOT)
            assertThrows(WrongMessageTypeException.class, () -> msg.getShot());
        if (msg.getType() != Message.Type.RESULT)
            assertThrows(WrongMessageTypeException.class, () -> msg.getHit());
    }

    @ParameterizedTest
    @MethodSource("messageProvider")
    public void shouldEqualToSelf_shouldNotEqualToNewMessage(Message msg) {
        Message m = Message.InitMsg();
        assertEquals(msg, msg);
        assertNotEquals(msg, m);
    }

    @Test
    public void initMessageEquals() throws Exception {
        Instant now = Instant.now();
        Message a = Message.InitMsg();
        Message b = Message.InitMsg();
        a.setTimestamp(now);
        b.setTimestamp(now);
        assertEquals(a, b);
    }

    @Test
    public void shotMessageEquals() throws Exception {
        Instant now = Instant.now();
        Message a = Message.ShotMsg(new Position(0, 0));
        Message b = Message.ShotMsg(a.getShot());
        a.setTimestamp(now);
        b.setTimestamp(now);
        assertEquals(a, b);
    }

    @Test
    public void resultMessageEquals() throws Exception {
        Instant now = Instant.now();
        Message a = Message.ResultMsg(true);
        Message b = Message.ResultMsg(a.getHit());
        a.setTimestamp(now);
        b.setTimestamp(now);
        assertEquals(a, b);
    }

    @Test
    public void lostMessageEquals() throws Exception {
        Instant now = Instant.now();
        Message a = Message.LostMsg();
        Message b = Message.LostMsg();
        a.setTimestamp(now);
        b.setTimestamp(now);
        assertEquals(a, b);
    }

    @Test
    public void shotMsgWithPosition_shouldHavePosition() throws Exception {
        Message m = Message.ShotMsg(new Position(1, 1));
        assertEquals(new Position(1, 1), m.getShot());
    }

    @Test
    public void resultMsgWithFalse_shouldHaveHitFalse() throws Exception {
        Message m = Message.ResultMsg(false);
        assertFalse(m.getHit());
    }

    @Test
    public void resultMsgWithHit_shouldHaveHit() throws Exception {
        Message m = Message.ResultMsg(true);
        assertTrue(m.getHit());
    }
}
