package SPLASH;

import com.sun.javafx.tk.Toolkit;
import com.sun.org.apache.xml.internal.security.Init;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import javax.imageio.IIOException;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

/**
 * Created by Marco on 22.09.2015.
 */
public class MainLoader implements Initializable{

    public static CheckBox startBox;
    @FXML private ProgressBar progressLoader = new ProgressBar();
    @FXML private Label statusLabel = new Label("Start");

    public static Scene scene;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        // TODO
    }

    public void load(ActionEvent actionEvent) {
        progressLoader.setVisible(true);
        Task task = createTask();
        progressLoader.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }

    private Task<Void> createTask(){
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for (double i = 0; i < 3; i = i + 0.1){
                    if(isCancelled()){
                        break;
                    }
                    updateProgress(i, 3);
                    updateMessage("Loading ...");
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException ex){
                        return null;
                    }
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Parent root = null;
                        System.out.println("DONE!");
                        try {
                            root = (Parent) FXMLLoader.load(getClass().getResource("design.fxml"));
                        } catch (IOException ex) {

                        }
                        if(scene == null){
                            scene = new Scene(root);
                            Loader.primaryStage.setScene(scene);
                        } else {
                            Loader.primaryStage.getScene().setRoot(root);
                        }
                        Loader.primaryStage.show();
                    }
                });
                progressLoader.setVisible(false);
                updateMessage(0 + "");
                updateProgress(3, 3);
                return null;
            }
        };
    }

}
