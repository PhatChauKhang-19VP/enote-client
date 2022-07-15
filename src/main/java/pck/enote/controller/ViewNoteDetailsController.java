package pck.enote.controller;

import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
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
import java.util.Locale;
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
                Enote.onError(e.getMessage());
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
            Enote.onError(e.getMessage());
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
            Enote.onError(e.getMessage());
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
            Enote.onError(e.getMessage());
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
            FileChooser fileChooser = new FileChooser();

            String param2 = "*." + note.getExtension();
            String param1 = String.format("%s file (%s)", note.getExtension().toUpperCase(Locale.ROOT), param2);

            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(param1, param2);
            fileChooser.getExtensionFilters().add(extFilter);


            //Opening a dialog box
            File file = fileChooser.showSaveDialog(Enote.stage);

            if (file != null) {
                try {
                    FileUtils.copyInputStreamToFile(new ByteArrayInputStream(note.getContent()), file);
                } catch (IOException e) {
                    e.printStackTrace();
                    Enote.onError(e.getMessage());
                }

                System.out.println(file.getName() + " , saved to " + file.getAbsolutePath());

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Tải xuống thành công");
                alert.setHeaderText(file.getName() + " lưu tại " + file.getAbsolutePath());
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Tải xuống thất bại");
                alert.setHeaderText("Không thể tải xuống file, có lỗi xảy ra");
                alert.showAndWait();
            }
        }
    }
}
