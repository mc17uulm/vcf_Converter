package GUI;

import ezvcard.property.Telephone;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.Dialog;
import java.awt.print.Book;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.GregorianCalendar;

import FILE.*;
import javafx.util.Callback;

public class Controller {

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
        filter = new FileChooser.ExtensionFilter("Contact Files", "*.vcf");

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

        Log.writeLog("File opened: " + inputFile.getAbsolutePath());

        /**
         * The method makeTable, will create the table which is displayed at the
         * programm.
         */
        makeTable(inputFile);
    }

    public void makeTable(File inputFile){

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
            readFile.readVCF(inputFile);

            /**
             * Now we get the arrays which were made with the above method
             */
            String[][] contactsFirst = readFile.getContactsFormatted();
            java.util.List<Telephone>[][] numbers = readFile.getContactsFormattedList();

            /**
             * We reset the currentList. Maybe the user wants to edit more files,
             * and so the programm works clean
             */
            currentList.removeAll(currentList);

            /**
             * With this loop we work trough the contacts
             */
            for(int i = 0; i < contactsFirst[0].length; i++){

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
                    currentList.add(new Record(contactsFirst[0][i], numbers[1][i].get(0).getText(), true));


                } catch (IndexOutOfBoundsException ioobe){

                } catch (NullPointerException npe){

                }
            }

            /**
             * This is used to get the columns and to add them the right list.
             */
            namColumn.setCellValueFactory(new PropertyValueFactory<Record, String>("fieldName"));
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
        saveCSVFile.saveFile(savedFile);
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
        Log.writeLog("Language changed to: German");
    }

    public void changeOptions(ActionEvent actionEvent) {
    }
}
