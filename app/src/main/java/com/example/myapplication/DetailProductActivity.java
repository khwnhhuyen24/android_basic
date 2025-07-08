package com.example.myapplication;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.myapplication.item.BannerAdapter;
import com.example.myapplication.item.ProductAdapter;
import com.example.myapplication.model.MockProductData;
import com.example.myapplication.model.ProductModel;

import java.util.List;

public class DetailProductActivity extends AppCompatActivity {

    ImageButton icBack, btnSearch, btnCart, imgDownload, migLink, imgNext;
    ImageView imgLogo, tym;
    TextView txtTitle, textReviewLabel, textReviewCount, number, txtPrice, txtOldPrice, txtShippingNote, txtGuarantee, txtBrand,tvDescription, NumberTym;
    RatingBar ratingBar;
    Button btnVoucher, btnSelectProduct;
    ViewPager2 bannerViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        // Ánh xạ view
        icBack = findViewById(R.id.icback);
        btnSearch = findViewById(R.id.btnSearch);
        btnCart = findViewById(R.id.btnCart);
        imgDownload = findViewById(R.id.imgDownload);
        migLink = findViewById(R.id.migLink);
        imgNext = findViewById(R.id.imgNext);


        imgLogo = findViewById(R.id.img_logo);
        tym = findViewById(R.id.tym);

        txtTitle = findViewById(R.id.txtTitle);
        textReviewLabel = findViewById(R.id.textReviewLabel);
        textReviewCount = findViewById(R.id.textReviewCount);
        number = findViewById(R.id.number);
        txtPrice = findViewById(R.id.txtPrice);
        txtOldPrice = findViewById(R.id.txtOldPrice);
        txtShippingNote = findViewById(R.id.txtShippingNote);
        txtGuarantee = findViewById(R.id.txtGuarantee);
        txtBrand = findViewById(R.id.txtBrand);
        NumberTym = findViewById(R.id.NumberTym);
        ratingBar = findViewById(R.id.ratingBar);
        btnVoucher = findViewById(R.id.btnVoucher);
        btnSelectProduct = findViewById(R.id.btnSelectProduct);
        tvDescription = findViewById(R.id.tv_description);
        bannerViewPager = findViewById(R.id.bannerViewPager);




        RecyclerView recyclerDetailProduct = findViewById(R.id.recyclerDetailProduct);

// Set LayoutManager theo chiều ngang
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerDetailProduct.setLayoutManager(layoutManager);

// Lấy danh sách sản phẩm giả lập
        List<ProductModel> productList = MockProductData.getFakeProducts();

// Tạo Adapter
        ProductAdapter productAdapter = new ProductAdapter(this, productList);
        recyclerDetailProduct.setAdapter(productAdapter);


        // Lấy dữ liệu ProductModel truyền qua Intent
        Intent intent = getIntent();
        ProductModel product = (ProductModel) intent.getSerializableExtra("product");

        if (product != null) {
            bindData(product);
        }

        icBack.setOnClickListener(v -> finish()); // Đóng activity khi nhấn back
    }

    private void bindData(ProductModel product) {

        // Tiêu đề sản phẩm
        txtTitle.setText(product.getProductName());

        // Giá bán hiện tại
        txtPrice.setText(String.format("%,dđ", product.getPriceSales()));

        // Giá gốc nếu có (đánh dấu gạch ngang)
        if (product.getPrice() > product.getPriceSales()) {
            txtOldPrice.setText(String.format("%,dđ", product.getPrice()));
            txtOldPrice.setPaintFlags(txtOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            txtOldPrice.setText("");
        }

        // Đánh giá trung bình (averageStar)
        ratingBar.setRating((float) product.getAverageStar());

        // Số lượng đánh giá
        textReviewCount.setText("(" + product.getCommentCount() + " đánh giá)");

        // Các TextView khác có thể set trống hoặc theo dữ liệu thực tế
        textReviewLabel.setText("");
        number.setText("");

        // Số lượt thích
        NumberTym.setText(String.valueOf(product.getLikeNumber()));



        // Mô tả hoặc các thông tin thêm
        tvDescription.setText(product.getProductDescription());

        btnVoucher.setText("Voucher 20K");

        // Tên thương hiệu
        if (product.getBrand() != null && product.getBrand().getBrandName() != null) {
            txtBrand.setText(product.getBrand().getBrandName());
        } else {
            txtBrand.setText("Thương hiệu không xác định");
        }

        // Load ảnh sản phẩm chính (resource id)
        Glide.with(this)
                .load(product.getProductImage())
                .into(bannerViewPager);

        // Load logo thương hiệu (brandAvatar là String URL hoặc đường dẫn)
        if (product.getBrand() != null && product.getBrand().getBrandAvatar() != null && !product.getBrand().getBrandAvatar().isEmpty()) {
            Glide.with(this)
                    .load(product.getBrand().getBrandAvatar())
                    .placeholder(R.drawable.ic_launcher_foreground) // ảnh tạm nếu cần
                    .error(R.drawable.ic_launcher_foreground)       // ảnh lỗi nếu load không được
                    .into(imgLogo);
        } else {
            imgLogo.setImageResource(R.drawable.ic_launcher_foreground); // ảnh mặc định nếu không có logo
        }

        if (product.getProductImages() != null && !product.getProductImages().isEmpty()) {
            BannerAdapter bannerAdapter = new BannerAdapter(this, product.getProductImages());
            bannerViewPager.setAdapter(bannerAdapter);
        }
    }

}
