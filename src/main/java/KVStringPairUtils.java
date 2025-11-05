import java.util.Arrays;

public class KVStringPairUtils {

    public static KVStringPair[] sortAndMergeDuplicateKeys(KVStringPair[] inputArray, int size) {
        if (inputArray == null || size <= 0) {
            return new KVStringPair[0]; // Return an empty array for invalid input
        }

        // Step 1: Sort the array by the key in ascending order
        Arrays.sort(inputArray, 0, size, (a, b) -> a.getKey().compareTo(b.getKey()));

        // Step 2: Merge duplicate keys and shift elements to remove duplicates
        int uniqueCount = 0; // Tracks the count of unique KVStringPair elements
        for (int i = 0; i < size; i++) {
            if (uniqueCount == 0 || !inputArray[uniqueCount - 1].getKey().equals(inputArray[i].getKey())) {
                // If the key is unique, place it in the unique position
                inputArray[uniqueCount] = inputArray[i];
                uniqueCount++;
            } else {
                // If the key is a duplicate, merge the values into the previous unique element
                String mergedValue = inputArray[uniqueCount - 1].getValue() + "\n" + inputArray[i].getValue();
                inputArray[uniqueCount - 1].setValue(mergedValue);
            }
        }

        // Step 3: Copy the unique elements into a new array of exact size
        KVStringPair[] resultArray = new KVStringPair[uniqueCount];
        System.arraycopy(inputArray, 0, resultArray, 0, uniqueCount);

        // Step 4: Return the resulting array
        return resultArray;
    }
}