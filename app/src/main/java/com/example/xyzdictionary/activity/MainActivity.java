package com.example.xyzdictionary.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.xyzdictionary.R;
import com.example.xyzdictionary.adapter.FragmentAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    TabLayout tabMenu;
    ViewPager2 viewPager2;
    FragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        setting();
    }

    private void setting() {
        viewPager2.setAdapter(adapter);
        tabMenu.addTab(tabMenu.newTab().setText("Explore"));
        tabMenu.addTab(tabMenu.newTab().setText("Favorites"));

        tabMenu.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabMenu.selectTab(tabMenu.getTabAt(position));
            }
        });
    }

    private void initialize() {
        tabMenu = findViewById(R.id.tabMenu);
        viewPager2 = findViewById(R.id.viewPager);
        adapter = new FragmentAdapter(getSupportFragmentManager(), getLifecycle());
    }
}