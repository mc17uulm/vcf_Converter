package GUI;

import FILE.Log;
import FILE.saveVCFFile;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

    public static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;

        Parent root = FXMLLoader.load(getClass().getResource("design.fxml"));
        try {
            primaryStage.getIcons().add(new Image("file:lib/icon.png"));
        } catch (IllegalArgumentException iae){
            iae.printStackTrace();
        }
        primaryStage.setTitle("VCF Converter 0.1");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
        primaryStage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if(/**Controller.getChanged()*/false){
                            primaryStage.show();
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Unsaved Changes");
                            alert.setHeaderText("Will you save your changes before closing?");

                            ButtonType yes = new ButtonType("Yes");
                            ButtonType no = new ButtonType("No");
                            ButtonType cancel = new ButtonType("Cancel");

                            alert.getButtonTypes().setAll(yes, no, cancel);

                            java.util.Optional<ButtonType> result = alert.showAndWait();
                            if(result.get() == yes){
                                saveVCFFile.saveFile(Controller.getFile(), Controller.getPhoneBook());
                                primaryStage.hide();
                                Log.closeLog();
                                System.exit(0);
                            } else if(result.get() == no) {
                                primaryStage.hide();
                                Log.closeLog();
                                System.exit(0);
                            }
                        } else{
                            Log.closeLog();
                            System.exit(0);
                        }
                    }
                });
            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
