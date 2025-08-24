package com.example.myapplication.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.local.AppDatabase;
import com.example.myapplication.model.ProductLocalModel;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<ProductLocalModel> cartItems;
    private OnCartItemChangeListener listener;
    private AppDatabase db;

    public CartAdapter(Context context, List<ProductLocalModel> cartItems,
                       OnCartItemChangeListener listener, AppDatabase db) {
        this.context = context;
        this.cartItems = cartItems;
        this.listener = listener;
        this.db = db;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_item_product_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        ProductLocalModel item = cartItems.get(position);

        // Hiển thị tên + size + màu
        String displayName = item.getProductname();
        if (item.getSize() != null && !item.getSize().isEmpty()) {
            displayName += " - Size: " + item.getSize();
        }
        if (item.getColor() != null && !item.getColor().isEmpty()) {
            displayName += " - Màu: " + item.getColor();
        }
        holder.tvName.setText(displayName);

        holder.tvQuantity.setText(String.valueOf(item.getCount()));
        holder.tvPrice.setText(formatCurrency(item.getPrice() * item.getCount()));

        Glide.with(context)
                .load(item.getProductImg())
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.imgProduct);

        // Tăng số lượng
        holder.btnPlus.setOnClickListener(v -> {
            item.setCount(item.getCount() + 1);
            holder.tvQuantity.setText(String.valueOf(item.getCount()));
            holder.tvPrice.setText(formatCurrency(item.getPrice() * item.getCount()));
            db.productDAO().updateProduct(item);
            if (listener != null) listener.onCartChanged();
        });

        // Giảm số lượng
        holder.btnMinus.setOnClickListener(v -> {
            if (item.getCount() > 1) {
                item.setCount(item.getCount() - 1);
                holder.tvQuantity.setText(String.valueOf(item.getCount()));
                holder.tvPrice.setText(formatCurrency(item.getPrice() * item.getCount()));
                db.productDAO().updateProduct(item);
                if (listener != null) listener.onCartChanged();
            }
        });

        // Xóa item
        holder.btnDelete.setOnClickListener(v -> {
            db.productDAO().deleteProduct(item);
            cartItems.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, cartItems.size());
            if (listener != null) listener.onCartChanged();
        });

        // ✅ Checkbox: set theo trạng thái của item, không set cứng true
        holder.cbItem.setOnCheckedChangeListener(null); // clear listener cũ
        holder.cbItem.setChecked(item.isSelected());
        holder.cbItem.setOnCheckedChangeListener((buttonView, isChecked) -> {
            item.setSelected(isChecked);
            if (listener != null) listener.onCartChanged();
        });
    }


    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvQuantity, tvOptions;
        ImageButton btnPlus, btnMinus;
        ImageView btnDelete, imgProduct;
        CheckBox cbItem;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvOptions = itemView.findViewById(R.id.tvOptions);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            btnMinus = itemView.findViewById(R.id.btnMinus);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            cbItem = itemView.findViewById(R.id.cbItem);
        }
    }

    private String formatCurrency(int value) {
        DecimalFormat formatter = new DecimalFormat("#,### đ");
        return formatter.format(value);
    }

    public interface OnCartItemChangeListener {
        void onCartChanged();
    }

    // Chọn tất cả item
    public void selectAll(boolean selected) {
        for (ProductLocalModel item : cartItems) {
            item.setSelected(selected);
        }
        notifyDataSetChanged();
        if (listener != null) listener.onCartChanged();
    }
}
