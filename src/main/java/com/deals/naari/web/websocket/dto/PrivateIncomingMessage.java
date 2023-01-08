package com.deals.naari.web.websocket.dto;

public class PrivateIncomingMessage {

    private String message;
    private String sendTo;

    public PrivateIncomingMessage() {}

    public String getMessageContent() {
        return message;
    }

    public void setMessageContent(String message) {
        this.message = message;
    }

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }
}
