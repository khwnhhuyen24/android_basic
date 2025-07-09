package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.myapplication.fragmenHome.AllFragment;
import com.example.myapplication.fragmenHome.BestFragment;
import com.example.myapplication.fragmenHome.BestReviewFragment;
import com.example.myapplication.item.BannerAdapter;
import com.example.myapplication.item.ProductAdapter;
import com.example.myapplication.model.MockProductData;
import com.example.myapplication.model.ProductModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DetailProductActivity extends AppCompatActivity {

    ImageButton icBack, btnSearch, btnCart, imgDownload, migLink, imgNext, imgNextBrand;
    ImageView imgLogo, tym;
    TextView txtTitle, textReviewLabel, textReviewCount, number,
            txtPrice, txtOldPrice, txtShippingNote, txtGuarantee,
            txtBrand, NumberTym;
    RatingBar ratingBar;
    Button btnVoucher, btnSelectProduct;
    ViewPager2 bannerViewPager;
    FragmentContainerView fragmentContainer;
    List<TextView> tabs;

    private boolean isLiked = false;
    private ProductModel product;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        // Ánh xạ
        icBack = findViewById(R.id.icback);
        btnSearch = findViewById(R.id.btnSearch);
        btnCart = findViewById(R.id.btnCart);
        imgDownload = findViewById(R.id.imgDownload);
        migLink = findViewById(R.id.migLink);
        imgNextBrand = findViewById(R.id.imgNextBrand);
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
        bannerViewPager = findViewById(R.id.bannerViewPager);
        fragmentContainer = findViewById(R.id.fragmentContainer);
        RecyclerView recyclerDetailProduct = findViewById(R.id.recyclerDetailProduct);

        TextView tabInfo = findViewById(R.id.tab_information);
        TextView tabComment = findViewById(R.id.tab_comment);
        TextView tabKolReview = findViewById(R.id.tab_KolReview);

        // Khởi tạo danh sách tab
        tabs = Arrays.asList(tabInfo, tabComment, tabKolReview);

        // Load mặc định fragment
        loadFragment(new ProductInfoFragment(), R.id.fragmentContainer);
        selectTab(tabInfo);

        // Sự kiện click tab
        tabInfo.setOnClickListener(v -> {
            selectTab(tabInfo);
            loadFragment(new ProductInfoFragment(), R.id.fragmentContainer);
        });

        tabComment.setOnClickListener(v -> {
            selectTab(tabComment);
            loadFragment(new ProductCommentFragment(), R.id.fragmentContainer);
        });

        tabKolReview.setOnClickListener(v -> {
            selectTab(tabKolReview);
            loadFragment(new ProductKolReviewFragment(), R.id.fragmentContainer);
        });

        // RecyclerView sản phẩm liên quan
        recyclerDetailProduct.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerDetailProduct.setAdapter(new ProductAdapter(this, MockProductData.getFakeProducts()));

        // Nhận dữ liệu sản phẩm
        Intent intent = getIntent();
        product = (ProductModel) intent.getSerializableExtra("product");

        if (product != null) {
            bindData(product);
        }

        // Nút quay lại
        icBack.setOnClickListener(v -> finish());


        imgNextBrand.setOnClickListener(v -> {
            Intent i = new Intent(this, FullFragmentDetailProActivity.class);
            i.putExtra("fragment", "brand");
            startActivity(i);
        });


        imgNext.setOnClickListener(v -> {
            Intent i = new Intent(this, FullFragmentDetailProActivity.class);
            i.putExtra("fragment", "select");
            startActivity(i);
        });

        btnVoucher.setOnClickListener(v -> {
            Intent i = new Intent(this, FullFragmentDetailProActivity.class);
            i.putExtra("fragment", "select");
            startActivity(i);
        });


        btnSelectProduct.setOnClickListener(v -> {
            if (product != null && product.getProductRemain() > 0) {
                Intent i = new Intent(this, FullFragmentDetailProActivity.class);
                i.putExtra("fragment", "select");
                startActivity(i);
            }
        });


        // Nút Tym like/unlike
        tym.setOnClickListener(v -> {
            isLiked = !isLiked;
            int like = Integer.parseInt(NumberTym.getText().toString());
            if (isLiked) {
                tym.setImageResource(R.drawable.ic_heart);
                NumberTym.setText(String.valueOf(like + 1));
            } else {
                tym.setImageResource(R.drawable.ic_tym);
                NumberTym.setText(String.valueOf(Math.max(0, like - 1)));
            }
        });
    }

    private void bindData(ProductModel product) {
        txtTitle.setText(product.getProductName());

        txtPrice.setText(String.format("%,dđ", product.getPriceSales()));
        if (product.getPrice() > product.getPriceSales()) {
            txtOldPrice.setText(String.format("%,dđ", product.getPrice()));
            txtOldPrice.setPaintFlags(txtOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            txtOldPrice.setText("");
        }

        ratingBar.setRating((float) product.getAverageStar());
        textReviewCount.setText("(" + product.getCommentCount() + " đánh giá)");
        textReviewLabel.setText("");
        number.setText("");

        NumberTym.setText(String.valueOf(product.getLikeNumber()));

        if (product.getBrand() != null && product.getBrand().getBrandName() != null) {
            txtBrand.setText(product.getBrand().getBrandName());
        } else {
            txtBrand.setText("Thương hiệu không xác định");
        }

        if (product.getBrand() != null && product.getBrand().getBrandAvatar() != null && !product.getBrand().getBrandAvatar().isEmpty()) {
            Glide.with(this)
                    .load(product.getBrand().getBrandAvatar())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(imgLogo);
        } else {
            imgLogo.setImageResource(R.drawable.ic_launcher_foreground);
        }

        List<Integer> bannerList = new ArrayList<>();
        bannerList.add(R.drawable.banner1);
        bannerList.add(R.drawable.banner2);
        bannerList.add(R.drawable.banner3);
        BannerAdapter bannerAdapter = new BannerAdapter(bannerList);
        bannerViewPager.setAdapter(bannerAdapter);

        if (product.getProductRemain() > 0) {
            btnSelectProduct.setText("Chọn sản phẩm");
            btnSelectProduct.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
            btnSelectProduct.setEnabled(true);
        } else {
            btnSelectProduct.setText("Sản phẩm đã hết hàng");
            btnSelectProduct.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            btnSelectProduct.setEnabled(false);
        }
    }

    // Đổi màu tab được chọn
    void selectTab(TextView selectedTab) {
        for (TextView tab : tabs) {
            if (tab == selectedTab) {
                tab.setTextColor(Color.parseColor("#FF000000")); // Sáng lên
            } else {
                tab.setTextColor(Color.parseColor("#80050000")); // Mờ đi
            }
        }
    }


    private void loadFragment(Fragment fragment, int containerId) {
        getSupportFragmentManager().beginTransaction()
                .replace(containerId, fragment)
                .commit();
    }


}
