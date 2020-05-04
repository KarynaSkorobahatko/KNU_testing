package com.company;

import java.io.*;
import java.util.*;
import java.lang.Math;

public class Main {

    private static final String FILE_INPUT_NAME = "INPUT.txt";
    private static final String DELIMITER_PATTERN = "[^a-zA-zа-яА-Я]";



    public static Set findMaxDistanceWordsWithMemory(String fileInputName) {

        File file = new File(fileInputName);
        String word;
        Set<String> input_words = new HashSet<>();

        int temp_distance, max_distance = -1;
        Set<String> result = new HashSet<>();

        try {
            Scanner input = new Scanner(file);
            input.useDelimiter(DELIMITER_PATTERN);
            while (input.hasNext()) {
                word = input.next();
                if (word.isBlank()) continue;
                input_words.add(word);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (String s1 : input_words) {
            for (String s2 : input_words) {
                temp_distance = distance(s1, s2);
                if (temp_distance > max_distance) {
                    max_distance = temp_distance;
                    result.clear();
                    result.add(createPair(s1, s2));
                } else {
                    if (temp_distance == max_distance) result.add(createPair(s1, s2));
                }
            }
        }
        return result;
    }

    public static Set findMaxDistanceWordsWithoutMemory(String fileInputName) {

        String first, second;
        int temp_distance, max_distance = -1;
        File file = new File(fileInputName);
        Set<String> result = new HashSet<>();

        try {
            Scanner input = new Scanner(file);
            input.useDelimiter(DELIMITER_PATTERN);
            while (input.hasNext()) {
                first = input.next();
                if (first.isBlank()) continue;
                Scanner loop_input = new Scanner(file);
                loop_input.useDelimiter(DELIMITER_PATTERN);
                while (loop_input.hasNext()) {
                    second = loop_input.next();
                    if (second.isBlank() | first.equals(second)) continue;
                    temp_distance = distance(first, second);
                    if (temp_distance > max_distance) {
                        max_distance = temp_distance;
                        result.clear();
                        result.add(createPair(first, second));
                    } else {
                        if (temp_distance == max_distance) result.add(createPair(first, second));
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static String createPair(String s1, String s2) {
        if (s1.compareTo(s2) > 0) {
            return s1 + " " + s2;
        } else {
            return s2 + " " + s1;
        }
    }

    public static int distance(String s1, String s2) {
        int min_lenght = Math.min(s1.length(), s2.length());
        int max_lenght = Math.max(s1.length(), s2.length());
        int word_distance = max_lenght - min_lenght;
        for (int i = 0; i < min_lenght; i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                word_distance++;
            }
        }
        return word_distance;
    }

    public static void main(String[] args) {

        Date start = new Date();

        System.out.println(findMaxDistanceWordsWithMemory(FILE_INPUT_NAME));
        System.out.println(findMaxDistanceWordsWithoutMemory(FILE_INPUT_NAME));

        Date end = new Date();
        System.out.println("Took " + (end.getTime() - start.getTime()) / 1000.0 + " seconds");

    }
}