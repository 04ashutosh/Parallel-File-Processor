package com.parallelprocessor.executor;

import com.parallelprocessor.counter.WordCounter;
import com.parallelprocessor.processor.FileProcessor;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ParallelExecutor {
    public static void processFiles(List<Path> files, WordCounter wordCounter) throws InterruptedException {
        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("Using Thread Pool with " + cores + " worker threads");

        ExecutorService executor = Executors.newFixedThreadPool(cores);

        for (Path file : files) {
            executor.submit(new FileProcessor(file, wordCounter));
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        System.out.println("✅ All files processed successfully in Parallel!");
    }
}
