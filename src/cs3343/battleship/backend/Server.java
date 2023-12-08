package cs3343.battleship.backend;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * This class represents a server in a client-server architecture over a socket
 * connection.
 */
public class Server extends SocketBackend {
    private ServerSocket serverSocket;
    private Socket socket;
    private static InetAddress ip;
    private int port;

    /**
     * Constructs a server that listens on the specified port. This constructor
     * spawns a thread to wait until a client to connect.
     * 
     * @param port The port to listen on.
     * @throws Exception
     */
    public Server(int port) throws Exception {
        this.port = port;
        try {
            ip = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            ip = InetAddress.getLoopbackAddress();
            throw new Exception(
                    "Cannot determine the address of local host. You can only play with another person on the same machine.");
        }
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Backend listening at " + ip + ":" + port);

            Thread thread = new Thread(() -> {
                try {
                    socket = serverSocket.accept();
                    System.out.println("Client connected: " + socket.getInetAddress().getHostAddress());
                    out = new ObjectOutputStream(socket.getOutputStream());
                    out.flush();
                    in = new ObjectInputStream(socket.getInputStream());
                    ready = true;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            thread.setUncaughtExceptionHandler((t, e) -> {
                System.out.println("Error accepting connection: " + e.getMessage());
            });
            thread.start();
        } catch (IOException e) {
            throw new Exception("Error initializing Server: " + e.getMessage());
        }
    }

    /**
     * Closes the socket connection.
     */
    public void close() throws IOException {
        super.close();
        serverSocket.close();
        socket.close();
    }
}
