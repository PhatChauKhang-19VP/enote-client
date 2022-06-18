package pck.enote.api.req;

public class SignInReq extends BaseReq {
    private String username;
    private String password;

    public SignInReq(String username, String password) {
        super(REQUEST_TYPE.SIGN_IN);
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
