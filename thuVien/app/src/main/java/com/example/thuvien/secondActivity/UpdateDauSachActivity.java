package com.example.thuvien.secondActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.thuvien.R;
import com.example.thuvien.dataConnection.DauSachDB;
import com.example.thuvien.model.DauSach;

import java.util.regex.Pattern;

public class UpdateDauSachActivity extends AppCompatActivity {
    private EditText edTen, edTacGia, edChuDe, edGia, edGhiChu;
    private Button btn;
    private Intent i;
    private DauSach dauSach;
    private DauSachDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_dau_sach);

        edTen = findViewById(R.id.edTen);
        edTacGia = findViewById(R.id.edTacGia);
        edChuDe = findViewById(R.id.edChuDe);
        edGia = findViewById(R.id.edGia);
        edGhiChu = findViewById(R.id.edGhiChu);
        btn = findViewById(R.id.btnUpdate);
        db = new DauSachDB(this);

        setText();
        btnListener();
    }

    private void btnListener() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ten = edTen.getText().toString();
                String tacGia = edTacGia.getText().toString();
                String chuDe = edChuDe.getText().toString();
                String gia = edGia.getText().toString();
                String ghiChu = edGhiChu.getText().toString();

                if(!Pattern.compile("[a-zA-Z]+").matcher(ten).find()){
                    Toast.makeText(getApplicationContext(), "Tên nhập vào không hợp lệ", Toast.LENGTH_LONG).show();
                }else if(!Pattern.compile("[a-zA-Z]+").matcher(tacGia).find()){
                    Toast.makeText(getApplicationContext(), "Tên tác giả nhập vào không hợp lệ", Toast.LENGTH_LONG).show();
                }else if(!Pattern.compile("[a-zA-Z]+").matcher(chuDe).find()){
                    Toast.makeText(getApplicationContext(), "Chủ đề nhập vào không hợp lệ", Toast.LENGTH_LONG).show();
                }else if(!gia.matches("[0-9]+[.]*[0-9]*")){
                    Toast.makeText(getApplicationContext(), "Giá nhập vào không hợp lệ", Toast.LENGTH_LONG).show();
                }else{
                    dauSach.setTen(ten);
                    dauSach.setTacGia(tacGia);
                    dauSach.setChuDe(chuDe);
                    dauSach.setGia(Double.parseDouble(gia));
                    dauSach.setGhiChu(ghiChu);

                    db.capNhat(dauSach);
                    Toast.makeText(getApplicationContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    private void setText() {
        i = getIntent();
        dauSach = (DauSach) i.getSerializableExtra("dauSach");

        edTen.setText(dauSach.getTen());
        edTacGia.setText(dauSach.getTacGia());
        edChuDe.setText(dauSach.getChuDe());
        edGia.setText(dauSach.getGia()+"");
        edGhiChu.setText(dauSach.getGhiChu());
    }
}