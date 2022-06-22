package pck.enote.be.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Server {
    private static Server instance;
    private String IP = "127.0.0.1";
    private int port = 7;
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

    public int getPort() {
        return port;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public DataOutputStream getDataOut() {
        return dataOut;
    }

    public void setDataOut(DataOutputStream dataOut) {
        this.dataOut = dataOut;
    }

    public DataInputStream getDataIn() {
        return dataIn;
    }

    public void setDataIn(DataInputStream dataIn) {
        this.dataIn = dataIn;
    }
}
