package com.example.thuvien.adapter.recycleViewAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thuvien.R;
import com.example.thuvien.model.DauSach;
import com.example.thuvien.model.daSach.DauSachThem;

import java.util.ArrayList;
import java.util.List;

public class DsDauSachAdapter extends RecyclerView.Adapter<DsDauSachAdapter.ThemHolder> {
    private List<DauSach> list;
    private DsDauSachAdapterListener listener;

    public DsDauSachAdapter() {
        list = new ArrayList<>();
    }

    public void setList(List<DauSach> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setListener(DsDauSachAdapterListener listener) {
        this.listener = listener;
    }

    public DauSach getItemAtPosition(int position){
        return list.get(position);
    }

    @NonNull
    @Override
    public ThemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_them_sach, parent, false);
        return new ThemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThemHolder holder, int position) {
        DauSach dauSach = list.get(position);

        holder.tvTen.setText(dauSach.getId()+"");
        holder.tvChuDe.setText(dauSach.getTen());
        holder.tvTacGia.setText(dauSach.getGhiChu());
        holder.tvSoLuong.setText(dauSach.getGia()+"đ");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ThemHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView tvTen, tvTacGia, tvChuDe, tvSoLuong;
        public ThemHolder(@NonNull View view) {
            super(view);

            tvTen = view.findViewById(R.id.tvTen);
            imageView = view.findViewById(R.id.img);
            tvTacGia = view.findViewById(R.id.tvTacGia);
            tvChuDe = view.findViewById(R.id.tvChuDe);
            tvSoLuong = view.findViewById(R.id.tvSoLuong);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        listener.onClickItemListener(view, list.get(getAdapterPosition()));
                    }
                }
            });
        }
    }

    public interface DsDauSachAdapterListener{
        void onClickItemListener(View view, DauSach dauSach);
    }
}
