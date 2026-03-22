package com.parallelprocessor.queue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Stream;

public class FileProducer implements Runnable {
    private final String folderPath;
    private final BlockingQueue<Path> queue;

    public static final Path POISON_PILL = Paths.get("POISON_PILL");

    public FileProducer(String folderPath, BlockingQueue<Path> queue) {
        this.folderPath = folderPath;
        this.queue = queue;
    }

    @Override
    public void run() {
        System.out.println("Producer is starting to scan files...");

        try (Stream<Path> paths = Files.walk(Paths.get(folderPath))) {
            paths.filter(Files::isRegularFile).forEach(file -> {
                try {
                    queue.put(file);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });

            queue.put(POISON_PILL);
            System.out.println("Producer finished scanning and sent the Poison Pill");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
