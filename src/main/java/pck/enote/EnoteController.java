package pck.enote;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import pck.enote.api.API;
import pck.enote.helper.FileHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;

public class EnoteController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        FileChooser fileChooser = new FileChooser();

        File file = fileChooser.showOpenDialog(welcomeText.getScene().getWindow());
        try {
            System.out.println(API.sendFile(file));;
        } catch (IOException e) {
            System.out.println("Send file failed");
        }
    }
}