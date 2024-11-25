package com.example.myapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.myapp.HomeModules.CertificateFragment;
import com.example.myapp.HomeModules.ChatFragment;
import com.example.myapp.HomeModules.ProfileFragment;
import com.example.myapp.HomeModules.ScoreFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.nav_certificate) {
                selectedFragment = new CertificateFragment();
            } else if (item.getItemId() == R.id.nav_profile) {
                selectedFragment = new ProfileFragment();
            } else if (item.getItemId() == R.id.nav_chat) {
                selectedFragment = new ChatFragment();
            } else if (item.getItemId() == R.id.nav_score) {
                selectedFragment = new ScoreFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            }

            return true;
        });

        // Set Profile as the default tab
        bottomNavigationView.setSelectedItemId(R.id.nav_profile);
    }
}
