package com.example.nhom3_flowershop.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.nhom3_flowershop.View.fragment.ChatFragment;
import com.example.nhom3_flowershop.View.fragment.HomeFragment;
import com.example.nhom3_flowershop.View.fragment.TaiKhoanFragment;
import com.example.nhom3_flowershop.View.fragment.ThongBaoFragment;

public class ViewPagerAdapTer extends FragmentStateAdapter{


    public ViewPagerAdapTer(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new HomeFragment();
            case 1: return new ChatFragment();
            case 2: return  new ThongBaoFragment();
            case 3: return new TaiKhoanFragment();
            default: return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}