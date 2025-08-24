package com.example.myapplication.fragmenHome;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DetailProductActivity;
import com.example.myapplication.R;
import com.example.myapplication.item.ProductAdapter;
import com.example.myapplication.model.BestProductResponse;
import com.example.myapplication.model.FavoriteProductResponse;
import com.example.myapplication.model.ListNews;
import com.example.myapplication.model.ProductModel;
import com.example.myapplication.remote.ApiClient;
import com.example.myapplication.remote.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private ArrayList<ProductModel> productList;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        Toast.makeText(this, "FavoriteActivity đã khởi động", Toast.LENGTH_LONG).show();

        initView();
        loadFavoriteProducts();
        initEvent();
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerViewFavorite);
        backButton = findViewById(R.id.back);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

//

    private void loadFavoriteProducts() {

        Toast.makeText(this, "Đang gọi API...", Toast.LENGTH_LONG).show();

        ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);
        Call<FavoriteProductResponse> call = apiService.getFavoriteProduct();


        call.enqueue(new Callback<FavoriteProductResponse>() {
            @Override
            public void onResponse(Call<FavoriteProductResponse> call, Response<FavoriteProductResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ListNews> apiList = response.body().getListNews();

                    // Convert ListNews -> ProductModel
                    List<ProductModel> productList = new ArrayList<>();
                    for (ListNews news : apiList) {
                        ProductModel product = new ProductModel(
                                news.getId(),
                                news.getNewsTitle(),
                                news.getNewsType(),
                                news.getCountryFlag(),
                                news.getNewsContent()
                                // thêm field nếu ProductModel có
                        );
                        productList.add(product);
                    }


                    adapter = new ProductAdapter(FavoriteActivity.this, productList);
                    recyclerView.setAdapter(adapter);

                    adapter.setOnProductClickListener(product -> {
                        Intent intent = new Intent(FavoriteActivity.this, DetailProductActivity.class);
                        intent.putExtra("product", product);
                        startActivity(intent);
                    });
                }
            }

            @Override
            public void onFailure(Call<FavoriteProductResponse> call, Throwable t) {
                Toast.makeText(FavoriteActivity.this,
                        "Lỗi API: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initEvent() {
        backButton.setOnClickListener(v -> finish());
    }
}
