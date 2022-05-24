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

import com.example.thuvien.adapter.ViewPagerAdapter;
import com.example.thuvien.model.NguoiDung;
import com.example.thuvien.secondActivity.doiMatKhau.DoiMatKhauActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private BottomNavigationView navigationView;
    private NguoiDung nguoiDung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        viewPagerAction();
        navigationViewAction();


    }

    public NguoiDung getNguoiDung() {
        return nguoiDung;
    }

    private void navigationViewAction() {
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.mTrangChu:{
                        viewPager.setCurrentItem(0);
                        break;
                    }
                    case R.id.mMuonSach:{
                        viewPager.setCurrentItem(1);
                        break;
                    }
                    case R.id.mQuanLi:{
                        viewPager.setCurrentItem(2);
                        break;
                    }
                }
                return true;
            }
        });
    }

    private void viewPagerAction() {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:{
                        navigationView.getMenu().findItem(R.id.mTrangChu).setChecked(true);
                        break;
                    }
                    case 1:{
                        navigationView.getMenu().findItem(R.id.mMuonSach).setChecked(true);
                        break;
                    }
                    case 2:{
                        navigationView.getMenu().findItem(R.id.mQuanLi).setChecked(true);
                        break;
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initView() {
        viewPager = findViewById(R.id.viewPager);
        navigationView = findViewById(R.id.navigation);

        Intent i = getIntent();
        nguoiDung = (NguoiDung) i.getSerializableExtra("nguoiDung");

        adapter = new ViewPagerAdapter(getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        viewPager.setAdapter(adapter);
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
                Intent i = new Intent(AdminActivity.this, DoiMatKhauActivity.class);

                i.putExtra("nguoiDung", nguoiDung);

                startActivityForResult(i, 123);
                break;
            }
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 123){
            if(resultCode == Activity.RESULT_OK){
                nguoiDung = (NguoiDung) data.getSerializableExtra("nguoiDung");
            }

        }
    }
}