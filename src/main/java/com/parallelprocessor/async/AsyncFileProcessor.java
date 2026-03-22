package com.parallelprocessor.async;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Stream;

import com.parallelprocessor.counter.WordCounter;

public class AsyncFileProcessor {
    public static CompletableFuture<Void> processFileAsync(Path filePath, WordCounter wordCounter,
            ExecutorService executor) {
        return CompletableFuture.runAsync(() -> {
            try (Stream<String> lines = Files.lines(filePath)) {
                lines.forEach(line -> {
                    String[] words = line.split("\\s+");
                    for (String word : words) {
                        wordCounter.addWord(word);
                    }
                });
                System.out.println(
                        Thread.currentThread().getName() + " finished asynchronously: " + filePath.getFileName());
            } catch (IOException e) {
                System.err.println("Error reading file: " + filePath);
            }
        }, executor);
    }
}
