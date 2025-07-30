package com.example.myapplication.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.myapplication.R;
import com.example.myapplication.base.BaseRecyclerViewAdapter;
import com.example.myapplication.base.BaseViewHolder;

import java.util.List;

public class BannerAdapter1 extends BaseRecyclerViewAdapter<Integer, BannerAdapter1.BannerAdapterViewHolder> {

    public BannerAdapter1(List<Integer> items) {
        super(items);
    }
    @Override
    protected BannerAdapterViewHolder createViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_banner, parent, false);
        return new  BannerAdapterViewHolder(view);
    }

    public static class BannerAdapterViewHolder extends BaseViewHolder<Integer> {
        ImageView imageView;
        public BannerAdapterViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.bannerImage);
        }

        @Override
        public void bind(Integer item) {
            imageView.setImageResource(item);
        }
    }

}

