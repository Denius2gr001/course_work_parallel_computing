package com.coursework;

import com.coursework.invertedindex.InvertedIndex;
import com.coursework.invertedindex.InvertedIndexBuilder;
import com.coursework.thread.UserThread;
import com.coursework.util.UserUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CourseWork {
    private final static String FILE_SOURCE = "source";
    private final static int EXIT_CODE = -1;
    private final static int INDEX_USERS_NUM = 5;

    public static void main(String[] args) {
        File sourceDirectory = new File(FILE_SOURCE);

        List<File> files = new ArrayList<>();
        if (sourceDirectory.isDirectory() && Objects.requireNonNull(sourceDirectory.listFiles()).length > 0) {
            files = Arrays.asList(Objects.requireNonNull(sourceDirectory.listFiles()));
        }

        if (files.size() == 0) {
            System.out.println("The source directory is empty! Nothing to process!");
            return;
        }

        int threadsNum = UserUtils.getThreadsNum();
        if (threadsNum == EXIT_CODE) {
            System.out.println("Exiting...");
            return;
        }

        long startMoment = System.currentTimeMillis();
        Map<String, List<String>> invertedIndex = InvertedIndexBuilder.build(files, threadsNum);
        long finishMoment = System.currentTimeMillis();

        System.out.println("The inverted index built for " + (finishMoment - startMoment) + "ms" );
        System.out.println("(Index size: " + invertedIndex.size() + " items)");

        if (UserUtils.isWritingToTheFileRequired()) {
            String fileName = UserUtils.createInvertedIndexFile(invertedIndex, threadsNum);

            if (fileName != null) {
                System.out.println("Inverted index was written to the file [" + fileName + "]");
            } else {
                System.out.println("Inverted index wasn't written to the file due to some errors!");
            }
        }

        System.out.println("Starting using of the index...");
        UserThread[] userThreads = new UserThread[INDEX_USERS_NUM];
        for (int i = 0; i < INDEX_USERS_NUM; i++) {
            userThreads[i] = new UserThread(new InvertedIndex(invertedIndex), new ArrayList<>(invertedIndex.keySet()), i);
            userThreads[i].start();
        }

        try {
            for (UserThread userThread : userThreads) {
                userThread.join();
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("Using of the index finished!");
    }
}
