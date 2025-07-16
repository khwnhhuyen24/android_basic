package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;

import com.example.myapplication.item.BannerProductAdapter;
import com.example.myapplication.item.ProductAdapter;
import com.example.myapplication.model.ProductModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DetailProductActivity extends AppCompatActivity {

    ImageButton icBack, btnSearch, btnCart, imgDownload, migLink, imgNext, imgNextBrand;
    ImageView imgLogo, expandIcon, tym;
    TextView txtTitle, textQuantitySold, textReviewCount, numberProduct,
            txtPrice, txtOldPrice, txtShippingNote, txtGuarantee,
            txtBrand, NumberTym;
    RatingBar ratingBar;
    Button btnVoucher, btnSelectProduct;
    ViewPager2 bannerViewPager;
    FragmentContainerView fragmentContainer;
    List<TextView> tabs;
    TextView tabInfo,tabComment, tabKolReview;
    private RecyclerView recyclerDetailProduct;
    private TextView pageCountText;
    LinearLayout layoutShippingNote;
    LinearLayout headerShipping;

    private boolean isLiked = false;
    private boolean isShippingNoteExpanded = false;
    private ProductModel product;

    private static final String PREFS_NAME = "like_prefs";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        initView();


        Intent intent = getIntent();
        product = (ProductModel) intent.getSerializableExtra("product");

        // ✅ Lấy danh sách tất cả sản phẩm từ Intent
        List<ProductModel> allProducts = (List<ProductModel>) intent.getSerializableExtra("allProducts");

        if (product != null) {
            bindData(product);
            loadLikeState();
            initEvent(); // ← Gọi sau khi có product

            // ✅ Gán danh sách sản phẩm liên quan vào RecyclerView
            if (allProducts != null && !allProducts.isEmpty()) {
                List<ProductModel> related = getRelatedProducts(allProducts, product);
                recyclerDetailProduct.setAdapter(new ProductAdapter(this, related));
            }
        }
    }

    private void initView(){
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
        textQuantitySold = findViewById(R.id.QuantitySold);
        textReviewCount = findViewById(R.id.textReviewCount);
        numberProduct = findViewById(R.id.numberProduct);
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
        recyclerDetailProduct = findViewById(R.id.recyclerDetailProduct);
        tabInfo = findViewById(R.id.tab_information);
        tabComment = findViewById(R.id.tab_comment);
        tabKolReview = findViewById(R.id.tab_KolReview);
        pageCountText = findViewById(R.id.pageCount);
        expandIcon = findViewById(R.id.expand);
        layoutShippingNote = findViewById(R.id.layoutShipping);
        headerShipping = findViewById(R.id.headerShipping);
    }

    private void initEvent(){
        tabs = Arrays.asList(tabInfo, tabComment, tabKolReview);
// Tab mặc định khi mở
        loadFragmentWithProduct(new ProductInfoFragment(), R.id.fragmentContainer, product);
        selectTab(tabInfo);

// Khi nhấn vào tab
        tabInfo.setOnClickListener(v -> {
            selectTab(tabInfo);
            loadFragmentWithProduct(new ProductInfoFragment(), R.id.fragmentContainer, product);
        });

        tabComment.setOnClickListener(v -> {
            selectTab(tabComment);
            loadFragmentWithProduct(new ProductCommentFragment(), R.id.fragmentContainer, product);
        });

        tabKolReview.setOnClickListener(v -> {
            selectTab(tabKolReview);
            loadFragmentWithProduct(new ProductKolReviewFragment(), R.id.fragmentContainer, product);
        });



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

        tym.setOnClickListener(v -> {
            isLiked = !isLiked;
            int like = Integer.parseInt(NumberTym.getText().toString());
            if (isLiked) {
                tym.setImageResource(R.drawable.ic_heart);
                like++;
            } else {
                tym.setImageResource(R.drawable.ic_tym);
                like = Math.max(0, like - 1);
            }
            NumberTym.setText(String.valueOf(like));

            // Lưu trạng thái vào SharedPreferences
            SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("liked_" + product.getProductId(), isLiked);
            editor.putInt("likes_" + product.getProductId(), like);
            editor.apply();
        });

        expandIcon.setOnClickListener(v -> toggleShippingNote());
        headerShipping.setOnClickListener(v -> toggleShippingNote());

    }

    private void updatePageCount(int currentIndex, int totalCount) {
        pageCountText.setText((currentIndex + 1) + "/" + totalCount);
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
        textReviewCount.setText("(" + product.getCommentCount() + " )");
        textQuantitySold.setText( product.getBuyCount() + " đã bán ");
        numberProduct.setText("Còn " + product.getProductRemain() + " sản phầm");

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

        // Thay bannerList bằng danh sách URL ảnh lấy từ API (giả sử product có List<String> getImageUrls())
        List<String> bannerList = product.getProductImages(); // phải là List<String> URL ảnh

        if (bannerList != null && !bannerList.isEmpty()) {
            setupBanner(bannerList);
        } else {
            // Nếu không có ảnh, bạn có thể truyền ảnh mặc định
            setupBanner(Arrays.asList());
        }

        if (product.getProductRemain() > 0) {
            btnSelectProduct.setText("Chọn sản phẩm");
            btnSelectProduct.setBackgroundColor(
                    ContextCompat.getColor(this, android.R.color.holo_green_dark)
            );
            btnSelectProduct.setEnabled(true);
        } else {
            btnSelectProduct.setText("Sản phẩm đã hết hàng");
            btnSelectProduct.setBackgroundColor(
                    ContextCompat.getColor(this, android.R.color.darker_gray)
            );
            btnSelectProduct.setEnabled(false);
        }
    }

    private void setupBanner(List<String> bannerUrls) {
        BannerProductAdapter adapter = new BannerProductAdapter(this, bannerUrls);
        bannerViewPager.setAdapter(adapter);

        // Cập nhật ban đầu số trang
        updatePageCount(0, bannerUrls.size());

        // Lắng nghe sự kiện chuyển trang
        bannerViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                updatePageCount(position, bannerUrls.size());
            }
        });
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

    private void toggleShippingNote() {
        isShippingNoteExpanded = !isShippingNoteExpanded;
        if (isShippingNoteExpanded) {
            txtShippingNote.setVisibility(View.VISIBLE);
            expandIcon.setImageResource(R.drawable.ic_expand); // icon thu gọn
        } else {
            txtShippingNote.setVisibility(View.GONE);
            expandIcon.setImageResource(R.drawable.ic_expand); // icon mở rộng
        }
    }

    private void loadLikeState() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        isLiked = prefs.getBoolean("liked_" + product.getProductId(), false);
        int likeCount = prefs.getInt("likes_" + product.getProductId(), product.getLikeNumber());

        NumberTym.setText(String.valueOf(likeCount));
        tym.setImageResource(isLiked ? R.drawable.ic_heart : R.drawable.ic_tym);
    }

    private void loadFragmentWithProduct(Fragment fragment, int containerId, ProductModel product) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("product", product); // Đảm bảo ProductModel implements Serializable
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(containerId, fragment)
                .commit();
    }

    private List<ProductModel> getRelatedProducts(List<ProductModel> allProducts, ProductModel currentProduct) {
        List<ProductModel> relatedList = new ArrayList<>();
        for (ProductModel p : allProducts) {
            if (p.getProductId() != currentProduct.getProductId() &&
                    (p.getBrand().getBrandId() == currentProduct.getBrand().getBrandId() ||
                            p.getCategory().getCategoryId() == currentProduct.getCategory().getCategoryId())) {
                relatedList.add(p);
            }
        }
        return relatedList;
    }

}
