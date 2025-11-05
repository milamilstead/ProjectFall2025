import edu.desu.utilities.NameGenerator;
import edu.desu.utilities.UtilityMethodsArrays;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class KVStringPairSortedArray {
    public String keyTitle;
    public String valueTitle;
    public int datasize = 0;
    public KVStringPair[] dataArray;

    // Book example constructor
    public KVStringPairSortedArray() {
        keyTitle = "ID";
        valueTitle = "Name";
        Random random = new Random(54321);
        int[] data = {2, 3, 5, 7, 11, 13, 17, 19, 23, 28, 31, 37, 39};
        datasize = data.length;
        dataArray = new KVStringPair[datasize];
        for (int i = 0; i < datasize; i++) {
            String thisKey = Integer.toString(data[i]);
            String thisValue = NameGenerator.generateRandomName(random);
            KVStringPair thisKVStringPair = new KVStringPair(thisKey, thisValue);
            dataArray[i] = thisKVStringPair;
        }
    }

    // Maximum size array constructor
    public KVStringPairSortedArray(int size) {
        keyTitle = "ID";
        valueTitle = "Name";
        Random random = new Random(54321);
        int[] data = UtilityMethodsArrays.generateSequentialIntArray(size);
        datasize = data.length;
        dataArray = new KVStringPair[datasize];
        for (int i = 0; i < datasize; i++) {
            String thisKey = Integer.toString(data[i]);
            String thisValue = NameGenerator.generateRandomName(random);
            KVStringPair thisKVStringPair = new KVStringPair(thisKey, thisValue);
            dataArray[i] = thisKVStringPair;
        }
    }

    // This part reads from a CSV file
    public KVStringPairSortedArray(String fileName) {
        // Read the CSV file data
        String line;
        String csvSplitBy = ",";
        int lineno = 0;
        String data[];
        int elemcount = 0;
        KVStringPair thisKeyValueStringPair;
        // Future: read size of input .csv
        // Allocate the array size to be the number of lines
        dataArray = new KVStringPair[100000];
        try (BufferedReader br =
                     new BufferedReader(new FileReader(fileName))) {
            while ((line = br.readLine()) != null) {
                if (lineno == 0) {
                    // Save the header
                    data = line.split(",", 2);
                    if (data.length == 2) {
                        keyTitle = data[0];
                        valueTitle = data[1];
                    }
                }
                else if (lineno > 0) {
                    data = line.split(",", 2);
                    if (data.length == 2) {
                        String keyInput = data[0].trim().replace("\"", "");
                        String valueInput = "Line " + lineno + ": " + data[1].trim();
                        KVStringPair thisKVStringPair = new KVStringPair(keyInput, valueInput);
                        dataArray[elemcount] = thisKVStringPair;
                        elemcount++;
                    }
                }
                lineno++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        datasize = elemcount;
        dataArray = KVStringPairUtils.sortAndMergeDuplicateKeys(dataArray, datasize);
        // Now reset datasize to the length of the new array
        // since it's been resized
        datasize = dataArray.length;
    }

    public static void test1() {
        // Pass the Class object for KVStringPair to the constructor
        KVStringPairSortedArray ourKVStringPairSortedArray = new KVStringPairSortedArray();
        String outputString;
        for (int i = 0; i < ourKVStringPairSortedArray.datasize; i++) {
            outputString = ourKVStringPairSortedArray.dataArray[i].toString();
            System.out.println(outputString);
        }
    }

    public static void test2() {
        // Pass the Class object for KVStringPair to the constructor
        KVStringPairSortedArray ourKVStringPairSortedArray = new KVStringPairSortedArray(100000);
        String outputString;
        for (int i = 0; i < 3; i++) {
            outputString = ourKVStringPairSortedArray.dataArray[i].toString();
            System.out.println(outputString);
        }
        System.out.println("Array size is: " + ourKVStringPairSortedArray.datasize);
    }

    // Test with .csv file
    public static void test3() {
        String fileName = "DataFiles/ThirdPartyDataCSVFiles/TopSongs5000JustMichaelEltonCher.csv";
        KVStringPairSortedArray ourKVStringPairSortedArray = new KVStringPairSortedArray(fileName);
        String outputString;
        int arraysize = ourKVStringPairSortedArray.datasize;
        for (int i = 0; i < arraysize; i++) {
            outputString = ourKVStringPairSortedArray.dataArray[i].toString();
            System.out.println(outputString);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;
        do {
            System.out.println("\nEnter a number (1 or 2) to call a method, or 'Q' to quit:");
            System.out.println("1 is the book example, 2 is a large array, 3 is a datafile.");
            input = scanner.nextLine();

            switch (input.toUpperCase()) {
                case "1":
                    test1();
                    break;
                case "2":
                    test2();
                    break;
                case "3":
                    test3();
                    break;
                case "Q":
                    System.out.println("Exiting program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid input. Please enter '1', '2', or 'Q'.");
                    break;
            }
        } while (!input.equalsIgnoreCase("Q"));
        scanner.close();
    }
}
