package edu.desu.utilities;

import java.util.Random;

public class NameGenerator {

    private static String[] firstNames = {"Alice", "Bob", "Charlie", "Diana", "Eve"};
    private static String[] lastNames = {"Sled", "Jones", "Williams", "Brown", "Davis"};

    public static String generateRandomName(Random random) {
        //Random random = new Random(12345);
        String randomFirstName = firstNames[random.nextInt(firstNames.length)];
        String randomLastName = lastNames[random.nextInt(lastNames.length)];
        return randomFirstName + " " + randomLastName;
    }

    public static void main(String[] args) {
        Random random = new Random(54321);
        System.out.println(generateRandomName(random));
        System.out.println(generateRandomName(random));
    }

}