package cs3343.battleship.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.Instant;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

import cs3343.battleship.backend.Message;
import cs3343.battleship.logic.Position;

public class MessageTests {
    @Test
    public void initMsg_shouldHaveTypeINIT() {
        Message m = Message.InitMsg();
        assertEquals(Message.Type.INIT, m.getType());
    }

    @Test
    public void shotMsg_shouldHaveTypeSHOT() {
        Message m = Message.ShotMsg(new Position(1, 1));
        assertEquals(Message.Type.SHOT, m.getType());
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

    @Test
    public void shouldHaveTimestampWithinCurrentMinute() {
        Message m = Message.LostMsg();
        int cur_min = Instant.now().atZone(ZoneOffset.UTC).getMinute();
        assertEquals(cur_min, m.getTimestamp().atZone(ZoneOffset.UTC).getMinute());
    }

    @Test
    public void shotMsgWithPosition_shouldHavePosition() {
        Message m = Message.ShotMsg(new Position(1, 1));
        assertEquals(new Position(1, 1), m.getShot());
    }

    @Test
    public void resultMsgWithFalse_shouldHaveHitFalse() {
        Message m = Message.ResultMsg(false);
        assertFalse(m.getHit());
    }

    @Test
    public void resultMsgWithHit_shouldHaveHit() {
        Message m = Message.ResultMsg(true);
        assertEquals(true, m.getHit());
    }
}
