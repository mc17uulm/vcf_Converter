package GUI;

import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Created by Marco on 19.09.2015.
 * This class is just for displaying the contact
 * informations on the guy
 */
public class Record{

    /**
     * The two parameters which will displayed at the gui table
     */
    private SimpleStringProperty fieldName;
    private SimpleObjectProperty fieldNumber;
    private BooleanProperty registered;

    /**
     * Constructor, which defines the name and the number
     * @param fName
     * @param fNumber
     * @param registered
     */
    public Record(String fName, Object fNumber, boolean registered){
        this.fieldName = new SimpleStringProperty(fName);
        this.fieldNumber = new SimpleObjectProperty<>(fNumber);
        this.registered = new SimpleBooleanProperty(registered);
    }

    /**
     * Standart toString method. Not used in the program.
     * @return String
     */
    @Override
    public String toString() {
        return "Record{" +
                "fieldName=" + fieldName +
                ", fieldNumber=" + fieldNumber +
                '}';
    }

    public String getFieldName() {
        return fieldName.get();
    }

    public void setFieldName(String fieldName){
        this.fieldName.set(fieldName);
        System.out.println("RECORD: " + fieldName);
    }

    public SimpleStringProperty getFieldNameProperty() { return fieldName; }

    public String getFieldNumber() {
        return (String) fieldNumber.getValue();
    }

    public void setFieldNumber(String fieldNumber){
        this.fieldNumber.set(fieldNumber);
    }

    public SimpleObjectProperty getFieldNumberProperty(){ return fieldNumber; }

    public boolean getRegistered() {
        return registered.get();
    }

    public void setRegistered(boolean registered){
        this.registered.setValue(registered);
    }

    public BooleanProperty registeredProperty() {
        return registered;
    }
}
