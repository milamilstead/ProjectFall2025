// These are generic constructors and methods for general use

import static edu.desu.utilities.UtilityMethodsString.compareNumericStrings;

public class KVStringPair {
    private String key;
    private String value;

    public KVStringPair(String key, String value) {
        this.key = key;
        this.value = value;
    }

    // Getter methods
    public String getKey() {
        return key;
    }
    public String getValue() { return value; };

    // Setter methods
    public void setValue(String value) {
        this.value = value;
    }

    // Return a string for printing (as single line - formatted)
    @Override
    public String toString() {
        String outputString = "Key: " + this.key + '\n' + "Value: " + this.value;
        return outputString;
    }

    // compareTo to defines the default "natural" ordering of portfolio holdings
    // based on the String for Key
    public int compareTo(KVStringPair p2) {
        // Get keys and use those for compare
        String str1 = this.getKey();
        String str2 = p2.getKey();
        return compareNumericStrings(str1, str2);
    }

    public static void main(String[] args) {
        KVStringPair myKVPair = new KVStringPair("1", "Fred");
        System.out.println(myKVPair.toString());
    }
}
