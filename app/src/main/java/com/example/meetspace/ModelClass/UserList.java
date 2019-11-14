package com.example.meetspace.ModelClass;

public class UserList {
    String TokenID;
    String FirstName;
    String LastName;
    String Reciever_UID;

    public UserList() {
    }

    public UserList(String reciever_UID,String TokenID,String firstName, String lastName) {
        Reciever_UID = reciever_UID;
        this.TokenID = TokenID;
        FirstName = firstName;
        LastName = lastName;
    }

    public String getTokenID() {
        return TokenID;
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

    public String getReciever_UID() {
        return Reciever_UID;
    }
}
