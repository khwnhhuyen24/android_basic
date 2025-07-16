package com.example.myapplication.model;

import java.io.Serializable;

public class DynamicColorsModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer dynamicColorId;
    private String dynamicColorCode;
    private String dynamicColorName;
    private String dynamicColorNameVn;
    private String dynamicColorNameKr;
    private Integer price;
    private Integer priceVn;
    private Integer priceKr;
    private Double percentSales;
    private Double percentSalesVn;
    private Double percentSalesKr;
    private Double percentKol;
    private Double percentKolVn;
    private Double percentKolKr;
    private Integer productRemain;
    private Integer productRemainVn;
    private Integer productRemainKr;
    private Integer priceSales;
    private Integer priceSalesVn;
    private Integer priceSalesKr;
    private Integer priceAfterSales;
    private Integer priceAfterSalesVn;
    private Integer priceAfterSalesKr;


    public DynamicColorsModel() {
    }
    // Constructor
    public DynamicColorsModel(Integer dynamicColorId, String dynamicColorCode, String dynamicColorName,
                              String dynamicColorNameVn, String dynamicColorNameKr, Integer price, Integer priceVn,
                              Integer priceKr, Double percentSales, Double percentSalesVn, Double percentSalesKr,
                              Double percentKol, Double percentKolVn, Double percentKolKr, Integer productRemain,
                              Integer productRemainVn, Integer productRemainKr, Integer priceSales,
                              Integer priceSalesVn, Integer priceSalesKr, Integer priceAfterSales,
                              Integer priceAfterSalesVn, Integer priceAfterSalesKr) {
        this.dynamicColorId = dynamicColorId;
        this.dynamicColorCode = dynamicColorCode;
        this.dynamicColorName = dynamicColorName;
        this.dynamicColorNameVn = dynamicColorNameVn;
        this.dynamicColorNameKr = dynamicColorNameKr;
        this.price = price;
        this.priceVn = priceVn;
        this.priceKr = priceKr;
        this.percentSales = percentSales;
        this.percentSalesVn = percentSalesVn;
        this.percentSalesKr = percentSalesKr;
        this.percentKol = percentKol;
        this.percentKolVn = percentKolVn;
        this.percentKolKr = percentKolKr;
        this.productRemain = productRemain;
        this.productRemainVn = productRemainVn;
        this.productRemainKr = productRemainKr;
        this.priceSales = priceSales;
        this.priceSalesVn = priceSalesVn;
        this.priceSalesKr = priceSalesKr;
        this.priceAfterSales = priceAfterSales;
        this.priceAfterSalesVn = priceAfterSalesVn;
        this.priceAfterSalesKr = priceAfterSalesKr;
    }

    // Getters and Setters
    public Integer getDynamicColorId() {
        return dynamicColorId;
    }

    public void setDynamicColorId(Integer dynamicColorId) {
        this.dynamicColorId = dynamicColorId;
    }

    public String getDynamicColorCode() {
        return dynamicColorCode;
    }

    public void setDynamicColorCode(String dynamicColorCode) {
        this.dynamicColorCode = dynamicColorCode;
    }

    public String getDynamicColorName() {
        return dynamicColorName;
    }

    public void setDynamicColorName(String dynamicColorName) {
        this.dynamicColorName = dynamicColorName;
    }

    public String getDynamicColorNameVn() {
        return dynamicColorNameVn;
    }

    public void setDynamicColorNameVn(String dynamicColorNameVn) {
        this.dynamicColorNameVn = dynamicColorNameVn;
    }

    public String getDynamicColorNameKr() {
        return dynamicColorNameKr;
    }

    public void setDynamicColorNameKr(String dynamicColorNameKr) {
        this.dynamicColorNameKr = dynamicColorNameKr;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPriceVn() {
        return priceVn;
    }

    public void setPriceVn(Integer priceVn) {
        this.priceVn = priceVn;
    }

    public Integer getPriceKr() {
        return priceKr;
    }

    public void setPriceKr(Integer priceKr) {
        this.priceKr = priceKr;
    }

    public Double getPercentSales() {
        return percentSales;
    }

    public void setPercentSales(Double percentSales) {
        this.percentSales = percentSales;
    }

    public Double getPercentSalesVn() {
        return percentSalesVn;
    }

    public void setPercentSalesVn(Double percentSalesVn) {
        this.percentSalesVn = percentSalesVn;
    }

    public Double getPercentSalesKr() {
        return percentSalesKr;
    }

    public void setPercentSalesKr(Double percentSalesKr) {
        this.percentSalesKr = percentSalesKr;
    }

    public Double getPercentKol() {
        return percentKol;
    }

    public void setPercentKol(Double percentKol) {
        this.percentKol = percentKol;
    }

    public Double getPercentKolVn() {
        return percentKolVn;
    }

    public void setPercentKolVn(Double percentKolVn) {
        this.percentKolVn = percentKolVn;
    }

    public Double getPercentKolKr() {
        return percentKolKr;
    }

    public void setPercentKolKr(Double percentKolKr) {
        this.percentKolKr = percentKolKr;
    }

    public Integer getProductRemain() {
        return productRemain;
    }

    public void setProductRemain(Integer productRemain) {
        this.productRemain = productRemain;
    }

    public Integer getProductRemainVn() {
        return productRemainVn;
    }

    public void setProductRemainVn(Integer productRemainVn) {
        this.productRemainVn = productRemainVn;
    }

    public Integer getProductRemainKr() {
        return productRemainKr;
    }

    public void setProductRemainKr(Integer productRemainKr) {
        this.productRemainKr = productRemainKr;
    }

    public Integer getPriceSales() {
        return priceSales;
    }

    public void setPriceSales(Integer priceSales) {
        this.priceSales = priceSales;
    }

    public Integer getPriceSalesVn() {
        return priceSalesVn;
    }

    public void setPriceSalesVn(Integer priceSalesVn) {
        this.priceSalesVn = priceSalesVn;
    }

    public Integer getPriceSalesKr() {
        return priceSalesKr;
    }

    public void setPriceSalesKr(Integer priceSalesKr) {
        this.priceSalesKr = priceSalesKr;
    }

    public Integer getPriceAfterSales() {
        return priceAfterSales;
    }

    public void setPriceAfterSales(Integer priceAfterSales) {
        this.priceAfterSales = priceAfterSales;
    }

    public Integer getPriceAfterSalesVn() {
        return priceAfterSalesVn;
    }

    public void setPriceAfterSalesVn(Integer priceAfterSalesVn) {
        this.priceAfterSalesVn = priceAfterSalesVn;
    }

    public Integer getPriceAfterSalesKr() {
        return priceAfterSalesKr;
    }

    public void setPriceAfterSalesKr(Integer priceAfterSalesKr) {
        this.priceAfterSalesKr = priceAfterSalesKr;
    }


}
