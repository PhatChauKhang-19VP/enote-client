package pck.enote.api;

import pck.enote.api.req.SendFileReq;
import pck.enote.api.req.TestConnectionReq;
import pck.enote.api.res.SendFileRes;
import pck.enote.api.res.TestConnectionRes;
import pck.enote.be.Server;
import pck.enote.be.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

public class API {
    private static final Server server = Server.getInstance();

    public static void main(String[] args) throws IOException {
        User u = new User("phat", "123");
        System.out.println(testConnection());
    }

    public static TestConnectionRes testConnection() throws IOException {
        Socket socket = new Socket(server.getIP(), server.getPort());
        DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());
        TestConnectionReq req = new TestConnectionReq();
        dataOut.writeUTF(req.getPackedReq());

        DataInputStream dataIn = new DataInputStream(socket.getInputStream());

        String packedRes = dataIn.readUTF();
        return new TestConnectionRes(packedRes);
    }

    public static SendFileRes sendFile(File file) throws IOException {
        Socket socket = new Socket(server.getIP(), server.getPort());
        DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());
        SendFileReq req = new SendFileReq(file);
        dataOut.writeUTF(req.getPackedReq());

        DataInputStream dataIn = new DataInputStream(socket.getInputStream());

        String packedRes = dataIn.readUTF();
        return new SendFileRes(packedRes);
    }

//    public static HashMap<String, String> login(User user) throws IOException {
//        socket = new Socket(server.getIP(), server.getPort());
//        DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());
//
//        String packedClientReq = Request.login(user);
//        System.out.println(packedClientReq);
//        dataOut.writeUTF(packedClientReq);
//
//        DataInputStream dataIn = new DataInputStream(socket.getInputStream());
//
//        String packedServerRes = dataIn.readUTF();
//
//        return StructClass.unpack(packedServerRes);
//    }
}
