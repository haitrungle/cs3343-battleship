package cs3343.battleship.backend;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import cs3343.battleship.game.Config;

public class Client extends SocketBackend {
    private Socket socket;

    public Client(String remoteHost, int remotePort) {
        if (remoteHost == null) {
            remoteHost = "localhost";
            remotePort = Config.DEFAULT_PORT;
        }
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
