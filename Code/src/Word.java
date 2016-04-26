/**
 * Created by Stephen on 2/12/16.
 */
public class Word {

    public Word(String address, Object value)
    {
        Address = address;
        Value = value;
    }
    public String Address;
    public Object Value;

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public Object getValue() {
        return Value;
    }

    public void setValue(Object value) {
        Value = value;
    }
}
