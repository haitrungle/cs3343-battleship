package cs3343.battleship.backend;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import cs3343.battleship.game.Config;

public class Client extends SocketBackend {
    private Socket socket;
    String host;
    int port;
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
        } catch (Exception e) {
            throw new Exception("Error initializing Client: " + e.getMessage());
        }
    }
    public void close() throws Exception {
        socket.close();
    }
}
