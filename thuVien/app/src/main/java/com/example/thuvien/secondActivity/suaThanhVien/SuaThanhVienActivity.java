package com.example.thuvien.secondActivity.suaThanhVien;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.thuvien.R;
import com.example.thuvien.dataConnection.NguoiDungDB;
import com.example.thuvien.model.NguoiDung;

import java.util.regex.Pattern;

public class SuaThanhVienActivity extends AppCompatActivity {
    private EditText edTen, edDiaChi, edSoDienThoai, edCMND, edMatKhau, edInputDialog;
    private Button btnCapNhat;
    private NguoiDung thanhVien, thuThu;
    private NguoiDungDB nguoiDungDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_thanh_vien);

        initView();
        btnCapNhatListener();
    }

    private void btnCapNhatListener() {
        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SuaThanhVienActivity.this);

                builder.setTitle("Xác nhận mật khẩu");
                builder.setMessage("Vui lòng nhập mật khẩu xác nhận");

                View viewInflasted = LayoutInflater.from(SuaThanhVienActivity.this)
                        .inflate(R.layout.input_dialog, null);
                edInputDialog = viewInflasted.findViewById(R.id.edInputDialog);

                builder.setView(viewInflasted);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String matKhau = edInputDialog.getText().toString();

                        if(thuThu.getPassword().equals(matKhau)){
                            String ten = edTen.getText().toString();
                            String diaChi = edDiaChi.getText().toString();
                            String soDienThoai = edSoDienThoai.getText().toString();
                            String cmnd = edCMND.getText().toString();

                            if(!Pattern.compile("[a-zA-Z]+").matcher(ten).find()){
                                Toast.makeText(getApplicationContext(), "Tên nhập vào không hợp lệ", Toast.LENGTH_LONG).show();
                            }else if(!Pattern.compile("[a-zA-Z]+").matcher(diaChi).find()){
                                Toast.makeText(getApplicationContext(), "Địa chỉ nhập vào không hợp lệ", Toast.LENGTH_LONG).show();
                            }else if(!soDienThoai.matches("[0-9]+")){
                                Toast.makeText(getApplicationContext(), "Số điện thoại nhập vào không hợp lệ", Toast.LENGTH_LONG).show();
                            }else if(!cmnd.matches("[0-9]+")){
                                Toast.makeText(getApplicationContext(), "CMND nhập vào không hợp lệ", Toast.LENGTH_LONG).show();
                            }else{
                                thanhVien.setName(ten);
                                thanhVien.setDiaChi(diaChi);
                                thanhVien.setSoDienThoai(soDienThoai);
                                thanhVien.setCmnd(cmnd);
                                thanhVien.setPassword(edMatKhau.getText().toString());

                                nguoiDungDB.capNhat2(thanhVien);
                                Toast.makeText(getApplicationContext(),
                                        "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                setThongTin();
                            }

                        }else{
                            Toast.makeText(getApplicationContext(),
                                    "Sai mật khẩu", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.create().show();
            }
        });
    }

    private void initView() {
        edTen = findViewById(R.id.edTen);
        edDiaChi = findViewById(R.id.edDiaChi);
        edSoDienThoai = findViewById(R.id.edSoDienThoai);
        edCMND = findViewById(R.id.edCMND);
        edMatKhau = findViewById(R.id.edMatKhau);

        btnCapNhat = findViewById(R.id.btnCapNhat);

        Intent i = getIntent();
        thanhVien = (NguoiDung) i.getSerializableExtra("nguoiDung");
        thuThu = (NguoiDung) i.getSerializableExtra("thuThu");

        nguoiDungDB = new NguoiDungDB(getApplicationContext());
        setThongTin();
    }

    private void setThongTin() {
        edTen.setText(thanhVien.getName());
        edDiaChi.setText(thanhVien.getDiaChi());
        edSoDienThoai.setText(thanhVien.getSoDienThoai());
        edCMND.setText(thanhVien.getCmnd());
        edMatKhau.setText(thanhVien.getPassword());
    }


}