package com.example.thuvien.model.sachChoMuon;

import com.example.thuvien.model.SachChoMuon;

import java.util.Date;

public class ThongTinMuon extends SachChoMuon {
    private String tenThanhVien;
    private String tenDauSach;

    public ThongTinMuon() {
        super();
    }

    public ThongTinMuon(SachChoMuon sachChoMuon) {
        super(sachChoMuon.getId(), sachChoMuon.getNgayMuon(),
                sachChoMuon.getNgayTra(), sachChoMuon.getIdThanhVien(), sachChoMuon.getIdDauSach());
    }

    public String getTenThanhVien() {
        return tenThanhVien;
    }

    public void setTenThanhVien(String tenThanhVien) {
        this.tenThanhVien = tenThanhVien;
    }

    public String getTenDauSach() {
        return tenDauSach;
    }

    public void setTenDauSach(String tenDauSach) {
        this.tenDauSach = tenDauSach;
    }
}
