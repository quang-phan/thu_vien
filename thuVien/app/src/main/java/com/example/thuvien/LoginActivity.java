package com.example.thuvien;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thuvien.dataConnection.NguoiDungDB;
import com.example.thuvien.model.NguoiDung;

public class LoginActivity extends AppCompatActivity {
    private Button btnDangNhap;
    private EditText edUsername, edPassword;
    private NguoiDungDB db;
    private TextView tvQuenMK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnDangNhap = findViewById(R.id.btnLogin);
        edUsername = findViewById(R.id.edUsername);
        edPassword = findViewById(R.id.edPassword);
        tvQuenMK = findViewById(R.id.tvQuenMK);
        db = new NguoiDungDB(getApplicationContext());

//        NguoiDung nguoiDung = new NguoiDung();
//
//        nguoiDung.setName("admin");
//        nguoiDung.setDiaChi("Hà nội");
//        nguoiDung.setSoDienThoai("04534523452");
//        nguoiDung.setCmnd("0524525552");
//        nguoiDung.setPassword("admin");
//        nguoiDung.setUsername("admin");
//        nguoiDung.setRole("TT");
//
//        db.themNguoiDung(nguoiDung);
//
//        Toast.makeText(getApplicationContext(), "them thanh cong", Toast.LENGTH_SHORT).show();

        tvQuenMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                System.out.println("Click vao text view");
                goiThuThu();
            }
        });

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = edUsername.getText().toString();
                String password = edPassword.getText().toString();

                NguoiDung nguoiDung = db.dangNhap(userName, password);

                if(nguoiDung == null){
                    Toast.makeText(getApplicationContext(),
                            "Sai thông tin đăng nhập", Toast.LENGTH_SHORT).show();
                }else{
                    if(nguoiDung.getRole().equals("TV")){
                        thanhVienDangNhap(nguoiDung);
                    }else{
                        thuThuDangNhap(nguoiDung);
                    }

                    edPassword.setText("");
                    edUsername.setText("");
                }
            }
        });
    }

    private void goiThuThu(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông báo");
        builder.setMessage("Vui lòng gọi cho nhân viên quản lí thư viện để được xử lí!");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:01234567"));
                startActivity(intent);
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.create().show();
    }

    private void thanhVienDangNhap(NguoiDung nguoiDung){
        Intent i = new Intent(LoginActivity.this, MemberActivity.class);

        i.putExtra("nguoiDung", nguoiDung);

        startActivity(i);
    }

    private void thuThuDangNhap(NguoiDung nguoiDung){
        Intent i = new Intent(LoginActivity.this, AdminActivity.class);

        i.putExtra("nguoiDung", nguoiDung);

        startActivity(i);
    }
}