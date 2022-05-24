package com.example.thuvien;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.thuvien.adapter.MemberViewPagerAdapter;
import com.example.thuvien.model.NguoiDung;
import com.example.thuvien.secondActivity.doiMatKhau.DoiMatKhauActivity;
import com.google.android.material.tabs.TabLayout;

public class MemberActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MemberViewPagerAdapter adapter;
    private NguoiDung nguoiDung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        Intent i = getIntent();
        nguoiDung = (NguoiDung) i.getSerializableExtra("nguoiDung");

        setViewPage();
    }

    private void setViewPage() {
        adapter = new MemberViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_mat_khau, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mDoiMK:{
                Intent i = new Intent(MemberActivity.this, DoiMatKhauActivity.class);

                i.putExtra("nguoiDung", nguoiDung);

                startActivityForResult(i, 124);
                break;
            }
        }
        return true;
    }

    public NguoiDung getNguoiDung() {
        return nguoiDung;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 124){
            if(resultCode == Activity.RESULT_OK){
                nguoiDung = (NguoiDung) data.getSerializableExtra("nguoiDung");
            }

        }
    }
}