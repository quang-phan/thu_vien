package com.example.thuvien.secondActivity.thanhVienMoi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thuvien.R;
import com.example.thuvien.dataConnection.NguoiDungDB;
import com.example.thuvien.model.NguoiDung;
import com.example.thuvien.secondActivity.muonTra.MuonTraActivity;

public class ThemThanhVienMoiActivity extends AppCompatActivity {
    private TextView tvUsername, tvPassword;
    private Button btnXacNhan;
    private NguoiDungDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_thanh_vien_moi);

        tvUsername = findViewById(R.id.tvUsername);
        tvPassword = findViewById(R.id.tvPassword);
        btnXacNhan = findViewById(R.id.btnXacNhan);
        db = new NguoiDungDB(getApplicationContext());

        Intent i = getIntent();
        NguoiDung nguoiDung = (NguoiDung) i.getSerializableExtra("nguoiDung");

        tvUsername.setText(nguoiDung.getId()+"");
        tvPassword.setText(nguoiDung.getId()+"");

        nguoiDung.setUsername(nguoiDung.getId()+"");
        nguoiDung.setPassword(nguoiDung.getId()+"");

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.capNhat(nguoiDung);
                Intent i = new Intent(ThemThanhVienMoiActivity.this,
                        MuonTraActivity.class);

                i.putExtra("nguoiDung", nguoiDung);

                startActivity(i);
            }
        });
    }
}