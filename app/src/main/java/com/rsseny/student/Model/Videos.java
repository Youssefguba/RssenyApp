package com.rsseny.student.Model;

public class Videos {
  private  String nameOfVideo, videoLink, MenuId;


    public Videos(String nameOfVideo, String videoLink, String menuId) {
        this.nameOfVideo = nameOfVideo;
        this.videoLink = videoLink;
        MenuId = menuId;
    }

    public Videos() {
    }

    public String getnameOfVideo() {
        return nameOfVideo;
    }

    public void setnameOfVideo(String nameOfVideo) {
        this.nameOfVideo = nameOfVideo;
    }

    public String getvideoLink() {
        return videoLink;
    }

    public void setvideoLink(String videoLink) {
        this.videoLink = videoLink;
    }
}
