
/**
 * Created by Stephen on 2/22/16.
 * Generic helper function. Contains functions that
 * are commonly used for conversions such as hex to binary
 *
 * @author Stephen
 */
public final class Helpers {

    /**
     * Contains a private instructor to ensure that no one can initiate this static class.
     */
    private Helpers(){}

    public static int convertFromHexToDecimal(String hex)
    {
        return Integer.parseInt(hex, 16);
    }

    public static String convertFromDecimalToHex(Integer dec)
    {
        return Integer.toHexString(dec);
    }

    public static String convertFromHexStringToBinaryString(String hex){

            long i = Long.parseLong(hex, 16);
            String bin = Long.toBinaryString(i);

            while(bin.length() != 32)
            {
                bin = "0" + bin;
            }

            return bin;
    }

    public static int convertFromBinaryStringToDecimalInteger(String bin)
    {
        int i = Integer.parseInt(bin, 2);
        return i;
    }
    public static int getPageNumberFromAddress(int address)
    {
        return address/RAM.getPageSize();
    }
}
