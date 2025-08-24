package com.example.myapplication.model;

import java.util.ArrayList;

public class FavoriteProductResponse {

    public ArrayList<ListNews> getListNews() {
        return listNews;
    }

    public void setListNews(ArrayList<ListNews> listNews) {
        this.listNews = listNews;
    }

    public ProductsPage getProductsPage() {
        return productsPage;
    }

    public void setProductsPage(ProductsPage productsPage) {
        this.productsPage = productsPage;
    }

    public ArrayList<ListNews> listNews;
    public ProductsPage productsPage;
}
