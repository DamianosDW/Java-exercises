package lab4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SeaBattleGame extends Application
{
    private static final String fxmlPath = "main.fxml";

    public static void main(String[] args) {
        // Start app
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("SeaBattle");

        // Create FXMLLoader
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));

        try {
            // Create scene and load main app window to stage
            Scene scene = new Scene(fxmlLoader.load());
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Lock app window size
        primaryStage.setResizable(false);
        // Show app window
        primaryStage.show();
    }
}
