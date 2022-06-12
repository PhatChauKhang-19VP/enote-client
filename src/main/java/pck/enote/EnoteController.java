package pck.enote;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EnoteController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}