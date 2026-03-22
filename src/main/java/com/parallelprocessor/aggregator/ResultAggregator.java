package com.parallelprocessor.aggregator;

import com.parallelprocessor.counter.WordCounter;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ResultAggregator {
    
    public static void printTopWords(WordCounter wordCounter, int limit) {
        System.out.println("\n--- TOP " + limit + " WORDS ---");
        
        wordCounter.getWordCounts().entrySet().stream()
                // Sort descending by value
                .sorted((entry1, entry2) -> Integer.compare(entry2.getValue().get(), entry1.getValue().get()))
                .limit(limit)
                .forEach(entry -> {
                    System.out.printf("%-15s -> %d%n", entry.getKey(), entry.getValue().get());
                });
    }
}
