package com.parallelprocessor.scanner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileScanner {
    public static List<Path> scanFolder(String folderPath) throws IOException {
        try (Stream<Path> paths = Files.walk(Paths.get(folderPath))) {
            return paths
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        }
    }
}