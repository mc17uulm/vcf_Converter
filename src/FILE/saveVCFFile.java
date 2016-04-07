package FILE;

import CONTACT.Contact;
import ezvcard.VCard;
import ezvcard.VCardVersion;
import ezvcard.io.text.VCardWriter;
import ezvcard.property.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Marco on 19.03.2016.
 */
public class saveVCFFile {

    public static void saveFile(File savedFile, Contact[] contacts){


        List<VCard> vCards = null;
        System.out.println(Arrays.toString(contacts));

        for(int i = 0; i < contacts.length; i++){
            try {
                VCard tmp = new VCard();
                FormattedName name = null;
                Address address = null;
                Email email = null;
                Telephone telephone = null;
                try {
                    name.setValue(contacts[i].getFullName());
                } catch (NullPointerException e){
                    e.printStackTrace();
                }
                try {
                    address.setStreetAddress(contacts[i].getAddress(0));
                } catch (NullPointerException e){
                    e.printStackTrace();
                }
                try {
                    email.setValue(contacts[i].getEmail(0));
                } catch (NullPointerException e){
                    e.printStackTrace();
                }
                try {
                    telephone.setText(contacts[i].getTelephoneNumber(0));
                } catch (NullPointerException e){
                    e.printStackTrace();
                }
                tmp.addFormattedName(name);
                tmp.addAddress(address);
                tmp.addEmail(email);
                tmp.addTelephoneNumber(telephone);

                vCards.add(tmp);
                System.out.println(i);
            } catch (NullPointerException e){
                e.printStackTrace();
            }
        }

        VCardWriter writer = null;
        /**
        try {
            writer = new VCardWriter(savedFile, VCardVersion.V4_0);
            for(VCard vcard : vCards){
                vcard.write();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e){

            }
        }*/
    }
}
