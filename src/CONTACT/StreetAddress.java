package CONTACT;

/**
 * Created by Marco on 08.04.2016.
 */
public class StreetAddress {

    private String address;

    public StreetAddress(String address){
        this.address = address;
    }

    @Override
    public String toString(){
        return address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
