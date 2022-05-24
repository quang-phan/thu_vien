package com.example.thuvien.fragment;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thuvien.R;
import com.example.thuvien.adapter.recycleViewAdapter.DsNguoiMuonAdapter;
import com.example.thuvien.dataConnection.DauSachDB;
import com.example.thuvien.dataConnection.NguoiDungDB;
import com.example.thuvien.dataConnection.SachChoMuonDB;
import com.example.thuvien.model.DauSach;
import com.example.thuvien.model.NguoiDung;
import com.example.thuvien.model.SachChoMuon;
import com.example.thuvien.model.sachChoMuon.ThongTinMuon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FragmentDanhSach extends Fragment {
    private EditText edTen, edDauSach, edFromdate, edToDate;
    private Button btnTimKiem;
    private RecyclerView recyclerView;
    private DsNguoiMuonAdapter adapter;
    private DauSachDB dauSachDB;
    private NguoiDungDB nguoiDungDB;
    private SachChoMuonDB sachChoMuonDB;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_danh_sach, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edTen = view.findViewById(R.id.edTen);
        edDauSach = view.findViewById(R.id.edDauSach);
        edFromdate = view.findViewById(R.id.edFromdate);
        edToDate = view.findViewById(R.id.edToDate);

        btnTimKiem = view.findViewById(R.id.btnTimKiem);

        recyclerView = view.findViewById(R.id.recyclerView);

        dauSachDB = new DauSachDB(getContext());
        nguoiDungDB = new NguoiDungDB(getContext());
        sachChoMuonDB = new SachChoMuonDB(getContext());

        try {
            setRecyclerView();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        btnTimKiemListener();
        setDateDialog();
    }

    private void setDateDialog() {
        Calendar calendar = Calendar.getInstance();
        edToDateDialog(calendar);
        edFromdateDialog(calendar);
    }

    private void edFromdateDialog(Calendar date) {
        edFromdate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                DatePickerDialog dialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                edFromdate.setText(day+"/"+(month+1)+"/"+year);
                            }
                        }, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));

                dialog.show();
            }
        });
    }

    private void edToDateDialog(Calendar date) {
        edToDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                DatePickerDialog dialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        edToDate.setText(day+"/"+(month+1)+"/"+year);
                    }
                }, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));

                dialog.show();
            }
        });


    }

    private void btnTimKiemListener() {
        btnTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    setAdapterList();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setRecyclerView() throws ParseException {
        adapter = new DsNguoiMuonAdapter();
        setAdapterList();

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    private void setAdapterList() throws ParseException {
        String tenThanhVien = edTen.getText().toString();
        String tenDauSach = edDauSach.getText().toString();
        String tuNgay = edFromdate.getText().toString();
        String denNgay = edToDate.getText().toString();

        List<NguoiDung> nguoiDungList = nguoiDungDB.timTheoTen(tenThanhVien);
        List<DauSach> dauSachList = dauSachDB.timTheoTen(tenDauSach);
        List<ThongTinMuon> thongTinMuonList = new ArrayList<>();
        List<ThongTinMuon> finalList = new ArrayList<>();

        if(nguoiDungList.isEmpty() || dauSachList.isEmpty()){
            return;
        }

        for (NguoiDung nd:
             nguoiDungList) {
            for (DauSach d:
                 dauSachList) {
                SachChoMuon sachChoMuon = sachChoMuonDB.getByThanhVienAndDauSach(
                        nd.getId(), d.getId()
                );

                if(sachChoMuon != null){
                    ThongTinMuon t = new ThongTinMuon(sachChoMuon);
                    t.setTenThanhVien(nd.getName());
                    t.setTenDauSach(d.getTen());

                    thongTinMuonList.add(t);
                }
            }
        }

        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            Date dateFrom = format.parse(tuNgay);
            Date dateTo = format.parse(denNgay);

            dateTo.setTime(dateTo.getTime()+(24L*60L*60L*1000L));

            for (ThongTinMuon thongTinMuon:
                 thongTinMuonList) {
                if(dateFrom.getTime() <= thongTinMuon.getNgayMuon().getTime() &&
                thongTinMuon.getNgayMuon().getTime() <= dateTo.getTime()){
                    finalList.add(thongTinMuon);
                }
            }
        }catch (Exception e){
            finalList = thongTinMuonList;
            e.printStackTrace();
        }

        adapter.setList(finalList);
    }

    @Override
    public void onResume() {
        super.onResume();
        edTen.setText("");
        edDauSach.setText("");
        edFromdate.setText("");
        edToDate.setText("");

        try {
            setAdapterList();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
