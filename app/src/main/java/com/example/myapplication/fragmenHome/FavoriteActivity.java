package com.example.myapplication.fragmenHome;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.item.ProductAdapter;
import com.example.myapplication.model.ProductModel;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private ArrayList<ProductModel> productList;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        initView();
        initEvent();
    }

    private void initView(){
        recyclerView = findViewById(R.id.recyclerViewFavorite);
        backButton = findViewById(R.id.back); // LinearLayout chứa ImageButton

    }

    private void initEvent(){
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // ❗ Không khai báo lại — dùng biến toàn cục
        productList = (ArrayList<ProductModel>) getIntent().getSerializableExtra("productList");

        // ✅ Kiểm tra null trước khi gán adapter
        if (productList != null) {
            adapter = new ProductAdapter(this, productList);
            recyclerView.setAdapter(adapter);
        }

        backButton.setOnClickListener(v -> finish());
    }

}
