package com.coursework;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class InvertedIndexBuilder {

    public static Map<String, List<String>> build(List<File> files, int threadsNum) {
        return threadsNum == 1 ? sequentialBuild(files) : parallelBuild(files, threadsNum);
    }

    private static Map<String, List<String>> sequentialBuild(List<File> files) {
        InvertedIndexBuilderUnit builderUnit = new InvertedIndexBuilderUnit(files);

        return builderUnit.getInvertedIndex();
    }

    private static Map<String, List<String>> parallelBuild(List<File> files, int threadsNum) {
        Map<String, List<String>> invertedIndex = new HashMap<>();

        BuildThread[] threads = new BuildThread[threadsNum];
        for (int threadId = 0; threadId < threadsNum; threadId++) {
            int startPosition = files.size() / threadsNum * threadId;
            int endPosition = threadId == (threadsNum - 1) ? files.size() : files.size() / threadsNum * (threadId + 1);

            threads[threadId] = new BuildThread(files.subList(startPosition, endPosition), threadId);
            threads[threadId].start();
        }

        try {
            for (BuildThread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        for (BuildThread thread : threads) {
            addNewPartToIndex(invertedIndex, thread.getInvertedIndexPart());
        }

        return invertedIndex;
    }

    private static void addNewPartToIndex(Map<String, List<String>> index, Map<String, List<String>> newPart) {
        for (Map.Entry<String, List<String>> item : newPart.entrySet()) {
            for (String position : item.getValue()) {
                if (index.containsKey(item.getKey())) {
                    index.get(item.getKey()).add(position);
                } else {
                    List<String> positionsList = new LinkedList<>();
                    positionsList.add(position);

                    index.put(item.getKey(), positionsList);
                }
            }
        }
    }
}
