package com.coursework;

import java.io.File;
import java.util.List;
import java.util.Map;

public class InvertedIndex {

    public static Map<String, List<String>> build(File[] files, int threadsNum) {
        return threadsNum == 1 ? sequentialBuild(files) : parallelBuild(files, threadsNum);
    }

    private static Map<String, List<String>> sequentialBuild(File[] files) {
        return null;
    }

    private static Map<String, List<String>> parallelBuild(File[] files, int threadsNum) {
        return null;
    }
}
