package com.coursework;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuildThread extends Thread {
    private final List<File> files;
    private final int startPosition;
    private final int endPosition;
    private Map<String, List<String>> invertedIndexPart;

    public BuildThread(List<File> files, int startPosition, int endPosition) {
        this.files = files;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    @Override
    public void run() {
        InvertedIndexBuilder builder = new InvertedIndexBuilder(files, startPosition, endPosition);

        invertedIndexPart = builder.buildInvertedIndex();
    }

    public Map<String, List<String>> getInvertedIndexPart() {
        return new HashMap<>(invertedIndexPart);
    }
}
