package com.example.myapplication.fragmenHome;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.DetailProductActivity;
import com.example.myapplication.R;
import com.example.myapplication.base.OnItemClickListener;
import com.example.myapplication.item.BannerAdapter;
import com.example.myapplication.item.BannerAdapter1;
import com.example.myapplication.item.ProductAdapter;
import com.example.myapplication.model.BestProductResponse;
import com.example.myapplication.model.MockProductData;
import com.example.myapplication.model.ProductModel;
import com.example.myapplication.remote.ApiClient;
import com.example.myapplication.remote.ApiService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.widget.Toast;

public class AllFragment extends Fragment {
    private ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);

    private ViewPager2 viewPager;
    private Handler handler = new Handler();
    private Runnable runnable;
    private final int delay = 2000;

    private Button SeeMoreSuggestion, SeeMoreFavorite, SeeMoreBest;
    private ProductAdapter suggestionAdapter, favoriteAdapter, bestSellerAdapter;
    private RecyclerView suggestion, favorite, bestSeller;
    private List<Integer> banners;

    private LinearLayout indicatorLine;
    private TextView pageCountText;

    // Biến lưu toàn bộ danh sách bestSeller từ API
    private List<ProductModel> allBestProducts = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_all, container, false);

        initView(view);
        initEvent();

        return view;
    }

    private void initView(View view) {
        SeeMoreSuggestion = view.findViewById(R.id.btnSeeMoreSuggestion);
        SeeMoreFavorite = view.findViewById(R.id.btnSeeMoreFavorite);
        SeeMoreBest = view.findViewById(R.id.btnSeeMoreBest);

        viewPager = view.findViewById(R.id.bannerViewPager);

        suggestion = view.findViewById(R.id.recycler_view_suggestion);
        favorite = view.findViewById(R.id.recycler_view_favorite);
        bestSeller = view.findViewById(R.id.recycler_view_best_seller);
        indicatorLine = view.findViewById(R.id.indicatorLine);
        pageCountText = view.findViewById(R.id.pageCount);

        // Setup layout managers nếu chưa có trong XML
        suggestion.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        favorite.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        bestSeller.setLayoutManager(new GridLayoutManager(requireContext(), 2));
    }

    private void initEvent() {
        // Gán dữ liệu giả lập cho suggestion và favorite (ví dụ)
        ArrayList<ProductModel> fullProductList = new ArrayList<>(MockProductData.getFakeProducts());
        List<ProductModel> firstFour = fullProductList.subList(0, Math.min(4, fullProductList.size()));

        suggestionAdapter = new ProductAdapter(requireContext(), firstFour);
        favoriteAdapter = new ProductAdapter(requireContext(), firstFour);

        suggestion.setAdapter(suggestionAdapter);
        favorite.setAdapter(favoriteAdapter);

        suggestionAdapter.setOnProductClickListener(this::openDetail);
        favoriteAdapter.setOnProductClickListener(this::openDetail);

        SeeMoreSuggestion.setOnClickListener(v -> openListActivity(SuggestionActivity.class, fullProductList));
        SeeMoreFavorite.setOnClickListener(v -> openListActivity(FavoriteActivity.class, fullProductList));

        // *** Chỉ gọi load API cho bestSeller ***
        loadBestProductsFromApi();

        SeeMoreBest.setOnClickListener(v -> {
            if (allBestProducts != null && !allBestProducts.isEmpty()) {
                openListActivity(BestSellerActivity.class, new ArrayList<>(allBestProducts));
            } else {
                Toast.makeText(requireContext(), "Dữ liệu chưa tải xong", Toast.LENGTH_SHORT).show();
            }
        });

            // Khóa cuộn tay ngang của ViewPager2
            viewPager.setOnTouchListener((v, event) -> true);

            // Dữ liệu banner giả lập
             banners = Arrays.asList(
                    R.drawable.banner1,
                    R.drawable.banner2,
                    R.drawable.banner4,
                    R.drawable.banner5,
                    R.drawable.banner6,
                    R.drawable.banner7
            );

            BannerAdapter bannerAdapter = new BannerAdapter(banners);
            viewPager.setAdapter(bannerAdapter);

        BannerAdapter1 bannerAdapter1 = new BannerAdapter1(banners);

        bannerAdapter1.setOnItemClickListener(new OnItemClickListener<Integer>() {
            @Override
            public void onItemClick(Integer item, int position) {

            }
        });
        viewPager.setAdapter(bannerAdapter1);

            // Setup indicator
            setupIndicator(banners.size());
            updateIndicator(0, banners.size());

            viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    updateIndicator(position, banners.size());
                }
            });

            // Tự động chuyển banner
            runnable = new Runnable() {
                @Override
                public void run() {
                    int currentItem = viewPager.getCurrentItem();
                    int nextItem = (currentItem + 1) % banners.size();
                    viewPager.setCurrentItem(nextItem, true);
                    if (runnable != null) {
                        handler.postDelayed(runnable, delay);
                    }
                }
            };

        }

    private void setupIndicator(int count) {
        indicatorLine.removeAllViews();
        for (int i = 0; i < count; i++) {
            View dot = new View(requireContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, 6, 1f);
            params.setMargins(4, 0, 4, 0);
            dot.setLayoutParams(params);
            dot.setBackgroundColor(0x80FFFFFF);
            indicatorLine.addView(dot);
        }
    }

    private void openDetail(ProductModel product) {
        Intent intent = new Intent(requireContext(), DetailProductActivity.class);
        intent.putExtra("productId", product.getProductId());
        startActivity(intent);
    }

    private void openListActivity(Class<?> cls, ArrayList<ProductModel> productList) {
        Intent intent = new Intent(requireContext(), cls);
        intent.putExtra("productList", productList);
        startActivity(intent);
    }

    private void updateIndicator(int index, int total) {
        for (int i = 0; i < indicatorLine.getChildCount(); i++) {
            View child = indicatorLine.getChildAt(i);
            child.setBackgroundColor(i == index ? 0xFFFFFFFF : 0x80FFFFFF);
        }
        pageCountText.setText((index + 1) + "/" + total);
    }

    private void loadBestProductsFromApi() {
        apiService.getBestProduct().enqueue(new Callback<BestProductResponse>() {
            @Override
            public void onResponse(Call<BestProductResponse> call, Response<BestProductResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    allBestProducts = response.body().getContent();

                    // Hiển thị 4 sản phẩm đầu tiên trong recycler bestSeller
                    List<ProductModel> firstFour = allBestProducts.subList(0, Math.min(4, allBestProducts.size()));

                    bestSellerAdapter = new ProductAdapter(requireContext(), firstFour);
                    bestSeller.setAdapter(bestSellerAdapter);
                    bestSellerAdapter.setOnProductClickListener(AllFragment.this::openDetail);

                } else {
                    Toast.makeText(requireContext(), "Lỗi khi lấy sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BestProductResponse> call, Throwable t) {
                Toast.makeText(requireContext(), "Lỗi mạng: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }




    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable, delay);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }
}