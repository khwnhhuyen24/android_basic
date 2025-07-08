package com.example.myapplication.fragmenHome;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.DetailProductActivity;
import com.example.myapplication.R;
import com.example.myapplication.fragmenHome.BestSellerActivity;
import com.example.myapplication.DetailProductActivity;
import com.example.myapplication.fragmenHome.FavoriteActivity;
import com.example.myapplication.fragmenHome.SuggestionActivity;
import com.example.myapplication.item.BannerAdapter;
import com.example.myapplication.item.ProductAdapter;
import com.example.myapplication.model.MockProductData;
import com.example.myapplication.model.ProductModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AllFragment extends Fragment {

    private ViewPager2 viewPager;
    private Handler handler = new Handler();
    private Runnable runnable;
    private final int delay = 2000;

    private ProductAdapter suggestionAdapter, favoriteAdapter, bestSellerAdapter;
    private LinearLayout indicatorLine;
    private TextView pageCountText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_all, container, false);

        // Khởi tạo view
        viewPager = view.findViewById(R.id.bannerViewPager);
        RecyclerView suggestion = view.findViewById(R.id.recycler_view_suggestion);
        RecyclerView favorite = view.findViewById(R.id.recycler_view_favorite);
        RecyclerView bestSeller = view.findViewById(R.id.recycler_view_best_seller);
        indicatorLine = view.findViewById(R.id.indicatorLine);
        pageCountText = view.findViewById(R.id.pageCount);

        // Lấy toàn bộ danh sách 7 item
        ArrayList<ProductModel> fullProductList = new ArrayList<>(MockProductData.getFakeProducts());

        // Hiển thị 4 item đầu tiên mỗi phần
        List<ProductModel> firstFour = fullProductList.subList(0, Math.min(4, fullProductList.size()));

        suggestionAdapter = new ProductAdapter(requireContext(), firstFour);
        favoriteAdapter = new ProductAdapter(requireContext(), firstFour);
        bestSellerAdapter = new ProductAdapter(requireContext(), firstFour);

        suggestion.setAdapter(suggestionAdapter);
        favorite.setAdapter(favoriteAdapter);
        bestSeller.setAdapter(bestSellerAdapter);

        // 👉 Xử lý sự kiện khi nhấn vào sản phẩm trong từng adapter
        suggestionAdapter.setOnProductClickListener(product -> {
            Intent intent = new Intent(requireContext(), DetailProductActivity.class);
            intent.putExtra("product", product);
            startActivity(intent);
        });

        favoriteAdapter.setOnProductClickListener(product -> {
            Intent intent = new Intent(requireContext(), DetailProductActivity.class);
            intent.putExtra("product", product);
            startActivity(intent);
        });

        bestSellerAdapter.setOnProductClickListener(product -> {
            Intent intent = new Intent(requireContext(), DetailProductActivity.class);
            intent.putExtra("product", product);
            startActivity(intent);
        });

        // Xử lý nút "Xem thêm"
        Button SeeMoreSuggestion = view.findViewById(R.id.btnSeeMoreSuggestion);
        Button SeeMoreFavorite = view.findViewById(R.id.btnSeeMoreFavorite);
        Button SeeMoreBest = view.findViewById(R.id.btnSeeMoreBest);

        SeeMoreSuggestion.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), SuggestionActivity.class);
            intent.putExtra("productList", fullProductList);
            startActivity(intent);
        });

        SeeMoreFavorite.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), FavoriteActivity.class);
            intent.putExtra("productList", fullProductList);
            startActivity(intent);
        });

        SeeMoreBest.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), BestSellerActivity.class);
            intent.putExtra("productList", fullProductList);
            startActivity(intent);
        });

        // Khóa cuộn tay ngang của ViewPager2
        viewPager.setOnTouchListener((v, event) -> true);

        // Dữ liệu banner giả lập
        List<Integer> banners = Arrays.asList(

        );

        BannerAdapter bannerAdapter = new BannerAdapter(banners);
        viewPager.setAdapter(bannerAdapter);

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
                handler.postDelayed(this, delay);
            }
        };

        return view;
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

    private void updateIndicator(int index, int total) {
        for (int i = 0; i < indicatorLine.getChildCount(); i++) {
            View child = indicatorLine.getChildAt(i);
            child.setBackgroundColor(i == index ? 0xFFFFFFFF : 0x80FFFFFF);
        }
        pageCountText.setText((index + 1) + "/" + total);
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
