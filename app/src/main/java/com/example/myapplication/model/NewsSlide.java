package com.example.myapplication.model;

public class NewsSlide {
    public int id;
    public int newsId;
    public String newsSlideType;
    public String newsSlideSource;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public String getNewsSlideType() {
        return newsSlideType;
    }

    public void setNewsSlideType(String newsSlideType) {
        this.newsSlideType = newsSlideType;
    }

    public String getNewsSlideSource() {
        return newsSlideSource;
    }

    public void setNewsSlideSource(String newsSlideSource) {
        this.newsSlideSource = newsSlideSource;
    }
}
