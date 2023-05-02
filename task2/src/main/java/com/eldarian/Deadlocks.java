package com.eldarian;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Deadlocks {
    private static final Logger LOGGER = LogManager.getLogger(Deadlocks.class);
    static final Object lock = new Object();
    static Object lock1 = new Object();

    public static void main(String[] args) throws InterruptedException {

        List<Integer> list = new ArrayList<>();

        Thread writerThread = new Thread(() -> {
            Random random = new Random();
            while(true) {
                synchronized (lock) {
                    list.add(random.nextInt(10));
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        Thread summatorThread = new Thread(() -> {
            while (true) {
                synchronized (lock) { //produces deadlock
                    LOGGER.info("Sum is: " + list.stream().reduce(0, Integer::sum));
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            });

        Thread squareRooterThread = new Thread(() -> {
           while (true) {
               synchronized (lock) {
                   LOGGER.info("Square root is: " + Math.sqrt(list.stream()
                           .map((element) -> element*element)
                           .reduce(0, Integer::sum))
                   );
                   try {
                       Thread.sleep(5);
                   } catch (InterruptedException e) {
                       throw new RuntimeException(e);
                   }

               }
           }
        });
        writerThread.start();
        summatorThread.start();
        squareRooterThread.start();
        Thread.sleep(1000000);
    }
}