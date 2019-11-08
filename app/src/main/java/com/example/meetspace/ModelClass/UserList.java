package com.example.meetspace.ModelClass;

public class UserList {
    String FirstName;
    String LastName;

    public UserList() {
    }

    public UserList(String firstName, String lastName) {
        FirstName = firstName;
        LastName = lastName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }
}
