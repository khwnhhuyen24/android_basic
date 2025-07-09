package com.example.myapplication.item;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.model.ProductModel;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private final List<ProductModel> productList;
    private final Context context;

    // Interface callback sự kiện click
    public interface OnProductClickListener {
        void onProductClick(ProductModel product);
    }

    private OnProductClickListener listener;

    // Setter listener
    public void setOnProductClickListener(OnProductClickListener listener) {
        this.listener = listener;
    }

    public ProductAdapter(Context context, List<ProductModel> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_item_all_fragment, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductModel product = productList.get(position);

        holder.textName.setText(product.getProductName());

        // Hiện giá gốc và giá sale
        holder.textOldPrice.setText(String.format("%,dđ", product.getPrice()));
        holder.textNewPrice.setText(String.format("%,dđ", product.getPriceSales()));

        // Số lượng đã bán
        holder.textSold.setText(product.getBuyCount() + " đã bán");

        // Số lượt đánh giá + nhãn
        holder.textReviewCount.setText("(" + product.getAverageStar() + ")");
        holder.textReviewLabel.setText(product.getCommentCount() + " ");
        holder.textReviewLabel.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_tym, 0);

        // Rating sao
        holder.ratingBar.setRating((float) product.getAverageStar());

        // ✅ Load ảnh sản phẩm từ URL
        Glide.with(context)
                .load(product.getProductImageUrl())
                .placeholder(R.drawable.banner1) // ảnh tạm
                .error(R.drawable.banner3)       // nếu lỗi
                .into(holder.imageProduct);

        // Gạch ngang giá gốc
        holder.textOldPrice.setPaintFlags(holder.textOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        // Xử lý sự kiện click item
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onProductClick(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (productList == null) ? 0 : productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imageProduct;
        TextView textName, textOldPrice, textNewPrice, textSold, textReviewCount, textReviewLabel;
        RatingBar ratingBar;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageProduct = itemView.findViewById(R.id.imageProduct);
            textName = itemView.findViewById(R.id.textProductName);
            textOldPrice = itemView.findViewById(R.id.textOldPrice);
            textNewPrice = itemView.findViewById(R.id.textNewPrice);
            textSold = itemView.findViewById(R.id.textSold);
            textReviewCount = itemView.findViewById(R.id.textReviewCount);
            textReviewLabel = itemView.findViewById(R.id.textReviewLabel);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }
}
