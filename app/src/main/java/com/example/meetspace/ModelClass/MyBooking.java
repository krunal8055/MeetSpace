package com.example.meetspace.ModelClass;

public class MyBooking {
    String Room,Date,Start,End;

    public MyBooking(String room, String date, String start, String end) {
        Room = room;
        Date = date;
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
}
