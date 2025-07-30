package com.example.myapplication;

import static android.content.Context.MODE_PRIVATE;
import static com.example.myapplication.LoginKolActivity.KEY_IS_LOGGED_IN;
import static com.example.myapplication.LoginKolActivity.PREFS_NAME;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.item.BrandAdapter;
import com.example.myapplication.item.FollowerAdapter;
import com.example.myapplication.item.ProductAdapter;
import com.example.myapplication.model.BestProductResponse;
import com.example.myapplication.model.KolAccountModel;
import com.example.myapplication.model.LoginParams;
import com.example.myapplication.model.LoginResponse;
import com.example.myapplication.model.LogoutParams;
import com.example.myapplication.model.RootBrand;
import com.example.myapplication.model.RootKol;
import com.example.myapplication.remote.ApiClient;
import com.google.android.material.imageview.ShapeableImageView;


import com.bumptech.glide.Glide; // nhớ thêm thư viện Glide nếu chưa có
import com.example.myapplication.remote.ApiService;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFragment extends Fragment {


    private ImageButton btnSearch, btnCart;
    private TextView txtDisplayName, txtEmail;
    private LinearLayout btnSetting, btnLogout;
    private ImageView imgWaybill, imgVoucher, imgTarget, imgAddress;
    private TextView txtKol, txtProduct, txtBrand;
    private ShapeableImageView imgAvatar;
    private RecyclerView recyclerKolAccount;

    private FollowerAdapter followerAdapter;
    private ProductAdapter productAdapter;
    private BrandAdapter brandAdapter;
    private ProgressBar progressBarComments;
    private View indicatorLine;
    private FrameLayout frameLayout;

    private ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        initView(view);

        recyclerKolAccount.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
        );
        followerAdapter = new FollowerAdapter(requireContext(), new ArrayList<>());
        productAdapter = new ProductAdapter(requireContext(), new ArrayList<>());
        brandAdapter = new BrandAdapter(requireContext(), new ArrayList<>());

        recyclerKolAccount.setAdapter(followerAdapter);

        highlightTabs(txtKol);
        fetchKolData(true);
        fetchProductData();
        fetchBrandData();
        animateIndicatorToTab(txtKol);
        fetchAccountInfo();

        initEvent();
        return view;
    }

    private void initView(View view) {
        btnSearch = view.findViewById(R.id.btnSearch);
        btnCart = view.findViewById(R.id.btnCart);
        imgAvatar = view.findViewById(R.id.imgAvatar);
        txtDisplayName = view.findViewById(R.id.txtDisplayName);
        txtEmail = view.findViewById(R.id.txtEmail);
        btnSetting = view.findViewById(R.id.setting);
        btnLogout = view.findViewById(R.id.logout);
        imgWaybill = view.findViewById(R.id.imgWaybill);
        imgVoucher = view.findViewById(R.id.imgVoucher);
        imgTarget = view.findViewById(R.id.imgTarget);
        imgAddress = view.findViewById(R.id.imgAddress);
        txtKol = view.findViewById(R.id.txtKol);
        txtProduct = view.findViewById(R.id.txtProduct);
        txtBrand = view.findViewById(R.id.txtBrand);
        recyclerKolAccount = view.findViewById(R.id.recyclerKolAccount);
        progressBarComments = view.findViewById(R.id.progressBarComments);
        indicatorLine = view.findViewById(R.id.indicatorLine);
        frameLayout = view.findViewById(R.id.fragment_container);
    }

    private void initEvent() {
       setupTabClick (txtKol, followerAdapter, () ->fetchKolData(true));
       setupTabClick (txtProduct, productAdapter, this:: fetchProductData);
       setupTabClick(txtBrand, brandAdapter, this::fetchBrandData);

       btnLogout.setOnClickListener(v -> showLogoutDialog());

       btnSetting.setOnClickListener(v -> {
           loadFragment(new UserProfileFragment());
       });

    }

    private void highlightTabs(TextView selected) {
        TextView[] tabs = {txtKol, txtProduct, txtBrand};
        for (TextView tab : tabs) {
            if (tab == selected) {
                tab.setTypeface(null, android.graphics.Typeface.BOLD);
                tab.setTextColor(getResources().getColor(R.color.black));
            } else {
                tab.setTypeface(null, android.graphics.Typeface.NORMAL);
                tab.setTextColor(getResources().getColor(R.color.gray));
            }
        }
    }

    private void fetchAccountInfo() {


        apiService.getKolInfo().enqueue(new Callback<KolAccountModel>() {
            @Override
            public void onResponse(Call<KolAccountModel> call, Response<KolAccountModel> response){

                if (response.isSuccessful() && response.body() != null) {
                    KolAccountModel account = response.body();

                    txtDisplayName.setText(account.getDisplayName());
                    txtEmail.setText(account.getEmail());
                    Glide.with(requireContext())
                            .load(account.getAvatar())
                            .placeholder(R.drawable.banner8)
                            .into(imgAvatar);

                }
            }

            @Override
            public void onFailure(Call<KolAccountModel> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private void fetchKolData(boolean showProgress) {
        if (showProgress) progressBarComments.setVisibility(View.VISIBLE);


        apiService.getKolsIFollow().enqueue(new Callback<RootKol>() {
            @Override
            public void onResponse(Call<RootKol> call, Response<RootKol> response) {
                progressBarComments.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null && response.body().content != null) {
                    followerAdapter.setData(response.body().content);
                    // Cập‑nhật tiêu đề tab
                    int kolCount = response.body().content.size();
                    setTabCount(txtKol, "KOL", kolCount);

                } else {
                    Log.e("API_ERROR", "Danh sách KOL rỗng");
                }

            }

            @Override
            public void onFailure(Call<RootKol> call, Throwable t) {

                Log.e("API_ERROR", "Response Code: ", t);

                t.printStackTrace();
            }
        });
    }

    private void fetchProductData() {
        progressBarComments.setVisibility(View.VISIBLE);

        apiService.getBestProduct().enqueue(new Callback<BestProductResponse>() {
            @Override
            public void onResponse(Call<BestProductResponse> call, Response<BestProductResponse> response) {
                progressBarComments.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null && response.body().content != null) {
                    productAdapter.setData(response.body().content);
                    int productCount = response.body().content.size();
                    setTabCount(txtProduct, "Product", productCount);

                } else {
                    Log.e("API_PRODUCT", "Dữ liệu rỗng hoặc lỗi");
                }
            }

            @Override
            public void onFailure(Call<BestProductResponse> call, Throwable t) {
                Log.e("API_PRODUCT", "Lỗi kết nối API", t);
            }
        });
    }

    private void fetchBrandData() {
        progressBarComments.setVisibility(View.VISIBLE);

        apiService.getBrandFollow().enqueue(new Callback<RootBrand>() {

                @Override
                public void onResponse(Call <RootBrand> call, Response <RootBrand> response){
                    progressBarComments.setVisibility(View.GONE);

                    if (response.isSuccessful()
                            && response.body() != null
                            && response.body().content != null) {

                        brandAdapter.setData(response.body().content);
                        int brandCount = response.body().content.size();
                        setTabCount(txtBrand, "Brand", brandCount);

                    } else {
                        Log.e("API_Brand", "Dữ liệu rỗng hoặc lỗi");
                        Log.d("API_BRAND_JSON", new Gson().toJson(response.body()));

                    }
                }

                @Override
                public void onFailure(Call<RootBrand> call, Throwable t) {
                    Log.e("API_Brand", "Lỗi kết nối API", t);
                    t.printStackTrace();
                }
            });

    }

    private void setupTabClick( TextView tabview, RecyclerView.Adapter<?> adapter, Runnable fetchData){
        tabview.setOnClickListener(v -> {
            recyclerKolAccount.setAdapter(adapter);
            fetchData.run();
            highlightTabs(tabview);
            animateIndicatorToTab(tabview);
        });
    }

    private void showLogoutDialog() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.dialog_logout, null);
        AlertDialog dialog = new AlertDialog.Builder(requireContext())
                .setView(view)
                .setCancelable(false)
                .create();

        Button btnCancel = view.findViewById(R.id.cancel);
        Button btnAgree = view.findViewById(R.id.agree);
        ProgressBar progressBar = view.findViewById(R.id.load);
        LinearLayout dialogContent = view.findViewById(R.id.dialogContent); // Nội dung dialog cần ẩn

        btnAgree.setOnClickListener(v -> {
            // Ẩn toàn bộ nội dung, chỉ hiện progressBar
            dialogContent.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);

            String deviceId = Settings.Secure.getString(
                    requireContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID
            );
            LogoutParams params = new LogoutParams(deviceId);

            apiService.logout(params).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {

                        // Lưu trạng thái đăng nhập vào SharedPreferences
                        SharedPreferences prefs = getActivity().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putBoolean(KEY_IS_LOGGED_IN, false);
                        editor.apply();
                        new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(() -> {
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            dialog.dismiss();
                        }, 300);
                        Toast.makeText(requireContext(), "Đăng xuất thành công", Toast.LENGTH_SHORT).show();


                    } else {
                        Toast.makeText(requireContext(), "Đăng xuất thất bại: " + response.code(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(requireContext(), "Lỗi kết nối khi đăng xuất", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                    dialog.dismiss();
                }
            });
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void animateIndicatorToTab(TextView selectedTab) {
        selectedTab.post(() -> {
            int tabWidth = selectedTab.getWidth();
            int tabX = selectedTab.getLeft();

            ViewGroup.LayoutParams params = indicatorLine.getLayoutParams();
            params.width = tabWidth;
            indicatorLine.setLayoutParams(params);

            indicatorLine.animate()
                    .x(tabX)
                    .setDuration(200)
                    .start();
        });
    }

    private void setTabCount(TextView tab, String baseTitle, int count) {
        tab.setText(String.format("%s (%d)", baseTitle, count));
    }

    private void loadFragment(Fragment fragment){
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,fragment)
                .addToBackStack(null)
                .commit();
    }
}