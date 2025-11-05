package edu.desu.utilities;

public class UtilityMethodsString {

    public static int compareNumericStrings(String str1, String str2) {
        try {
            // Attempt to parse the strings as integers
            int num1 = Integer.parseInt(str1);
            int num2 = Integer.parseInt(str2);
            // Compare the parsed integer values
            if (num1 < num2) {
                return -1; // str1 is numerically smaller than str2
            } else if (num1 > num2) {
                return 1;  // str1 is numerically larger than str2
            } else {
                return 0;  // str1 and str2 are numerically equal
            }
        } catch (NumberFormatException e) {
            // Now do the String compare
            // System.out.println("They are strings not integer");
            int intresult = str1.compareTo(str2);
            return intresult;
        }
    }
}
