package pck.enote.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import pck.enote.Enote;
import pck.enote.api.API;
import pck.enote.be.model.Server;

import java.net.URL;
import java.util.ResourceBundle;

public class IPScreenController implements Initializable {
    public TextField ipField;
    public Label ipWarningField;

    public TextField portField;
    public Text portWarningField;

    public Button connectButton;
    public Label statusAlert;
    public ImageView ivPortIcon;
    public ImageView ivIPIcon;

    protected
    String successMessage = String.format("-fx-text-fill: GREEN;");
    String errorMessage = String.format("-fx-text-fill: RED;");
    String errorStyle = String.format("-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 5;");
    String successStyle = String.format("-fx-border-color: #A9A9A9; -fx-border-width: 2; -fx-border-radius: 5;");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ipField.setText(Server.getInstance().getIP());
        portField.setText(String.valueOf(Server.getInstance().getPort()));
        ivIPIcon.setImage(new Image("static/icons/ip-address.png"));
        ivPortIcon.setImage(new Image("static/icons/ethernet.png"));

        ipField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches(".*\\s")) {
                newValue = oldValue;
                ipField.setText(newValue);
            } else {
                checkIPValid(newValue);
                statusAlert.setVisible(false);
            }
        });

        portField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches(".*\\s")) {
                newValue = oldValue;
                portField.setText(newValue);
            } else {
                checkPortValid(newValue);
                statusAlert.setVisible(false);
            }
        });
    }

    public boolean checkIPValid(String ip) {
        if (ip.isBlank()) {
            ipField.setStyle(errorStyle);
            ipWarningField.setText("Vui l??ng nh???p ?????a ch??? IP!");
            ipWarningField.setStyle(errorMessage);

            return false;
        }

        String regex = "(\\b25[0-5]|\\b2[0-4][0-9]|\\b[01]?[0-9][0-9]?)(\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)){3}";

        if (!ip.matches(regex)) {
            ipField.setStyle(errorStyle);
            ipWarningField.setText("?????a ch??? IP c?? d???ng: (0-255).(0-255).(0.255).(0-255) !");
            ipWarningField.setStyle(errorMessage);

            return false;
        }

        ipField.setStyle(successStyle);
        ipWarningField.setText("");
        ipWarningField.setStyle(successMessage);

        return true;
    }

    public boolean checkPortValid(String port) {
        if (port.isBlank()) {
            portField.setStyle(errorStyle);
            portWarningField.setText("Vui l??ng nh???p s??? c???ng!");
            portWarningField.setStyle(errorMessage);

            return false;
        }

        String regex = "^((6553[0-5])|(655[0-2][0-9])|(65[0-4][0-9]{2})|(6[0-4][0-9]{3})|([1-5][0-9]{4})|([0-5]{0,5})|([0-9]{1,4}))$";

        if (!port.matches(regex)) {
            portField.setStyle(errorStyle);
            portWarningField.setText("S??? c???ng ch??? bao g???m c??c k?? t??? t??? 0-9 v?? n???m trong kho???ng 0 - 65535!");
            // portWarningField.setStyle(errorMessage);

            return false;
        }

        portField.setStyle(successStyle);
        portWarningField.setText("");
        // portWarningField.setStyle(successMessage);

        portWarningField.setWrappingWidth(250);
        return true;
    }

    public void onConnectButtonClicked(ActionEvent ae) {
        if (ae.getSource() == connectButton) {
            if (checkIPValid(ipField.getText()) && checkPortValid(portField.getText())) {
                Server.getInstance().setIP(ipField.getText());
                Server.getInstance().setPort(Integer.parseInt(portField.getText()));
                if (API.connectToServer()) {
                    pck.enote.Enote.gotoSignInPage();
                } else {
                    statusAlert.setStyle(errorStyle);
                    statusAlert.setText("Kh??ng th??? k???t n???i ?????n server !");
                    statusAlert.setStyle(errorMessage);
                    statusAlert.setVisible(true);

                    Platform.runLater(() -> {
                        ipField.setStyle(errorStyle);
                        portField.setStyle(errorStyle);

                        new animatefx.animation.Shake(ipField).play();
                        new animatefx.animation.Shake(portField).play();
                    });

                    System.out.println("Ket noi ko thanh cong den server");
                }
            }
        }

        if (!checkIPValid(ipField.getText())) {
            new animatefx.animation.Shake(ipField).play();
        }

        if (!checkPortValid(portField.getText())) {
            new animatefx.animation.Shake(portField).play();
        }
    }
}
