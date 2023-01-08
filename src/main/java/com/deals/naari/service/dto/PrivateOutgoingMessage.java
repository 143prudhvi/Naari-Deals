package com.deals.naari.service.dto;

// import org.springframework.web.bind.annotation.ResponseBody;

//@ResponseBody
public class PrivateOutgoingMessage {

    private String title;
    private String message;
    private String type;
    private int unreadNotificationCount;

    public PrivateOutgoingMessage() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getUnreadNotificationCount() {
        return unreadNotificationCount;
    }

    public void setUnreadNotificationCount(int unreadNotificationCount) {
        this.unreadNotificationCount = unreadNotificationCount;
    }

    @Override
    public String toString() {
        return "PrivateOutgoingMessage [message=" + message + "]";
    }
}
