package com.example.thuvien.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.thuvien.fragment.FragmentDanhSach;
import com.example.thuvien.fragment.FragmentMuonTra;
import com.example.thuvien.fragment.FragmentQuanLi;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private int numpage = 3;

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:{
                return new FragmentDanhSach();
            }
            case 1:{
                return new FragmentMuonTra();
            }
            case 2:{
                return new FragmentQuanLi();
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        return numpage;
    }
}
