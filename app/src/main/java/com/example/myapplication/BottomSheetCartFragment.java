package com.example.myapplication;

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

import com.example.myapplication.model.DynamicColorsModel;
import com.example.myapplication.model.DynamicSizesModel;
import com.example.myapplication.model.ProductModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class BottomSheetCartFragment extends BottomSheetDialogFragment {

    private LinearLayout layoutSize, layoutColor, layoutSelected;
    private TextView txtTotalPrice, price;
    private AppCompatButton btnAddCart, btnBuyNow;
    private TextView txtName, txtPrice, txtQuantity;
    private ImageButton btnRemove, decrease, increase;

    private ProductModel product;
    private Map<String, View> selectedItems = new HashMap<>(); // lưu các size/màu đã chọn

    public static BottomSheetCartFragment newInstance(ProductModel product) {
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
            product = (ProductModel) getArguments().getSerializable("product");
        }

        initView(view);
        setupUI();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        // Fix kéo lên/xuống
        View bottomSheet = getDialog().findViewById(com.google.android.material.R.id.design_bottom_sheet);
        if (bottomSheet != null) {
            BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(bottomSheet);
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            behavior.setHideable(true);
            behavior.setDraggable(true);
            behavior.setPeekHeight(300); // chiều cao collapsed
        }
    }

    private void initView(View view) {
        layoutSize = view.findViewById(R.id.layoutSize);
        layoutColor = view.findViewById(R.id.layoutColor);
        layoutSelected = view.findViewById(R.id.layoutSelected);
        txtTotalPrice = view.findViewById(R.id.txtTotalPrice);
        price = view.findViewById(R.id.price);
        btnAddCart = view.findViewById(R.id.btnAddCart);
        btnBuyNow = view.findViewById(R.id.btnBuyNow);
    }

    private void setupUI() {
        if (product == null) return;

        // Hiển thị giá
        price.setText(formatCurrency(product.getPriceSales()));

        // Hiển thị Size
        if (product.getDynamicSizes() != null && !product.getDynamicSizes().isEmpty()) {
            layoutSize.setVisibility(View.VISIBLE);
            layoutSize.removeAllViews();
            for (DynamicSizesModel size : product.getDynamicSizes()) {
                TextView btnSize = createOptionButton(String.valueOf(size));
                btnSize.setOnClickListener(v -> addSelectedItem(String.valueOf(size), null));
                layoutSize.addView(btnSize);
            }
        } else {
            layoutSize.setVisibility(View.GONE);
        }

        // Hiển thị Màu
        if (product.getDynamicColors() != null && !product.getDynamicColors().isEmpty()) {
            layoutColor.setVisibility(View.VISIBLE);
            layoutColor.removeAllViews();
            for (DynamicColorsModel color : product.getDynamicColors()) {
                TextView btnColor = createOptionButton(String.valueOf(color));
                btnColor.setOnClickListener(v -> addSelectedItem(null, String.valueOf(color)));
                layoutColor.addView(btnColor);
            }
        } else {
            layoutColor.setVisibility(View.GONE);
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
        tv.setBackgroundResource(R.drawable.ic_checked); // drawable bo tròn
        tv.setText(text);
        tv.setTextSize(14);
        tv.setTextColor(Color.BLACK);
        return tv;
    }

    private void addSelectedItem(String size, String color) {
        String key = size != null ? "size_" + size : "color_" + color;
        if (selectedItems.containsKey(key)) return;

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View itemView = inflater.inflate(R.layout.item_selected_product, layoutSelected, false);

        txtName = itemView.findViewById(R.id.txtProductSize);
        txtPrice = itemView.findViewById(R.id.txtProductPrice);
        txtQuantity = itemView.findViewById(R.id.txtQuantity);
        decrease = itemView.findViewById(R.id.decrease);
        increase = itemView.findViewById(R.id.increase);
        btnRemove = itemView.findViewById(R.id.btnRemove);

        int unitPrice = product.getPriceSales();
        txtQuantity.setText("1");

        if (size != null) {
            txtName.setText(product.getProductName() + " size " + size);
        } else if (color != null) {
            txtName.setText(product.getProductName() + " màu " + color);
        }

        txtPrice.setText(formatCurrency(unitPrice));

        // Tăng số lượng
        increase.setOnClickListener(v -> {
            int quantity = Integer.parseInt(txtQuantity.getText().toString()) + 1;
            txtQuantity.setText(String.valueOf(quantity));
            txtPrice.setText(formatCurrency(unitPrice * quantity));
            updateTotalPrice();
        });

        // Giảm số lượng
        decrease.setOnClickListener(v -> {
            int quantity = Integer.parseInt(txtQuantity.getText().toString());
            if (quantity > 1) {
                quantity--;
                txtQuantity.setText(String.valueOf(quantity));
                txtPrice.setText(formatCurrency(unitPrice * quantity));
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
            total += product.getPriceSales() * quantity;
        }
        txtTotalPrice.setText(formatCurrency(total));
    }

    private String formatCurrency(int value) {
        DecimalFormat formatter = new DecimalFormat("#,### đ");
        return formatter.format(value);
    }
}
