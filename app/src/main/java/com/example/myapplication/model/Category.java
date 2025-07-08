package com.example.myapplication.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Category implements Serializable {
    private int categoryId;
    private int categoryParentId;
    private String categoryParentName;
    private String categoryParentImage;
    private String categoryParentBackgroundColor;
    private String categoryName;
    private String categoryImage;
    private ArrayList<Object> categorySlideImages;
    private ArrayList<CategoryParentSlideImage> categoryParentSlideImages;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getCategoryParentId() {
        return categoryParentId;
    }

    public void setCategoryParentId(int categoryParentId) {
        this.categoryParentId = categoryParentId;
    }

    public String getCategoryParentName() {
        return categoryParentName;
    }

    public void setCategoryParentName(String categoryParentName) {
        this.categoryParentName = categoryParentName;
    }

    public String getCategoryParentImage() {
        return categoryParentImage;
    }

    public void setCategoryParentImage(String categoryParentImage) {
        this.categoryParentImage = categoryParentImage;
    }

    public String getCategoryParentBackgroundColor() {
        return categoryParentBackgroundColor;
    }

    public void setCategoryParentBackgroundColor(String categoryParentBackgroundColor) {
        this.categoryParentBackgroundColor = categoryParentBackgroundColor;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public ArrayList<Object> getCategorySlideImages() {
        return categorySlideImages;
    }

    public void setCategorySlideImages(ArrayList<Object> categorySlideImages) {
        this.categorySlideImages = categorySlideImages;
    }

    public ArrayList<CategoryParentSlideImage> getCategoryParentSlideImages() {
        return categoryParentSlideImages;
    }

    public void setCategoryParentSlideImages(ArrayList<CategoryParentSlideImage> categoryParentSlideImages) {
        this.categoryParentSlideImages = categoryParentSlideImages;
    }
}
