package cs3343.battleship.test.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import cs3343.battleship.backend.Client;
import cs3343.battleship.backend.Message;
import cs3343.battleship.backend.Server;
import cs3343.battleship.backend.SocketBackend;
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

    @ParameterizedTest
    @ArgumentsSource(MessageArgumentsProvider.class)
    public void clientSendMsg_shouldBeSameWhenServerReceived(Message msg) {
        client.sendMessage(msg);
        Message received = server.waitForMessage();
        assertEquals(msg, received);
    }

    @ParameterizedTest
    @ArgumentsSource(MessageArgumentsProvider.class)
    public void serverSendMsg_shouldBeSameWhenClientReceived(Message msg) {
        server.sendMessage(msg);
        Message received = client.waitForMessage();
        assertEquals(msg, received);
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

class MessageArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Message.InitMsg(),
                Message.ShotMsg(new Position(2, 3)),
                Message.ShotMsg(new Position(0, 0)),
                Message.ResultMsg(true),
                Message.ResultMsg(false),
                Message.LostMsg()).map(Arguments::of);
    }
}