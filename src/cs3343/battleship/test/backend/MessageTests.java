package cs3343.battleship.test.backend;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.time.ZoneOffset;

import org.junit.Test;

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
        boolean b = m.getShot().equals(new Position(1, 1));
        assertTrue(b);
    }

    @Test
    public void resultMsgWithFalse_shouldHaveHitFalse() {
        Message m = Message.ResultMsg(false);
        assertFalse(m.getHit());
    }
}

// class test_backend_socket_backend{
// private SocketBackend server;
// private SocketBackend client;
// private Thread server_start;
// private final int port = 1234;
// @Before
// public void setup() throws Exception{
//
//
// server_start = new Thread(()->{
// try {
// server = new Server(port);
// } catch (Exception e) {
// throw new RuntimeException(e);
// }
//
// });
// server_start.start();
//
// client = new Client(null, port);
//
//
// }
// @After
// public void teardown() throws Exception{
// server_start.join();
// server.close();
// client.close();
//
// Thread.sleep(100);
// }
//
// @Test
// public void test_backend_socket_backend_1(){
// Message m = Message.InitMsg();
// client.sendMessage(m);
// }
//
// @Test
// public void test_backend_socket_backend_2() throws Exception {
//
// Thread client_wait_message = new Thread(()->{
// try {
// client.waitForMessage();
// } catch (Exception e) {
// assertEquals("Error reading incoming message: null" ,e.getMessage());
// }
// });
// client_wait_message.start();
// Message m = Message.InitMsg();
// client.sendMessage(m);
// }
//
// @Test
// public void test_backend_socket_backend_3() {
//
// Exception e = assertThrows(Exception.class,()->new Server(1234));
// assertEquals("Error initializing Server: Address already in use: JVM_Bind",
// e.getMessage());
// }
//
// @Test
// public void test_backend_socket_backend_4() throws Exception {
// Message m = Message.ResultMsg(true);
// client.sendMessage(m);
// Message message = server.waitForMessage();
// assertEquals(m.getType(),message.getType());
//
//
// }
// }
