package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.model.ProductModel;

public class ProductInfoFragment extends Fragment {
    private WebView webView;
    private ProductModel product;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_product_info, container, false);

        initView(view);
        initEvent();
        return view;
    }

    private void initView(@NonNull View view){
        webView = view.findViewById(R.id.webViewInfo);
    }

    private void initEvent(){

        // Lấy dữ liệu từ arguments
        if (getArguments() != null) {
            product = (ProductModel) getArguments().getSerializable("product");

            if (product != null && product.getProductDescription() != null) {
                loadHtmlToWebView(product.getProductDescription());
            }
        }
    }
    private void loadHtmlToWebView(String htmlContent) {
        // Cấu hình hiển thị tốt hơn
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // nếu cần tương tác
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);

        // Tải nội dung HTML
        webView.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null);
    }
}
