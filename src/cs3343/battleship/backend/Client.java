package cs3343.battleship.backend;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import cs3343.battleship.exceptions.BackendException;
import cs3343.battleship.game.Config;

/**
 * This class represents a client in a client-server architecture over a socket
 * connection.
 */
public class Client extends SocketBackend {
    private Socket socket;
    String remoteHost;
    int remotePort;

    /**
     * Constructs a client that connects to the specified remote host and port.
     * 
     * @param remoteHost The remote host to connect to. If null, the client will
     *                   connect to "localhost".
     * @param remotePort The remote port to connect to. If null, the client will
     *                   connect to {@link Config#DEFAULT_PORT}.
     * @throws BackendException if the client or the I/O streams cannot be
     *                          initialized
     */
    public Client(String remoteHost, int remotePort) throws BackendException {
        this.remoteHost = remoteHost;
        this.remotePort = remotePort;
        if (remoteHost == null) {
            this.remoteHost = "localhost";
        }
        try {
            socket = new Socket(this.remoteHost, this.remotePort);
            System.out.println("Server connected: " + socket.getInetAddress().getHostAddress());
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
            ready = true;
        } catch (IOException e) {
            throw new BackendException("Cannot initialize Client: " + e.getMessage());
        }
    }

    /**
     * Closes the socket connection.
     * 
     * @throws IOException if an I/O error occurs when closing the socket
     */
    public void close() throws IOException {
        super.close();
        if (socket != null)
            socket.close();
    }
}
