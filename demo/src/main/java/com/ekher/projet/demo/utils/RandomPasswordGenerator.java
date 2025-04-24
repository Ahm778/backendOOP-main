package com.ekher.projet.demo.utils;

import java.util.Random;

public class RandomPasswordGenerator {
    static final String PASSDICTIONARY = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!#$%&*";
    static final int PASSWORD_LENGTH = 10; // Define the desired length

    public static String generateRandomPassword() {
        Random random = new Random();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int index = random.nextInt(PASSDICTIONARY.length());
            password.append(PASSDICTIONARY.charAt(index));
        }
        return password.toString();
    }
}
