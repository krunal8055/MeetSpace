package com.example.meetspace;

public class AvailableResource_Room {
    String resourceQTY, resourceID, resourcename;
    String name;

    public AvailableResource_Room() {
    }

    public AvailableResource_Room(String resourceQTY, String resourceID, String resourcename) {
        this.resourceQTY = resourceQTY;
        this.resourceID = resourceID;
        this.resourcename = resourcename;
    }

    public AvailableResource_Room(String name,String resourcename) {
        this.name = name;
        this.resourcename = resourcename;
    }


    public String getResourceQTY() {
        return resourceQTY;
    }

    public String getResourceID() {
        return resourceID;
    }

    public String getResourcename() {
        return resourcename;
    }

    public String getName() {
        return name;
    }

}
