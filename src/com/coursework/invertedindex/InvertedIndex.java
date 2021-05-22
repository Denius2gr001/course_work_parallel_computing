package com.coursework.invertedindex;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InvertedIndex {
    private final Map<String, List<String>> invertedIndex;

    public InvertedIndex(Map<String, List<String>> invertedIndex) {
        this.invertedIndex = invertedIndex;
    }

    public synchronized List<String> getPositions(String word) {
        return new ArrayList<>(invertedIndex.get(word));
    }

    public synchronized boolean removePositionForWord(String word, String position) {
        return invertedIndex.get(word).remove(position);
    }
}
