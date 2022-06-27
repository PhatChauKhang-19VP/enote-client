package pck.enote.api;

import pck.enote.api.req.*;
import pck.enote.api.res.*;
import pck.enote.be.model.Server;
import pck.enote.fe.model.Note;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

import static javafx.application.Platform.exit;

/**
 * API class for client
 * providing the methods that send request to server and receive its response as a map
 */
public class API {
    private static final Server server = Server.getInstance();
    static DataOutputStream dataOut = null;
    static DataInputStream dataIn = null;
    private static Socket socket = null;

    public static void main(String[] args) throws IOException {
        //        User u = new User("phat", "123");
        //        System.out.println(testConnection());
        connectToServer();
        //System.out.println(sendReq(new GetNoteListReq("phat1")));
        System.out.println(sendReq(new GetNoteReq("phat", 1)));

    }

    public static boolean connectToServer() {
        boolean createConnRes = server.createConnection();

        if (createConnRes) {
            socket = server.getSocket();
            dataIn = Server.getInstance().getDataIn();
            dataOut = server.getDataOut();

            BaseRes testConnectionReq = sendReq(new TestConnectionReq());

            assert testConnectionReq != null;
            return testConnectionReq.getStatus() == RESPONSE_STATUS.SUCCESS;
        }

        return false;
    }

    public static BaseRes sendReq(BaseReq req) {
        try {

            if (server == null) {
                System.out.println("CANNOT CONNECT TO SERVER !!! APPLICATION WILL EXIT");

                //todo: goto IP config screen else [exit]
                exit();
            }
            REQUEST_TYPE reqType = req.getType();

            switch (reqType) {
                case TEST_CONNECTION -> {
                    TestConnectionReq testConnectionReq = (TestConnectionReq) req;

                    dataOut.writeUTF(testConnectionReq.getType().name());

                    REQUEST_TYPE resType = REQUEST_TYPE.valueOf(dataIn.readUTF());

                    if (resType != reqType) {
                        return null;
                    }
                    return new TestConnectionRes(
                            RESPONSE_STATUS.valueOf(dataIn.readUTF()),
                            dataIn.readUTF()
                    );
                }

                case SIGN_IN -> {
                    //* send sign in request to server
                    SignInReq signInReq = (SignInReq) req;

                    // write type
                    dataOut.writeUTF(signInReq.getType().name());

                    // write username:
                    dataOut.writeUTF(signInReq.getUsername());

                    // write password:
                    dataOut.writeUTF(signInReq.getPassword());
                    dataOut.flush();

                    REQUEST_TYPE resType = REQUEST_TYPE.valueOf(dataIn.readUTF());

                    if (resType != reqType) {
                        return null;
                    }

                    return new SignInRes(
                            RESPONSE_STATUS.valueOf(dataIn.readUTF()),
                            dataIn.readUTF()
                    );
                }

                case SIGN_UP -> {
                    //* send sign in request to server
                    SignUpReq signUpReq = (SignUpReq) req;

                    // write type
                    dataOut.writeUTF(signUpReq.getType().name());

                    // write username:
                    dataOut.writeUTF(signUpReq.getUsername());

                    // write password:
                    dataOut.writeUTF(signUpReq.getPassword());

                    //* read data from server
                    REQUEST_TYPE resType = REQUEST_TYPE.valueOf(dataIn.readUTF());

                    if (resType != reqType) {
                        return null;
                    }

                    return new SignUpRes(
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

                    if (resType != reqType) {
                        return null;
                    }

                    return new SendFileRes(
                            RESPONSE_STATUS.valueOf(dataIn.readUTF()),
                            dataIn.readUTF(),
                            dataIn.readUTF()
                    );
                }

                case GET_NOTE_LIST -> {
                    //* send data to server
                    GetNoteListReq getNoteListReq = (GetNoteListReq) req;
                    // write type:
                    dataOut.writeUTF(getNoteListReq.getType().name());
                    // write filename:
                    dataOut.writeUTF(getNoteListReq.getUsername());

                    //* read data from server
                    REQUEST_TYPE resType = REQUEST_TYPE.valueOf(dataIn.readUTF());

                    if (resType != reqType) {
                        return null;
                    }
                    RESPONSE_STATUS status = RESPONSE_STATUS.valueOf(dataIn.readUTF());
                    String msg = dataIn.readUTF();
                    int size = dataIn.readInt();

                    HashMap<Integer, Note> noteList = new HashMap<>();
                    for (int i = 0; i < size; i++) {
                        Integer id = dataIn.readInt();
                        String type = dataIn.readUTF(),
                                uri = dataIn.readUTF(),
                                createdAt = dataIn.readUTF();
                        noteList.put(id, new Note(id, type, uri, createdAt));
                    }

                    return new GetNoteListRes(
                            status,
                            msg,
                            noteList
                    );
                }

                case GET_NOTE -> {
                    //* send data to server
                    GetNoteReq getNoteReq = (GetNoteReq) req;
                    // write type:
                    dataOut.writeUTF(getNoteReq.getType().name());
                    // write filename:
                    dataOut.writeUTF(getNoteReq.getUsername());
                    // write note id:
                    dataOut.writeInt(getNoteReq.getNoteId());

                    //* read data from server
                    REQUEST_TYPE resType = REQUEST_TYPE.valueOf(dataIn.readUTF());

                    if (resType != reqType) {
                        return null;
                    }
                    RESPONSE_STATUS status = RESPONSE_STATUS.valueOf(dataIn.readUTF());
                    String msg = dataIn.readUTF();

                    Note note = new Note(
                            dataIn.readInt(),
                            dataIn.readUTF(),
                            dataIn.readUTF(),
                            dataIn.readUTF()
                    );

                    // get file content
                    Integer length = dataIn.readInt();
                    byte[] buffer = null;
                    if (length > 0) {
                        buffer = new byte[length];
                        dataIn.readFully(buffer, 0, buffer.length);
                    }
                    // random file name
                    //                    int leftLimit = 97; // letter 'a'
                    //                    int rightLimit = 122; // letter 'z'
                    //                    int targetStringLength = 10;
                    //                    Random random = new Random();
                    //                    StringBuilder tmp = new StringBuilder(targetStringLength);
                    //                    for (int i = 0; i < targetStringLength; i++) {
                    //                        int randomLimitedInt = leftLimit + (int)
                    //                                (random.nextFloat() * (rightLimit - leftLimit + 1));
                    //                        tmp.append((char) randomLimitedInt);
                    //                    }
                    //                    String filename = tmp.toString();
                    // save the file
                    //                    try (FileOutputStream fos = new FileOutputStream(filename)) {
                    //                        fos.write(buffer);
                    //                    }

                    return new GetNoteRes(
                            status,
                            msg,
                            note
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

    public static SendFileRes sendFile(File file) throws IOException {
        Socket socket = new Socket(server.getIP(), server.getPort());

        //* data to server
        SendFileReq req = new SendFileReq(file);

        SendFileRes res = (SendFileRes) sendReq(req);

        System.out.println(res);

        return res;
    }

}