package cs3343.battleship.backend;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Backend {
    private ServerSocket serverSocket;
    private Socket socket;

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Backend listening on port " + port);

            socket = serverSocket.accept();
            System.out.println("Client connected: " + socket.getInetAddress().getHostAddress());

            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("Error initializing Server: " + e.getMessage());
        }
    }
}
