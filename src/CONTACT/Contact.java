package CONTACT;

import ezvcard.property.Address;
import ezvcard.property.Email;
import ezvcard.property.Telephone;

import java.util.List;

/**
 * Created by Marco on 10.03.2016.
 */
public class Contact {

    private String fullName;
    private String lastName;
    private List<Address> addressList;
    private List<Telephone> telephoneList;
    private List<Email> emailList;

    public Contact(String fullName, String lastName, List<Address> addressList, List<Telephone> telephoneList, List<Email> emailList){
        this.fullName = fullName;
        this.lastName = lastName;
        this.addressList = addressList;
        this.telephoneList = telephoneList;
        this.emailList = emailList;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "fullName='" + fullName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", addressList=" + addressList +
                ", telephoneList=" + telephoneList +
                ", emailList=" + emailList +
                '}';
    }

    public String getFullName(){
        return fullName;
    }

    public  String getLastName(){
        return lastName;
    }

    public List<Address> getAddressList(){
        return addressList;
    }

    public String getAddress(int index){
        try {
            return addressList.get(index).getStreetAddress();
        } catch (IndexOutOfBoundsException e){
            return "";
        }
    }

    public List<Telephone> getTelephoneList(){
        return telephoneList;
    }

    public String getTelephoneNumber(int index){
        try {
            return telephoneList.get(index).getText();
        } catch (IndexOutOfBoundsException e){
            return "";
        }
    }

    public List<Email> getEmailList(){
        return emailList;
    }

    public String getEmail(int index){
        try{
            return emailList.get(index).toString();
        } catch (IndexOutOfBoundsException e){
            return "";
        }
    }

    public void changeFullName(String fullName) {
        this.fullName = fullName;
    }

    public void changeLastName(String lastName){
        this.lastName = lastName;
    }

    public void changeAddress(String address, int index){
        this.addressList.get(index).setStreetAddress(address);
    }

    public void changeTelephoneNumber(String telephoneNumber, int index){
        this.telephoneList.get(index).setText(telephoneNumber);
    }

    public void changeEmailAddress(String email, int index){
        this.telephoneList.get(index).setText(email);
    }

    public void addAddress(String address){
        Address tmp = new Address();
        tmp.setStreetAddress(address);
        this.addressList.add(tmp);
    }

    public void addTelephoneNumber(String telephoneNumber){
        Telephone tmp = new Telephone(telephoneNumber);
        this.telephoneList.add(tmp);
    }

    public void addEmailAddress(String email){
        Email tmp = new Email(email);
        this.emailList.add(tmp);
    }
}
