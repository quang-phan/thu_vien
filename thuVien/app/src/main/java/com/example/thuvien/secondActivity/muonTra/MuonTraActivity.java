package com.example.thuvien.secondActivity.muonTra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.thuvien.R;
import com.example.thuvien.adapter.recycleViewAdapter.SachMuonAdapter;
import com.example.thuvien.adapter.recycleViewAdapter.TimSachAdapter;
import com.example.thuvien.dataConnection.DauSachDB;
import com.example.thuvien.dataConnection.NguoiDungDB;
import com.example.thuvien.dataConnection.SachChoMuonDB;
import com.example.thuvien.model.DauSach;
import com.example.thuvien.model.NguoiDung;
import com.example.thuvien.model.SachChoMuon;
import com.example.thuvien.model.daSach.DauSachThem;
import com.example.thuvien.secondActivity.suaThanhVien.SuaThanhVienActivity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MuonTraActivity extends AppCompatActivity implements
TimSachAdapter.TimAdapterListener, SachMuonAdapter.ThemAdapterListener{

    private TextView tvTen, tvSdt;
    private RecyclerView recyclerViewDaMuon, recyclerViewKhoSach;
    private SearchView searchView;
    private SachMuonAdapter sachMuonAdapteradapter;
    private TimSachAdapter timSachAdapter;
    private SachChoMuonDB db;
    private DauSachDB dauSachDB;
    private NguoiDungDB nguoiDungDB;
    private NguoiDung nguoiDung, thuThu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muon_tra);

        initView();

        try {
            setRecyclerViewAdapter();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        setSearchViewListener();
    }

    private void setSearchViewListener() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<DauSach> list = dauSachDB.timTheoTen(s);
                List<DauSach> finalList = new ArrayList<>();
                List<SachChoMuon> sachChoMuonList = null;
                try {
                    sachChoMuonList = db.getByThanhVienId(nguoiDung.getId());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                for (DauSach d:
                     list) {
                    if(!db.kiemTraDauSach(d.getId())){
                        if(checkSachDaMuon(d, sachChoMuonList)){
                            finalList.add(d);
                        }
                    }
                }

                timSachAdapter.setList(finalList);
                return true;
            }
        });
    }

    private void setRecyclerViewAdapter() throws ParseException {
        choMuon();
        khoSach();
    }

    private void choMuon() throws ParseException {
        Intent i = getIntent();
        nguoiDung = (NguoiDung) i.getSerializableExtra("nguoiDung");
        thuThu = (NguoiDung) i.getSerializableExtra("thuThu");
        sachMuonAdapteradapter = new SachMuonAdapter();
        sachMuonAdapteradapter.setListener(this);

        setListChoMuon();

        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        manager.setOrientation(RecyclerView.VERTICAL);

        recyclerViewDaMuon.setLayoutManager(manager);
        recyclerViewDaMuon.setAdapter(sachMuonAdapteradapter);
    }

    private void khoSach() {
        timSachAdapter = new TimSachAdapter();
        timSachAdapter.setListener(this);
        try {
            setListKhoSach();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        manager.setOrientation(RecyclerView.VERTICAL);

        recyclerViewKhoSach.setLayoutManager(manager);
        recyclerViewKhoSach.setAdapter(timSachAdapter);
    }

    private void initView(){
        tvTen = findViewById(R.id.tvTen);
        tvSdt = findViewById(R.id.tvSdt);
        recyclerViewDaMuon = findViewById(R.id.recyclerViewDaMuon);
        recyclerViewKhoSach = findViewById(R.id.recyclerViewKhoSach);
        searchView = findViewById(R.id.searchView);

        db = new SachChoMuonDB(getApplicationContext());
        dauSachDB = new DauSachDB(getApplicationContext());
        nguoiDungDB = new NguoiDungDB(getApplicationContext());
    }

    private void setListChoMuon() throws ParseException {
        List<SachChoMuon> listSachMuon = db.getByThanhVienId(nguoiDung.getId());
        List<DauSachThem> dauSachThemList = new ArrayList<>();

        if(listSachMuon.isEmpty() || listSachMuon == null){
            return;
        }

        Date currDate = Calendar.getInstance().getTime();


        for (SachChoMuon s:
                listSachMuon) {
            DauSach dauSach = dauSachDB.getById(s.getIdDauSach());

            long temp = s.getNgayTra().getTime() - currDate.getTime();
            int day = (int)(temp/(24L*60L*60L*1000L));

            DauSachThem dauSachThem = new DauSachThem(dauSach, day);

            dauSachThemList.add(dauSachThem);
        }


        sachMuonAdapteradapter.setList(dauSachThemList);
    }

    private void setListKhoSach() throws ParseException {
        List<DauSach> dauSachList = dauSachDB.getAll();
        List<DauSach> finalList = new ArrayList<>();
        List<SachChoMuon> sachChoMuonList = db.getByThanhVienId(nguoiDung.getId());

        if(dauSachList.isEmpty()){
            return;
        }

        for (DauSach d:
                dauSachList) {
            if(!db.kiemTraDauSach(d.getId())){
                if(checkSachDaMuon(d, sachChoMuonList)){
                    finalList.add(d);
                }
            }
        }

        timSachAdapter.setList(finalList);
    }

    private boolean checkSachDaMuon(DauSach dauSach, List<SachChoMuon> list) {
        for (SachChoMuon sachChoMuon:
             list) {

            DauSach temp = dauSachDB.getById(sachChoMuon.getIdDauSach());

            if(temp.getChuDe().equals(dauSach.getChuDe()) &&
            temp.getTen().equals(dauSach.getTen()) &&
            temp.getTacGia().equals(dauSach.getTacGia())){
                return false;
            }
        }
        return true;
    }

    @Override
    public void onClickItemListener(View view, DauSach d){
        db.themSachMuon(nguoiDung.getId(), d.getId());

        try {
            setListChoMuon();
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            setListKhoSach();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClickSachMuonListener(View view, DauSachThem d) {
        db.traSach(nguoiDung.getId(), d.getId());

        try {
            setListChoMuon();
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            setListKhoSach();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_sua_thanh_vien, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mSuaThanhVien:{
                Intent i =
                        new Intent(MuonTraActivity.this, SuaThanhVienActivity.class);

                i.putExtra("nguoiDung", nguoiDung);
                i.putExtra("thuThu", thuThu);

                startActivity(i);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        nguoiDung = nguoiDungDB.getById(nguoiDung.getId());
        tvTen.setText(nguoiDung.getName());
        tvSdt.setText(nguoiDung.getSoDienThoai());
    }

}