package com.example.myapplication.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.base.BaseRecyclerViewAdapter;
import com.example.myapplication.base.BaseViewHolder;
import com.example.myapplication.model.BrandModel;
import com.google.android.material.imageview.ShapeableImageView;

public class BrandAdapter1 extends BaseRecyclerViewAdapter<BrandModel, BrandAdapter1.BrandViewHolder> {

    public BrandAdapter1(java.util.List<BrandModel> items) {
        super(items);
    }

    @Override
    protected BrandViewHolder createViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_brand_fragment, parent, false);
        return new BrandViewHolder(view);
    }

    public static class BrandViewHolder extends BaseViewHolder<BrandModel> {
        ShapeableImageView imgLogo;
        TextView tvBrand;

        public BrandViewHolder(View itemView) {
            super(itemView);
            imgLogo = itemView.findViewById(R.id.img_logo);
            tvBrand = itemView.findViewById(R.id.tvBrand);
        }


        @Override
        public void bind(BrandModel item) {
            tvBrand.setText(item.getBrandName());

            Glide.with(itemView.getContext())
                    .load(item.getBrandAvatar())
                    .placeholder(R.drawable.img_brand1)
                    .error(R.drawable.img_brand5)
                    .into(imgLogo);

            // Nếu muốn xử lý sự kiện click:
//            itemView.setOnClickListener(v -> {
//                if (listener != null) {
//                    listener.onItemClick(getAdapterPosition());
//                }
//            });
        }
    }
}
