package com.example.meetspace;

public class User_info {
    String First_Name,Last_Name,Email,Password,Emp_id,Designation/*,UID*/;

    public User_info() {
    }

    public User_info(String first_name,String last_name, String email, String password, String emp_id, String designation/*, String UID*/) {
        First_Name = first_name;
        Last_Name = last_name;
        Email = email;
        Password = password;
        Emp_id = emp_id;
        Designation = designation;
        //this.UID = UID;
    }

    public String getFirst_Name() {
        return First_Name;
    }

    public void setFirst_Name(String first_Name) {
        First_Name = first_Name;
    }

    public String getLast_Name() {
        return Last_Name;
    }

    public void setLast_Name(String last_Name) {
        Last_Name = last_Name;
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
}
