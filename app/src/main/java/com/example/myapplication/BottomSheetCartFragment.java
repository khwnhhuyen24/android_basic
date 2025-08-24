package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.DynamicColorsModel;
import com.example.myapplication.model.DynamicSizesModel;
import com.example.myapplication.model.ProductLocalModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BottomSheetCartFragment extends BottomSheetDialogFragment {

    private LinearLayout layoutSize;
    private LinearLayout layoutColor;
    private LinearLayout layoutSelected;

    private TextView price; // tổng tiền
    private AppCompatButton btnAddCart;
    private AppCompatButton btnBuyNow;

    private ProductLocalModel product;
    private Map<String, View> selectedItems = new HashMap<>(); // lưu các size/màu đã chọn

    public static BottomSheetCartFragment newInstance(ProductLocalModel product) {
        BottomSheetCartFragment sheet = new BottomSheetCartFragment();
        Bundle args = new Bundle();
        args.putSerializable("product", product);
        sheet.setArguments(args);
        return sheet;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_sheet_cart, container, false);

        if (getArguments() != null) {
            product = (ProductLocalModel) getArguments().getSerializable("product");
        }

        initView(view);
        setupUI();
        initEvent();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        // Fix kéo lên/xuống
        View bottomSheet = getDialog().findViewById(com.google.android.material.R.id.design_bottom_sheet);
        if (bottomSheet != null) {
            BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(bottomSheet);
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED); // collapsed lúc mở
            behavior.setHideable(true);
            behavior.setDraggable(true);
            behavior.setPeekHeight(500); // chiều cao collapsed
        }
    }

    private void initView(View view) {
        layoutSize = view.findViewById(R.id.layoutSize);
        layoutColor = view.findViewById(R.id.layoutColor);
        layoutSelected = view.findViewById(R.id.layoutSelected);
        price = view.findViewById(R.id.price);
        btnAddCart = view.findViewById(R.id.btnAddCart);
        btnBuyNow = view.findViewById(R.id.btnBuyNow);
    }

    private void initEvent(){
        btnAddCart.setOnClickListener(v -> {
            ArrayList<ProductLocalModel> cartItems = new ArrayList<>();

            for (Map.Entry<String, View> entry : selectedItems.entrySet()) {
                View itemView = entry.getValue();

                TextView txtQuantity = itemView.findViewById(R.id.txtQuantity);
                int quantity = Integer.parseInt(txtQuantity.getText().toString());

                String size = null, color = null;
                String key = entry.getKey();
                if (key.startsWith("size")) size = key.replace("size", "");
                if (key.startsWith("color_")) color = key.replace("color_", "");

                ProductLocalModel item = new ProductLocalModel();
                item.setProductname(product.getProductname());
                item.setProductImg(product.getProductImg());
                item.setPrice(product.getPrice());
                item.setCount(quantity);
                item.setSize(size);
                item.setColor(color);

                cartItems.add(item);
            }

            // Chuyển sang ProductCartActivity
            Intent intent = new Intent(getContext(), ProductCartActivity.class);
            intent.putExtra("cartItems", cartItems); // nhớ ProductLocalModel implements Serializable
            startActivity(intent);
            dismiss(); // đóng BottomSheet
        });


    }

    private void setupUI() {
        // Hiển thị giá tổng ban đầu = giá 1 sản phẩm
        price.setText(formatCurrency(product.getPrice()));

        boolean hasSize = product.getSize() != null && !product.getSize().isEmpty();
        boolean hasColor = product.getColor() != null && !product.getColor().isEmpty();

        // ===== HIỂN THỊ SIZE =====
        if (product.getSize() != null && !product.getSize().isEmpty()) {
            layoutSize.setVisibility(View.VISIBLE);
            layoutSize.removeAllViews();

            TextView btnSize = createOptionButton(product.getSize());
            btnSize.setOnClickListener(v -> addSelectedItem(product.getSize(), null));
            layoutSize.addView(btnSize);
        } else {
            layoutSize.setVisibility(View.GONE);
        }

// ===== HIỂN THỊ COLOR =====
        if (product.getColor() != null && !product.getColor().isEmpty()) {
            layoutColor.setVisibility(View.VISIBLE);
            layoutColor.removeAllViews();

            TextView btnColor = createOptionButton(product.getColor());
            btnColor.setOnClickListener(v -> addSelectedItem(null, product.getColor()));
            layoutColor.addView(btnColor);
        } else {
            layoutColor.setVisibility(View.GONE);
        }

        // ===== NẾU KHÔNG CÓ SIZE VÀ COLOR =====
        if (!hasSize && !hasColor) {
            addSelectedItem("Product", "Product");
        }
    }

    private TextView createOptionButton(String text) {
        TextView tv = new TextView(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(16, 8, 16, 8);
        tv.setLayoutParams(params);
        tv.setPadding(20, 10, 20, 10);
        tv.setBackgroundResource(R.drawable.ic_checked);
        tv.setText(text);
        tv.setTextSize(14);
        tv.setTextColor(Color.BLACK);
        return tv;
    }

    private void addSelectedItem(String size, String color) {
        String key;
        if (size != null){
            key = "size" + size;
        } else if (color != null) {
            key =  "color_" + color;
        } else {
            key = "Product";
        }

        if (selectedItems.containsKey(key)) return;

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View itemView = inflater.inflate(R.layout.item_selected_product, layoutSelected, false);

        TextView txtName = itemView.findViewById(R.id.txtProductSize);
        TextView txtPriceItem = itemView.findViewById(R.id.txtProductPrice); // giá cố định
        TextView txtQuantity = itemView.findViewById(R.id.txtQuantity);
        ImageButton decrease = itemView.findViewById(R.id.decrease);
        ImageButton increase = itemView.findViewById(R.id.increase);
        ImageButton btnRemove = itemView.findViewById(R.id.btnRemove);

        int unitPrice = product.getPrice(); // giá 1 sản phẩm
        txtQuantity.setText("1");

        if (size != null) {
            txtName.setText(product.getProductname() + " size " + size);
        } else if (color != null) {
            txtName.setText(product.getProductname() + " màu " + color);
        } else {
            txtName.setText(product.getId());
        }

        // Giá cố định của sản phẩm
        txtPriceItem.setText(formatCurrency(unitPrice));

        // Tăng số lượng
        increase.setOnClickListener(v -> {
            int quantity = Integer.parseInt(txtQuantity.getText().toString()) + 1;
            txtQuantity.setText(String.valueOf(quantity));
            updateTotalPrice();
        });

        // Giảm số lượng
        decrease.setOnClickListener(v -> {
            int quantity = Integer.parseInt(txtQuantity.getText().toString());
            if (quantity > 1) {
                quantity--;
                txtQuantity.setText(String.valueOf(quantity));
                updateTotalPrice();
            }
        });

        // Xoá item
        btnRemove.setOnClickListener(v -> {
            layoutSelected.removeView(itemView);
            selectedItems.remove(key);
            updateTotalPrice();
        });

        layoutSelected.addView(itemView);
        selectedItems.put(key, itemView);

        updateTotalPrice();
    }

    private void updateTotalPrice() {
        int total = 0;
        for (View v : selectedItems.values()) {
            TextView txtQuantity = v.findViewById(R.id.txtQuantity);
            int quantity = Integer.parseInt(txtQuantity.getText().toString());
            total += product.getPrice() * quantity; // giá cố định từ API
        }
        price.setText(formatCurrency(total)); // hiển thị tổng tiền
    }

    private String formatCurrency(int value) {
        DecimalFormat formatter = new DecimalFormat("#,### đ");
        return formatter.format(value);
    }
}
