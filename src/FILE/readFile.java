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
            List<VCard> vCard = Ezvcard.parse(br).all();
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

    public static List[][] getContactsFormattedList() {
        return contactsFormattedList;
    }

    public static String[][] getContactsFormatted() {
        return contactsFormatted;
    }
}
