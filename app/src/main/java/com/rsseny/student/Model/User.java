package com.rsseny.student.Model;

public class User {
    private String Name;
    private String Email;
    private String Birthday;
    private String Gender;
    private String Phone;
    private String Password;
    private String Uid;
    private String First_Name;
    private String Middle_Name;
    private String Last_Name;

    public User(String uid, String name, String email) {
        Name = name;
        Uid = uid;
        Email = email;
    }

    public User(String name, String email, String phone, String password) {
        Name = name;
        Email = email;
        Phone = phone;
        Password = password;
    }

    public User(String uid,String name, String email, String phone, String password) {
        Uid = uid;
        Name = name;
        Email = email;
        Phone = phone;
        Password = password;
    }

    public User(String uid, String name, String email, String phone, String password, String gender) {
        Name = name;
        Email = email;
        Gender = gender;
        Phone = phone;
        Password = password;
        Uid = uid;
    }

    public User() {
    }



    public String getFirst_Name() {
        return First_Name;
    }

    public void setFirst_Name(String first_Name) {
        First_Name = first_Name;
    }

    public String getMiddle_Name() {
        return Middle_Name;
    }

    public void setMiddle_Name(String middle_Name) {
        Middle_Name = middle_Name;
    }

    public String getLast_Name() {
        return Last_Name;
    }

    public void setLast_Name(String last_Name) {
        Last_Name = last_Name;
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

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }
}