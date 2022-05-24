package com.example.thuvien.fragment.fragmentMember;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.thuvien.MemberActivity;
import com.example.thuvien.R;
import com.example.thuvien.dataConnection.NguoiDungDB;
import com.example.thuvien.model.NguoiDung;

import java.util.regex.Pattern;

public class FragmentThongTinThanhVien extends Fragment{

    private EditText edDiaChi, edSoDienThoai;
    private TextView edTen, edCMND;
    private Button btnCapNhat;
    private NguoiDung nguoiDung;
    private NguoiDungDB db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thong_tin_thanh_vien, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edDiaChi = view.findViewById(R.id.edDiaChi);
        edSoDienThoai = view.findViewById(R.id.edSoDienThoai);
        btnCapNhat = view.findViewById(R.id.btnCapNhat);
        edTen = view.findViewById(R.id.edTen);
        edCMND = view.findViewById(R.id.edCMND);

        btnCapNhat.setEnabled(false);

        nguoiDung = ((MemberActivity) getActivity()).getNguoiDung();

        db = new NguoiDungDB(getContext());

        setThongTin();
        btnCapNhatListener();

        setOnToch();

    }

    private void setOnToch() {
        edDiaChi.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                btnCapNhat.setEnabled(true);
                return false;
            }
        });

        edSoDienThoai.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                btnCapNhat.setEnabled(true);
                return false;
            }
        });
    }

    private void setThongTin(){
        edTen.setText(nguoiDung.getName());
        edDiaChi.setText(nguoiDung.getDiaChi());
        edSoDienThoai.setText(nguoiDung.getSoDienThoai());
        edCMND.setText(nguoiDung.getCmnd());
    }

    private void btnCapNhatListener() {
        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new
                        AlertDialog.Builder(getActivity());

                builder.setTitle("Cập nhật thông tin");
                builder.setMessage("Bạn có chắc muốn cập nhập thông tin cá nhân?");

                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String diaChi = edDiaChi.getText().toString();
                        String soDienThoai = edSoDienThoai.getText().toString();

                        if(!Pattern.compile("[a-zA-Z]+").matcher(diaChi).find()){
                            Toast.makeText(getContext(), "Địa chỉ nhập vào không hợp lệ", Toast.LENGTH_LONG).show();
                        }else if(!soDienThoai.matches("[0-9]+")){
                            Toast.makeText(getContext(), "Số điện thoại nhập vào không hợp lệ", Toast.LENGTH_LONG).show();
                        }else{
                            nguoiDung.setSoDienThoai(soDienThoai);
                            nguoiDung.setDiaChi(diaChi);
                            db.capNhat(nguoiDung);
                            Toast.makeText(getActivity(), "Cập nhật thành công",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.create().show();
                btnCapNhat.setEnabled(false);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        setThongTin();
    }
}
