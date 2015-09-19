package FILE;

import ezvcard.property.Telephone;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
    private static final String FILE_HEADER = "NR,NAME,TELEFONNUMMER";
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPERATOR = "\n";

    /**
     * The method which gets the csv file, in which the informations
     * should be written.
     * @param savedFile
     */
    public static void saveFile(File savedFile){

        FileWriter fileWriter = null;

        try {

            /**
             * Inintalize the csv file, and add the header and go
             * to a new line
             */
            fileWriter = new FileWriter(savedFile);

            fileWriter.append(FILE_HEADER.toString());

            fileWriter.append(NEW_LINE_SEPERATOR);

            /**
             * get the informations from the vcf file and save them in
             * two arrays
             */
            String[][] contactsFirst = readFile.getContactsFormatted();
            java.util.List<Telephone>[][] numbers = readFile.getContactsFormattedList();

            /**
             * iterate trough the contacts and create a line for each contact,
             * with the informations: id, name and telephone number
             */
            for(int i = 0; i < contactsFirst[0].length; i++){

                fileWriter.append(String.valueOf(i+1));
                fileWriter.append(COMMA_DELIMITER);

                fileWriter.append(contactsFirst[0][i]);
                fileWriter.append(COMMA_DELIMITER);

                fileWriter.append(numbers[1][i].get(0).getText());
                fileWriter.append(NEW_LINE_SEPERATOR);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException npe){
            npe.printStackTrace();
        } finally {
            try {

                /**
                 * After the file is written, we give the user the confirmation,
                 * that the file was created successfully.
                 */
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("File saved!");
                alert.setHeaderText("File saved successfully");
                alert.setContentText("Saved under: " + savedFile.getAbsolutePath());
                alert.showAndWait();

                /**
                 * Flush and close the writer
                 */
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
