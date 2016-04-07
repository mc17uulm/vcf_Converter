package FILE;

import CONTACT.Contact;
import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.property.Address;
import ezvcard.property.Email;
import ezvcard.property.Telephone;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
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

    private static List<Contact> phoneBook = new LinkedList<>();

    private static Contact[] contacts = {};

    public static List<Contact> readVCF(File file){

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
                    String fullName = "";
                    List<Address> address = new LinkedList<>();
                    List<Telephone> numbers = new LinkedList<>();
                    List<Email> mails = new LinkedList<>();
                    try {
                        fullName = vCard.get(i).getFormattedName().getValue();
                    } catch (NullPointerException e){}
                    try {
                        address = vCard.get(i).getAddresses();
                    } catch (NullPointerException e){}
                    try {
                        numbers = vCard.get(i).getTelephoneNumbers();
                    } catch (NullPointerException e){}
                    try {
                        mails = vCard.get(i).getEmails();
                    } catch (NullPointerException e){}

                    /**
                     * Save each information in a specific array.
                     */

                    phoneBook.add(new Contact(fullName, address, numbers, mails));
                    contacts[i] = new Contact(fullName, address, numbers, mails);
                } catch(NullPointerException npe){
                    npe.printStackTrace();
                } catch (IndexOutOfBoundsException ioobe){
                    ioobe.printStackTrace();
                }
            }
            return phoneBook;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return phoneBook;
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
