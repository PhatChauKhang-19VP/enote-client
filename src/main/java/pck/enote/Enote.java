package pck.enote;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import pck.enote.controller.ViewNoteDetailsController;
import pck.enote.fe.model.Note;
import pck.enote.helper.ScreenConfig;

import static javafx.application.Platform.exit;

public class Enote extends Application {
    public static Stage stage = null;

    public static FXMLLoader loader = null;

    public static Stage getStage() {
        return stage;
    }

    public static FXMLLoader getLoader() {
        return loader;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void gotoSignUpPage() {
        try {
            replaceSceneContent("signUpPage.fxml", 600, 650);
        } catch (Exception ex) {
            ex.printStackTrace();
            onError(ex.getMessage());
        }
    }

    public static Parent replaceSceneContent(String fxml, int width, int height) throws Exception {
        FXMLLoader loader = new FXMLLoader(Enote.class.getResource(fxml), null, new JavaFXBuilderFactory());

        Parent page = loader.load();

        Enote.loader = loader;

        Scene scene = new Scene(page, width, height);
        stage.setScene(scene);

        stage.sizeToScene();
        stage.show();
        return page;
    }

    public static void gotoSignInPage() {
        try {
            replaceSceneContent("signInPage.fxml", 600, 600);
        } catch (Exception ex) {
            ex.printStackTrace();
            onError(ex.getMessage());
        }
    }

    public static void gotoViewNotesPage() {
        try {
            replaceSceneContent("viewNotes.fxml", 800, 600);
        } catch (Exception ex) {
            ex.printStackTrace();
            onError(ex.getMessage());
        }
    }

    public static void gotoViewNoteDetailsPage(Note note) {
        try {
            replaceSceneContent("viewNoteDetails.fxml", 800, 600);

            ViewNoteDetailsController ctrl = loader.getController();
            ctrl.noteProperty.setValue(note);
        } catch (Exception ex) {
            ex.printStackTrace();
            onError(ex.getMessage());
        }
    }

    @Override
    public void start(Stage primaryStage) {
        Platform.runLater(() -> {
            stage = primaryStage;
            try {
                stage.getIcons().clear();
                stage.getIcons().add(new Image(ScreenConfig.getLogoPath()));
                stage.setTitle(ScreenConfig.getTitle());
                stage.setResizable(false);
                stage.setFullScreen(false);

                // gotoSignInPage();
                // gotoSignUpPage();
                gotoConnectScreen();
                // gotoViewNotesPage();
                // gotoViewNoteDetailsPage(???);
            } catch (Exception e) {
                e.printStackTrace();
                onError(e.getMessage());
                exit();
            }
        });
    }

    public static void gotoConnectScreen() {
        try {
            replaceSceneContent("IPScreen.fxml", 600, 650);
        } catch (Exception ex) {
            ex.printStackTrace();
            onError(ex.getMessage());
        }
    }

    public static void onError(String errMsg){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Thông báo");
        alert.setHeaderText(errMsg);
        alert.showAndWait();
    }
}