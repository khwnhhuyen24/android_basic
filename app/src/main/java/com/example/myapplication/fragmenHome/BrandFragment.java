package com.example.myapplication.fragmenHome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.item.BrandAdapter;
import com.example.myapplication.model.BrandModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BrandFragment extends Fragment {

    private RecyclerView recyclerView;
    private BrandAdapter adapter;
    private List<BrandModel> brandList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_brand, container, false);




        initView(view);
        initEvent();
        return view;
    }

    private void initView(View view){
        recyclerView = view.findViewById(R.id.recyclerBrand);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 3));
    }

    private void initEvent(){
        // 👉 Dùng dữ liệu từ file giả lập riêng
        if (brandList != null && !brandList.isEmpty()) {
            adapter = new BrandAdapter(requireContext(), brandList);
            recyclerView.setAdapter(adapter);
        }

    }
}
