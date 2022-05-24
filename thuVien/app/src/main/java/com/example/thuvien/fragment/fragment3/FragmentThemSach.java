package com.example.thuvien.fragment.fragment3;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thuvien.R;
import com.example.thuvien.adapter.recycleViewAdapter.ThemAdapter;
import com.example.thuvien.dataConnection.DauSachDB;
import com.example.thuvien.model.DauSach;
import com.example.thuvien.model.daSach.DauSachThem;
import com.example.thuvien.secondActivity.DsDauSachActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class FragmentThemSach extends Fragment implements ThemAdapter.ThemAdapterListener {
    private EditText edTen, edTacGia, edChuDe, edGia, edGhiChu, edSoLuong;
    private RecyclerView recyclerView;
    private Button btnThem;
    private ThemAdapter adapter;
    private DauSachDB db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_them_sach, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edTen = view.findViewById(R.id.edTen);
        edTacGia = view.findViewById(R.id.edTacGia);
        edChuDe = view.findViewById(R.id.edChuDe);
        edGia = view.findViewById(R.id.edGia);
        edGhiChu = view.findViewById(R.id.edGhiChu);
        edSoLuong = view.findViewById(R.id.edSoLuong);
        btnThem = view.findViewById(R.id.btnThem);
        recyclerView = view.findViewById(R.id.recyclerView);
        db = new DauSachDB(getContext());
        
        setRecyclerViewAdapter();
        buttonThemListener();
    }

    private void buttonThemListener() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ten = edTen.getText().toString();
                String tacGia = edTacGia.getText().toString();
                String chuDe = edChuDe.getText().toString();
                String gia = edGia.getText().toString();
                String ghiChu = edGhiChu.getText().toString();
                String soLuong = edSoLuong.getText().toString();

                if(!Pattern.compile("[a-zA-Z]+").matcher(ten).find()){
                    Toast.makeText(getContext(), "Tên nhập vào không hợp lệ", Toast.LENGTH_LONG).show();
                }else if(!Pattern.compile("[a-zA-Z]+").matcher(tacGia).find()){
                    Toast.makeText(getContext(), "Tên tác giả nhập vào không hợp lệ", Toast.LENGTH_LONG).show();
                }else if(!Pattern.compile("[a-zA-Z]+").matcher(chuDe).find()){
                    Toast.makeText(getContext(), "Chủ đề nhập vào không hợp lệ", Toast.LENGTH_LONG).show();
                }else if(!gia.matches("[0-9]+[.]*[0-9]*")){
                    Toast.makeText(getContext(), "Giá nhập vào không hợp lệ", Toast.LENGTH_LONG).show();
                }
                else if(!soLuong.matches("[0-9]+")){
                    Toast.makeText(getContext(), "Số lượng nhập vào không hợp lệ", Toast.LENGTH_LONG).show();
                }else{
                    DauSach dauSach = new DauSach(ten, tacGia, chuDe,
                            Double.parseDouble(gia), ghiChu);

                    int t = Integer.parseInt(soLuong);
                    while(t > 0){
                        db.them(dauSach);
                        t--;
                    }

                    getAllDauSach();

                    edChuDe.setText("");
                    edTen.setText("");
                    edTacGia.setText("");
                    edGia.setText("");
                    edGhiChu.setText("");
                    edSoLuong.setText("1");
                }
            }
        });
    }

    private void setRecyclerViewAdapter() {
        adapter = new ThemAdapter();

        adapter.setListener(this);

        getAllDauSach();
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    private void getAllDauSach(){
        List<DauSach> list = db.getAll();
        if(list.isEmpty()){
            return;
        }

//        System.out.println(list);
        List<DauSachThem> finalList = new ArrayList<>();

        DauSach dauSach = list.get(0);

        DauSachThem dauSachThem = new DauSachThem(dauSach, 1);
        finalList.add(dauSachThem);

        for(int i = 1; i < list.size(); i++){
            DauSachThem dst = finalList.get(finalList.size()-1);
            if(dst.getTen().equals(list.get(i).getTen()) &&
                    dst.getTacGia().equals(list.get(i).getTacGia()) &&
                dst.getChuDe().equals(list.get(i).getChuDe())){

                dst.setSoLuong(dst.getSoLuong()+1);

            }else{
                dst = new DauSachThem(list.get(i), 1);
                finalList.add(dst);
            }
        }

        adapter.setList(finalList);
    }

    @Override
    public void onClickItemListener(View view, DauSachThem d) {
        Intent intent = new Intent(getActivity(), DsDauSachActivity.class);

        intent.putExtra("dauSachThem", d);

        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        getAllDauSach();

        edTen.setText("");
        edTacGia.setText("");
        edChuDe.setText("");
        edGia.setText("");
        edGhiChu.setText("");
        edSoLuong.setText("1");
    }
}
