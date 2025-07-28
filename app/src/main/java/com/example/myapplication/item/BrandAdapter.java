package com.example.myapplication.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.model.BrandModel;
import com.example.myapplication.model.FollowerRespon;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.BrandViewHolder> {

    private Context context;
    private List<BrandModel> brandList;

    public BrandAdapter(Context context, List<BrandModel> brandList) {
        this.context = context;
        this.brandList = brandList;
    }

    @NonNull
    @Override
    public BrandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_brand_fragment, parent, false);
        return new BrandViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BrandViewHolder holder, int position) {
        BrandModel brand = brandList.get(position);
        holder.tvBrand.setText(brand.getBrandName());

        String avatarUrl = brand.getBrandAvatar();

        Glide.with(context)
                .load(avatarUrl) // load ảnh từ URL
                .placeholder(R.drawable.img_brand1) // ảnh mặc định khi đang load
                .error(R.drawable.img_brand5) // ảnh hiển thị khi load lỗi
                .into(holder.imgLogo);
    }

    @Override
    public int getItemCount() {
        return brandList.size();
    }

    public void setData(ArrayList<BrandModel> list) {
        this.brandList = list != null ? list : new ArrayList<>();
        notifyDataSetChanged();
    }

    public static class BrandViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView imgLogo;
        TextView tvBrand;

        public BrandViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLogo = itemView.findViewById(R.id.img_logo);
            tvBrand = itemView.findViewById(R.id.tvBrand);
        }
    }
}
