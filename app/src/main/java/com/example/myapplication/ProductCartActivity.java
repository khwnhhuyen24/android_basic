package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.item.CartAdapter;
import com.example.myapplication.local.AppDatabase;
import com.example.myapplication.model.ProductLocalModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ProductCartActivity extends AppCompatActivity {

    private RecyclerView rvCartItems;
    private TextView tvSubtotal, tvDiscount, tvVoucher;
    private CheckBox cbSelectAll;
    private LinearLayout productCart, cartEmpty;
    private ArrayList<ProductLocalModel> cartItems;
    private CartAdapter adapter;

    private AppDatabase db;
    private boolean isUpdatingSelectAll = false; // tránh infinite loop

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_cart);

        try {
            db = AppDatabase.getInstance(this);
            cartItems = new ArrayList<>();

            initViews();
            handleIntentData();

        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent().getSerializableExtra("cartItems") == null) {
            loadCartItemsFromDB();
        }
    }

    private void initViews() {
        rvCartItems = findViewById(R.id.rvCartItems);
        tvSubtotal = findViewById(R.id.tvSubtotal);
        tvDiscount = findViewById(R.id.tvDiscount);
        tvVoucher = findViewById(R.id.tvVoucher);
        cbSelectAll = findViewById(R.id.cbSelectAll);
        productCart = findViewById(R.id.productCart);
        cartEmpty = findViewById(R.id.cartEmpty);

        ImageButton back = findViewById(R.id.back);
        if (back != null) {
            back.setOnClickListener(v -> finish());
        }

        if (cbSelectAll != null) {
            cbSelectAll.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (!isUpdatingSelectAll && adapter != null && cartItems != null) {
                    Log.d("CART_DEBUG", "Select all clicked: " + isChecked);
                    selectAllItems(isChecked); // cập nhật DB luôn
                    updateTotalPrice();
                }
            });
        }
    }

    private void handleIntentData() {
        ArrayList<ProductLocalModel> intentCartItems =
                (ArrayList<ProductLocalModel>) getIntent().getSerializableExtra("cartItems");

        if (intentCartItems != null && !intentCartItems.isEmpty()) {
            cartItems.clear();
            cartItems.addAll(intentCartItems);

            // Đặt mặc định tất cả là selected
            for (ProductLocalModel item : cartItems) {
                item.setSelected(true);
            }

            saveCartItemsToDB(cartItems);
            setupRecyclerView();
            updateCartUI();
        } else {
            loadCartItemsFromDB();
        }
    }

    private void saveCartItemsToDB(ArrayList<ProductLocalModel> items) {
        new Thread(() -> {
            try {
                for (ProductLocalModel item : items) {
                    db.productDAO().insertProduct(item);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void loadCartItemsFromDB() {
        new Thread(() -> {
            try {
                List<ProductLocalModel> itemsFromDB = db.productDAO().getAllProductLocalModel();
                runOnUiThread(() -> {
                    cartItems.clear();
                    if (itemsFromDB != null) cartItems.addAll(itemsFromDB);
                    setupRecyclerView();
                    updateCartUI();
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void setupRecyclerView() {
        adapter = new CartAdapter(this, cartItems, this::onCartItemChanged, db);
        rvCartItems.setLayoutManager(new LinearLayoutManager(this));
        rvCartItems.setAdapter(adapter);
    }

    private void onCartItemChanged() {
        updateTotalPrice();
        updateSelectAllCheckbox();
    }

    private void updateCartUI() {
        if (cartItems == null || cartItems.isEmpty()) {
            cartEmpty.setVisibility(View.VISIBLE);
            productCart.setVisibility(View.GONE);
        } else {
            cartEmpty.setVisibility(View.GONE);
            productCart.setVisibility(View.VISIBLE);
            updateTotalPrice();
            updateSelectAllCheckbox();
        }
    }

    private void updateTotalPrice() {
        int subtotal = 0;
        int selectedCount = 0;

        for (ProductLocalModel item : cartItems) {
            if (item.isSelected()) {
                subtotal += item.getPrice() * item.getCount();
                selectedCount++;
            }
        }

        tvSubtotal.setText(formatCurrency(subtotal));
        int discount = selectedCount > 0 ? 20000 : 0;
        tvDiscount.setText("- " + formatCurrency(discount));
    }

    private void updateSelectAllCheckbox() {
        if (cbSelectAll == null || cartItems.isEmpty()) return;

        isUpdatingSelectAll = true;

        int selectedCount = 0;
        int totalCount = cartItems.size();
        for (ProductLocalModel item : cartItems) {
            if (item.isSelected()) selectedCount++;
        }

        cbSelectAll.setChecked(selectedCount == totalCount);

        isUpdatingSelectAll = false;
    }

    private void selectAllItems(boolean selected) {
        for (ProductLocalModel item : cartItems) {
            item.setSelected(selected);
        }
        adapter.notifyDataSetChanged();
        updateTotalPrice(); // 👈 gọi lại để cập nhật giá theo lựa chọn
    }


    private String formatCurrency(int value) {
        DecimalFormat formatter = new DecimalFormat("#,### đ");
        return formatter.format(value);
    }
}
