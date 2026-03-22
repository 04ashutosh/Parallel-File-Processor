package com.parallelprocessor.async;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.management.RuntimeErrorException;

public class AsyncFileScanner {
    public static CompletableFuture<List<Path>> scanFolderAsync(String folderPath) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " is scanning the hard drive...");

            try (Stream<Path> paths = Files.walk(Paths.get(folderPath))) {
                return paths
                        .filter(Files::isRegularFile)
                        .collect(Collectors.toList());
            } catch (IOException e) {
                throw new RuntimeException("Failed to scan folder", e);
            }
        });
    }
}
