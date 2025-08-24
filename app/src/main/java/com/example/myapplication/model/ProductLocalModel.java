package com.example.myapplication.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Ignore;   // 👈 thêm import này

import java.io.Serializable;

@Entity(tableName = "product")
public class ProductLocalModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String productname;

    private String productImg;

    private int count;

    private int price;

    private String color;

    private String size;

    // ⚡ Trạng thái checkbox (chỉ dùng trong UI, không lưu DB)
    @Ignore
    private boolean selected = false; // 👈 mặc định là chưa chọn

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    // Getter/Setter cho selected
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
