package com.example.thuvien.fragment.fragment3;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thuvien.R;
import com.example.thuvien.adapter.recycleViewAdapter.TimSachAdapter;
import com.example.thuvien.dataConnection.DauSachDB;
import com.example.thuvien.dataConnection.SachChoMuonDB;
import com.example.thuvien.model.DauSach;
import com.example.thuvien.model.SachChoMuon;
import com.example.thuvien.secondActivity.DsDauSachActivity;
import com.example.thuvien.secondActivity.UpdateDauSachActivity;

import java.util.ArrayList;
import java.util.List;

public class FragmentTimSach extends Fragment implements TimSachAdapter.TimAdapterListener{
    private EditText edTen, edTacgia, edChuDe;
    private TimSachAdapter adapter;
    private Button btnTim;
    private RecyclerView recyclerView;
    private DauSachDB db;
    private SachChoMuonDB sachChoMuonDB;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tim_sach, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edTen = view.findViewById(R.id.edTen);
        edTacgia = view.findViewById(R.id.edTacGia);
        edChuDe = view.findViewById(R.id.edChuDe);
        btnTim = view.findViewById(R.id.btnTimKiem);
        recyclerView = view.findViewById(R.id.recyclerView);
        db = new DauSachDB(getActivity());
        sachChoMuonDB = new SachChoMuonDB(getActivity());

        setRecycleView();
        btnTimListener();

        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);

        adapter.setListener(this);
    }

    private void btnTimListener() {
        btnTim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setListDauSach();
            }
        });
    }

    private void setRecycleView() {
        List<DauSach> list = new ArrayList<>();

        adapter = new TimSachAdapter();
        adapter.setList(list);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(RecyclerView.VERTICAL);

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    private void setListDauSach(){
        String ten = ""+edTen.getText().toString();
        String tacGia = ""+edTacgia.getText().toString();
        String chuDe = ""+edChuDe.getText().toString();

        List<DauSach> list = db.findDauSach(ten, tacGia, chuDe);

        adapter.setList(list);
    }

    ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            DauSach dauSach = adapter.getItemAtPosition(viewHolder.getAdapterPosition());

            if(sachChoMuonDB.kiemTraDauSach(dauSach.getId())){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                        .setTitle("Sách đang được mượn!")
                        .setMessage("Đầu sách này hiện đang được mượn nên bạn không thể xóa!");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        setListDauSach();
                    }
                });

                builder.create().show();
            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                        .setTitle("Xóa đầu sách!")
                        .setMessage("Bạn có chắc muốn xóa đầu sách này không?");

                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.xoa(dauSach);
                        setListDauSach();
                        Toast.makeText(getActivity(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        setListDauSach();
                    }
                });

                builder.create().show();
            }
        }
    };

    @Override
    public void onClickItemListener(View view, DauSach d) {
        Intent i = new Intent(getActivity(), UpdateDauSachActivity.class);

        i.putExtra("dauSach", d);

        startActivity(i);
    }

    @Override
    public void onResume() {
        super.onResume();
        setListDauSach();

        edTen.setText("");
        edTacgia.setText("");
        edChuDe.setText("");
    }
}
