package com.eldarian;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BusMain {
    private static final Logger LOGGER = LogManager.getLogger(BusMain.class);

    public static void main(String[] args) {
        MessageBus messageBus = new MessageBus();
        messageBus.registerConsumer(new Consumer("topic1", messageBus) {
            @Override
            public void consume(Message message) {
                LOGGER.info("Consumed message on topic1: " + message.getMessageText());
            }
        });

        messageBus.registerConsumer(new Consumer("topic2", messageBus) {
            @Override
            public void consume(Message message) {
                LOGGER.info("Consumed message on topic2: " + message.getMessageText());
            }
        });
        messageBus.registerConsumer(new Consumer("topic3", messageBus) {
            @Override
            public void consume(Message message) {
                LOGGER.info("Consumed message on topic3: " + message.getMessageText());
            }
        });
        messageBus.start();

    }
}