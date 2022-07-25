package pck.enote.be.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Server {
    private static Server instance;
    private String IP = "127.0.0.1";
    private int port = 7777;
    private Socket socket = null;
    private DataOutputStream dataOut = null;
    private DataInputStream dataIn = null;

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

    public void setIP(String IP) {
        this.IP = IP;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Socket getSocket() {
        return socket;
    }

    public DataOutputStream getDataOut() {
        return dataOut;
    }

    public DataInputStream getDataIn() {
        return dataIn;
    }

    public boolean createConnection() {
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(IP, port), 2 * 1000);
            dataIn = new DataInputStream(socket.getInputStream());
            dataOut = new DataOutputStream(socket.getOutputStream());

            return true;
        } catch (IOException e) {
            return false;
        }
    }
}