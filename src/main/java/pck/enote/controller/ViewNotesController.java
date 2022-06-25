package pck.enote.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import pck.enote.api.API;
import pck.enote.api.req.GetNoteListReq;
import pck.enote.api.res.GetNoteListRes;
import pck.enote.fe.model.Note;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewNotesController implements Initializable {

    public TableView<Note> tableView;
    public TableColumn<Note, Integer> colId;
    public TableColumn<Note, String> colFileName;
    public TableColumn<Note, String> colFileType;
    public TableColumn colBtn;
    public TableColumn<Note, String> colCreatedAt;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<Note, Integer>("id"));
        colFileName.setCellValueFactory(new PropertyValueFactory<Note, String>("uri"));
        colFileType.setCellValueFactory(new PropertyValueFactory<Note, String>("type"));
        colCreatedAt.setCellValueFactory(new PropertyValueFactory<Note, String>("createdAt"));

        Callback<TableColumn<Note, String>, TableCell<Note, String>> cellFactory = new Callback<>() {
            @Override
            public TableCell call(final TableColumn<Note, String> param) {
                final TableCell<Note, String> cell = new TableCell<Note, String>() {
                    final Button btn = new Button("Xem chi tiết");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {
                                Note Note = getTableView().getItems().get(getIndex());
                                System.out.println("select Note " + Note.getUri());
//                                gotoListProduct(Note);
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };
        colBtn.setCellFactory(cellFactory);
        this.getNotes("phat1");
    }

    private void getNotes(String username) {
        GetNoteListRes res = (GetNoteListRes) API.sendReq(new GetNoteListReq(username));
        tableView.getItems().clear();
        tableView.getItems().addAll(res.getNoteList().values());
    }
}
