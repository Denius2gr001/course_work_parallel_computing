package com.coursework;

import java.io.File;
import java.util.*;

public class InvertedIndexBuilder {
    private final List<File> files;
    private final int startPosition;
    private final int endPosition;

    public InvertedIndexBuilder(List<File> files, int startPosition, int endPosition) {
        this.files = files;

        this.startPosition = startPosition >=0 && startPosition < files.size() ? startPosition : 0;
        this.endPosition = endPosition >= startPosition && endPosition > 0 && endPosition <= files.size()
                ? endPosition : files.size() - 1;
    }

    public Map<String, List<String>> buildInvertedIndex() {
        Map<String, List<String>> invertedIndex = new HashMap<>();


        return invertedIndex;
    }
}
