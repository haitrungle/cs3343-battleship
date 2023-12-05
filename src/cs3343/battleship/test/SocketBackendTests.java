package cs3343.battleship.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cs3343.battleship.backend.Client;
import cs3343.battleship.backend.Message;
import cs3343.battleship.backend.Server;
import cs3343.battleship.backend.SocketBackend;

public class SocketBackendTests {
    private SocketBackend server;
    private SocketBackend client;
    private Thread server_start;
    private final int port = 1234;

    @Before
    public void setup() throws Exception {
        server_start = new Thread(() -> {
            try {
                server = new Server(port);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        server_start.start();

        client = new Client(null, port);
    }

    @After
    public void teardown() throws Exception {
        server_start.join();
        server.close();
        client.close();

        Thread.sleep(100);
    }

    @Test
    public void sendInitMsg() {
        Message m = Message.InitMsg();
        client.sendMessage(m);
    }

    @Test
    public void waitMsg_thenSendInitMsg() throws Exception {
        Thread client_wait_message = new Thread(() -> {
            try {
                client.waitForMessage();
            } catch (Exception e) {
                assertEquals("Error reading incoming message: null", e.getMessage());
            }
        });
        client_wait_message.start();
        Message m = Message.InitMsg();
        client.sendMessage(m);
    }

    @Test
    public void newServerOnUsedPort_shouldFail() {
        Exception e = assertThrows(Exception.class, () -> new Server(1234));
        assertEquals("Error initializing Server: Address already in use: JVM_Bind", e.getMessage());
    }

    @Test
    public void clientSendResultMsg_serverShouldReceiveResultMsg() throws Exception {
        Message m = Message.ResultMsg(true);
        client.sendMessage(m);
        Message message = server.waitForMessage();
        assertEquals(m.getType(), message.getType());
    }
}
