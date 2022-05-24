package com.example.thuvien.fragment.fragmentMember;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thuvien.MemberActivity;
import com.example.thuvien.R;
import com.example.thuvien.adapter.recycleViewAdapter.SachMuonAdapter;
import com.example.thuvien.dataConnection.DauSachDB;
import com.example.thuvien.dataConnection.SachChoMuonDB;
import com.example.thuvien.model.DauSach;
import com.example.thuvien.model.NguoiDung;
import com.example.thuvien.model.SachChoMuon;
import com.example.thuvien.model.daSach.DauSachThem;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FragmentDsMuon extends Fragment {
    private RecyclerView recyclerView;
    private SachMuonAdapter adapter;
    private NguoiDung nguoiDung;
    private SachChoMuonDB sachChoMuonDB;
    private DauSachDB dauSachDB;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_danh_sach_muon, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nguoiDung = ((MemberActivity) getActivity()).getNguoiDung();
        sachChoMuonDB = new SachChoMuonDB(getContext());
        dauSachDB = new DauSachDB(getContext());

        recyclerView = view.findViewById(R.id.recyclerView);

        adapter = new SachMuonAdapter();

        try {
            setList();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    private void setList() throws ParseException {
        List<SachChoMuon> choMuonList = sachChoMuonDB.getByThanhVienId(nguoiDung.getId());

        if (choMuonList.isEmpty()){
            return;
        }

        List<DauSachThem> finalList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();

        for (SachChoMuon s:
             choMuonList) {
            long temp = s.getNgayTra().getTime() - calendar.getTime().getTime();
            int day = (int)(temp/(24L*60L*60L*1000L));

            DauSach dauSach = dauSachDB.getById(s.getIdDauSach());

            if(dauSach != null){
                DauSachThem dauSachThem = new DauSachThem(dauSach, day);

                finalList.add(dauSachThem);
            }
        }

        adapter.setList(finalList);
    }

}