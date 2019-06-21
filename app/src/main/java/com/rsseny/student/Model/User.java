package com.rsseny.student.Model;

public class User {
    private String Name;
    private String Email;
    private String Birthday;
    private String Gender;
    private String Phone;
    private String Password;
    private String Uid;


    public User(String email, String password) {
        Email = email;
        Password = password;
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

    public User(String email, String birthday, String gender) {
        Email = email;
        Birthday = birthday;
        Gender = gender;
    }


    public User() {
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