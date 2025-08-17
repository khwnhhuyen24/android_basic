package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.local.AppDatabase;
import com.example.myapplication.model.ProductLocalModel;
import com.example.myapplication.model.ProductModel;

import java.util.List;

public class ProductCartActivity extends AppCompatActivity {

    private LinearLayout productCart;
    private LinearLayout cartEmpty;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_cart);

        db = AppDatabase.getInstance(this);
        initView();
        initEvent();
    }

    private void initView(){
        productCart = findViewById(R.id.productCart);
        cartEmpty = findViewById(R.id.cartEmpty);
    }

    private void initEvent(){
        List<ProductLocalModel> cartItems = db.productDAO().getAllProductLocalModel();
        if (cartItems != null && cartItems.isEmpty()){
            productCart.setVisibility(View.VISIBLE);

        }

        else {
            cartEmpty.setVisibility(View.VISIBLE);
        }
    }
}