package com.parallelprocessor.queue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Stream;

import com.parallelprocessor.counter.WordCounter;

public class FileConsumer implements Runnable {
    private final BlockingQueue<Path> queue;
    private final WordCounter wordCounter;

    public FileConsumer(BlockingQueue<Path> queue, WordCounter wordCounter) {
        this.queue = queue;
        this.wordCounter = wordCounter;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Path file = queue.take();

                if (file == FileProducer.POISON_PILL) {
                    System.out.println(Thread.currentThread().getName() + " ate the poison pill. Shutting down.");

                    queue.put(FileProducer.POISON_PILL);
                    break;
                }

                processFile(file);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void processFile(Path filePath) {
        try (Stream<String> lines = Files.lines(filePath)) {
            lines.forEach(line -> {
                String[] words = line.split("\\s+");
                for (String word : words) {
                    wordCounter.addWord(word);
                }
            });
            System.out.println(Thread.currentThread().getName() + " consumed: " + filePath.getFileName());
        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath);
        }
    }
}
