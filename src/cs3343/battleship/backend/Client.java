package cs3343.battleship.backend;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client extends Backend {
    private Socket socket;

    public Client(String remoteHost, int remotePort) {
        try {
            socket = new Socket(remoteHost, remotePort);
            System.out.println("Server connected: " + socket.getInetAddress().getHostAddress());
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) {
            System.out.println("Error initializing Client: " + e.getMessage());
        }
    }
}
