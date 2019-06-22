package com.rsseny.student.Model;

public class ChoosingItem {

    private String NameOfFaculty, Image;

    public ChoosingItem(String nameOfFaculty, String image) {
        NameOfFaculty = nameOfFaculty;
        Image = image;
    }

    public ChoosingItem() {
    }

    public String getNameOfFaculty() {
        return NameOfFaculty;
    }

    public void setNameOfFaculty(String nameOfFaculty) {
        NameOfFaculty = nameOfFaculty;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
