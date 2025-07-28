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
import com.example.myapplication.model.ProductModel;
import com.example.myapplication.remote.ApiClient;
import com.example.myapplication.remote.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BestSellerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private ImageButton backButton;

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_seller);

        initView();
        loadBestSellerProducts();
        initEvent();
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerViewBestSeller);
        backButton = findViewById(R.id.back);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

//

    private void loadBestSellerProducts() {
        ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);
        Call<BestProductResponse> call = apiService.getBestProduct();
        call.enqueue(new Callback<BestProductResponse>() {
            @Override
            public void onResponse(Call<BestProductResponse> call, Response<BestProductResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ProductModel> productList = response.body().getContent();

                    adapter = new ProductAdapter(BestSellerActivity.this, productList);
                    recyclerView.setAdapter(adapter);

                    adapter.setOnProductClickListener(product -> {
                        Intent intent = new Intent(BestSellerActivity.this, DetailProductActivity.class);
                        intent.putExtra("product", product); // ProductModel cần implement Serializable hoặc Parcelable
                        startActivity(intent);
                    });
                }
            }

            @Override
            public void onFailure(Call<BestProductResponse> call, Throwable t) {
                Toast.makeText(BestSellerActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API_ERROR", "Exception: ", t);
            }
        });
    }

    private void initEvent() {
        backButton.setOnClickListener(v -> finish());
    }
}
