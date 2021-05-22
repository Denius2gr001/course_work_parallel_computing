package com.coursework;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuildThread extends Thread {
    private final List<File> files;
    private final int threadIndex;
    private Map<String, List<String>> invertedIndexPart;

    public BuildThread(List<File> files, int threadIndex) {
        this.files = files;
        this.threadIndex = threadIndex;
    }

    @Override
    public void run() {
        System.out.println("Thread " + threadIndex + " started working ...");

        InvertedIndexBuilder builder = new InvertedIndexBuilder(files);
        invertedIndexPart = builder.buildInvertedIndex();

        System.out.println("Thread " + threadIndex + " finished working!");
    }

    public Map<String, List<String>> getInvertedIndexPart() {
        return new HashMap<>(invertedIndexPart);
    }
}
