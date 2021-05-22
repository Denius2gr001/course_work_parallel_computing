package com.coursework;

import java.io.File;
import java.util.*;

public class Main {
    private final static String SOURCE = "source";
    private final static int EXIT_CODE = -1;

    public static void main(String[] args) {
        File sourceFolder = new File(SOURCE);

        List<File> files = new ArrayList<>();
        if (sourceFolder.isDirectory() && Objects.requireNonNull(sourceFolder.listFiles()).length > 0) {
            files = Arrays.asList(Objects.requireNonNull(sourceFolder.listFiles()));
        }

        if (files.size() == 0) {
            System.out.println("The source directory is empty! Nothing to process!");
            return;
        }

        int threadsNum = UserInputHandler.getThreadsNum();
        if (threadsNum == EXIT_CODE) {
            System.out.println("Exiting...");
            return;
        }

        long startMoment = System.currentTimeMillis();
        Map<String, List<String>> invertedIndex = InvertedIndexBuilder.build(files, threadsNum);
        long finishMoment = System.currentTimeMillis();

        System.out.println("The inverted index built for " + (finishMoment - startMoment) + "ms" );
        System.out.println("(Index size: " + invertedIndex.size() + " items)");

        if (UserInputHandler.isWritingToTheFileRequired()) {
            boolean wasWritten = UserInputHandler.createInvertedIndexFile(invertedIndex, threadsNum);

            if (wasWritten) {
                System.out.println("Inverted index was written to the file!");
            } else {
                System.out.println("Inverted index wasn't written to the file due to some errors!");
            }
        }
    }
}
