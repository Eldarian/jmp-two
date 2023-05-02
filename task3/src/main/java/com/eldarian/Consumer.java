package com.eldarian;

import java.util.List;

public abstract class Consumer implements Runnable {
    private final String topic;
    private MessageBus bus;

    public Consumer(String topic, MessageBus bus) {
        this.topic = topic;
        this.bus = bus;
    }

    public String getTopic() {
        return topic;
    }

    public void run() {
        while (true) {
            try {
                Message message = bus.getMessage();
                if (message.getTopic().equals(topic)) {
                    consume(message);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



    public abstract void consume(Message message);
}
