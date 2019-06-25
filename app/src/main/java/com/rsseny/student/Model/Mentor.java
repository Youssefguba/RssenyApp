package com.rsseny.student.Model;

public class Mentor {
    private String Name, PhoneNumber, Cost ,Photo, Description;

    public Mentor(String name, String phoneNumber, String cost, String photo, String description) {
        Name = name;
        PhoneNumber = phoneNumber;
        Cost = cost;
        Photo = photo;
        Description = description;
    }

    public Mentor() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getCost() {
        return Cost;
    }

    public void setCost(String cost) {
        Cost = cost;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
