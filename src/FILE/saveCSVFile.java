package FILE;

import ezvcard.property.Telephone;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Marco on 19.09.2015.
 */
public class saveCSVFile {
    public static void saveFile(File savedFile){
        String FILE_HEADER = "NR,NAME,TELEFONNUMMER";
        String COMMA_DELIMITER = ",";
        String NEW_LINE_SEPERATOR = "\n";

        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(savedFile);

            fileWriter.append(FILE_HEADER.toString());

            fileWriter.append(NEW_LINE_SEPERATOR);

            String[][] contactsFirst = readFile.getContactsFormatted();
            java.util.List<Telephone>[][] numbers = readFile.getContactsFormattedList();

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
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("File saved!");
                alert.setHeaderText("File saved successfully");
                alert.setContentText("Saved under: " + savedFile.getAbsolutePath());
                alert.showAndWait();
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
