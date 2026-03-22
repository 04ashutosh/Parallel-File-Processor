package com.parallelprocessor.counter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class WordCounter {
    private final ConcurrentHashMap<String, AtomicInteger> wordCounts = new ConcurrentHashMap<>();

    public void addWord(String word) {
        String cleanWord = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
        if (!cleanWord.isEmpty()) {
            wordCounts.computeIfAbsent(cleanWord, k -> new AtomicInteger(0)).incrementAndGet();
        }
    }

    public ConcurrentHashMap<String, AtomicInteger> getWordCounts() {
        return wordCounts;
    }
}
