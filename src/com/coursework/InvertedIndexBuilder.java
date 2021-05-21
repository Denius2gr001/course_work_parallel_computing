package com.coursework;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class InvertedIndexBuilder {
    private static Scanner in;
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

        try {
            for (int i = startPosition; i < endPosition; i++) {
                File currentFile = files.get(i);

                StringBuilder fileContent = new StringBuilder();
                in = new Scanner(currentFile);
                while (in.hasNext()) {
                    fileContent.append(in.nextLine());
                }

                Set<String> contentWords =  new HashSet<>(Arrays.asList(prepareContent(fileContent).split(" ")));
                for (String word : contentWords) {
                    if (word.length() < 2) {
                        continue;
                    }

                    if (invertedIndex.containsKey(word)) {
                        invertedIndex.get(word).add(currentFile.getName());
                    } else {
                        List<String> positionsList = new LinkedList<>();
                        positionsList.add(currentFile.getName());

                        invertedIndex.put(word, positionsList);
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("An exception occurred while opening file!");
            ex.printStackTrace();
        }

        return invertedIndex;
    }

    private String prepareContent(StringBuilder unpreparedString) {
        return unpreparedString.toString()
                .replaceAll("<.*?>", "") // remove all html tags
                .replaceAll("[^A-Za-z\\s]", "") // remove all symbols that are neither letter nor space
                .replaceAll("\\s+", " ") // remove redundant spaces
                .trim() // remove spaces in the beginning and in the end of the string
                .toLowerCase(); // inverted index is not case sensitive
    }
}
