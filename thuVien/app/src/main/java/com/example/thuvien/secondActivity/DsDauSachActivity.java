package com.example.thuvien.secondActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.thuvien.R;
import com.example.thuvien.adapter.recycleViewAdapter.DsDauSachAdapter;
import com.example.thuvien.dataConnection.DauSachDB;
import com.example.thuvien.model.DauSach;
import com.example.thuvien.model.daSach.DauSachThem;

import java.util.List;

public class DsDauSachActivity extends AppCompatActivity implements DsDauSachAdapter.DsDauSachAdapterListener {
    private RecyclerView recyclerView;
    private DsDauSachAdapter adapter;
    private DauSachDB db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ds_dau_sach);

        recyclerView = findViewById(R.id.recyclerView);
        db = new DauSachDB(getApplicationContext());

        setRecyclerViewAdapter();

        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);
    }

    private void setRecyclerViewAdapter() {
        adapter = new DsDauSachAdapter();
        adapter.setListener(this);

        setListItem();

        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        manager.setOrientation(RecyclerView.VERTICAL);

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    private void setListItem(){
        Intent intent = getIntent();
        DauSachThem dauSachThem = (DauSachThem) intent.getSerializableExtra("dauSachThem");
        List<DauSach> list = db.getDauSachGroup(dauSachThem.getTen(),
                dauSachThem.getTacGia(), dauSachThem.getChuDe());

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
            AlertDialog.Builder builder = new AlertDialog.Builder(DsDauSachActivity.this)
                    .setTitle("Xóa đầu sách!")
                    .setMessage("Bạn có chắc muốn xóa đầu sách này không?");

            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    db.xoa(dauSach);
                    setListItem();
                    Toast.makeText(getApplicationContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                }
            });

            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    setListItem();
                }
            });

            builder.create().show();
        }
    };

    @Override
    public void onClickItemListener(View view, DauSach dauSach) {
//        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();

        Intent i = new Intent(DsDauSachActivity.this, UpdateDauSachActivity.class);

        i.putExtra("dauSach", dauSach);

        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setListItem();
    }
}