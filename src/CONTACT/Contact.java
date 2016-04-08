package CONTACT;

import java.util.List;

/**
 * Created by Marco on 10.03.2016.
 */
public class Contact {

    private String fullName;
    private List<StreetAddress> addressList;
    private List<Phone> telephoneList;
    private List<Mail> emailList;

    public Contact(String fullName, List<StreetAddress> addressList, List<Phone> telephoneList, List<Mail> emailList){
        this.fullName = fullName;
        this.addressList = addressList;
        this.telephoneList = telephoneList;
        this.emailList = emailList;
    }

    @Override
    public String toString() {
        String out = "Name: " + fullName;

        if(!telephoneList.isEmpty()){
            out += "\r\nTelephone Number: " + telephoneList.get(0).toString();
        }
        if(!emailList.isEmpty()){
            out += "\r\nMail: " + emailList.get(0).toString();
        }
        if(!addressList.isEmpty()){
            out += "\r\nAddress: " + addressList.get(0).toString();
        }
        return out;
    }

    public String getFullName(){
        return fullName;
    }

    public List<StreetAddress> getAddressList(){
        return addressList;
    }

    public String getAddress(int index){
        try {
            return addressList.get(index).getAddress();
        } catch (IndexOutOfBoundsException e){
            return "";
        }
    }

    public List<Phone> getTelephoneList(){
        return telephoneList;
    }

    public String getTelephoneNumber(int index){
        try {
            return telephoneList.get(index).getNumber();
        } catch (IndexOutOfBoundsException e){
            return "";
        }
    }

    public List<Mail> getEmailList(){
        return emailList;
    }

    public String getEmail(int index){
        try{
            return emailList.get(index).getAddress();
        } catch (IndexOutOfBoundsException e){
            return "";
        }
    }

    public void changeFullName(String fullName) {
        this.fullName = fullName;
    }

    public void changeAddress(String address, int index){
        this.addressList.get(index).setAddress(address);
    }

    public void changeTelephoneNumber(String telephoneNumber, int index){
        this.telephoneList.get(index).setNumber(telephoneNumber);
    }

    public void changeEmailAddress(String email, int index){
        this.emailList.get(index).setAddress(email);
    }

    public void addAddress(String address){
        this.addressList.add(new StreetAddress(address));
    }

    public void addTelephoneNumber(String telephoneNumber){
        this.telephoneList.add(new Phone(telephoneNumber));
    }

    public void addEmailAddress(String email){
        this.emailList.add(new Mail(email));
    }
}
