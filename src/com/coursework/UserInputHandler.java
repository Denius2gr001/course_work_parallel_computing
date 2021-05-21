package com.coursework;

import static java.lang.Integer.parseInt;

import java.util.Scanner;

public class UserInputHandler {
    private static final Scanner IN = new Scanner(System.in);
    private static final int EXIT_CODE = -1;
    private static final int MIN_THREADS_NUM = 1;
    private static final int MAX_THREADS_NUM = 1;

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

            if (!input.matches("\\d+")) {
                boolean isCorrectThreadsNum = parseInt(input) >= MIN_THREADS_NUM && parseInt(input) <= MAX_THREADS_NUM;
                if (isCorrectThreadsNum || parseInt(input) == EXIT_CODE) {
                    return parseInt(input);
                }
            }

            System.out.println("Incorrect symbol! Should be the number (from 1 to 50)");
        }
    }
}
