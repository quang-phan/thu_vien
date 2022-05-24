package com.example.thuvien.model;

import java.io.Serializable;
import java.util.Date;

public class SachChoMuon implements Serializable {
    private int id;
    private Date ngayMuon;
    private Date ngayTra;
    private int idThanhVien;
    private int idDauSach;

    public SachChoMuon() {
    }

    public SachChoMuon(int id, Date ngayMuon, Date ngayTra, int idThanhVien, int idDauSach) {
        this.id = id;
        this.ngayMuon = ngayMuon;
        this.ngayTra = ngayTra;
        this.idThanhVien = idThanhVien;
        this.idDauSach = idDauSach;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getNgayMuon() {
        return ngayMuon;
    }

    public void setNgayMuon(Date ngayMuon) {
        this.ngayMuon = ngayMuon;
    }

    public Date getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(Date ngayTra) {
        this.ngayTra = ngayTra;
    }

    public int getIdThanhVien() {
        return idThanhVien;
    }

    public void setIdThanhVien(int idThanhVien) {
        this.idThanhVien = idThanhVien;
    }

    public int getIdDauSach() {
        return idDauSach;
    }

    public void setIdDauSach(int idDauSach) {
        this.idDauSach = idDauSach;
    }
}
