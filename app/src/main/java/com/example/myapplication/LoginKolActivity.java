package com.example.myapplication;

import android.content.Intent;
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
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginKolActivity extends AppCompatActivity {
    EditText edtEmail, edtPassword;
    TextView tvEmailError, tvPasswordError;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_kol);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        tvEmailError = findViewById(R.id.tvEmailError);
        tvPasswordError = findViewById(R.id.tvPasswordError);
        btnLogin = findViewById(R.id.btnLogin);
        TextView signUp = findViewById(R.id.sign_up);
        ImageButton icPassword =findViewById(R.id.ic_password);

        icPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        return true;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        edtPassword.setSelection(edtPassword.getText().length());
                }
                return false;
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new  Intent(LoginKolActivity.this,TargetActivity.class);
                startActivity(intent);
            }
        });
        // Làm đậm phần "Đăng ký ngay"
        String fullText = "Chưa có Tài khoản? Đăng ký ngay";
        SpannableString spannable = new SpannableString(fullText);
        spannable.setSpan(new StyleSpan(Typeface.BOLD),
                fullText.indexOf("Đăng ký ngay"),
                fullText.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        signUp.setText(spannable);

        // Tắt nút login ban đầu
        btnLogin.setEnabled(false);
        btnLogin.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#666666")));

        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkEmailOnly();
                checkEnableLogin();
            }
            @Override public void afterTextChanged(Editable s) {}
        });


        edtPassword.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkPasswordOnly();
                checkEnableLogin();
            }
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    private void checkEmailOnly() {
        String email = edtEmail.getText().toString().trim();
        boolean isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches();
        tvEmailError.setVisibility(isEmailValid ? View.GONE : View.VISIBLE);
    }

    private void checkPasswordOnly() {
        String password = edtPassword.getText().toString();
        boolean isPassValid = password.length() >= 6 && password.length() <= 8;
        tvPasswordError.setVisibility(isPassValid ? View.GONE : View.VISIBLE);
    }

    private void checkEnableLogin() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString();

        boolean isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches();
        boolean isPassValid = password.length() >= 6 && password.length() <= 8;

        if (isEmailValid && isPassValid) {
            btnLogin.setEnabled(true);
            btnLogin.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#60D7B2")));
        } else {
            btnLogin.setEnabled(false);
            btnLogin.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#666666")));
        }

    }
}
