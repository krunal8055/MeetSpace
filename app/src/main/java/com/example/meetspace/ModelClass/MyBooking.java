package com.example.meetspace.ModelClass;

public class MyBooking {
    String BookingID,Room,Date,Start,End,Reason,No_people;
    boolean expanded;

    public MyBooking(String bookingID,String reason, String no_people,String room, String date, String start, String end) {
        Room = room;
        Date = date;
        Start = start;
        End = end;
        Reason = reason;
        No_people = no_people;
        BookingID = bookingID;
    }

    public MyBooking()
    {

    }

    public MyBooking(String start, String end) {
        Start = start;
        End = end;
    }

    public String getRoom() {
        return Room;
    }

    public String getDate() {
        return Date;
    }

    public String getStart() {
        return Start;
    }

    public String getEnd() {
        return End;
    }

    public String getReason() {
        return Reason;
    }

    public String getNo_people() {
        return No_people;
    }

    public String getBookingID() {
        return BookingID;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
