package com.eldarian;

public class Message {
    protected final String topic;
    private final String messageText;

    public Message(String topic, String messageText) {
        this.topic = topic;
        this.messageText = messageText;
    }

    public String getTopic() {
        return topic;
    }

    public String getMessageText() {
        return messageText;
    }
}