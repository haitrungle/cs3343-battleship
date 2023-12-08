package cs3343.battleship.backend;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import cs3343.battleship.game.Config;

/**
 * This class represents a client in a client-server architecture over a socket
 * connection.
 */
public class Client extends SocketBackend {
    private Socket socket;
    String host;
    int port;

    /**
     * Constructs a client that connects to the specified remote host and port.
     * 
     * @param remoteHost The remote host to connect to. If null, the client will
     *                   connect to "localhost".
     * @param remotePort The remote port to connect to. If null, the client will
     *                   connect to {@link Config#DEFAULT_PORT}.
     * @throws Exception
     */
    public Client(String remoteHost, int remotePort) throws Exception {
        this.host = remoteHost;
        this.port = remotePort;
        if (remoteHost == null) {
            this.host = "localhost";
            this.port = Config.DEFAULT_PORT;
        }
        try {
            socket = new Socket(this.host, this.port);
            System.out.println("Server connected: " + socket.getInetAddress().getHostAddress());
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
            ready = true;
        } catch (Exception e) {
            throw new Exception("Error initializing Client: " + e.getMessage());
        }
    }

    /**
     * Closes the socket connection.
     */
    public void close() throws IOException {
        super.close();
        socket.close();
    }
}
