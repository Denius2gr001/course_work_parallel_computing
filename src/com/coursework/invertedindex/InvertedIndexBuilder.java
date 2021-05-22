package com.coursework.invertedindex;

import com.coursework.thread.BuilderThread;

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

        BuilderThread[] builderThreads = new BuilderThread[threadsNum];
        for (int threadId = 0; threadId < threadsNum; threadId++) {
            int startPosition = files.size() / threadsNum * threadId;
            int endPosition = threadId == (threadsNum - 1) ? files.size() : files.size() / threadsNum * (threadId + 1);

            builderThreads[threadId] = new BuilderThread(files.subList(startPosition, endPosition), threadId);
            builderThreads[threadId].start();
        }

        try {
            for (BuilderThread thread : builderThreads) {
                thread.join();
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        for (BuilderThread thread : builderThreads) {
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
