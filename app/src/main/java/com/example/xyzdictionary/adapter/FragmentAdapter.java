package com.example.xyzdictionary.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.xyzdictionary.fragment.ExploreFragment;
import com.example.xyzdictionary.fragment.FavoriteFragment;

public class FragmentAdapter extends FragmentStateAdapter {
    ExploreFragment exploreFragment = new ExploreFragment();
    FavoriteFragment favoriteFragment = new FavoriteFragment();

    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        exploreFragment.subscribe(favoriteFragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return exploreFragment;
            case 1:
                return favoriteFragment;
        }
        return exploreFragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
