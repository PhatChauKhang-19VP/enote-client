package pck.enote.controller.views;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import pck.enote.Enote;
import pck.enote.api.API;
import pck.enote.api.req.SendFileReq;
import pck.enote.api.res.RESPONSE_STATUS;
import pck.enote.api.res.SendFileRes;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.ResourceBundle;

public class InputTextViewController implements Initializable {
    public TextField tfNoteName;
    public TextArea taContent;
    public Button btnUploadText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void uploadText(ActionEvent ae) {
        if (ae.getSource() == btnUploadText) {
            try {
                System.out.println(getClass() + ": btnUploadText onclick");

                File file = new File(tfNoteName.getText().length() == 0 ? "noname" : tfNoteName.getText() + ".txt");
                file.deleteOnExit();
                FileUtils.writeStringToFile(file, taContent.getText(), StandardCharsets.UTF_8);

                SendFileRes res = (SendFileRes) API.sendReq(new SendFileReq(file));

                assert res != null;
                System.out.println(res);
                if (res.getStatus() == RESPONSE_STATUS.SUCCESS) {
                    System.out.println("Upload text successfully" + res.getFileUrl());

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Thông báo");
                    alert.setHeaderText("Tải lên text thàng công");
                    alert.showAndWait();

                    ((Node)(ae.getSource())).getScene().getWindow().hide();
                } else {
                    System.out.println("Upload text failed" + res.getFileUrl());

                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Thông báo");
                    alert.setHeaderText("Tải lên text thất bại, vui lòng thử lại");
                    Optional<ButtonType> option = alert.showAndWait();
                }
            } catch (Exception e) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Thông báo");
                alert.setHeaderText("Có lỗi bất ngờ, không thử tải lên file");
                alert.showAndWait();
            }
        }
    }
}
