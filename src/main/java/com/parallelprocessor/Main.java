package com.parallelprocessor;

import com.parallelprocessor.aggregator.ResultAggregator;
import com.parallelprocessor.async.AsyncOrchestrator;
import com.parallelprocessor.counter.WordCounter;

public class Main {
    public static void main(String[] args) {
        String folder = "D:/parallel-file-processor/data/files";
        WordCounter wordCounter = new WordCounter();

        long startTime = System.currentTimeMillis();

        System.out.println("Main thread is kicking off the pipeline...");

        AsyncOrchestrator.runAsyncPipeline(folder, wordCounter)
                .thenAccept(v -> {
                    long endTime = System.currentTimeMillis();
                    System.out.println("\n--- FINAL SUMMARY ---");
                    System.out.println("Total Unique Words: " + wordCounter.getWordCounts().size());
                    System.out.println("Time taken: " + (endTime - startTime) + " ms");

                    ResultAggregator.printTopWords(wordCounter, 50);
                })
                .join();

        System.out.println(
                "Main thread is completely FREE and prints this instantly while processing happens in the background!");
    }
}