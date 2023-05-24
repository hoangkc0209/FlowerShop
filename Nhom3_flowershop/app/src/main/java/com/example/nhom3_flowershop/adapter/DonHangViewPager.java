package com.example.nhom3_flowershop.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.nhom3_flowershop.View.fragment.ChoFragment;
import com.example.nhom3_flowershop.View.fragment.DaNhanFragment;
import com.example.nhom3_flowershop.View.fragment.DangFragment;
import com.example.nhom3_flowershop.View.fragment.HuyFragment;

public class DonHangViewPager extends FragmentStateAdapter {


    public DonHangViewPager(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new ChoFragment();
            case 1:
                return new DangFragment();
            case 2:
                return  new DaNhanFragment();
            case 3:
                return new HuyFragment();
            default: return new DangFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
