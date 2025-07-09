package com.example.myapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

public class FullFragmentDetailProActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_fragment_detail_pro);

        String fragmentName = getIntent().getStringExtra("fragment");

        Fragment fragment = null;
        switch (fragmentName) {
            case "brand":
                fragment = new DetailBrandFragment();
                break;
            case "select":
                fragment = new SelectProductFragment();
                break;
            case "related":
                fragment = new RelatedProductFragment();
                break;
            case "voucher":
                fragment = new VoucherFragment();
                break;
        }

        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main, fragment)
                    .commit();
        }
    }
}