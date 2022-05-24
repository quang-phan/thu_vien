package com.example.thuvien.secondActivity.doiMatKhau;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.thuvien.R;
import com.example.thuvien.dataConnection.NguoiDungDB;
import com.example.thuvien.model.NguoiDung;

public class DoiMatKhauActivity extends AppCompatActivity {
    private EditText edMatKhauCu, edMatKhauMoi, edMatKhauLap;
    private Button btnDoiMK, btnHuy;
    private NguoiDung nguoiDung;
    private NguoiDungDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);

        initView();
        buttonListener();
    }

    private void buttonListener() {
        btnHuyListener();
        btnDoiMKListener();
    }

    private void btnDoiMKListener() {
        btnDoiMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mkMoi = edMatKhauMoi.getText().toString();
                String mkLap = edMatKhauLap.getText().toString();

                if(mkMoi.equals(mkLap)){
                    String mkCu = edMatKhauCu.getText().toString();

                    if(nguoiDung.getPassword().equals(mkCu)){
                        nguoiDung.setPassword(mkMoi);
                        db.doiMK(nguoiDung);
                        Toast.makeText(getApplicationContext(),
                                "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();

                        Intent i = new Intent();
                        i.putExtra("nguoiDung", nguoiDung);
                        setResult(Activity.RESULT_OK, i);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(),
                                "Mật khẩu cũ sai", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),
                            "Mật khẩu lặp lại sai", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void btnHuyListener() {
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView() {
        edMatKhauCu = findViewById(R.id.edMatKhauCu);
        edMatKhauMoi = findViewById(R.id.edMatKhauMoi);
        edMatKhauLap = findViewById(R.id.edMatKhauLap);
        btnDoiMK = findViewById(R.id.btnDoiMK);
        btnHuy = findViewById(R.id.btnHuy);

        Intent i = getIntent();
        nguoiDung = (NguoiDung) i.getSerializableExtra("nguoiDung");

        db = new NguoiDungDB(getApplicationContext());
    }

}