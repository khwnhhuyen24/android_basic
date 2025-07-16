package com.example.myapplication.model;

import java.io.Serializable;

public class CategoryParentSlideImageModel implements Serializable {
    private int id;
    private int categoryId;
    private int productId;
    private String imageName;

    public CategoryParentSlideImageModel(int id, int categoryId, int productId, String imageName) {
        this.id = id;
        this.categoryId = categoryId;
        this.productId = productId;
        this.imageName = imageName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
