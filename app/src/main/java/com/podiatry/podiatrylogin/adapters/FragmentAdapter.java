package com.podiatry.podiatrylogin.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.podiatry.podiatrylogin.controlPanel.AllProductsFragment;
import com.podiatry.podiatrylogin.controlPanel.MostRankedFragment;

import java.util.ArrayList;

public class FragmentAdapter extends FragmentStateAdapter {

    private ArrayList<Fragment> my_fragments;
    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        this.my_fragments= new ArrayList<>();
        this.my_fragments.add(new AllProductsFragment());
        this.my_fragments.add(new MostRankedFragment());
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return this.my_fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return this.my_fragments.size();
    }
}
