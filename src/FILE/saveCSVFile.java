package FILE;

import CONTACT.Contact;
import GUI.Controller;
import com.sun.jndi.cosnaming.IiopUrl;
import ezvcard.property.Address;
import ezvcard.property.Email;
import ezvcard.property.Telephone;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Marco on 19.09.2015.
 * This class saves the informations from the vcf File
 * into a csv file, which can be opened by excel and similar
 * programs
 */
public class saveCSVFile {

    /**
     * These Strings define the header of the csv file,
     * the delimiter and the new line seperator.
     */
    private static final String[] FILE_HEADER = {"NAME", "MAIL", "ADDRESS","TELEPHONNUMBER(S)"};
    private static final String[] FILE_HEADER_GERMAN = {"NAME", "E-MAIL", "ADRESSE","TELEFONNUMMER(N)"};
    private static final String COMMA_DELIMITER = ";";
    private static final String NEW_LINE_SEPERATOR = "\n";
    private static boolean successfullWritten = true;

    /**
     * The method which gets the csv file, in which the informations
     * should be written.
     * @param savedFile
     */
    public static void saveFile(File savedFile, Contact[] contacts){

        successfullWritten = true;
        FileWriter fileWriter = null;

        try {

            /**
             * Inintalize the csv file, and add the header and go
             * to a new line
             */
            fileWriter = new FileWriter(savedFile);

            fileWriter.append("NR:");
            fileWriter.append(COMMA_DELIMITER);

            for(int j = 0; j < 4; j++){
                if(Controller.getOptions()[j]){
                    if(Controller.getLanguage() == 0){
                        fileWriter.append(FILE_HEADER[j]);
                        fileWriter.append(COMMA_DELIMITER);
                    } else {
                        fileWriter.append(FILE_HEADER_GERMAN[j]);
                        fileWriter.append(COMMA_DELIMITER);
                    }
                }
            }

            fileWriter.append(NEW_LINE_SEPERATOR);

            /**
             * iterate trough the contacts and create a line for each contact,
             * with the informations: id, name and telephone number
             */

            for(int i = 0; i < contacts.length; i++){
                if(contacts[i].equals(null)){

                } else{
                    fileWriter.append(String.valueOf(i+1));
                    fileWriter.append(COMMA_DELIMITER);

                    if(Controller.getOptions()[0]) {
                        fileWriter.append(contacts[i].getFullName());
                        System.out.println(contacts[i].getFullName());
                        fileWriter.append(COMMA_DELIMITER);
                    }

                    if(Controller.getOptions()[2]){
                        try {
                            if(contacts[i].getEmail(0).equals(null)){
                                fileWriter.append(COMMA_DELIMITER);
                            } else {
                                fileWriter.append(contacts[i].getEmail(0));
                                fileWriter.append(COMMA_DELIMITER);
                            }
                        } catch (IndexOutOfBoundsException we){
                            fileWriter.append(COMMA_DELIMITER);
                        }
                    }

                    if(Controller.getOptions()[3]) {
                        try {
                            if (contacts[i].getAddress(0).equals(null)) {
                                fileWriter.append(COMMA_DELIMITER);
                            } else {
                                fileWriter.append(contacts[i].getAddress(0));
                                fileWriter.append(COMMA_DELIMITER);
                            }
                        } catch (IndexOutOfBoundsException io) {
                            fileWriter.append(COMMA_DELIMITER);
                        }
                    }

                    if(Controller.getOptions()[1]) {
                        for (int k = 0; k < contacts[i].getTelephoneList().size(); k++) {
                            fileWriter.append(contacts[i].getTelephoneNumber(k));
                            fileWriter.append(COMMA_DELIMITER);
                        }
                        fileWriter.append(NEW_LINE_SEPERATOR);
                    }
                }
            }
        } catch (IOException e) {
            try{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("file:lib/icon.png"));
                if(Controller.getLanguage() == 0){
                    alert.setTitle("Error! File not found!");
                    alert.setHeaderText("ERROR!");
                    alert.setContentText("An Error occured. There is a problem with the choosen file");
                } else {
                    alert.setTitle("Fehler! Datei nicht gefunden!");
                    alert.setHeaderText("FEHLER!");
                    alert.setContentText("Es ist ein Fehler aufgetaucht! Es gibt ein Problem mit der ausgew\u00e4hlten Datei");
                }
                successfullWritten = false;
                Log.writeLog("Error file writting! : " + savedFile.getAbsolutePath());
                alert.showAndWait();
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException ex){
            }
        } catch (NullPointerException npe){
        }  finally {
            if (successfullWritten) {
                try {

                    /**
                     * After the file is written, we give the user the confirmation,
                     * that the file was created successfully.
                     */
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("file:lib/icon.png"));
                    if (Controller.getLanguage() == 0) {
                        alert.setTitle("File saved!");
                        alert.setHeaderText("File saved successfully");
                        alert.setContentText("Saved under: " + savedFile.getAbsolutePath());
                    } else {
                        alert.setTitle("Datei gespeichert!");
                        alert.setHeaderText("Datei erfolgreich gespeichert!");
                        alert.setContentText("Gespeichert unter: " + savedFile.getAbsolutePath());
                    }
                    alert.showAndWait();
                    Log.writeLog("File successfully saved under: " + savedFile.getAbsolutePath());
                    /**
                     * Flush and close the writer
                     */
                    fileWriter.flush();
                    fileWriter.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
