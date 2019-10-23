package com.example.meetspace;

public class User_info {
    String Name,Email,Password,Emp_id,Designation/*,UID*/;

    public User_info() {
    }

    public User_info(String name, String email, String password, String emp_id, String designation/*, String UID*/) {
        Name = name;
        Email = email;
        Password = password;
        Emp_id = emp_id;
        Designation = designation;
        //this.UID = UID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmp_id() {
        return Emp_id;
    }

    public void setEmp_id(String emp_id) {
        Emp_id = emp_id;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }
/*
    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }*/
}
