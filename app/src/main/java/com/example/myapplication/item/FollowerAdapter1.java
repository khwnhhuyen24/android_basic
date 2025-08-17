package com.example.myapplication.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.base.BaseRecyclerViewAdapter;
import com.example.myapplication.base.BaseViewHolder;
import com.example.myapplication.model.FollowerRespon;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class FollowerAdapter1 extends BaseRecyclerViewAdapter<FollowerRespon, FollowerAdapter1.FollowerViewHolder> {

    public FollowerAdapter1(List<FollowerRespon> items) {
        super(items);
    }

    @Override
    protected FollowerViewHolder createViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_kol_activity, parent, false);
        return new FollowerViewHolder(view);
    }

    public static class FollowerViewHolder extends BaseViewHolder<FollowerRespon> {
        ShapeableImageView imgAvatar;
        TextView txtDisplayName;

        public FollowerViewHolder(View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            txtDisplayName = itemView.findViewById(R.id.txtDisplayName);
        }

        @Override
        public void bind(FollowerRespon item) {
            txtDisplayName.setText(item.displayName != null ? item.displayName : "No Name");

            Glide.with(itemView.getContext())
                    .load(item.avatar != null ? item.avatar : "")
                    .placeholder(R.drawable.img_brand1)
                    .error(R.drawable.banner8)
                    .into(imgAvatar);
        }
    }
}
