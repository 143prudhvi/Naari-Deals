package com.deals.naari.web.websocket.dto;

public class ResponseMessageDTO {

    private String content;

    public ResponseMessageDTO() {}

    public ResponseMessageDTO(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
