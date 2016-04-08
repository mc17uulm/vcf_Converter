package GUI;

import javafx.beans.InvalidationListener;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import sun.java2d.pipe.SpanShapeRenderer;

/**
 * Created by Marco on 19.09.2015.
 * This class is just for displaying the contact
 * informations on the gui
 */
public class Record{

    private SimpleIntegerProperty fieldId;
    private SimpleStringProperty fieldName;
    private SimpleObjectProperty fieldNumber;
    private SimpleStringProperty fieldMail;
    private SimpleStringProperty fieldAddress;

    public Record(int fId, String fName, Object fNumber, String fMail, String fAddress){
        this.fieldId = new SimpleIntegerProperty(fId);
        this.fieldName = new SimpleStringProperty(fName);
        this.fieldNumber = new SimpleObjectProperty<>(fNumber);
        this.fieldMail = new SimpleStringProperty(fMail);
        this.fieldAddress = new SimpleStringProperty(fAddress);
    }

    @Override
    public String toString() {
        return "Record{" +
                "fieldName=" + fieldName +
                ", fieldNumber=" + fieldNumber +
                ", fieldID=" + fieldId +
                '}';
    }

    public String getInformations(){
        return "Name: " + fieldName.get() +
                " | Number: " + fieldNumber.get() + "\r\n";
    }

    public int getID(){ return fieldId.get(); }

    public void setFieldId(int fieldId) { this.fieldId.set(fieldId);}

    public SimpleIntegerProperty getFieldIdProperty() { return fieldId; }

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

    public String getFieldMail(){ return fieldMail.get(); }

    public void setFieldMail(String fieldMail){ this.fieldMail.set(fieldMail); }

    public SimpleStringProperty getFieldMailProperty() { return  fieldMail; }

    public String getFieldAddress() { return fieldAddress.get(); }

    public void setFieldAddress(String fieldAddress) { this.fieldAddress.set(fieldAddress); }

    public SimpleStringProperty getFieldAddressProperty() { return fieldAddress; }
}
