package pck.enote.controller;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import pck.enote.Enote;
import pck.enote.api.API;
import pck.enote.api.req.GetNoteReq;
import pck.enote.api.res.GetNoteRes;
import pck.enote.be.model.User;
import pck.enote.fe.model.Note;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;

public class ViewNoteDetailsController implements Initializable {

    public TextField tfId;
    public TextField tfFileName;
    public TextField tfFileType;
    public TextField tfCreatedAt;
    public Pane content;

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
            FXMLLoader fxmlLoaderClient = new FXMLLoader();
            fxmlLoaderClient.setLocation(Enote.class.getResource("mediaView.fxml"));
            AnchorPane mediaView = fxmlLoaderClient.load();
            MediaViewController ctrl = fxmlLoaderClient.getController();

            //! test
            URL urlTest = new URL("http://clips.vorwaerts-gmbh.de/VfE_html5.mp4");
            URLConnection conn = urlTest.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.connect();

            File destFile= new File("somefile.txt");
            FileUtils.copyInputStreamToFile(conn.getInputStream(), destFile);

            ctrl.fileProperty.setValue(destFile);

            spContent.setContent(mediaView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getNoteDetails(String username, Integer noteId) {
        GetNoteRes res = (GetNoteRes) API.sendReq(new GetNoteReq(username, noteId));
        assert res != null;
        Note note = res.getNote();
        tfId.setText(String.valueOf(note.getId()));
        String[] splitFileName = note.getUri().split("/");
        tfFileName.setText(splitFileName[splitFileName.length - 1]);
        tfFileType.setText(note.getType());
        tfCreatedAt.setText(note.getCreatedAt());
        content.getChildren().clear();
        content.getChildren().add(
                new ImageView(new Image(note.getUri()))
        );
    }
}
