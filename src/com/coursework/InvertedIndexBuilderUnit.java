package com.coursework;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class InvertedIndexBuilderUnit {
    private final List<File> files;
    private Scanner in;
    private Map<String, List<String>> invertedIndex;

    public InvertedIndexBuilderUnit(List<File> files) {
        this.files = files;
    }

    public Map<String, List<String>> getInvertedIndex() {
        if (invertedIndex == null) {
            Map<String, List<String>> newInvertedIndex = new HashMap<>();

            try {
                for (File file : files) {
                    StringBuilder fileContent = new StringBuilder();
                    in = new Scanner(file);
                    while (in.hasNext()) {
                        fileContent.append(in.nextLine());
                    }

                    Set<String> contentWords =  new HashSet<>(Arrays.asList(prepareContent(fileContent).split(" ")));
                    for (String word : contentWords) {
                        if (word.length() < 2) {
                            continue;
                        }

                        if (newInvertedIndex.containsKey(word)) {
                            newInvertedIndex.get(word).add(file.getName());
                        } else {
                            List<String> positionsList = new LinkedList<>();
                            positionsList.add(file.getName());

                            newInvertedIndex.put(word, positionsList);
                        }
                    }
                }
            } catch (FileNotFoundException ex) {
                System.out.println("An exception occurred while opening file!");
                ex.printStackTrace();
            }
            invertedIndex = newInvertedIndex;
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
