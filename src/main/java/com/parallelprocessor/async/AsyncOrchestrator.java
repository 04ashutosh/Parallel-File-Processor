package com.parallelprocessor.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.parallelprocessor.counter.WordCounter;

public class AsyncOrchestrator {
    public static CompletableFuture<Void> runAsyncPipeline(String folder, WordCounter wordCounter) {
        int cores = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(cores);

        return AsyncFileScanner.scanFolderAsync(folder)
                .thenCompose(files -> {
                    System.out.println("Scanner found " + files.size() + " files. Starting processors...");

                    CompletableFuture<Void>[] futures = files.stream()
                            .map(file -> AsyncFileProcessor.processFileAsync(file, wordCounter, executor))
                            .toArray(CompletableFuture[]::new);

                    return CompletableFuture.allOf(futures);
                })

                .whenComplete((result, exception) -> {
                    executor.shutdown();
                });
    }
}
