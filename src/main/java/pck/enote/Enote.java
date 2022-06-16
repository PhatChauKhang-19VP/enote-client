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

import java.util.Objects;

import static javafx.application.Platform.exit;

public class Enote extends Application {
    private static Enote instance;
    private Stage stage = null;
    private FXMLLoader loader = null;

    public Enote() {
        instance = this;
    }

    public static Enote getInstance() {
        if (instance == null) {
            instance = new Enote();
        }

        return instance;
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        Platform.runLater(() -> {
            stage = primaryStage;
            try {
                Parent parent = replaceSceneContent("hello-view.fxml", ScreenConfig.getWidth(), ScreenConfig.getHeight());
                stage.getIcons().clear();
                stage.getIcons().add(new Image(ScreenConfig.getLogoPath()));
                stage.setTitle(ScreenConfig.getTitle());
                stage.setResizable(false);
                stage.setFullScreen(false);
            } catch (Exception e) {
                e.printStackTrace();
                exit();
            }

        });
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