package com.eldarian;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());


    static long sum = 0;

    public static void main(String[] args) {
        Map<Integer, Integer> hashMap = new HashMap<>();
        //Map<Integer, Integer> map = hashMap;   //leads to ConcurrentModificationException
        //Map<Integer, Integer> map = Collections.synchronizedMap(hashMap); // the same issue
        Map<Integer, Integer> map = new ConcurrentHashMap<>(hashMap); //everything's good here

        int max = 10000000;

        Thread writerThread = new Thread(() -> {
            try {
                Random r = new Random();
                for (int i = 0; i < max; i++) {
                    var element = r.nextInt(10);
                    map.put(i, element);
                }


            } catch (Exception e) {
                LOGGER.severe( "Exception in writer");
            }

        });

        Thread readerThread = new Thread(() -> {
            try {
                for (Map.Entry<Integer, Integer> entry:map.entrySet()) {
                    sum+=entry.getValue();
                }
            } catch (ConcurrentModificationException e) {
                e.printStackTrace();
            }
            LOGGER.log(Level.INFO, Long.toString(sum));
        });
        writerThread.start();
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        readerThread.start();
    }
}