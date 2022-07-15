package pck.enote.controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pck.enote.Enote;
import pck.enote.api.API;
import pck.enote.api.req.SignUpReq;
import pck.enote.api.res.RESPONSE_STATUS;
import pck.enote.api.res.SignUpRes;
import pck.enote.be.model.Server;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class SignUpPageController implements Initializable {
    public TextField usernameField;
    public PasswordField passwordField;
    public PasswordField confirmPasswordField;

    public Label passwordWarningField;
    public Label usernameWarningField;
    public Label confirmPassWarningField;

    public Label successAlert;

    public Button loginButton;
    public Hyperlink signInHyperLink;
    public Hyperlink connectHyperLink;
    public Label connectionInfo;
    public ImageView ivUserIcon;
    public ImageView ivPwdIcon;
    public ImageView ivPwdCfIcon;

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
        ivPwdCfIcon.setImage(new Image("static/icons/pwd-icon.png"));

        usernameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches(".*\\s")) {
                newValue = oldValue;
                usernameField.setText(newValue);
            } else {
                checkUsernameValid(newValue);
                successAlert.setVisible(false);
            }
        });

        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches(".*\\s")) {
                newValue = oldValue;
                passwordField.setText(newValue);
            } else {
                checkPasswordValid(newValue);
                successAlert.setVisible(false);
            }
        });

        confirmPasswordField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches(".*\\s")) {
                newValue = oldValue;
                confirmPasswordField.setText(newValue);
            } else {
                checkConfirmPasswordValid(newValue);
                successAlert.setVisible(false);
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

    public boolean checkConfirmPasswordValid(String password) {
        if (password.isBlank()) {
            confirmPasswordField.setStyle(errorStyle);
            confirmPassWarningField.setText("Vui lòng nhập mật khẩu!");
            confirmPassWarningField.setStyle(errorMessage);

            return false;
        }

        if (password.length() < 3) {
            confirmPasswordField.setStyle(errorStyle);
            confirmPassWarningField.setText("Mật khẩu phải dài hơn 3 kí tự!");
            confirmPassWarningField.setStyle(errorMessage);

            return false;
        }

        if (!password.equals(passwordField.getText())) {
            confirmPasswordField.setStyle(errorStyle);
            confirmPassWarningField.setText("Mật khẩu không khớp!");
            confirmPassWarningField.setStyle(errorMessage);

            return false;
        }

        confirmPasswordField.setStyle(successStyle);
        confirmPassWarningField.setText("");
        confirmPassWarningField.setStyle(successMessage);

        return true;
    }

    public void onSignUpButtonClicked(ActionEvent ae) {
        if (ae.getSource() == loginButton) {
            if (checkUsernameValid(usernameField.getText())
                    && checkPasswordValid(passwordField.getText())
                    && checkConfirmPasswordValid(confirmPasswordField.getText())
            ) {
                System.out.println(confirmPassWarningField.getText());

                SignUpRes signUpRes = (SignUpRes) API.sendReq(new SignUpReq(usernameField.getText(), passwordField.getText()));
                assert signUpRes != null;
                if (signUpRes.getStatus() == RESPONSE_STATUS.SUCCESS) {
                    ButtonType btnGotoSignIn = new ButtonType("Đi đến đăng nhập", ButtonBar.ButtonData.YES);
                    Alert alert = new Alert(
                            Alert.AlertType.INFORMATION,
                            "",
                            btnGotoSignIn);
                    alert.setTitle("Thông báo");
                    alert.setHeaderText("Đăng kí thành công tài khoản " + usernameField.getText() + " !");
                    alert.setOnCloseRequest(Event::consume);
                    // option != null.
                    Optional<ButtonType> option = alert.showAndWait();

                    if (option.isPresent() && option.get().getButtonData() == ButtonBar.ButtonData.YES) {
                        Enote.gotoSignInPage();
                    }
                }
            }

            if (!checkUsernameValid(usernameField.getText())) {
                new animatefx.animation.Shake(usernameField).play();
            }

            if (!checkPasswordValid(passwordField.getText())) {
                new animatefx.animation.Shake(passwordField).play();
            }

            if (!checkConfirmPasswordValid(confirmPasswordField.getText())) {
                new animatefx.animation.Shake(confirmPasswordField).play();
            }
        }
    }

    public void onSignInHyperLinkClicked(ActionEvent ae) {
        if (ae.getSource() == signInHyperLink) {
            pck.enote.Enote.gotoSignInPage();
        }
    }

    public void onConnectHyperLinkClicked(ActionEvent ae) {
        if (ae.getSource() == connectHyperLink) {
            pck.enote.Enote.gotoConnectScreen();
        }
    }

    private void displayError() {

    }
}

