package com.example.thuvien.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.thuvien.fragment.fragment3.FragmentTest;
import com.example.thuvien.fragment.fragment3.FragmentThemSach;
import com.example.thuvien.fragment.fragment3.FragmentTimSach;

public class ViewPagerAdapter3 extends FragmentStatePagerAdapter {
    private int numPage = 2;

    public ViewPagerAdapter3(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:{
                System.out.println("!!! Fragment tim sach duoc goi");
                return new FragmentTimSach();
            }
            case 1:{
                System.out.println("!!! Fragment them sach duoc goi");
                return new FragmentThemSach();
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        return numPage;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:{

                return "Tìm sách";
            }
            case 1:{
                return "Thêm sách";
            }
        }
        return null;
    }
}
