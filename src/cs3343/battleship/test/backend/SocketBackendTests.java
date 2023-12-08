package cs3343.battleship.test.backend;

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
    private Thread serverThread;
    private final int port = 1234;

    @Before
    public void setup() throws Exception {
        Thread.sleep(200);
        serverThread = new Thread(() -> {
            try {
                server = new Server(port);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        serverThread.start();

        Thread.sleep(200);
        client = new Client(null, port);
    }

    @After
    public void teardown() throws Exception {
        serverThread.join();
        server.close();
        client.close();

        Thread.sleep(200);
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
        Exception e = assertThrows(Exception.class, () -> new Server(port));
        assertEquals("Error initializing Server: Address already in use", e.getMessage());
    }

    @Test
    public void clientSendResultMsg_serverShouldReceiveResultMsg() throws Exception {
        Message m = Message.ResultMsg(true);
        client.sendMessage(m);
        Message message = server.waitForMessage();
        assertEquals(m.getType(), message.getType());
    }
}
