package pck.enote.be.model;

public class Server {
    private static Server instance;
    private String IP = "127.0.0.1";
    private int port = 7;

    Server() {

    }

    public static Server getInstance() {
        if (instance == null) {
            instance = new Server();
        }
        return instance;
    }

    public String getIP() {
        return IP;
    }

    public int getPort() {
        return port;
    }
}
