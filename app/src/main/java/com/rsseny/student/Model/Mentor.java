package com.rsseny.student.Model;

public class Mentor {
    private String Name, PhoneNumber, Cost, Photo, Description, Details, Book;

    public Mentor(String name, String cost, String photo, String description, String details, String book) {
        Name = name;
        Cost = cost;
        Photo = photo;
        Description = description;
        Details = details;
        Book = book;
    }

    public Mentor() {}

    public String getBook() {
        return Book;
    }

    public void setBook(String book) {
        Book = book;
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

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }
}
