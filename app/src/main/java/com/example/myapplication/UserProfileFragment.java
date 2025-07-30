package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class UserProfileFragment extends Fragment {
    ImageView back;
    LinearLayout userInfo;
    TextView vietnamese, korean;
    SwitchCompat switchToggle;
    FrameLayout frameLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_user_profile, container, false);
        initView(view);
        initEvent();
        return view;
    }
    private void initView(View view){
        back = view.findViewById(R.id.back);
        userInfo = view.findViewById(R.id.UserInfo);
        vietnamese = view. findViewById(R.id.vietnamese);
        korean = view.findViewById(R.id.Korean);
        switchToggle = view.findViewById(R.id.Switcht_toggle);
        frameLayout = view.findViewById(R.id.fragment_container);
    }

    private void initEvent(){
        back.setOnClickListener(v ->{
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        userInfo.setOnClickListener(v -> {
            loadFragment(new EditUserProfile());
        });
    }

    private void loadFragment(Fragment fragment){
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,fragment)
                .addToBackStack(null)
                .commit();
    }
}