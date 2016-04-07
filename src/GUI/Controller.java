package GUI;

import CONTACT.Contact;
import ezvcard.property.Telephone;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.Dialog;
import java.awt.print.Book;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.SyncFailedException;
import java.net.URL;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import FILE.*;
import javafx.util.Callback;

public class Controller implements Initializable{

    @FXML private Button saveBtn;
    @FXML private Button deleteBtn;
    @FXML private Button addBtn;
    @FXML private Label informationField;
    @FXML private CheckBox nameInfo;
    @FXML private CheckBox telephoneInfo;
    @FXML private CheckBox mailInfo;
    @FXML private CheckBox addressInfo;
    @FXML private Button applyInfo;
    @FXML private RadioMenuItem germanToggle;
    @FXML private RadioMenuItem englishToggle;
    @FXML private Menu helpMenu;
    @FXML private Menu LanguageMenu;
    @FXML private ToggleGroup Language;
    @FXML private Label vcfFileLabel;
    @FXML private MenuItem deleteMenu;
    @FXML private Menu editMenu;
    @FXML private Menu fileMenu;
    @FXML private Tab vcfTab;
    @FXML private Tab OptionsTab;
    @FXML private MenuItem aboutMenu;
    @FXML private TableColumn activeColumn;
    @FXML private MenuItem openMenu;
    @FXML private MenuItem saveMenu;
    @FXML private MenuItem saveAsMenu;
    @FXML private MenuItem closeMenu;
    @FXML private TableView<Record> tableView = new TableView<>();
    @FXML private TableColumn namColumn;
    @FXML private TableColumn NumberColumn;
    @FXML private TextField fileField;
    @FXML private Button openBtn;
    @FXML private Button saveAsBtn;

    private static final ObservableList<Record> currentList = FXCollections.observableArrayList();
    private Desktop desktop = Desktop.getDesktop();
    private File inputFile;
    private static boolean[] options = new boolean[4];
    private static Contact[] contacts = {};

    /**
     * 0 = English
     * 1 = German
     */
    private static int language = 0;

