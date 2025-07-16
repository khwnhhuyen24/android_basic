package com.example.myapplication.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private int productId;
    private String productCode;
    private int productRemain;
    private int productRemainVn;
    private int productRemainKr;

    private String madeIn;
    private int buyCount;
    private int likeNumber;
    private int commentCount;
    private double averageStar;

    private int price;
    private int priceVn;
    private int priceKr;

    private int priceSales;
    private int priceSalesVn;
    private int priceSalesKr;

    private double percentKol;
    private double percentKolVn;
    private double percentKolKr;

    private String productName;
    private String productDescription;

    // Danh sách URL ảnh cho banner
    private ArrayList<String> productImages;

    private boolean isFreeDelivery;
    private boolean isFreeDeliveryVn;
    private boolean isFreeDeliveryKr;

    private boolean isSupportBankTransfer;
    private boolean isSupportTransportInternational;

    private boolean isActive;

    private String mfgDate;
    private String insertDateTime;
    private String updateDateTime;

    private int brandId;
    private int categoryId;

    private Brand brand;
    private CategoryModel category;

    private boolean likeStatus;
    private String saveStatus;

    private List<DynamicSizesModel> dynamicSizes;
    private transient List<DynamicColorsModel> dynamicColors;

    // Getter và Setter
    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public String getProductCode() { return productCode; }
    public void setProductCode(String productCode) { this.productCode = productCode; }

    public int getProductRemain() { return productRemain; }
    public void setProductRemain(int productRemain) { this.productRemain = productRemain; }

    public int getProductRemainVn() { return productRemainVn; }
    public void setProductRemainVn(int productRemainVn) { this.productRemainVn = productRemainVn; }

    public int getProductRemainKr() { return productRemainKr; }
    public void setProductRemainKr(int productRemainKr) { this.productRemainKr = productRemainKr; }

    public String getMadeIn() { return madeIn; }
    public void setMadeIn(String madeIn) { this.madeIn = madeIn; }

    public int getBuyCount() { return buyCount; }
    public void setBuyCount(int buyCount) { this.buyCount = buyCount; }

    public int getLikeNumber() { return likeNumber; }
    public void setLikeNumber(int likeNumber) { this.likeNumber = likeNumber; }

    public int getCommentCount() { return commentCount; }
    public void setCommentCount(int commentCount) { this.commentCount = commentCount; }

    public double getAverageStar() { return averageStar; }
    public void setAverageStar(double averageStar) { this.averageStar = averageStar; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public int getPriceVn() { return priceVn; }
    public void setPriceVn(int priceVn) { this.priceVn = priceVn; }

    public int getPriceKr() { return priceKr; }
    public void setPriceKr(int priceKr) { this.priceKr = priceKr; }

    public int getPriceSales() { return priceSales; }
    public void setPriceSales(int priceSales) { this.priceSales = priceSales; }

    public int getPriceSalesVn() { return priceSalesVn; }
    public void setPriceSalesVn(int priceSalesVn) { this.priceSalesVn = priceSalesVn; }

    public int getPriceSalesKr() { return priceSalesKr; }
    public void setPriceSalesKr(int priceSalesKr) { this.priceSalesKr = priceSalesKr; }

    public double getPercentKol() { return percentKol; }
    public void setPercentKol(double percentKol) { this.percentKol = percentKol; }

    public double getPercentKolVn() { return percentKolVn; }
    public void setPercentKolVn(double percentKolVn) { this.percentKolVn = percentKolVn; }

    public double getPercentKolKr() { return percentKolKr; }
    public void setPercentKolKr(double percentKolKr) { this.percentKolKr = percentKolKr; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getProductDescription() { return productDescription; }
    public void setProductDescription(String productDescription) { this.productDescription = productDescription; }

    public ArrayList<String> getProductImages() { return productImages; }
    public void setProductImages(ArrayList<String> productImages) { this.productImages = productImages; }

    public boolean isFreeDelivery() { return isFreeDelivery; }
    public void setFreeDelivery(boolean freeDelivery) { isFreeDelivery = freeDelivery; }

    public boolean isFreeDeliveryVn() { return isFreeDeliveryVn; }
    public void setFreeDeliveryVn(boolean freeDeliveryVn) { isFreeDeliveryVn = freeDeliveryVn; }

    public boolean isFreeDeliveryKr() { return isFreeDeliveryKr; }
    public void setFreeDeliveryKr(boolean freeDeliveryKr) { isFreeDeliveryKr = freeDeliveryKr; }

    public boolean isSupportBankTransfer() { return isSupportBankTransfer; }
    public void setSupportBankTransfer(boolean supportBankTransfer) { isSupportBankTransfer = supportBankTransfer; }

    public boolean isSupportTransportInternational() { return isSupportTransportInternational; }
    public void setSupportTransportInternational(boolean supportTransportInternational) { isSupportTransportInternational = supportTransportInternational; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public String getMfgDate() { return mfgDate; }
    public void setMfgDate(String mfgDate) { this.mfgDate = mfgDate; }

    public String getInsertDateTime() { return insertDateTime; }
    public void setInsertDateTime(String insertDateTime) { this.insertDateTime = insertDateTime; }

    public String getUpdateDateTime() { return updateDateTime; }
    public void setUpdateDateTime(String updateDateTime) { this.updateDateTime = updateDateTime; }

    public int getBrandId() { return brandId; }
    public void setBrandId(int brandId) { this.brandId = brandId; }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public Brand getBrand() { return brand; }
    public void setBrand(Brand brand) { this.brand = brand; }

    public CategoryModel getCategory() { return category; }
    public void setCategory(CategoryModel category) { this.category = category; }

    public boolean isLikeStatus() { return likeStatus; }
    public void setLikeStatus(boolean likeStatus) { this.likeStatus = likeStatus; }

    public String getSaveStatus() { return saveStatus; }
    public void setSaveStatus(String saveStatus) { this.saveStatus = saveStatus; }

    public List<DynamicSizesModel> getDynamicSizes() { return dynamicSizes; }
    public void setDynamicSizes(List<DynamicSizesModel> dynamicSizes) { this.dynamicSizes = dynamicSizes; }

    public List<DynamicColorsModel> getDynamicColors() { return dynamicColors; }
    public void setDynamicColors(List<DynamicColorsModel> dynamicColors) { this.dynamicColors = dynamicColors; }

    public String getFirstImageUrl() {
        if (productImages != null && !productImages.isEmpty()) {
            return productImages.get(0);
        }
        return null; // hoặc return URL ảnh mặc định
    }
}
