package com.coursework.thread;

import com.coursework.invertedindex.InvertedIndexBuilderUnit;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuilderThread extends Thread {
    private final List<File> files;
    private final int threadIndex;
    private Map<String, List<String>> invertedIndexPart;

    public BuilderThread(List<File> files, int threadIndex) {
        this.files = files;
        this.threadIndex = threadIndex;
    }

    @Override
    public void run() {
        System.out.println("Thread " + threadIndex + " started working ...");

        InvertedIndexBuilderUnit builderUnit = new InvertedIndexBuilderUnit(files);
        invertedIndexPart = builderUnit.getInvertedIndex();

        System.out.println("Thread " + threadIndex + " finished working!");
    }

    public Map<String, List<String>> getInvertedIndexPart() {
        return new HashMap<>(invertedIndexPart);
    }
}
