package pck.enote.controller;

import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import org.apache.commons.io.FileUtils;
import pck.enote.Enote;
import pck.enote.api.API;
import pck.enote.api.req.GetNoteReq;
import pck.enote.api.res.GetNoteRes;
import pck.enote.be.model.User;
import pck.enote.controller.views.ImageViewController;
import pck.enote.controller.views.MediaViewController;
import pck.enote.controller.views.TextViewController;
import pck.enote.fe.model.Note;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewNoteDetailsController implements Initializable {
    public TextField tfId;
    public TextField tfFileName;
    public TextField tfFileType;
    public TextField tfCreatedAt;
    public Pane content;
    public Button btnBack;
    public Button btnDownload;
    public Label notification;
    public Property<Note> noteProperty = new SimpleObjectProperty<>();
    Note note = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        noteProperty.addListener(
                (observableValue, note, t1) -> {
                    if (noteProperty.getValue() != null) {
                        System.out.println("prop trigger");
                        getNoteDetails(User.getInstance().getUsername(), noteProperty.getValue().getId());
                    }
                }
        );
    }

    private void getNoteDetails(String username, Integer noteId) {
        GetNoteRes res = (GetNoteRes) API.sendReq(new GetNoteReq(username, noteId));
        assert res != null;
        note = res.getNote();
        tfId.setText(String.valueOf(note.getId()));
        tfFileName.setText(note.getFilename());
        tfFileType.setText(note.getType());
        tfCreatedAt.setText(note.getCreatedAt());

        // display content
        String type = note.getType();
        if (type.contains("text")) {
            String content = new String(note.getContent());
            displayText(content);
        } else if (type.contains("image")) {
            Image img = new Image(new ByteArrayInputStream(note.getContent()));
            displayImage(img);
        } else if (type.contains("video")) {
            File videoFile = new File("myVideo");
            videoFile.deleteOnExit();
            try {
                FileUtils.copyInputStreamToFile(new ByteArrayInputStream(note.getContent()), videoFile);
                displayVideo(videoFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Loại file " + note.getType() + " không được hỗ trợ");
        }
    }

    private void displayText(String content) {
        try {
            FXMLLoader fxmlLoaderClient = new FXMLLoader();
            fxmlLoaderClient.setLocation(Enote.class.getResource("views/textView.fxml"));
            AnchorPane imgView = fxmlLoaderClient.load();

            TextViewController ctrl = fxmlLoaderClient.getController();

            Platform.runLater(() -> {
                ctrl.taContent.setText(content);
            });
            this.content.getChildren().clear();
            this.content.getChildren().add(imgView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayImage(Image img) {
        try {
            FXMLLoader fxmlLoaderClient = new FXMLLoader();
            fxmlLoaderClient.setLocation(Enote.class.getResource("views/imageView.fxml"));
            AnchorPane imgView = fxmlLoaderClient.load();

            ImageViewController ctrl = fxmlLoaderClient.getController();

            Platform.runLater(() -> {
                ctrl.ivImage.setImage(img);
            });

            content.getChildren().clear();
            content.getChildren().add(imgView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayVideo(File videoFile) {
        try {
            FXMLLoader fxmlLoaderClient = new FXMLLoader();
            fxmlLoaderClient.setLocation(Enote.class.getResource("views/mediaView.fxml"));
            AnchorPane mediaView = fxmlLoaderClient.load();

            MediaViewController ctrl = fxmlLoaderClient.getController();
            ctrl.fileProperty.setValue(videoFile);

            content.getChildren().clear();
            content.getChildren().add(mediaView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void back(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnBack) {
            System.out.println("back");
            Enote.gotoViewNotesPage();
        }
    }


    public void download(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnDownload) {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setInitialDirectory(new File("./"));
            File selectedDirectory = directoryChooser.showDialog(Enote.stage);

            if (selectedDirectory.isDirectory()) {
                File downloadFile = new File(selectedDirectory.getAbsolutePath() + "/" + note.getFilename());
                try {
                    FileUtils.copyInputStreamToFile(new ByteArrayInputStream(note.getContent()), downloadFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println("downloaded the file " + note.getFilename() + " , saved to " + selectedDirectory.getAbsolutePath());
            }
        }
    }
}
