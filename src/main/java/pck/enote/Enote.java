package pck.enote;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import pck.enote.controller.ViewNoteDetailsController;
import pck.enote.controller.ViewNotesController;
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
        launch();
    }

    public static Parent replaceSceneContent(String fxml, int width, int height) throws Exception {
        FXMLLoader loader = new FXMLLoader(Enote.class.getResource(fxml), null, new JavaFXBuilderFactory());

        Parent page = loader.load();

        Enote.loader = loader;

        Scene scene = stage.getScene();
        if (scene == null) {
            scene = new Scene(page, width, height);
            stage.setScene(scene);
        } else {
            stage.getScene().setRoot(page);
        }

        stage.sizeToScene();
        stage.show();
        return page;
    }

    public static void gotoSignUpPage() {
        try {
            replaceSceneContent("signUpPage.fxml", 600, 600);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void gotoSignInPage() {
        try {
            replaceSceneContent("signInPage.fxml", 600, 600);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void gotoViewNotesPage(String username) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    Enote.class.getResource("viewNotes.fxml"),
                    null,
                    new JavaFXBuilderFactory()
            );
            ViewNotesController controller = new ViewNotesController();
            controller.setUsername(username);
            loader.setController(controller);
            Parent page = loader.load();
            Enote.loader = loader;

            Scene scene = stage.getScene();
            if (scene == null) {
                scene = new Scene(page, 800, 600);
                stage.setScene(scene);
            } else {
                stage.getScene().setRoot(page);
            }

            stage.sizeToScene();
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void gotoViewNoteDetailsPage(String username, Integer noteId) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    Enote.class.getResource("viewNoteDetails.fxml"),
                    null,
                    new JavaFXBuilderFactory()
            );
            ViewNoteDetailsController controller = new ViewNoteDetailsController();
            controller.setUsername(username);
            controller.setNoteId(noteId);
            loader.setController(controller);
            Parent page = loader.load();
            Enote.loader = loader;

            Scene scene = stage.getScene();
            if (scene == null) {
                scene = new Scene(page, 800, 600);
                stage.setScene(scene);
            } else {
                stage.getScene().setRoot(page);
            }

            stage.sizeToScene();
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
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

//                gotoSignInPage();
                // gotoSignUpPage();
                gotoViewNotesPage("phat1");
//                gotoViewNoteDetailsPage();
            } catch (Exception e) {
                e.printStackTrace();
                exit();
            }

        });
    }
}