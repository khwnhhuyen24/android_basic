package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


public class DetailBrandFragment extends Fragment {

    private ImageButton Back;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_select_product, container, false);

        initView(view);
        initEvent();
        return view;

    }
    private void initView(View view){
        Back = view.findViewById(R.id.icback);
    }

    private void initEvent(){
        Back.setOnClickListener(v -> {
            requireActivity().finish();
        });
    }
}