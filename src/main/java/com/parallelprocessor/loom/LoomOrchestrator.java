package com.parallelprocessor.loom;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

import com.parallelprocessor.counter.WordCounter;

public class LoomOrchestrator {
    public static void runLoomPipeline(String folder, WordCounter wordCounter) {
        List<Path> allFiles;
        try (Stream<Path> paths = Files.walk(Paths.get(folder))) {
            allFiles = paths.filter(Files::isRegularFile).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out
                .println("Loom found " + allFiles.size() + "files. Spawning " + allFiles.size() + " Virtual Threads!");

        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (Path file : allFiles) {
                executor.submit(new VirtualThreadProcessor(file, wordCounter));
            }
        }

        System.out.println("All Virtual Threads have completed their work!");
    }
}
