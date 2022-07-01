package pck.enote.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import pck.enote.Enote;
import pck.enote.api.API;
import pck.enote.api.req.GetNoteReq;
import pck.enote.api.res.GetNoteRes;
import pck.enote.fe.model.Note;

import java.io.ByteArrayInputStream;
import java.net.URL;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getNoteDetails();
        btnBack.setOnAction(event -> {
            Enote.gotoViewNotesPage(username);
        });
    }

    private void getNoteDetails() {
        GetNoteRes res = (GetNoteRes) API.sendReq(new GetNoteReq(this.username, this.noteId));
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
