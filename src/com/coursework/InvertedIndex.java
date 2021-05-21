package com.coursework;

import java.io.File;
import java.util.List;
import java.util.Map;

public class InvertedIndex {

    public static Map<String, List<String>> build(List<File> files, int threadsNum) {
        return threadsNum == 1 ? sequentialBuild(files) : parallelBuild(files, threadsNum);
    }

    private static Map<String, List<String>> sequentialBuild(List<File> files) {
        InvertedIndexBuilder builder = new InvertedIndexBuilder(files);

        return builder.buildInvertedIndex();
    }

    private static Map<String, List<String>> parallelBuild(List<File> files, int threadsNum) {
        return null;
    }
}
