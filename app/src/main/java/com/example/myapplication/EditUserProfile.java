package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.model.KolAccountModel;
import com.example.myapplication.remote.ApiClient;
import com.example.myapplication.remote.ApiService;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditUserProfile extends Fragment {

    private EditText name;
    private EditText phone;
    private Spinner sex;
    private Spinner year;
    private AppCompatButton save;

    private final String[] sexOptions = {"Nam", "Nữ", "Khác"};
    private ArrayList<String> yearOptions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_user_profile, container, false);
        initView(view);
        initSpinner();
        initEvent();
        return view;
    }

    private void initView(View view) {
        name = view.findViewById(R.id.name);
        phone = view.findViewById(R.id.phone);
        sex = view.findViewById(R.id.sex);
        year = view.findViewById(R.id.year);
        save = view.findViewById(R.id.save);
    }

    private void initSpinner() {
        // Spinner giới tính
        ArrayAdapter<String> adapterSex = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, sexOptions);
        adapterSex.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sex.setAdapter(adapterSex);

        // Spinner năm sinh (từ 1980 đến 2025)
        yearOptions = new ArrayList<>();
        for (int i = 1980; i <= 2025; i++) {
            yearOptions.add(String.valueOf(i));
        }
        ArrayAdapter<String> adapterYear = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, yearOptions);
        adapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(adapterYear);
    }

    private void initEvent() {
        // Load dữ liệu từ SharedPreferences và hiển thị
        SharedPreferences prefs = requireContext().getSharedPreferences("USER_PREF", Context.MODE_PRIVATE);
        String nameStr = prefs.getString("name", "");
        String phoneStr = prefs.getString("phone", "");
        String genderStr = prefs.getString("gender", "Nam");
        String birthYear = prefs.getString("birthYear", "2000");

        name.setText(nameStr);
        phone.setText(phoneStr);

        // set gender Spinner
        int genderIndex = Arrays.asList(sexOptions).indexOf(genderStr);
        if (genderIndex != -1) {
            sex.setSelection(genderIndex);
        }

        // set year Spinner
        int yearIndex = yearOptions.indexOf(String.valueOf(birthYear));
        if (yearIndex != -1) {
            year.setSelection(yearIndex);
        }

        // Bắt sự kiện lưu
        save.setOnClickListener(v -> {
            String userName = name.getText().toString().trim();
            String userPhone = phone.getText().toString().trim();
            String userSex = sex.getSelectedItem().toString();
            String userYear = year.getSelectedItem().toString();

            // Gửi dữ liệu lên API
            KolAccountModel user = new KolAccountModel();

            user.displayName = userName;
            user.phone = userPhone;
            user.sex = userSex;
            user.birthday = userYear;

            ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);
            Call<KolAccountModel> call = apiService.updateUser(user); // PUT

            call.enqueue(new Callback<KolAccountModel>() {
                @Override
                public void onResponse(@NonNull Call<KolAccountModel> call, @NonNull Response<KolAccountModel> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(requireContext(), "Cập nhật thành công!", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(requireContext(), "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<KolAccountModel> call, @NonNull Throwable t) {
                    Toast.makeText(requireContext(), "Lỗi kết nối máy chủ", Toast.LENGTH_SHORT).show();

                }
            });
        });
    }
}
