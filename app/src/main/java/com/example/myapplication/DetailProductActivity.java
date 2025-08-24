package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
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
import com.example.myapplication.local.AppDatabase;
import com.example.myapplication.model.ProductLocalModel;
import com.example.myapplication.model.ProductModel;
import com.example.myapplication.remote.ApiClient;
import com.example.myapplication.remote.ApiService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProductActivity extends AppCompatActivity {

    ImageButton icBack;
    ImageButton btnSearch;
    ImageButton btnCart;
    ImageButton imgDownload;
    ImageButton migLink;
    ImageButton imgNext;
    ImageButton imgNextBrand;
    ImageView imgLogo;
    ImageView expandIcon;
    ImageView tym;
    TextView txtTitle;
    TextView textQuantitySold;
    TextView textReviewCount;
    TextView numberProduct;
    TextView txtPrice;
    TextView txtOldPrice;
    TextView txtShippingNote;
    TextView txtGuarantee;
    TextView txtBrand;
    TextView NumberTym;
    RatingBar ratingBar;
    Button btnVoucher;
    Button btnSelectProduct;
    ViewPager2 bannerViewPager;
    FragmentContainerView fragmentContainer;
    List<TextView> tabs;
    TextView tabInfo;
    TextView tabComment;
    TextView tabKolReview;
    private RecyclerView recyclerDetailProduct;
    private TextView pageCountText;
    LinearLayout layoutShippingNote;
    LinearLayout headerShipping;
    LinearLayout Size;
    LinearLayout color;

    private boolean isLiked = false;
    private boolean isShippingNoteExpanded = false;
    private ProductModel product;

    private static final String PREFS_NAME = "like_prefs";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        try {
            initView();

            Intent intent = getIntent();
            int productId = intent.getIntExtra("productId", -1);
            Log.d("PRODUCT_ID_INTENT", "ID nhận từ intent: " + productId);

            List<ProductModel> allProducts = (List<ProductModel>) intent.getSerializableExtra("allProducts");

            if (productId != -1) {
                callApiGetProductById(productId, allProducts);
            } else {
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }
    }

    private void callApiGetProductById(int productId, List<ProductModel> allProducts) {
        try {
            ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);

            apiService.getProductById(productId).enqueue(new Callback<ProductModel>() {
                @Override
                public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                    try {
                        if (response.isSuccessful() && response.body() != null) {
                            product = response.body();

                            bindData(product);
                            loadLikeState();
                            initEvent();

                            if (allProducts != null && !allProducts.isEmpty() && recyclerDetailProduct != null) {
                                List<ProductModel> related = getRelatedProducts(allProducts, product);
                                recyclerDetailProduct.setAdapter(new ProductAdapter(DetailProductActivity.this, related));
                            }
                        } else {
                            finish();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<ProductModel> call, Throwable t) {
                    t.printStackTrace();
                    finish();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }
    }

    private void setupBanner(List<String> bannerList) {
        try {
            if (bannerViewPager != null && bannerList != null) {
                BannerProductAdapter bannerAdapter = new BannerProductAdapter(this, bannerList);
                bannerViewPager.setAdapter(bannerAdapter);
                bannerViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);
                        updatePageCount(position, bannerList.size());
                    }
                });
                updatePageCount(0, bannerList.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bindData(ProductModel product) {
        try {
            if (product == null) return;

            if (txtTitle != null) txtTitle.setText(product.getProductName());
            if (txtPrice != null) txtPrice.setText(String.format("%,dđ", product.getPriceSales()));

            if (txtOldPrice != null) {
                if (product.getPrice() > product.getPriceSales()) {
                    txtOldPrice.setText(String.format("%,dđ", product.getPrice()));
                    txtOldPrice.setPaintFlags(txtOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    txtOldPrice.setText("");
                }
            }

            if (ratingBar != null) ratingBar.setRating((float) product.getAverageStar());
            if (textReviewCount != null) textReviewCount.setText("(" + product.getCommentCount() + " )");
            if (textQuantitySold != null) textQuantitySold.setText(product.getBuyCount() + " đã bán ");
            if (numberProduct != null) numberProduct.setText("Còn " + product.getProductRemain() + " sản phẩm");

            if (NumberTym != null) NumberTym.setText(String.valueOf(product.getLikeNumber()));

            if (txtBrand != null) {
                if (product.getBrand() != null && product.getBrand().getBrandName() != null) {
                    txtBrand.setText(product.getBrand().getBrandName());
                } else {
                    txtBrand.setText("Thương hiệu không xác định");
                }
            }

            if (imgLogo != null) {
                if (product.getBrand() != null && product.getBrand().getBrandAvatar() != null && !product.getBrand().getBrandAvatar().isEmpty()) {
                    Glide.with(this)
                            .load(product.getBrand().getBrandAvatar())
                            .placeholder(R.drawable.ic_launcher_foreground)
                            .into(imgLogo);
                } else {
                    imgLogo.setImageResource(R.drawable.ic_launcher_foreground);
                }
            }

            List<String> bannerList = product.getProductImages();
            if (bannerList != null && !bannerList.isEmpty()) {
                setupBanner(bannerList);
            } else {
                setupBanner(new ArrayList<>());
            }

            if (btnSelectProduct != null) {
                if (product.getProductRemain() > 0) {
                    btnSelectProduct.setText("Chọn sản phẩm");
                    btnSelectProduct.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_green_dark));
                    btnSelectProduct.setEnabled(true);
                } else {
                    btnSelectProduct.setText("Sản phẩm đã hết hàng");
                    btnSelectProduct.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray));
                    btnSelectProduct.setEnabled(false);
                }
            }

            // Check hiển thị size
            if (Size != null) {
                if (product.getDynamicSizes() != null && !product.getDynamicSizes().isEmpty()) {
                    Size.setVisibility(View.VISIBLE);
                } else {
                    Size.setVisibility(View.GONE);
                }
            }

            // Check hiển thị màu
            if (color != null) {
                if (product.getDynamicColors() != null && !product.getDynamicColors().isEmpty()) {
                    color.setVisibility(View.VISIBLE);
                } else {
                    color.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        try {
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
            color = findViewById(R.id.color);
            Size = findViewById(R.id.size);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initEvent() {
        try {
            if (tabInfo != null && tabComment != null && tabKolReview != null) {
                tabs = Arrays.asList(tabInfo, tabComment, tabKolReview);
                loadFragmentWithProduct(new ProductInfoFragment(), R.id.fragmentContainer, product);
                selectTab(tabInfo);

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
            }

            if (icBack != null) {
                icBack.setOnClickListener(v -> finish());
            }

            if (imgNextBrand != null) {
                imgNextBrand.setOnClickListener(v -> startFullFragment("brand"));
            }

            if (imgNext != null) {
                imgNext.setOnClickListener(v -> startFullFragment("select"));
            }

            if (btnVoucher != null) {
                btnVoucher.setOnClickListener(v -> startFullFragment("select"));
            }

            if (btnSelectProduct != null) {
                btnSelectProduct.setOnClickListener(v -> {
                    try {
                        if (product != null && product.getProductRemain() > 0) {
                            // Chuyển từ ProductModel sang ProductLocalModel
                            ProductLocalModel localProduct = mapToLocalProduct(product);

                            if (localProduct != null) {
                                // Tạo BottomSheet với ProductLocalModel
                                BottomSheetCartFragment bottomSheet = BottomSheetCartFragment.newInstance(localProduct);
                                bottomSheet.show(getSupportFragmentManager(), "BottomSheetCartFragment");
                            }
                        }
                    } catch (Exception e) {
                        Log.e("SELECT_PRODUCT_ERROR", "Lỗi khi chọn sản phẩm", e);
                    }
                });
            }

            if (btnCart != null) {
                btnCart.setOnClickListener(v -> {
                    try {
                        Intent intent = new Intent(DetailProductActivity.this, ProductCartActivity.class);
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }

            if (tym != null && NumberTym != null) {
                tym.setOnClickListener(v -> {
                    try {
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

                        if (product != null) {
                            SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putBoolean("liked_" + product.getProductId(), isLiked);
                            editor.putInt("likes_" + product.getProductId(), like);
                            editor.apply();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }

            if (expandIcon != null) {
                expandIcon.setOnClickListener(v -> toggleShippingNote());
            }

            if (headerShipping != null) {
                headerShipping.setOnClickListener(v -> toggleShippingNote());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updatePageCount(int currentIndex, int totalCount) {
        try {
            if (pageCountText != null) {
                pageCountText.setText((currentIndex + 1) + "/" + totalCount);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadFragmentWithProduct(Fragment fragment, int containerId, ProductModel product) {
        try {
            if (fragment != null && product != null) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("product", product);
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .replace(containerId, fragment)
                        .commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void selectTab(TextView selectedTab) {
        try {
            if (tabs != null && selectedTab != null) {
                for (TextView tab : tabs) {
                    if (tab != null) {
                        tab.setTextColor(tab == selectedTab ? Color.parseColor("#FF000000") : Color.parseColor("#80050000"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void toggleShippingNote() {
        try {
            if (txtShippingNote != null && expandIcon != null) {
                isShippingNoteExpanded = !isShippingNoteExpanded;
                txtShippingNote.setVisibility(isShippingNoteExpanded ? View.VISIBLE : View.GONE);
                expandIcon.setImageResource(R.drawable.ic_expand);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadLikeState() {
        try {
            if (product != null && NumberTym != null && tym != null) {
                SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                isLiked = prefs.getBoolean("liked_" + product.getProductId(), false);
                int likeCount = prefs.getInt("likes_" + product.getProductId(), product.getLikeNumber());

                NumberTym.setText(String.valueOf(likeCount));
                tym.setImageResource(isLiked ? R.drawable.ic_heart : R.drawable.ic_tym);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<ProductModel> getRelatedProducts(List<ProductModel> allProducts, ProductModel currentProduct) {
        List<ProductModel> relatedList = new ArrayList<>();
        try {
            if (allProducts != null && currentProduct != null) {
                for (ProductModel p : allProducts) {
                    if (p != null && p.getProductId() != currentProduct.getProductId()) {
                        boolean sameBrand = false;
                        boolean sameCategory = false;

                        // Kiểm tra brand
                        if (p.getBrand() != null && currentProduct.getBrand() != null) {
                            sameBrand = p.getBrand().getBrandId() == currentProduct.getBrand().getBrandId();
                        }

                        // Kiểm tra category
                        if (p.getCategory() != null && currentProduct.getCategory() != null) {
                            sameCategory = p.getCategory().getCategoryId() == currentProduct.getCategory().getCategoryId();
                        }

                        if (sameBrand || sameCategory) {
                            relatedList.add(p);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return relatedList;
    }

    private void startFullFragment(String fragmentValue) {
        try {
            Intent intent = new Intent(this, FullFragmentDetailProActivity.class);
            intent.putExtra("fragment", fragmentValue);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ProductLocalModel mapToLocalProduct(ProductModel product) {
        ProductLocalModel local = null;
        try {
            if (product == null) return null;

            local = new ProductLocalModel();
            local.setProductname(product.getProductName());
            local.setProductImg(product.getProductImages() != null && !product.getProductImages().isEmpty()
                    ? product.getProductImages().get(0) : "");
            local.setPrice(product.getPriceSales());
            local.setCount(1); // mặc định thêm 1 sản phẩm vào giỏ
            local.setSelected(false); // mặc định chưa được chọn

            // Nếu có lựa chọn size/color, bạn có thể set size/color đầu tiên
            if (product.getDynamicSizes() != null && !product.getDynamicSizes().isEmpty()) {
                local.setSize(product.getDynamicSizes().get(0).getDynamicSizeCode());
            }
            if (product.getDynamicColors() != null && !product.getDynamicColors().isEmpty()) {
                local.setColor(product.getDynamicColors().get(0).getDynamicColorName());
            }

            // KHÔNG lưu vào database ở đây - để BottomSheet xử lý
            Log.d("PRODUCT_MAP", "Mapped product: " + local.getProductname());

        } catch (Exception e) {
            Log.e("MAP_PRODUCT_ERROR", "Lỗi khi chuyển đổi ProductModel sang ProductLocalModel", e);
        }
        return local;
    }
}