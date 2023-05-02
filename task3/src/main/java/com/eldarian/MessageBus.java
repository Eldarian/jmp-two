package com.eldarian;

import java.util.ArrayList;
import java.util.List;

public class MessageBus {
    private List<Message> buffer;
    private List<Consumer> consumers;

    public MessageBus() {
        buffer = new ArrayList<>();
        consumers = new ArrayList<>();
    }

    public synchronized void addMessage(Message message) {
        buffer.add(message);
        notifyAll();
    }

    protected synchronized Message getMessage() throws InterruptedException {
        while (buffer.isEmpty()) {
            wait();
        }
        Message message = buffer.remove(0);
        notifyAll();
        return message;
    }

    public void registerConsumer(Consumer consumer) {
        consumers.add(consumer);
    }

    public void start() {
        new Thread(new Producer(this, "Eugene")).start();
        new Thread(new Producer(this, "Veronika")).start();
        for (Consumer consumer : consumers) {
            new Thread(consumer).start();
        }
    }

    public List<Message> getBuffer() {
        return buffer;
    }
}
