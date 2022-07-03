package pck.enote.controller.views;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class TextViewController implements Initializable {
    public TextArea taContent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        taContent.setText("");
    }
}
