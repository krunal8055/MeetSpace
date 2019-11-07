package com.example.meetspace;

public class list_data {
    //private String imageid;
    private String Room_no;

    private String resource_name,resource_type;

    public list_data(String room_no) {
        Room_no = room_no;
    }

    public list_data(String resource_name, String resource_type) {
        this.resource_name = resource_name;
        this.resource_type = resource_type;
    }

    public String getResource_name() {
        return resource_name;
    }

    public String getResource_type() {
        return resource_type;
    }

    /*public String getImageid() {
        return imageid;
    }

    public void setImageid(String imageid) {
        this.imageid = imageid;
    }*/

    public String getRoom_no() {
        return Room_no;
    }

    public void setRoom_no(String room_no) {
        Room_no = room_no;
    }
}
