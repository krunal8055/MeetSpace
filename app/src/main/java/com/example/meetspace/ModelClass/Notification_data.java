package com.example.meetspace.ModelClass;

public class Notification_data {

    String SenderID,RecieverID,Date,StartTime,EndTime,Roomno;
    public Notification_data() {
    }

    public Notification_data(String SenderID, String RecieverID, String roomno, String date, String startTime, String endTime) {
        this.SenderID = SenderID;
        this.RecieverID = RecieverID;
        Roomno = roomno;
        Date = date;
        StartTime = startTime;
        EndTime = endTime;
    }

    public void setSenderID(String senderID) {
        SenderID = senderID;
    }

    public void setRecieverID(String recieverID) {
        RecieverID = recieverID;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public void setRoomno(String roomno) {
        Roomno = roomno;
    }

    public String getSenderID() {
        return SenderID;
    }

    public String getRecieverID() {
        return RecieverID;
    }

    public String getRoomno() {
        return Roomno;
    }

    public String getDate() {
        return Date;
    }

    public String getStartTime() {
        return StartTime;
    }

    public String getEndTime() {
        return EndTime;
    }
}
