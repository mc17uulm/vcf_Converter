package GUI;

import FILE.Log;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("design.fxml"));
        try {
            primaryStage.getIcons().add(new Image("file:lib/icon.png"));
        } catch (IllegalArgumentException iae){
            iae.printStackTrace();
        }
        primaryStage.setTitle("VCF Converter 0.1");
        primaryStage.setScene(new Scene(root, 600, 450));
        primaryStage.show();
        primaryStage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Log.closeLog();
                        System.exit(0);
                    }
                });
            }
        });
        Log.initalize();
        Log.writeLog("Program started!");
    }


    public static void main(String[] args) {
        launch(args);
    }
}
