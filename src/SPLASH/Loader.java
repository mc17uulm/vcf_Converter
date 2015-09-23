package SPLASH;/**
 * Created by Marco on 22.09.2015.
 */

import FILE.Log;
import GUI.Main;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Loader extends Application {

    public static Stage primaryStage;

    public static void main(String[] args) throws Exception { launch(args); }

    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("screen.fxml"));
        try {
            stage.getIcons().add(new Image("file:lib/icon.png"));
        } catch (IllegalArgumentException iae){
            iae.printStackTrace();
        }
        stage.setTitle("VCF Converter 0.1");
        stage.setScene(new Scene(root, 400, 300));
        stage.show();
    }
}
