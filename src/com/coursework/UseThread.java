package com.coursework;

import java.util.List;
import java.util.Random;

public class UseThread extends Thread {
    private static final int USE_COUNT = 10;
    private final int threadId;
    private final Random random = new Random();
    private final InvertedIndex invertedIndex;
    private final List<String> dictionary;

    public UseThread(InvertedIndex invertedIndex, List<String> dictionary, int threadId) {
        this.invertedIndex = invertedIndex;
        this.dictionary = dictionary;
        this.threadId = threadId;
    }

    @Override
    public void run() {
        for (int i = 0; i < USE_COUNT; i++) {
            String randomWord = dictionary.get(random.nextInt(dictionary.size()));

            List<String> wordPositions = invertedIndex.getPositions(randomWord);
            String randomPosition = wordPositions.get(random.nextInt(wordPositions.size()));

            System.out.print("Thread " + threadId + ": ");
            System.out.println("[" + randomWord + "] is in file " + randomPosition);
            System.out.println("Removing the position " + randomPosition);

            invertedIndex.removePositionForWord(randomWord, randomPosition);

            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
