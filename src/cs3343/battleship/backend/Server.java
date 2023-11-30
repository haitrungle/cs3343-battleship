package cs3343.battleship.backend;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.net.InetAddress;

public class Server extends SocketBackend {
    private ServerSocket serverSocket;
    private Socket socket;
    private static InetAddress ip;

    public int port;

    public Server(int port) {
        this.port = port;
        try {
            ip = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            System.out.println("Cannot determine the address of local host. You can only play with another person on the same machine.");
            ip = InetAddress.getLoopbackAddress();
        }
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Backend listening at " + ip + ":" + port);

            socket = serverSocket.accept();
            System.out.println("Client connected: " + socket.getInetAddress().getHostAddress());

            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("Error initializing Server: " + e.getMessage());
        }
    }

    public void close() throws Exception {
        serverSocket.close();
        socket.close();
    }
}
