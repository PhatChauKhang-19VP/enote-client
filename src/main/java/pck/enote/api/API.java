package pck.enote.api;

import pck.enote.api.req.BaseReq;
import pck.enote.api.req.REQUEST_TYPE;
import pck.enote.api.req.SendFileReq;
import pck.enote.api.req.TestConnectionReq;
import pck.enote.api.res.BaseRes;
import pck.enote.api.res.RESPONSE_STATUS;
import pck.enote.api.res.SendFileRes;
import pck.enote.api.res.TestConnectionRes;
import pck.enote.be.Server;
import pck.enote.be.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

/**
 * API class for client
 * providing the methods that send request to server and receive its response as a map
 */
public class API {
    private static final Server server = Server.getInstance();

    public static void main(String[] args) throws IOException {
        User u = new User("phat", "123");
        System.out.println(testConnection());
    }

    public static BaseRes sendReq(BaseReq req) {

        try (
                Socket socket = new Socket(server.getIP(), server.getPort());
                DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());
                DataInputStream dataIn = new DataInputStream(socket.getInputStream())
        ) {
            REQUEST_TYPE reqType = req.getType();

            switch (reqType) {
                case TEST_CONNECTION -> {
                    TestConnectionReq testConnectionReq = (TestConnectionReq) req;

                    dataOut.writeUTF(testConnectionReq.getType().name());

                    REQUEST_TYPE resType = REQUEST_TYPE.valueOf(dataIn.readUTF());

                    if (resType != reqType){
                        return null;
                    }
                    return new TestConnectionRes(
                            RESPONSE_STATUS.valueOf(dataIn.readUTF()),
                            dataIn.readUTF()
                    );
                }
                case UPLOAD -> {
                    //* send data to server
                    SendFileReq sendFileRes = (SendFileReq) req;

                    // write type:
                    dataOut.writeUTF(sendFileRes.getType().name());

                    // write filename:
                    dataOut.writeUTF(sendFileRes.getFilename());

                    // write file buffer
                    dataOut.writeInt(sendFileRes.getBuffer().length);
                    dataOut.write(sendFileRes.getBuffer());

                    //* read data from server

                    REQUEST_TYPE resType = REQUEST_TYPE.valueOf(dataIn.readUTF());

                    if (resType != reqType){
                        return null;
                    }

                    return new SendFileRes(
                            RESPONSE_STATUS.valueOf(dataIn.readUTF()),
                            dataIn.readUTF(),
                            dataIn.readUTF()
                    );
                }
                default -> {
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    /**
     * just for testing if server is alive
     *
     * @return Server Response
     */
    public static TestConnectionRes testConnection() throws IOException {
        Socket socket = new Socket(server.getIP(), server.getPort());

        //* data to server
        DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());
        TestConnectionReq req = new TestConnectionReq();
        dataOut.writeUTF(req.getType().name());

        //* data from server
        DataInputStream dataIn = new DataInputStream(socket.getInputStream());

        //todo:
        return null;
    }

    public static SendFileRes sendFile(File file) throws IOException {
        Socket socket = new Socket(server.getIP(), server.getPort());

        //* data to server
        DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());
        SendFileReq req = new SendFileReq(file);

        SendFileRes res = (SendFileRes) sendReq(req);

        System.out.println(res);

        return res;
    }
}
