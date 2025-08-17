package com.example.myapplication.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.base.BaseRecyclerViewAdapter;
import com.example.myapplication.base.BaseViewHolder;

public class BannerProductAdapter1 extends BaseRecyclerViewAdapter<String, BannerProductAdapter1.BannerViewHolder> {

    public BannerProductAdapter1(java.util.List<String> items) {
        super(items);
    }

    @Override
    protected BannerViewHolder createViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_banner, parent, false);
        return new BannerViewHolder(view);
    }

    public static class BannerViewHolder extends BaseViewHolder<String> {
        ImageView imageView;

        public BannerViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.bannerImage);
        }

        @Override
        public void bind(String imageUrl) {
            Glide.with(itemView.getContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.banner1)
                    .error(R.drawable.banner3)
                    .into(imageView);

            // Nếu bạn muốn bắt sự kiện click:
//            itemView.setOnClickListener(v -> {
//                if (listener != null) {
//                    listener.onItemClick(getAdapterPosition());
//                }
//            });
        }
    }
}
