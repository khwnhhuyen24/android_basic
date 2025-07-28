package com.example.myapplication.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.model.FollowerRespon;

import com.google.android.material.imageview.ShapeableImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class FollowerAdapter extends RecyclerView.Adapter<FollowerAdapter.KolViewHolder> {

    private Context context;
    private List<FollowerRespon> kolList;

    public FollowerAdapter(Context context, List<FollowerRespon> kolList) {
        this.context = context;
        this.kolList = kolList != null ? kolList : new ArrayList<>();
    }

    @NonNull
    @Override
    public KolViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_kol_activity, parent, false);
        return new KolViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KolViewHolder holder, int position) {
        FollowerRespon kol = kolList.get(position);

        // Gán tên KOL
        String name = kol.displayName != null ? kol.displayName : "No Name";
        holder.txtDisplayName.setText(name);

        // Load ảnh từ URL avatar
        String avatarUrl = kol.avatar != null ? kol.avatar : "";
        Glide.with(context)
                .load(avatarUrl)
                .placeholder(R.drawable.img_brand1)
                .error(R.drawable.banner8)
                .into(holder.imgAvatar);
    }

    @Override
    public int getItemCount() {
        return kolList.size();
    }

    public void setData(List<FollowerRespon> list) {
        this.kolList = list != null ? list : new ArrayList<>();
        notifyDataSetChanged();
    }

    public static class KolViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView imgAvatar;
        TextView txtDisplayName;

        public KolViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            txtDisplayName = itemView.findViewById(R.id.txtDisplayName);
        }
    }
}
