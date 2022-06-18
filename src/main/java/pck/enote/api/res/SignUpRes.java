package pck.enote.api.res;

import pck.enote.api.req.REQUEST_TYPE;

public class SignUpRes extends BaseRes {
    public SignUpRes(RESPONSE_STATUS status, String msg) {
        super(status, msg, REQUEST_TYPE.SIGN_UP);
    }

    @Override
    public String toString() {
        return "SignInRes{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", type=" + type +
                '}';
    }
}
