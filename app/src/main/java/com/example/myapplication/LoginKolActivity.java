package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.myapplication.model.LoginParams;
import com.example.myapplication.model.LoginResponse;
import com.example.myapplication.remote.ApiClient;
import com.example.myapplication.remote.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginKolActivity extends AppCompatActivity {
    private ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);

    private EditText edtEmail;
    private EditText edtPassword;
    private TextView tvEmailError;
    private TextView tvPasswordError;
    private AppCompatButton btnLogin;
    private TextView tvForgotPassword;
    private TextView signUp;
    private ImageButton icPassword;
    private ProgressBar progressBar;

    // Thêm cờ để kiểm soát người dùng đã nhập ô nào
    private boolean emailStarted = false;
    private boolean passwordStarted = false;

    private static final String PREFS_NAME = "tungvu11";
    private static final String KEY_IS_LOGGED_IN = "Hanoi@123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_login_kol);

        initView();
        initEvent();
    }

    private void initEvent() {
        // Hiện/ẩn mật khẩu
        icPassword.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                return true;
            } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                edtPassword.setSelection(edtPassword.getText().length());
            }
            return false;
        });

        // Đăng nhập
        btnLogin.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            LoginParams params = new LoginParams();
            params.setLoginId(edtEmail.getText().toString());
            params.setPasswords(edtPassword.getText().toString());

            Call<LoginResponse> call = apiService.login(params);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    progressBar.setVisibility(View.GONE);
                    if (response.isSuccessful()) {
                        Toast.makeText(LoginKolActivity.this, "Login successful", Toast.LENGTH_LONG).show();

                        // Lưu trạng thái đăng nhập vào SharedPreferences
                        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putBoolean(KEY_IS_LOGGED_IN, true);
                        editor.apply();

                        Log.e("LoginKolActivity", response.body().getCustomerAccount().avatar);
                        Intent intent = new Intent(LoginKolActivity.this, TargetActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginKolActivity.this, "Login failed", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable throwable) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginKolActivity.this, "Login failed", Toast.LENGTH_LONG).show();
                }
            });
        });

        // Quên mật khẩu
        tvForgotPassword.setOnClickListener(v -> {
            AlertDialog dialog = new AlertDialog.Builder(LoginKolActivity.this)
                    .setView(getLayoutInflater().inflate(R.layout.dialog, null))
                    .create();
            dialog.show();
            dialog.findViewById(R.id.btnDong).setOnClickListener(v2 -> dialog.dismiss());
        });

        // Làm đậm "Đăng ký ngay"
        String fullText = "Chưa có Tài khoản? Đăng ký ngay";
        SpannableString spannable = new SpannableString(fullText);
        spannable.setSpan(new StyleSpan(Typeface.BOLD),
                fullText.indexOf("Đăng ký ngay"), fullText.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        signUp.setText(spannable);

        // Disable login ban đầu
        btnLogin.setEnabled(false);
        btnLogin.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#666666")));

        // Gắn listener riêng cho email
        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emailStarted = true;
                checkEnableLogin();
            }
        });

        // Gắn listener riêng cho password
        edtPassword.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordStarted = true;
                checkEnableLogin();
            }
        });
    }

    private void initView() {
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        tvEmailError = findViewById(R.id.tvEmailError);
        tvPasswordError = findViewById(R.id.tvPasswordError);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        signUp = findViewById(R.id.sign_up);
        icPassword = findViewById(R.id.ic_password);
        progressBar = findViewById(R.id.progress_login);
    }

    // Kiểm tra và cập nhật UI
    private void checkEnableLogin() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString();

        boolean isEmailValid = true; // Bạn có thể thay bằng kiểm tra email hợp lệ như Patterns.EMAIL_ADDRESS.matcher(email).matches()
        boolean isPassValid = password.length() >= 6 && password.length() <= 20;

        // Chỉ hiển thị lỗi khi người dùng đã bắt đầu nhập ô đó
        tvEmailError.setVisibility(emailStarted && !isEmailValid ? View.VISIBLE : View.GONE);
        tvPasswordError.setVisibility(passwordStarted && !isPassValid ? View.VISIBLE : View.GONE);

        boolean isValid = isEmailValid && isPassValid;
        btnLogin.setEnabled(isValid);
        btnLogin.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(
                isValid ? "#60D7B2" : "#666666"
        )));
    }
}
