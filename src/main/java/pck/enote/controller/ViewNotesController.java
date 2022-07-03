package pck.enote.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import pck.enote.Enote;
import pck.enote.api.API;
import pck.enote.api.req.GetNoteListReq;
import pck.enote.api.req.SendFileReq;
import pck.enote.api.res.GetNoteListRes;
import pck.enote.api.res.RESPONSE_STATUS;
import pck.enote.api.res.SendFileRes;
import pck.enote.be.model.Server;
import pck.enote.be.model.User;
import pck.enote.fe.model.Note;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewNotesController implements Initializable {
    public TableView<Note> tableView;
    public TableColumn<Note, Integer> colId;
    public TableColumn<Note, String> colFileName;
    public TableColumn<Note, String> colFileType;
    public TableColumn<Note, String> colBtn;
    public TableColumn<Note, String> colCreatedAt;
    public TextField tfUsername;
    public TextField tfNumberNote;
    public TextField tfIP;
    public TextField tfPort;
    public Hyperlink hlEditSever;
    public Button btnUpload;
    public ImageView ivPlusIcon;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("init");
        tfUsername.setText(User.getInstance().getUsername());
        tfNumberNote.setText("0");
        tfIP.setText(Server.getInstance().getIP());
        tfPort.setText(String.valueOf(Server.getInstance().getPort()));
        ivPlusIcon.setImage(new Image("static/icons/plus.png"));

        this.getNotes(User.getInstance().getUsername());
        colId.setCellValueFactory(new PropertyValueFactory<Note, Integer>("id"));
        colFileName.setCellValueFactory(new PropertyValueFactory<Note, String>("filename"));
        colFileType.setCellValueFactory(new PropertyValueFactory<Note, String>("type"));
        colCreatedAt.setCellValueFactory(new PropertyValueFactory<Note, String>("createdAt"));
        Callback<TableColumn<Note, String>, TableCell<Note, String>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Note, String> call(final TableColumn<Note, String> param) {
                return new TableCell<>() {
                    final Button btn = new Button("Xem chi tiết");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            btn.setOnAction(event -> {
                                Note note = getTableView().getItems().get(getIndex());
                                System.out.println("select Note " + note.getUri());
                                Enote.gotoViewNoteDetailsPage(note);
                            });
                            setGraphic(btn);
                        }
                        setText(null);
                    }
                };
            }
        };
        colBtn.setCellFactory(cellFactory);
        this.getNotes(User.getInstance().getUsername());
    }

    private void getNotes(String username) {
        GetNoteListRes res = (GetNoteListRes) API.sendReq(new GetNoteListReq(username));
        tableView.getItems().clear();
        assert res != null;
        tfNumberNote.setText(String.valueOf(res.getNoteList().size()));
        tableView.getItems().addAll(res.getNoteList().values());
    }

    public void editConnection(ActionEvent actionEvent) {
        if (actionEvent.getSource() == hlEditSever) {
            Enote.gotoConnectScreen();
        }
    }

    public void choseFileAndUpload(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnUpload) {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(Enote.stage);

            if (file != null) {
                SendFileRes res = (SendFileRes) API.sendReq(new SendFileReq(file));

                assert res != null;
                System.out.println(res);
                if (res.getStatus() == RESPONSE_STATUS.SUCCESS){
                    System.out.println("upload thanh cong" + res.getFileUrl());
                    Enote.gotoViewNotesPage();
                }
            }
        }
    }
}
