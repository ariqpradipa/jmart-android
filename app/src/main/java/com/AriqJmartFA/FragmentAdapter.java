package com.AriqJmartFA;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.AriqJmartFA.fragment.FilterFragment;
import com.AriqJmartFA.fragment.ProductFragment;

public class FragmentAdapter extends FragmentStateAdapter {
    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if (position == 1) {

            return new FilterFragment();

        }

        return new ProductFragment();

    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
