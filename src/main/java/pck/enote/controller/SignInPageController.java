package pck.enote.controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pck.enote.Enote;
import pck.enote.api.API;
import pck.enote.api.req.REQUEST_TYPE;
import pck.enote.api.req.SignInReq;
import pck.enote.api.res.BaseRes;
import pck.enote.api.res.RESPONSE_STATUS;
import pck.enote.api.res.SignInRes;
import pck.enote.be.model.Server;
import pck.enote.be.model.User;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class SignInPageController implements Initializable {
    public TextField usernameField;
    public PasswordField passwordField;

    public Label passwordWarningField;
    public Label usernameWarningField;
    public Label succesAlert;

    public Button loginButton;
    public Hyperlink signUpHyperLink;
    public Hyperlink connectHyperLink;
    public Label connectionInfo;
    public ImageView ivUserIcon;
    public ImageView ivPwdIcon;

    protected
    String successMessage = String.format("-fx-text-fill: GREEN;");
    String errorMessage = String.format("-fx-text-fill: RED;");
    String errorStyle = String.format("-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 5;");
    String successStyle = String.format("-fx-border-color: #A9A9A9; -fx-border-width: 2; -fx-border-radius: 5;");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connectionInfo.setText(Server.getInstance().getIP() + "/" + Server.getInstance().getPort());
        ivUserIcon.setImage(new Image("static/icons/user-icon.png"));
        ivPwdIcon.setImage(new Image("static/icons/pwd-icon.png"));

        usernameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches(".*\\s")) {
                newValue = oldValue;
                usernameField.setText(newValue);
            } else {
                checkUsernameValid(newValue);
                succesAlert.setVisible(false);
            }
        });

        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches(".*\\s")) {
                newValue = oldValue;
                passwordField.setText(newValue);
            } else {
                checkPasswordValid(newValue);
                succesAlert.setVisible(false);
            }
        });
    }

    public boolean checkUsernameValid(String username) {
        if (username.isBlank()) {
            usernameField.setStyle(errorStyle);
            usernameWarningField.setText("Vui l??ng nh???p t??n t??i kho???n!");
            usernameWarningField.setStyle(errorMessage);

            return false;
        }
        if (username.length() < 5) {
            usernameField.setStyle(errorStyle);
            usernameWarningField.setText("T??n t??i kho???n ph???i d??i h??n 5 k?? t???!");
            usernameWarningField.setStyle(errorMessage);

            return false;
        }

        String regex = "^[0-9a-zA-Z]\\w{4,29}$";

        if (!username.matches(regex)) {
            usernameField.setStyle(errorStyle);
            usernameWarningField.setText("M???t kh???u ch??? bao g???m c??c k?? t???: a-z, A-Z, 0-9!");
            usernameWarningField.setStyle(errorMessage);

            return false;
        }

        usernameField.setStyle(successStyle);
        usernameWarningField.setText("");
        usernameWarningField.setStyle(successMessage);

        return true;
    }

    public boolean checkPasswordValid(String password) {
        if (password.isBlank()) {
            passwordField.setStyle(errorStyle);
            passwordWarningField.setText("Vui l??ng nh???p m???t kh???u!");
            passwordWarningField.setStyle(errorMessage);

            return false;
        }

        if (password.length() < 3) {
            passwordField.setStyle(errorStyle);
            passwordWarningField.setText("M???t kh???u ph???i d??i h??n 3 k?? t???!");
            passwordWarningField.setStyle(errorMessage);

            return false;
        }

        passwordField.setStyle(successStyle);
        passwordWarningField.setText("");
        passwordWarningField.setStyle(successMessage);

        return true;
    }

    public void onLoginButtonClicked(ActionEvent ae) {
        if (ae.getSource() == loginButton) {
            if (checkUsernameValid(usernameField.getText()) && checkPasswordValid(passwordField.getText())) {
                BaseRes res = API.sendReq(new SignInReq(usernameField.getText(), passwordField.getText()));
                assert res != null;
                if (res.getType() != REQUEST_TYPE.SIGN_IN) {
                    //show noti login fail w msg:
                    succesAlert.setStyle(errorMessage);
                    succesAlert.setText(res.getMsg());
                    succesAlert.setVisible(true);
                    return;
                }

                SignInRes signInRes = (SignInRes) res;
                if (signInRes.getStatus() == RESPONSE_STATUS.FAILED) {
                    //show noti login fail w msg:
                    succesAlert.setStyle(errorMessage);
                    succesAlert.setText(signInRes.getMsg());
                    succesAlert.setVisible(true);
                    return;
                }

                if (signInRes.getStatus() == RESPONSE_STATUS.SUCCESS) {
                    //show noti login fail w msg:
                    succesAlert.setStyle(successMessage);
                    succesAlert.setText(signInRes.getMsg());
                    succesAlert.setVisible(true);


                    User.getInstance().setUsername(usernameField.getText());
                    User.getInstance().setPassword(passwordField.getText());

                    ButtonType btnGotoSignIn = new ButtonType("??i ?????n trang ch??nh", ButtonBar.ButtonData.YES);
                    Alert alert = new Alert(
                            Alert.AlertType.INFORMATION,
                            "",
                            btnGotoSignIn);
                    alert.setTitle("Th??ng b??o");
                    alert.setHeaderText("????ng nh???p th??nh c??ng v???i t??i kho???n " + usernameField.getText() + " !");

                    // option != null.
                    Optional<ButtonType> option = alert.showAndWait();

                    if (option.isPresent() && option.get().getButtonData() == ButtonBar.ButtonData.YES) {
                        Enote.gotoViewNotesPage();
                    }

                    return;
                }
            }

            if (!checkUsernameValid(usernameField.getText())) {
                new animatefx.animation.Shake(usernameField).play();
            }

            if (!checkPasswordValid(passwordField.getText())) {
                new animatefx.animation.Shake(passwordField).play();
            }
        }
    }

    public void onSignUpHyperLinkClicked(ActionEvent ae) {
        if (ae.getSource() == signUpHyperLink) {
            pck.enote.Enote.gotoSignUpPage();
        }
    }

    public void onConnectHyperLinkClicked(ActionEvent ae) {
        if (ae.getSource() == connectHyperLink) {
            pck.enote.Enote.gotoConnectScreen();
        }
    }
}

