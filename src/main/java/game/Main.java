package game;

import javafx.application.Application;
import org.tinylog.Logger;

public class Main {
    public static void main(String[] args) {
        Logger.info("Application launched");
        Application.launch(GameApplication.class, args);
    }
}
