package FILE;

import CONTACT.Contact;
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

    private static Contact[] contacts = {};

    public static Contact[] readVCF(File file){

        try {

            BufferedReader br = new BufferedReader(new FileReader(file));

            /**
             * All the vCars which are maybe in the opened file, will
             * be saved in a list.
             */
            List<VCard> vCard = Ezvcard.parse(br).all();
            contacts = new Contact[vCard.size()];

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

                    contacts[i] = new Contact(fullName, lastName, address, numbers, mails);
                } catch(NullPointerException npe){
                    npe.printStackTrace();
                } catch (IndexOutOfBoundsException ioobe){
                    ioobe.printStackTrace();
                }
            }
            return contacts;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return contacts;
    }

    public static Contact[] getContacts(){
        return contacts;
    }

    public static String getExtension(File file){
        String url = file.getAbsolutePath();

        String tmp = url.substring(url.length() - 5, url.length());
        String extension = "";

        for(int i = 0; i < tmp.length(); i++){
            if(tmp.charAt(i) == '.'){
                extension = tmp.substring(i, tmp.length());
            }
        }

        return extension;
    }
}
