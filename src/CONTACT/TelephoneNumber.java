package CONTACT;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Marco on 08.04.2016.
 */
public class TelephoneNumber {

    private List number;
    private boolean international;

    public TelephoneNumber(String in) {
        try {
            this.number = encrypt(in);
        } catch (NumberFormatException e){
            this.number = null;
            this.international = false;
        }
    }

    private List encrypt(String in) throws NumberFormatException{
        List tmp = new LinkedList<>();
        for(int i = 0; i < in.length(); i++){
            if(i == 0){
                if(in.charAt(i) == '+'){
                    this.international = true;
                    tmp.add("" + in.charAt(i));
                } else{
                    this.international = false;
                    int num = Integer.parseInt("" + in.charAt(i));
                    tmp.add(num);

                }
            } else{
                int num = Integer.parseInt("" + in.charAt(i));
                tmp.add(num);
            }
        }
        return tmp;

    }
}
