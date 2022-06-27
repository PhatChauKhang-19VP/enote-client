package pck.enote.be.model;

public class User {
    private String username;
    private String password;

    private static User instance = null;

    public static User getInstance(){
        if (instance == null){
            instance = new User("", "");
        }

        return instance;
    }

    private User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
