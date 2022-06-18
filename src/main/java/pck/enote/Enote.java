package pck.enote;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
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
                gotoSignUpPage();
            } catch (Exception e) {
                e.printStackTrace();
                exit();
            }

        });
    }

    public void gotoSignInPage() {
        try {
            replaceSceneContent("signInPage.fxml", 600, 600);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void gotoSignUpPage() {
        try {
            replaceSceneContent("signUpPage.fxml", 600, 600);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Parent replaceSceneContent(String fxml, int width, int height) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml), null, new JavaFXBuilderFactory());

        Parent page = loader.load();

        this.loader = loader;

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
}