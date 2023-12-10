package cs3343.battleship.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import cs3343.battleship.backend.Client;
import cs3343.battleship.backend.Message;
import cs3343.battleship.backend.Server;
import cs3343.battleship.backend.SocketBackend;
import cs3343.battleship.exceptions.BackendException;
import cs3343.battleship.logic.Position;

public class SocketBackendTests {
    private SocketBackend server;
    private SocketBackend client;
    private final int port = 1234;

    @BeforeEach
    public void setup() throws Exception {
        // Start server in a new thread since one thread cannot listen on a port and
        // connect to the same port
        new Thread(() -> {
            try {
                server = new Server(port);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }).start();
        // Polling until server is created
        while (server == null)
            Thread.sleep(30);
        client = new Client(null, port);
        // Polling until server and client are ready
        while (!server.isReady() || !client.isReady())
            Thread.sleep(30);
    }

    @AfterEach
    public void teardown() throws Exception {
        server.close();
        client.close();
    }

    static Stream<Message> messageProvider() throws Exception {
        return Stream.of(
                Message.InitMsg(),
                Message.ShotMsg(new Position(2, 3)),
                Message.ShotMsg(new Position(0, 0)),
                Message.ResultMsg(true),
                Message.ResultMsg(false),
                Message.LostMsg());
    }

    @ParameterizedTest
    @MethodSource("messageProvider")
    public void clientSendMsg_shouldBeSameWhenServerReceived(Message msg) throws Exception {
        client.sendMessage(msg);
        Message received = server.waitForMessage();
        assertEquals(msg, received);
    }

    @ParameterizedTest
    @MethodSource("messageProvider")
    public void serverSendMsg_shouldBeSameWhenClientReceived(Message msg) throws Exception {
        server.sendMessage(msg);
        Message received = client.waitForMessage();
        assertEquals(msg, received);
    }

    @ParameterizedTest
    @MethodSource("messageProvider")
    public void clientSendMsg_serverShouldReceiveSameMsg(Message msg) throws Exception {
        client.sendMessage(msg);
        Message m = server.waitForMessage();
        assertEquals(msg, m);
    }

    @ParameterizedTest
    @MethodSource("messageProvider")
    public void serverSendMsg_clientShouldReceiveSameMsg(Message msg) throws Exception {
        server.sendMessage(msg);
        Message m = client.waitForMessage();
        assertEquals(msg, m);
    }

    @Test
    public void newServerOnUsedPort_shouldFail() {
        assertThrows(BackendException.class, () -> new Server(port));
    }
}
