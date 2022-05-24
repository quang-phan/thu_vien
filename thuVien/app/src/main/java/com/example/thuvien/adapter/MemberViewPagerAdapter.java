package com.example.thuvien.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.thuvien.fragment.fragmentMember.FragmentDsMuon;
import com.example.thuvien.fragment.fragmentMember.FragmentThongTinThanhVien;

public class MemberViewPagerAdapter extends FragmentStatePagerAdapter {
    private int numPage = 2;

    public MemberViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:{
                return new FragmentDsMuon();
            }
            case 1:{
                return new FragmentThongTinThanhVien();
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
                return "Sách đã mượn";
            }
            case 1:{
                return "Cá nhân";
            }
        }
        return null;
    }
}
