package pck.enote.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginPageController {
    public TextField usernameField;
    public Label usernameWarningField;
    public PasswordField passwordField;
    public Label passwordWarningField;
    public Button loginButton;

    protected
    String successMessage = String.format("-fx-text-fill: GREEN;");
    String errorMessage = String.format("-fx-text-fill: RED;");

    public void onLoginButtonClicked(ActionEvent ae) {
        if (ae.getSource() == loginButton) {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (username.isBlank() || password.isBlank()) {
                if (username.isBlank() && password.isBlank()) {
                    usernameWarningField.setText("Vui lòng nhập tên đăng nhập!");
                    passwordWarningField.setText("Vui lòng nhập mật khẩu!");

                    usernameWarningField.setStyle(errorMessage);
                    passwordWarningField.setStyle(errorMessage);
                } else {
                    if (username.isBlank()) {
                        usernameWarningField.setText("Vui lòng nhập tên đăng nhập!");
                        usernameWarningField.setStyle(errorMessage);
                    } else {
                        if (password.isBlank()) {
                            passwordWarningField.setText("Vui lòng nhập mật khẩu!");
                            passwordWarningField.setStyle(errorMessage);
                        }
                    }
                }
            } else {
                usernameWarningField.setVisible(false);
                passwordWarningField.setVisible(false);
            }
        }
    }
}
