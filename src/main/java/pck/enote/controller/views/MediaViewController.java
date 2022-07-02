package pck.enote.controller.views;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import pck.enote.helper.FileHelper;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MediaViewController implements Initializable {
    public MediaView mvVideo;

    public Button btnPlay;
    public Button btnPause;
    public Button btnReset;

    Media media = null;
    MediaPlayer mediaPlayer = null;
    public Property<File> fileProperty = new SimpleObjectProperty<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fileProperty.addListener((observableValue, oldOne, newOne) -> {
            if (observableValue.getValue() != null) {
                media = new Media(fileProperty.getValue().toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                mvVideo.setMediaPlayer(mediaPlayer);
                play();
            }
        });
    }

    private void init() {
    }

    public void play() {
        mediaPlayer.play();
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public void reset() {
        mediaPlayer.seek(Duration.ZERO);
    }
}
