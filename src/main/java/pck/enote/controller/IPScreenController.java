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
import pck.enote.api.req.TestConnectionReq;
import pck.enote.api.res.RESPONSE_STATUS;
import pck.enote.api.res.TestConnectionRes;
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
            ipWarningField.setText("Vui lòng nhập địa chỉ IP!");
            ipWarningField.setStyle(errorMessage);

            return false;
        }

        String regex = "(\\b25[0-5]|\\b2[0-4][0-9]|\\b[01]?[0-9][0-9]?)(\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)){3}";

        if (!ip.matches(regex)) {
            ipField.setStyle(errorStyle);
            ipWarningField.setText("Địa chỉ IP có dạng: (0-255).(0-255).(0.255).(0-255) !");
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
            portWarningField.setText("Vui lòng nhập số cổng!");
            portWarningField.setStyle(errorMessage);

            return false;
        }

        String regex = "^((6553[0-5])|(655[0-2][0-9])|(65[0-4][0-9]{2})|(6[0-4][0-9]{3})|([1-5][0-9]{4})|([0-5]{0,5})|([0-9]{1,4}))$";

        if (!port.matches(regex)) {
            portField.setStyle(errorStyle);
            portWarningField.setText("Số cổng chỉ bao gồm các kí tự từ 0-9 và nằm trong khoảng 0 - 65535!");
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
                    //create a new thread to check connection
                    new Thread(()->{
                        try {
                            while (!API.getSocket().isClosed()){
                                int seconds = 10;
                                Thread.sleep(seconds*1000);
                                TestConnectionRes res = (TestConnectionRes) API.sendReq(new TestConnectionReq());

                                if (res == null){
                                    throw new Exception("Mất kết nối với server");
                                }
                                else {
                                    System.out.println("Server still working");
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Enote.onError(e.getMessage());
                        }
                    }).start();

                    pck.enote.Enote.gotoSignInPage();
                } else {
                    statusAlert.setStyle(errorStyle);
                    statusAlert.setText("Không thể kết nối đến server !");
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
