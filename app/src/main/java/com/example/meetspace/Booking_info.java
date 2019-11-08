package com.example.meetspace;

public class Booking_info {
    String BookingDate,StartTime,EndTime,RoomNO,BookingReason,PersonNo;

    public Booking_info() {
    }

    public Booking_info(String bookingDate, String startTime, String endTime, String roomNO, String bookingReason, String personNo) {
        BookingDate = bookingDate;
        StartTime = startTime;
        EndTime = endTime;
        RoomNO = roomNO;
        BookingReason = bookingReason;
        PersonNo = personNo;
    }

  /* public void setBookingDate(String bookingDate) {
        BookingDate = bookingDate;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public void setRoomNO(String roomNO) {
        RoomNO = roomNO;
    }

    public void setBookingReason(String bookingReason) {
        BookingReason = bookingReason;
    }

    public void setPersonNo(String personNo) {
        PersonNo = personNo;
    }*/


    public String getBookingDate() {
        return BookingDate;
    }

    public String getStartTime() {
        return StartTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public String getRoomNO() {
        return RoomNO;
    }

    public String getBookingReason() {
        return BookingReason;
    }

    public String getPersonNo() {
        return PersonNo;
    }
}
