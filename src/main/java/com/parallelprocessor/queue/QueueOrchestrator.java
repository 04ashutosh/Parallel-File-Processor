package com.parallelprocessor.queue;

import java.nio.file.Path;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.parallelprocessor.counter.WordCounter;

public class QueueOrchestrator {
    public static void runQueuePipeline(String folder, WordCounter wordCounter) throws InterruptedException {
        BlockingQueue<Path> queue = new ArrayBlockingQueue<>(100);

        Thread producerThread = new Thread(new FileProducer(folder, queue));
        producerThread.start();

        int cores = Runtime.getRuntime().availableProcessors();
        ExecutorService consumerExecutor = Executors.newFixedThreadPool(cores);

        System.out.print("Starting " + cores + " consumers...");

        for (int i = 0; i < cores; i++) {
            consumerExecutor.submit(new FileConsumer(queue, wordCounter));
        }

        producerThread.join();

        consumerExecutor.shutdown();
        consumerExecutor.awaitTermination(1, TimeUnit.HOURS);

        System.out.println("All queue items processed and Poison Pills consumed!");
    }
}
