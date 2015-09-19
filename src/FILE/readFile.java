package FILE;

import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.property.Address;
import ezvcard.property.Email;
import ezvcard.property.Telephone;

import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Marco on 04.09.2015.
 * This class reads the informations from the specific
 * files and saves the informations into arrays.
 *
 * The methods to read and write the vcf files are form
 * the ezvcard Package, which is under following license:
 *
 * Downloaded here:
 */
public class readFile {

    /**
     * In this arrays, we save the informations from each
     * contacts file. Trough the getter, we have access
     * trough other classes.
     */
    private static List[][] contactsFormattedList = null;
    private static String[][] contactsFormatted = null;

    public static boolean readVCF(File file){

        try {

            BufferedReader br = new BufferedReader(new FileReader(file));

            /**
             * All the vCars which are maybe in the opened file, will
             * be saved in a list.
             */
            List<VCard> vCard = Ezvcard.parse(br).all();

            /**
             * Informations form these vCards will be divided in the singular informations
             * in the contactsFormatted array (like name) and the plural informations
             * in the contactsFormattedList array (like numbers, addresses, etc.).
             */
            contactsFormattedList = new List[5][vCard.size()];
            contactsFormatted = new String[2][vCard.size()];

            int j = 0;

            for(int i = 0; i < vCard.size(); i++){
                try {
                    String fullName = vCard.get(i).getFormattedName().getValue();
                    String lastName = vCard.get(i).getStructuredName().getFamily();
                    List<Address> address = vCard.get(i).getAddresses();
                    List<Telephone> numbers = vCard.get(i).getTelephoneNumbers();
                    List<Email> mails = vCard.get(i).getEmails();

                    /**
                     * Save each information in a specific array.
                     */
                    contactsFormatted[0][j] = fullName;
                    contactsFormatted[1][j] = lastName;
                    contactsFormattedList[0][j] = address;
                    contactsFormattedList[1][j] = numbers;
                    contactsFormattedList[2][j] = mails;

                    numbers.get(0).getText();
                    j++;
                } catch(NullPointerException npe){

                } catch (IndexOutOfBoundsException ioobe){
                    j--;
                }
            }
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Getter for the arrays, with the vCard informations
     * @return
     */
    public static List[][] getContactsFormattedList() {
        return contactsFormattedList;
    }

    public static String[][] getContactsFormatted() {
        return contactsFormatted;
    }
}
