package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.fragmenHome.AllFragment;
import com.example.myapplication.fragmenHome.BestFragment;
import com.example.myapplication.fragmenHome.BestReviewFragment;
import com.example.myapplication.fragmenHome.EventFragment;

import java.util.Arrays;
import java.util.List;


public class HomeFragment extends Fragment {

    TextView tabAll, tabBest, tabReview, tabEvent;
    List<TextView> tabs;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initView(view);
        initEvemt();
        return view;

    }

    private void initView(View view){
        tabAll = view.findViewById(R.id.tab_all);
        tabBest = view.findViewById(R.id.tab_best);
        tabReview = view.findViewById(R.id.tab_review);
        tabEvent = view.findViewById(R.id.tab_event);

    }

    private void initEvemt(){
        // Khởi tạo danh sách sau khi findViewById
        tabs = Arrays.asList(tabAll, tabBest, tabReview, tabEvent);

        // Load mặc định
        loadFragment(new AllFragment());
        selectTab(tabAll); // Làm sáng tab mặc định

        tabAll.setOnClickListener(v -> {
            selectTab(tabAll);
            loadFragment(new AllFragment());
        });

        tabBest.setOnClickListener(v -> {
            selectTab(tabBest);
            loadFragment(new BestFragment());
        });

        tabReview.setOnClickListener(v -> {
            selectTab(tabReview);
            loadFragment(new BestReviewFragment());
        });

        tabEvent.setOnClickListener(v -> {
            selectTab(tabEvent);
            loadFragment(new EventFragment());
        });


    }

    // Hàm đổi màu tab
    void selectTab(TextView selectedTab) {
        for (TextView tab : tabs) {
            if (tab == selectedTab) {
                tab.setTextColor(Color.parseColor("#FF000000")); // sáng lên
            } else {
                tab.setTextColor(Color.parseColor("#80050000")); // nhạt đi
            }
        }
    }
    public void loadAllTab() {
        loadFragment(new AllFragment());
    }

    private void loadFragment(Fragment fragment) {
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}

