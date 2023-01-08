package com.deals.naari.web.websocket.dto;

public class OutputMessage {

    private String from;
    private String message;
    private String time;

    public OutputMessage(String from, String message, String time) {
        this.from = from;
        this.message = message;
        this.time = time;
    }
}
