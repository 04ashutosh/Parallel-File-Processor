package com.parallelprocessor.loom;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import com.parallelprocessor.counter.WordCounter;

public class VirtualThreadProcessor implements Runnable {
    private final Path filePath;
    private final WordCounter wordCounter;

    public VirtualThreadProcessor(Path filePath, WordCounter wordCounter) {
        this.filePath = filePath;
        this.wordCounter = wordCounter;
    }

    @Override
    public void run() {
        try (Stream<String> lines = Files.lines(filePath)) {
            lines.forEach(line -> {
                String[] words = line.split("\\s+");
                for (String word : words) {
                    wordCounter.addWord(word);
                }
            });

            System.out.println(Thread.currentThread() + " consumed: " + filePath.getFileName());
        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath);
        }
    }
}
