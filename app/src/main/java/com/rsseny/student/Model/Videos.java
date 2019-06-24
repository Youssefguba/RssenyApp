package com.rsseny.student.Model;

public class Videos {
  private  String nameOfVideo, videoLink;

    public Videos(String nameOfVideo, String videoLink) {
        this.nameOfVideo = nameOfVideo;
        this.videoLink = videoLink;
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
