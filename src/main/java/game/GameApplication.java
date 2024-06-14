package game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;
import java.util.Objects;

public class GameApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui.fxml"));
        GameController controller;
        try {
            //root = fxmlLoader.load(Objects.requireNonNull(getClass().getResource("/ui.fxml")));
            root = fxmlLoader.load();
        } catch (IOException e) {
            Logger.error("FXML not found");
            throw new RuntimeException(e);
        }

        controller = (GameController) fxmlLoader.getController();
        if (controller==null){
            Logger.error("Controller not found");
            throw new RuntimeException("Controller not found");
        }

        primaryStage.setTitle("Hexagon puzzle");
        Scene scene = new Scene(root);
        //might not be mvc, could prob do from other script

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        controller.postInitialize();
    }
}
