package pck.enote.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import pck.enote.api.API;
import pck.enote.api.req.GetNoteReq;
import pck.enote.api.res.GetNoteRes;
import pck.enote.fe.model.Note;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewNoteDetailsController implements Initializable {

    public TextField tfId;
    public TextField tfFileName;
    public TextField tfFileType;
    public TextField tfCreatedAt;
    public Pane content;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getNoteDetails("phat", 1);
    }

    private void getNoteDetails(String username, Integer noteId) {
        GetNoteRes res = (GetNoteRes) API.sendReq(new GetNoteReq(username, noteId));
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
