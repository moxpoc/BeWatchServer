package com.bewatches.server.Dto;

public class DeviceNotification {

    private String to;

    private class Notification{

        private String body;
        private String title;

    }

    DeviceNotification(String to, Notification notification){
        this.to = to;
        super.Notification =notification;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}

