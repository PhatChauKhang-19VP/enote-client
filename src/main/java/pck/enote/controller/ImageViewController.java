package pck.enote.controller;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class ImageViewController implements Initializable {
    public ImageView ivImage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ivImage.imageProperty().addListener((observableValue, image, t1) -> {
            System.out.println("ivImage.imageProperty is trigged");
            Platform.runLater(() -> {
                centerImage();
            });
        });
    }

    public void centerImage() {
        Image img = ivImage.getImage();
        if (img != null) {
            double w = 0;
            double h = 0;

            double ratioX = ivImage.getFitWidth() / img.getWidth();
            double ratioY = ivImage.getFitHeight() / img.getHeight();

            double reducCoeff = Math.min(ratioX, ratioY);

            w = img.getWidth() * reducCoeff;
            h = img.getHeight() * reducCoeff;

            ivImage.setX((ivImage.getFitWidth() - w) / 2);
            ivImage.setY((ivImage.getFitHeight() - h) / 2);
        }
    }
}
