package com.example.meetspace.ModelClass;

public class Notification_data {

    String Message;
    private boolean expanded;
    public Notification_data() {
    }

    public Notification_data(String message) {
        Message = message;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
    public boolean isExpanded() {
        return expanded;
    }
}
