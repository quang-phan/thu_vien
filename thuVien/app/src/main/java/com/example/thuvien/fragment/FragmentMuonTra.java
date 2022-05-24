package com.example.thuvien.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.thuvien.AdminActivity;
import com.example.thuvien.R;
import com.example.thuvien.dataConnection.NguoiDungDB;
import com.example.thuvien.model.NguoiDung;
import com.example.thuvien.secondActivity.muonTra.MuonTraActivity;
import com.example.thuvien.secondActivity.thanhVienMoi.ThemThanhVienMoiActivity;

import java.util.regex.Pattern;

public class FragmentMuonTra extends Fragment {
    private EditText edTen, edDiaChi, edSoDienThoai, edCMND, edMaThanhVien;
    private TextView tvTen, tvDiaChi, tvSoDienThoai, tvCMND, tvMaThanhVien;
    private Button btnXacNhan;
    private NguoiDungDB db;
    private RadioGroup radioGroup;
    private NguoiDung thuThu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thong_tin_nguoi_muon, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        radioGroupListener();
        btnListener();
    }

    private void btnListener() {
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(radioGroup.getCheckedRadioButtonId() == R.id.rdNguoiMoi){
                    taoThongTinThanhVien();
                }else{
                    layThongTinThanhVien();
                }
            }
        });
    }

    private void layThongTinThanhVien() {
        int id = Integer.parseInt(edMaThanhVien.getText().toString());

        NguoiDung nguoiDung = db.getById(id);

        if(nguoiDung == null){
            Toast.makeText(getContext(), "Mã người dùng không tồn tại", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent i = new Intent(getActivity(), MuonTraActivity.class);

        i.putExtra("nguoiDung", nguoiDung);
        i.putExtra("thuThu", thuThu);

        startActivity(i);
    }

    private void taoThongTinThanhVien(){
        String ten = edTen.getText().toString();
        String diaChi = edDiaChi.getText().toString();
        String soDienThoai = edSoDienThoai.getText().toString();
        String cmnd = edCMND.getText().toString();

        if(!Pattern.compile("[a-zA-Z]+").matcher(ten).find()){
            Toast.makeText(getContext(), "Tên nhập vào không hợp lệ", Toast.LENGTH_LONG).show();
        }else if(!Pattern.compile("[a-zA-Z]+").matcher(diaChi).find()){
            Toast.makeText(getContext(), "Địa chỉ nhập vào không hợp lệ", Toast.LENGTH_LONG).show();
        }else if(!soDienThoai.matches("[0-9]+")){
            Toast.makeText(getContext(), "Số điện thoại nhập vào không hợp lệ", Toast.LENGTH_LONG).show();
        }else if(!cmnd.matches("[0-9]+")){
            Toast.makeText(getContext(), "CMND nhập vào không hợp lệ", Toast.LENGTH_LONG).show();
        }else{
           if(db.themNguoiDung(ten, diaChi, soDienThoai, cmnd) != 0){
               Toast.makeText(getContext(), "them thanh cong", Toast.LENGTH_SHORT).show();
               NguoiDung nguoiDung = db.getNguoiDung(ten, diaChi, soDienThoai, cmnd);

               Intent i = new Intent(getActivity(), ThemThanhVienMoiActivity.class);

               i.putExtra("nguoiDung", nguoiDung);
               i.putExtra("thuThu", thuThu);

               startActivity(i);
           }
        }
    }

    private void radioGroupListener() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (radioGroup.getCheckedRadioButtonId()){
                    case R.id.rdNguoiCu:{
                        thanhVienCu();
                        break;
                    }
                    case R.id.rdNguoiMoi:{
                        thanhVienMoi();
                        break;
                    }
                }
            }
        });
    }

    private void initView(View view) {
        edTen = view.findViewById(R.id.edTen);
        edDiaChi = view.findViewById(R.id.edDiaChi);
        edSoDienThoai = view.findViewById(R.id.edSoDienThoai);
        edCMND = view.findViewById(R.id.edCMND);
        edMaThanhVien = view.findViewById(R.id.edMaThanhVien);
        btnXacNhan = view.findViewById(R.id.btnXacNhan);

        tvTen = view.findViewById(R.id.tvTen);
        tvDiaChi = view.findViewById(R.id.tvDiaChi);
        tvSoDienThoai = view.findViewById(R.id.tvSoDienThoai);
        tvCMND = view.findViewById(R.id.tvCMND);
        tvMaThanhVien = view.findViewById(R.id.tvMaThanhVien);

        radioGroup = view.findViewById(R.id.radioGroup);

        db = new NguoiDungDB(getContext());

        thuThu = ((AdminActivity)getActivity()).getNguoiDung();
    }

    private void thanhVienCu(){
        edTen.setVisibility(View.INVISIBLE);
        edDiaChi.setVisibility(View.INVISIBLE);
        edSoDienThoai.setVisibility(View.INVISIBLE);
        edCMND.setVisibility(View.INVISIBLE);
        edMaThanhVien.setVisibility(View.VISIBLE);

        tvTen.setVisibility(View.INVISIBLE);
        tvDiaChi.setVisibility(View.INVISIBLE);
        tvSoDienThoai.setVisibility(View.INVISIBLE);
        tvCMND.setVisibility(View.INVISIBLE);
        tvMaThanhVien.setVisibility(View.VISIBLE);

    }

    private void thanhVienMoi(){
        edTen.setVisibility(View.VISIBLE);
        edDiaChi.setVisibility(View.VISIBLE);
        edSoDienThoai.setVisibility(View.VISIBLE);
        edCMND.setVisibility(View.VISIBLE);
        edMaThanhVien.setVisibility(View.INVISIBLE);

        tvTen.setVisibility(View.VISIBLE);
        tvDiaChi.setVisibility(View.VISIBLE);
        tvSoDienThoai.setVisibility(View.VISIBLE);
        tvCMND.setVisibility(View.VISIBLE);
        tvMaThanhVien.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        edTen.setText("");
        edDiaChi.setText("");
        edSoDienThoai.setText("");
        edCMND.setText("");
        edMaThanhVien.setText("");
    }
}
