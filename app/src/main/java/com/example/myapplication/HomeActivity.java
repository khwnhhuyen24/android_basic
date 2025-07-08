package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.myapplication.fragmenHome.BrandFragment;
import com.example.myapplication.fragmenHome.DiscoverFragment;
import com.example.myapplication.fragmenHome.NotifyFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.os.Handler;

public class HomeActivity extends AppCompatActivity {
    private ActionBar toolbar;
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        bottomNav = findViewById(R.id.bottom_navigation);

        // Set Fragment mặc định
        loadFragment(new HomeFragment());

        bottomNav.setOnItemSelectedListener(item ->  {

                Fragment selectedFragment = null;

                int itemId = item.getItemId();

                if (itemId == R.id.nav_home) {
                    selectedFragment = new HomeFragment();

                } else if (itemId == R.id.nav_discover) {
                    selectedFragment = new DiscoverFragment();

                } else if (itemId == R.id.nav_account) {
                    selectedFragment = new AccountFragment();

                } else if (itemId == R.id.nav_brand) {
                    selectedFragment = new BrandFragment();

                } else if (itemId == R.id.nav_notify) {
                    selectedFragment = new NotifyFragment();

                }

                return loadFragment(selectedFragment);

        });


        // 👉 Nếu được yêu cầu mở "AllFragment" trong Home
        if (getIntent().getBooleanExtra("open_all_tab", false)) {
            // Delay 1 chút để đảm bảo HomeFragment đã gắn vào trước
            new Handler().postDelayed(() -> {
                Fragment current = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                if (current instanceof HomeFragment) {
                    ((HomeFragment) current).loadAllTab(); // Gọi vào Fragment con
                }
            }, 200);
        }
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
            return true;
        }
        return false;
    }
}