    /**
     * Here the program gets the direction in which the file chooser starts. As default, the
     * file chooser starts at the desktop.
     */
    private File dir = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "Desktop");
    private FileChooser fileChooser;

    public void initialize(URL fxmlFileLocation, ResourceBundle resources){
        Log.initalize();
        Log.writeLog("Program started!");
        options[0] = true;
        options[1] = true;
    }

    /**
     * If the user clicks on the open button,
     * the fileOpen() methode will executed.
     * @param actionEvent
     */
    public void openFile(ActionEvent actionEvent) {
        fileOpen();
    }

    public static int getLanguage() {
        return language;
    }

    public static boolean[] getOptions() {
        return options;
    }

    public static void setOptions(boolean[] options) {
        Controller.options = options;
    }

    /**
     * This method starts the file chooser an get
     * the choosen file.
     */


    private void fileOpen(){

        fileChooser = new FileChooser();

        /**
         * This file filter only allows .csv and .vcf files
         */
        FileChooser.ExtensionFilter filter;
        filter = new FileChooser.ExtensionFilter("Contact Files", "*.vcf", "*.csv");

        /**
         * Just the normal Open File Dialog
         */
        if(language == 0){
            fileChooser.setTitle("Open Contact File");
        } else{
            fileChooser.setTitle("Kontaktdatei \u00F6ffnen");
        }

        /**
         * Here we set the initial Directory to the Desktop
         */
        fileChooser.setInitialDirectory(dir);

        /**
         * We add the extensions filter which only allows .vcf and .csv files.
         */
        fileChooser.getExtensionFilters().add(filter);

        inputFile = fileChooser.showOpenDialog(openBtn.getScene().getWindow());

        String extension = readFile.getExtension(inputFile);

        System.out.println("Extension: " + extension);

        Log.writeLog("File opened: " + inputFile.getAbsolutePath());

        /**
         * At first we check if the file is empty, then we work
         * with the file.
         */
        if(inputFile != null) {

            /**
             * The file path will be set to the gui.
             */
            fileField.setText(inputFile.getAbsolutePath());

            /**
             * With the readVCF method from our readFile class,
             * we read out the informations from the vcf file
             * and save them in different arrays.
             */
            if(extension.equals(".vcf")) {
                contacts = readFile.readVCF(inputFile);
            } else if(extension.equals(".csv")){

            }

            /**
             * We reset the currentList. Maybe the user wants to edit more files,
             * and so the programm works clean
             */
            currentList.removeAll(currentList);

            /**
             * With this loop we work trough the contacts
             */
            for(int i = 0; i < contacts.length; i++){

                /**
                 * Because there could be empty or damaged contacts, so
                 * we just use the try-catch-block
                 */
                try {

                    /**
                     * To the obserable list, which is used to display the table,
                     * we add each contact.
                     *
                     * First we get the name of the contact from the contactsFirst array
                     * Then we get the numbers list from the numbers array, and get just
                     * the first number (just to display, in the converted file, there will
                     * be all numbers.
                     */
                    currentList.add(new Record(contacts[i].getFullName(), contacts[i].getTelephoneNumber(0), true));


                } catch (IndexOutOfBoundsException ioobe){

                } catch (NullPointerException npe){

                }
            }

            /**
             * This is used to get the columns and to add them the right list.
             */
            namColumn.setCellValueFactory(new PropertyValueFactory<Record, String>("fieldName"));
            namColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            NumberColumn.setCellValueFactory(new PropertyValueFactory<Record, String>("fieldNumber"));
            activeColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Record, Boolean>, ObservableValue<Boolean>>() {
                @Override
                public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Record, Boolean> param) {
                    return param.getValue().registeredProperty();
                }
            });
            activeColumn.setCellFactory(CheckBoxTableCell.forTableColumn(activeColumn));

            /**
             * The observable list is added to the table
             */
            tableView.setItems(currentList);
        }
    }

    public void saveAs(ActionEvent actionEvent) {
        saveOptions();
    }

    /**
     * The same as the open Button
     * @param actionEvent
     */
    public void openMenu(ActionEvent actionEvent) {
        fileOpen();
    }

    public void saveMenu(ActionEvent actionEvent) {
        saveDirect();
    }

    public void saveAsMenu(ActionEvent actionEvent) {
        saveOptions();
    }


    /**
     * Standart: Program gets closed.
     * @param actionEvent
     */
    public void close(ActionEvent actionEvent) {
        for(int i = 0; i < 2; i++) {
            if (i == 0) {
                Log.closeLog();
            } else {
                System.exit(0);
            }
        }
    }

    public void about(ActionEvent actionEvent) throws Exception{
        Stage aboutStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("about.fxml"));
        try {
            aboutStage.getIcons().add(new javafx.scene.image.Image("file:lib/icon.png"));
        } catch (IllegalArgumentException iae){
            iae.printStackTrace();
        }
        aboutStage.setTitle("VCF Converter 0.1 | About");
        aboutStage.setScene(new Scene(root, 400, 300));
        aboutStage.show();
    }

    public void saveOptions(){
        FileChooser saveOpt = new FileChooser();
        if(language == 0) {
            saveOpt.setTitle("Save As ...");
        } else{
            saveOpt.setTitle("Speichern unter ...");
        }
        saveOpt.setInitialDirectory(dir);
        saveOpt.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Contact Files", "*.csv")
        );

        File savedFile = saveOpt.showSaveDialog(saveAsBtn.getScene().getWindow());
        saveCSVFile.saveFile(savedFile, contacts);
    }

    public void saveDirect(){
    }

    public void english(ActionEvent actionEvent) {
        language = 0;
        openBtn.setText("Open...");
        saveAsBtn.setText("Save As .csv");
        fileMenu.setText("File");
        openMenu.setText("Open");
        saveMenu.setText("Save");
        saveAsMenu.setText("Save As...");
        closeMenu.setText("Close");
        editMenu.setText("Edit");
        deleteMenu.setText("Delete");
        helpMenu.setText("Help");
        aboutMenu.setText("About");
        LanguageMenu.setText("Language");
        englishToggle.setText("English");
        germanToggle.setText("German");
        vcfFileLabel.setText(".vcf File:");
        NumberColumn.setText("Number");
        activeColumn.setText("Active");
        vcfTab.setText("vcf to csv");
        OptionsTab.setText("Options");
        informationField.setText("Informationes:");
        telephoneInfo.setText("Telephonnumber(s)");
        addressInfo.setText("Address");
        mailInfo.setText("E-Mail-Address");
        applyInfo.setText("Apply");
        Log.writeLog("Language changed to: English");
    }

    public void german(ActionEvent actionEvent) {
        language = 1;
        openBtn.setText("\u00D6ffnen...");
        saveAsBtn.setText("Speichern als .csv");
        fileMenu.setText("Datei");
        openMenu.setText("\u00D6ffnen");
        saveMenu.setText("Speichern");
        saveAsMenu.setText("Speichern unter...");
        closeMenu.setText("Schlie\u00DFen");
        editMenu.setText("Bearbeiten");
        deleteMenu.setText("L\u00F6schen");
        helpMenu.setText("Hilfe");
        aboutMenu.setText("\u00DCber");
        LanguageMenu.setText("Sprache");
        englishToggle.setText("Englisch");
        germanToggle.setText("Deutsch");
        vcfFileLabel.setText(".vcf Datei:");
        NumberColumn.setText("Nummer");
        activeColumn.setText("Auswahl");
        vcfTab.setText("vcf zu csv");
        OptionsTab.setText("Einstellungen");
        informationField.setText("Informationen:");
        telephoneInfo.setText("Telefonnummer(n)");
        addressInfo.setText("Adresse");
        mailInfo.setText("E-Mail-Adresse");
        applyInfo.setText("Speichern");
        Log.writeLog("Language changed to: German");
    }

    public void changeOptions(ActionEvent actionEvent) {
        if(nameInfo.isSelected()){
            options[0] = true;
        } else {
            options[0] = false;
        }
        if(telephoneInfo.isSelected()){
            options[1] = true;
        } else {
            options[1] = false;
        }
        if(mailInfo.isSelected()){
            options[2] = true;
        } else {
            options[2] = false;
        }
        if(addressInfo.isSelected()){
            options[3] = true;
        } else {
            options[3] = false;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("file:lib/icon.png"));
        if (Controller.getLanguage() == 0) {
            alert.setTitle("Options saved!");
            alert.setHeaderText("Success!");
            alert.setContentText("All options are saved!");
        } else {
            alert.setTitle("Einstellungen gespeichert!");
            alert.setHeaderText("Erfolg!");
            alert.setContentText("Alle Einstellungen wurden gespeichert!");
        }
        alert.showAndWait();
        Log.writeLog("Options changed: ");
    }

    public void addContact(ActionEvent actionEvent) {

    }

    public void deleteContact(ActionEvent actionEvent) {

    }

    public void editName(TableColumn.CellEditEvent<Record, String> stCellEditEvent) {
        saveBtn.setDisable(false);
        ((Record) stCellEditEvent.getTableView().getItems().get(stCellEditEvent.getTablePosition().getRow())).setFieldName(stCellEditEvent.getNewValue());
        contacts[stCellEditEvent.getTablePosition().getRow()].changeFullName(stCellEditEvent.getNewValue());
        System.out.println("Value After: " + stCellEditEvent.getNewValue());
    }

    public void editNumber(TableColumn.CellEditEvent<Record, String> stCellEditEvent) {
        saveBtn.setDisable(false);
        ((Record) stCellEditEvent.getTableView().getItems().get(stCellEditEvent.getTablePosition().getRow())).setFieldName(stCellEditEvent.getNewValue());
        contacts[stCellEditEvent.getTablePosition().getRow()].changeTelephoneNumber(stCellEditEvent.getNewValue(), 0);
        System.out.println("Value After: " + stCellEditEvent.getNewValue());
    }

    public void saveFile(ActionEvent actionEvent) {
        saveVCFFile.saveFile(inputFile, contacts);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("file:lib/icon.png"));
        if (Controller.getLanguage() == 0) {
            alert.setTitle("File saved!");
            alert.setHeaderText("Success!");
            alert.setContentText("Saved under: " + inputFile.getAbsolutePath());
        } else {
            alert.setTitle("Datei gespeichert!");
            alert.setHeaderText("Erfolg!");
            alert.setContentText("Gespeichert unter: " + inputFile.getAbsolutePath());
        }
        alert.showAndWait();
        saveBtn.setDisable(true);
    }
}
