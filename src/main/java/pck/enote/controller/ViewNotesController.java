package pck.enote.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
import pck.enote.helper.ScreenConfig;

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
    public Button btnUploadText;
    public ImageView ivAddTextIcon;
    public Button btnUploadImage;
    public ImageView ivAddImageIcon;
    public Button btnUploadFile;
    public ImageView ivAddFileIcon;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("init");
        tfUsername.setText(User.getInstance().getUsername());
        tfNumberNote.setText("0");
        tfIP.setText(Server.getInstance().getIP());
        tfPort.setText(String.valueOf(Server.getInstance().getPort()));

        // set icon for buttons upload
        ivAddTextIcon.setImage(new Image("static/icons/add-text-icon.png"));
        ivAddImageIcon.setImage(new Image("static/icons/add-image-icon.png"));
        ivAddFileIcon.setImage(new Image("static/icons/add-file-icon.png"));

        // set tooltip
        btnUploadText.setTooltip(new Tooltip("Nhập ghi chú và tải lên"));
        btnUploadImage.setTooltip(new Tooltip("Chọn và tải hình ảnh lên"));
        btnUploadFile.setTooltip(new Tooltip("Chọn và tải file lên"));


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
        Platform.runLater(() -> {
            this.getNotes(User.getInstance().getUsername());
        });
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

    public void inputTextAndUpload(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnUploadText) {
            Stage dialog = new Stage();
            dialog.getIcons().clear();
            dialog.getIcons().add(new Image(ScreenConfig.getLogoPath()));
            dialog.setTitle(ScreenConfig.getTitle() + " - text");
            dialog.setResizable(false);
            dialog.setFullScreen(false);
            dialog.initOwner(Enote.stage);
            dialog.initModality(Modality.APPLICATION_MODAL);

            try {
                FXMLLoader loader = new FXMLLoader(Enote.class.getResource("views/inputTextView.fxml"), null, new JavaFXBuilderFactory());

                Parent page = loader.load();

                Enote.loader = loader;

                Scene scene = new Scene(page, 600, 400);
                dialog.setScene(scene);

                dialog.sizeToScene();
                dialog.showAndWait();

                Enote.gotoViewNotesPage();
            } catch (Exception e) {
                e.printStackTrace();
                Enote.onError(e.getMessage());
            }
        }
    }

    public void choseImageAndUpload(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnUploadImage) {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter imageFilter
                    = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
            fileChooser.getExtensionFilters().add(imageFilter);

            File file = fileChooser.showOpenDialog(Enote.stage);

            if (file != null) {
                SendFileRes res = (SendFileRes) API.sendReq(new SendFileReq(file));

                assert res != null;
                System.out.println(res);
                if (res.getStatus() == RESPONSE_STATUS.SUCCESS) {
                    System.out.println("Upload image successfully" + res.getFileUrl());

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Thông báo");
                    alert.setHeaderText("Tải lên hình ảnh thàng công");
                    alert.showAndWait();

                    Enote.gotoViewNotesPage();
                } else {
                    System.out.println("Upload image failed" + res.getFileUrl());

                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Thông báo");
                    alert.setHeaderText("Tải lên hình ảnh thất bại, vui lòng thử lại");
                    alert.showAndWait();
                }
            }
        }
    }

    public void choseFileAndUpload(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnUploadFile) {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter imageFilter
                    = new FileChooser.ExtensionFilter("All files (*.*)", "*.*");
            fileChooser.getExtensionFilters().add(imageFilter);
            File file = fileChooser.showOpenDialog(Enote.stage);

            if (file != null) {
                SendFileRes res = (SendFileRes) API.sendReq(new SendFileReq(file));

                assert res != null;
                System.out.println(res);
                if (res.getStatus() == RESPONSE_STATUS.SUCCESS) {
                    System.out.println("Upload image successfully" + res.getFileUrl());

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Thông báo");
                    alert.setHeaderText("Tải lên file thàng công");
                    alert.showAndWait();

                    Enote.gotoViewNotesPage();
                } else {
                    System.out.println("Upload image failed" + res.getFileUrl());

                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Thông báo");
                    alert.setHeaderText("Tải lên file thất bại, vui lòng thử lại");
                    alert.showAndWait();
                }
            }
        }
    }
}
