package com.parallelprocessor;

import com.parallelprocessor.aggregator.ResultAggregator;
import com.parallelprocessor.async.AsyncOrchestrator;
import com.parallelprocessor.counter.WordCounter;
import com.parallelprocessor.queue.QueueOrchestrator;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        String folder = "D:/parallel-file-processor/data/files";
        WordCounter wordCounter = new WordCounter();

        long startTime = System.currentTimeMillis();

        System.out.println("Main thread is kicking off the Queue Pipeline...");

        QueueOrchestrator.runQueuePipeline(folder, wordCounter);

        long endTime = System.currentTimeMillis();
        System.out.println("\n---FINAL SUMMARY---");
        System.out.println("Total Unique Words: " + wordCounter.getWordCounts().size());
        System.out.println("Time taken: " + (endTime - startTime) + " ms");

        ResultAggregator.printTopWords(wordCounter, 50);
    }
}