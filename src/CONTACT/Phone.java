package CONTACT;

/**
 * Created by Marco on 08.04.2016.
 */
public class Phone {

    private String number;

    public Phone(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
