package pck.enote.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import pck.enote.api.API;
import pck.enote.api.req.REQUEST_TYPE;
import pck.enote.api.req.SignInReq;
import pck.enote.api.res.BaseRes;
import pck.enote.api.res.RESPONSE_STATUS;
import pck.enote.api.res.SignInRes;

import java.net.URL;
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

    protected
    String successMessage = String.format("-fx-text-fill: GREEN;");
    String errorMessage = String.format("-fx-text-fill: RED;");
    String errorStyle = String.format("-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 5;");
    String successStyle = String.format("-fx-border-color: #A9A9A9; -fx-border-width: 2; -fx-border-radius: 5;");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
            usernameWarningField.setText("Vui lòng nhập tên tài khoản!");
            usernameWarningField.setStyle(errorMessage);

            return false;
        }
        if (username.length() < 5) {
            usernameField.setStyle(errorStyle);
            usernameWarningField.setText("Tên tài khoản phải dài hơn 5 kí tự!");
            usernameWarningField.setStyle(errorMessage);

            return false;
        }

        String regex = "^[0-9a-zA-Z]\\w{4,29}$";

        if (!username.matches(regex)) {
            usernameField.setStyle(errorStyle);
            usernameWarningField.setText("Mật khẩu chỉ bao gồm các kí tự: a-z, A-Z, 0-9!");
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
            passwordWarningField.setText("Vui lòng nhập mật khẩu!");
            passwordWarningField.setStyle(errorMessage);

            return false;
        }

        if (password.length() < 3) {
            passwordField.setStyle(errorStyle);
            passwordWarningField.setText("Mật khẩu phải dài hơn 3 kí tự!");
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
                if(signInRes.getStatus() ==  RESPONSE_STATUS.FAILED) {
                    //show noti login fail w msg:
                    succesAlert.setStyle(errorMessage);
                    succesAlert.setText(signInRes.getMsg());
                    succesAlert.setVisible(true);
                    return;
                }

                if(signInRes.getStatus() ==  RESPONSE_STATUS.SUCCESS) {
                    //show noti login fail w msg:
                    succesAlert.setStyle(successMessage);
                    succesAlert.setText(signInRes.getMsg());
                    succesAlert.setVisible(true);

                    Platform.runLater(()->{
                        try {
                            Thread.sleep(2000);
                            pck.enote.Enote.gotoSignUpPage();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
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
        if(ae.getSource() == signUpHyperLink) {
            pck.enote.Enote.gotoSignUpPage();
        }
    }

    public void onConnectHyperLinkClicked(ActionEvent ae) {
        if(ae.getSource() == connectHyperLink) {
            pck.enote.Enote.gotoConnectScreen();
        }
    }
}

