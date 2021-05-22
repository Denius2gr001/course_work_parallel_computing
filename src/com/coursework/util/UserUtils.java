package com.coursework.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class UserUtils {
    private static final Scanner IN = new Scanner(System.in);
    private static final Calendar CALENDAR = new GregorianCalendar();
    private static final int EXIT_CODE = -1;
    private static final int MIN_THREADS_NUM = 1;
    private static final int MAX_THREADS_NUM = 50;

    public static int getThreadsNum() {
        String prompt = "Please, provide the number of threads to build inverted index: (from " +
                MIN_THREADS_NUM +
                " to " +
                MAX_THREADS_NUM +
                "): " +
                "\n[or enter " +
                EXIT_CODE +
                " to exit]";
        System.out.println(prompt);

        while (true) {
            String input = IN.nextLine();

            if (input.matches("\\d+")) {
                boolean isCorrectThreadsNum = parseInt(input) >= MIN_THREADS_NUM && parseInt(input) <= MAX_THREADS_NUM;
                if (isCorrectThreadsNum || parseInt(input) == EXIT_CODE) {
                    return parseInt(input);
                }
            }

            System.out.println("Incorrect symbol! Should be the number (from 1 to 50)");
        }
    }

    public static boolean isWritingToTheFileRequired() {
        System.out.println("Write built index to the file? [Yes/No, Y/N]: ");
        String answer = IN.nextLine();

        return answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("y");
    }

    public static String createInvertedIndexFile(Map<String, List<String>> invertedIndex, int threadsNum) {
        String fileName = "IndexFile_" + threadsNum + "threads_" +
                CALENDAR.get(Calendar.YEAR) +
                CALENDAR.get(Calendar.MONTH) +
                CALENDAR.get(Calendar.DATE) +
                CALENDAR.get(Calendar.MINUTE) +
                CALENDAR.get(Calendar.SECOND) +
                ".txt";

        List<String> dictionary = new ArrayList<>(invertedIndex.keySet()).stream()
                .sorted(String::compareTo)
                .collect(Collectors.toList());
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            for (String word : dictionary) {
                StringBuilder indexItem = new StringBuilder(word + " -> ");
                for (String position : invertedIndex.get(word)) {
                    indexItem.append(position)
                            .append("  ");
                }
                indexItem.append("\n");

                writer.write(indexItem.toString());
                writer.flush();
            }

        } catch (IOException ex) {
            System.out.println("Error while writing the inverted index to the file!");
            return null;
        }

        return fileName;
    }
}
