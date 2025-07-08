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

public class SuggestionActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private ArrayList<ProductModel> productList;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);

        // Ánh xạ các view
        recyclerView = findViewById(R.id.recyclerViewSuggestion);
        backButton = findViewById(R.id.back); // LinearLayout chứa ImageButton

        // Thiết lập layout cho RecyclerView (2 cột)
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        @SuppressWarnings("unchecked")
        ArrayList<ProductModel> productList =
                (ArrayList<ProductModel>) getIntent().getSerializableExtra("productList");


        // Gán adapter
        adapter = new ProductAdapter(this, productList);
        recyclerView.setAdapter(adapter);

        // Xử lý nút back
        backButton.setOnClickListener(v -> finish()); // Quay lại Fragment trước (AllFragment)
    }
}
