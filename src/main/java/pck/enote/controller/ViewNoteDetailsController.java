package pck.enote.controller;

import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.apache.commons.io.FileUtils;
import pck.enote.Enote;
import pck.enote.api.API;
import pck.enote.api.req.GetNoteReq;
import pck.enote.api.res.GetNoteRes;
import pck.enote.be.model.User;
import pck.enote.fe.model.Note;

import java.io.File;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;

public class ViewNoteDetailsController implements Initializable {
    public TextField tfId;
    public TextField tfFileName;
    public TextField tfFileType;
    public TextField tfCreatedAt;
    public Pane content;
    public Label notification;
    public Button btnBack;
    public Button btnDownload;
    private String username;
    private Integer noteId;

    public Property<Note> noteProperty = new SimpleObjectProperty<>();
    public ScrollPane spContent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        noteProperty.addListener(
                (observableValue, note, t1) -> {
                    if (noteProperty.getValue() != null) {
                        getNoteDetails(User.getInstance().getUsername(), noteProperty.getValue().getId());
                    }
                }
        );

        try {


            //! test video player
            String testWhat = "image";

            if (testWhat.equals("video")) {
                FXMLLoader fxmlLoaderClient = new FXMLLoader();
                fxmlLoaderClient.setLocation(Enote.class.getResource("mediaView.fxml"));
                AnchorPane mediaView = fxmlLoaderClient.load();

                MediaViewController ctrl = fxmlLoaderClient.getController();
                URL urlTest = new URL("http://clips.vorwaerts-gmbh.de/VfE_html5.mp4");
                URLConnection conn = urlTest.openConnection();
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);
                conn.connect();

                File destFile = new File("somefile.txt");
                FileUtils.copyInputStreamToFile(conn.getInputStream(), destFile);

                ctrl.fileProperty.setValue(destFile);

                spContent.setContent(mediaView);
            }
            //! test image view
            else if (testWhat.equals("image")) {
                FXMLLoader fxmlLoaderClient = new FXMLLoader();
                fxmlLoaderClient.setLocation(Enote.class.getResource("imageView.fxml"));
                AnchorPane imgView = fxmlLoaderClient.load();

                ImageViewController ctrl = fxmlLoaderClient.getController();

                Image image = new Image("https://res.cloudinary.com/pck-group/image/upload/v1648455715/samples/bike.jpg");

                Platform.runLater(() -> {
                    ctrl.ivImage.setImage(image);
                });

                spContent.setContent(imgView);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getNoteDetails(String username, Integer noteId) {
        GetNoteRes res = (GetNoteRes) API.sendReq(new GetNoteReq(username, noteId));
        assert res != null;
        Note note = res.getNote();
        tfId.setText(String.valueOf(note.getId()));

        // split the path on splash to get file name
        String[] splitFileName = note.getUri().split("/");
        tfFileName.setText(splitFileName[splitFileName.length - 1]);

        tfFileType.setText(note.getType());
        tfCreatedAt.setText(note.getCreatedAt());

        // display content
        String type = note.getType();
        if (type.contains("plain")) {
            content.getChildren().clear();
            content.getChildren().add(
                    new Label(new String(note.getContent()))
            );
        } else if (type.contains("image")) {
            content.getChildren().clear();
            Image img = new Image(new ByteArrayInputStream(note.getContent()));
            content.getChildren().add(
                    new ImageView(img)
            );
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }
}
