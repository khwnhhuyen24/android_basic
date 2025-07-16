package com.example.myapplication;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.util.ArrayList;

public class TargetActivity extends AppCompatActivity {

    private AppCompatButton btnSave, btnSkip;
    private RadioButton[] radioButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);

        initView();
        initEvent();

    }

    private void initView(){
        radioButtons = new RadioButton[]{
                findViewById(R.id.rbChamSocDaMat),
                findViewById(R.id.rbMakeup),
                findViewById(R.id.rbVitamin),
                findViewById(R.id.rbChamSocCoThe),
                findViewById(R.id.rbChamSocToc),
                findViewById(R.id.rbChamSocRangMieng),
                findViewById(R.id.rbAoNu),
                findViewById(R.id.rbChanVay),
                findViewById(R.id.rbAoNam),
                findViewById(R.id.rbPhuKien),
                findViewById(R.id.rbQuanNu),
                findViewById(R.id.rbDam),
                findViewById(R.id.rbQuanNam)
        };
        btnSave = findViewById(R.id.btnSave);
        btnSkip = findViewById(R.id.Skip);

    }

    private void initEvent(){
        // Mặc định nút save bị tắt
        updateSaveButtonState();

        // Tạo listener toggle cho tất cả RadioButton
        View.OnTouchListener toggleListener = (v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                RadioButton rb = (RadioButton) v;
                rb.setChecked(!rb.isChecked());
                updateSaveButtonState();
                return true;
            }
            return false;
        };

        // Gán listener cho từng RadioButton
        for (RadioButton rb : radioButtons) {
            rb.setOnTouchListener(toggleListener);
        }

        btnSave.setOnClickListener(v -> {
            ArrayList<String> selectedItems = new ArrayList<>();
            for (RadioButton rb : radioButtons) {
                if (rb.isChecked()) selectedItems.add(rb.getText().toString());
                Toast.makeText(TargetActivity.this, "Please select 1 item", Toast.LENGTH_LONG).show();
            }


            Intent intent = new Intent(this, HomeActivity.class);
            intent.putStringArrayListExtra("selectedTargets", selectedItems);
            startActivity(intent);
            finish();
        });

        btnSkip.setOnClickListener(v -> {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        });
    }

    private void updateSaveButtonState() {
        boolean hasChecked = false;
        for (RadioButton rb : radioButtons) {
            if (rb.isChecked()) {
                hasChecked = true;
                break;
            }
        }

        btnSave.setEnabled(hasChecked);
        btnSave.setBackgroundTintList(ColorStateList.valueOf(
                Color.parseColor(hasChecked ? "#60D7B2" : "#666666")
        ));
    }
}
