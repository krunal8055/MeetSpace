package com.example.meetspace.ModelClass;

public class ResourceList {
    String ResourceName;

    public ResourceList() {
    }

    public ResourceList(String resourceName) {
        ResourceName = resourceName;
    }

    public String getResourceName() {
        return ResourceName;
    }

    public void setResourceName(String resourceName) {
        ResourceName = resourceName;
    }
}
