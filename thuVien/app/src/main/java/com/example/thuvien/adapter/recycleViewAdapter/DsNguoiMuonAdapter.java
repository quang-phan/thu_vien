package com.example.thuvien.adapter.recycleViewAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thuvien.R;
import com.example.thuvien.model.sachChoMuon.ThongTinMuon;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DsNguoiMuonAdapter extends RecyclerView.Adapter<DsNguoiMuonAdapter.DsNguoiMuonViewHolder>{
    private List<ThongTinMuon> list;

    public DsNguoiMuonAdapter() {
        list = new ArrayList<>();
    }

    public void setList(List<ThongTinMuon> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DsNguoiMuonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_danh_sach, parent,
                false);
        return new DsNguoiMuonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DsNguoiMuonViewHolder holder, int position) {
        ThongTinMuon thongTinMuon = list.get(position);

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        Date date = new Date();

        long temp = thongTinMuon.getNgayTra().getTime() - date.getTime();

        int day = (int)(temp/(24L*60L*60L*1000L));

        holder.tvMaThanhVien.setText("Mã thành viên: "+thongTinMuon.getIdThanhVien());
        holder.tvTenThanhVien.setText("Tên thành viên: "+thongTinMuon.getTenThanhVien());
        holder.tvTenDauSach.setText("Tên sách: "+thongTinMuon.getTenDauSach());
        holder.tvNgayMuon.setText("Ngày mượn: "+format.format(thongTinMuon.getNgayMuon()));

        if(day >= 0){
            holder.tvNgayConLai.setText("Còn "+day+" ngày");
        }else{
            day *= -1;
            holder.tvNgayConLai.setText("Quá hạn "+day+" ngày");
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class DsNguoiMuonViewHolder extends RecyclerView.ViewHolder{
        private TextView tvMaThanhVien, tvTenThanhVien, tvTenDauSach,
                tvNgayMuon, tvNgayConLai;

        public DsNguoiMuonViewHolder(@NonNull View view) {
            super(view);

            tvMaThanhVien = view.findViewById(R.id.tvMaThanhVien);
            tvTenThanhVien = view.findViewById(R.id.tvTenThanhVien);
            tvTenDauSach = view.findViewById(R.id.tvTenDauSach);
            tvNgayMuon = view.findViewById(R.id.tvNgayMuon);
            tvNgayConLai = view.findViewById(R.id.tvNgayConLai);
        }
    }
}
