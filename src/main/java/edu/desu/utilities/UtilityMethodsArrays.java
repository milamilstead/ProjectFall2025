package edu.desu.utilities;

import java.util.*;

public class UtilityMethodsArrays {

    public static int[] generateSequentialIntArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }
        return array;
    }

    public static void scrambleIntArray(int[] intArray) {
        // AI-generated from Perplexity.ai
        // 1. Convert int[] to Integer[]
        Integer[] integerArray = new Integer[intArray.length];
        for (int i = 0; i < intArray.length; i++) {
            integerArray[i] = intArray[i];
        }
        // 2. Convert Integer[] to a Collection (List)
        List<Integer> integerList = new ArrayList<>(Arrays.asList(integerArray));
        // 3. Shuffle the Collection
        Collections.shuffle(integerList);
        // 4. Convert back to int[]
        int[] scrambledIntArray = new int[integerList.size()];
        for (int i = 0; i < intArray.length; i++) {
            intArray[i] = integerList.get(i);
        }
        intArray = scrambledIntArray;
    }

    public static int[] generateScrambledIntArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }
        scrambleIntArray(array);
        return array;
    }

    public static int[][] generateSequentialIntArrays(int numArrays, int size) {
        int[][] array = new int[3][size];
        int curnumber = 0;
        for (int i = 0; i < numArrays; i++) {
            for (int j = 0; j < size; j++) {
                array[i][j] = curnumber;
                curnumber++;
            }
        }
        return array;
    }

    public static Integer[] convertintArrayToInteger(int[] data) {
        Integer[] result = new Integer[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = data[i];
        }
        return result;
    }

    static class DefaultComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer a, Integer b) {
            return a.compareTo(b);
        }
    }
}
