package FILE;

import CONTACT.Contact;
import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.VCardVersion;
import ezvcard.io.text.VCardWriter;
import ezvcard.property.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by Marco on 19.03.2016.
 */
public class saveVCFFile {

    public static void saveFile(File savedFile, List<Contact> phoneBook){


        Collection<VCard> vCards = new LinkedList<>();

        for(int i = 0; i < phoneBook.size(); i++){
            try {
                VCard tmp = new VCard();
                FormattedName fullName = new FormattedName(phoneBook.get(i).getFullName());
                Address address = new Address();
                Email email = new Email(phoneBook.get(i).getEmail(0));
                Telephone telephone = new Telephone(phoneBook.get(i).getTelephoneNumber(0));
                try {
                    address.setStreetAddress(phoneBook.get(i).getAddress(0));
                } catch (NullPointerException e){
                    e.printStackTrace();
                }
                tmp.addFormattedName(fullName);
                tmp.addAddress(address);
                tmp.addEmail(email);
                tmp.addTelephoneNumber(telephone);

                vCards.add(tmp);
            } catch (NullPointerException e){
                e.printStackTrace();
            }
        }

        try {
            Ezvcard.write(vCards).go(savedFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
