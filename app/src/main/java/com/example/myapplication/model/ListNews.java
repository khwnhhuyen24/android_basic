package com.example.myapplication.model;

public class ListNews {
    public int id;
    public String newsTitle;
    public String newsType;
    public String countryFlag;

    public ProductModel getNewsProduct() {
        return newsProduct;
    }

    public void setNewsProduct(ProductModel newsProduct) {
        this.newsProduct = newsProduct;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsType() {
        return newsType;
    }

    public void setNewsType(String newsType) {
        this.newsType = newsType;
    }

    public String getCountryFlag() {
        return countryFlag;
    }

    public void setCountryFlag(String countryFlag) {
        this.countryFlag = countryFlag;
    }

    public NewsSlide getNewsSlide() {
        return newsSlide;
    }

    public void setNewsSlide(NewsSlide newsSlide) {
        this.newsSlide = newsSlide;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public ProductModel newsProduct;
    public NewsSlide newsSlide;
    public String newsContent;
}
