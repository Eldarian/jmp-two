package com.eldarian;

public class Producer implements Runnable {
    MessageBus bus;
    String name;

    Producer(MessageBus bus, String name) {
        this.bus = bus;
        this.name = name;
    }
    private final String[] topics = {"topic1", "topic2", "topic3"};

    public void run() {
        while (true) {
            try {
                Thread.sleep((long)(Math.random() * 1000));
                Message message = new Message(topics[(int)(Math.random() * 3)],
                        "Message from "
                        + name + ": "
                        + (int)(Math.random()*10));
                bus.addMessage(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
