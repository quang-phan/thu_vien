package com.example.thuvien.model.daSach;

import com.example.thuvien.model.DauSach;

import java.io.Serializable;

public class DauSachThem extends DauSach implements Serializable {
    private int soLuong;

    public DauSachThem(int soLuong) {
        this.soLuong = soLuong;
    }

    public DauSachThem(String ten, String tacGia, String chuDe, Double gia, String ghiChu, int soLuong) {
        super(ten, tacGia, chuDe, gia, ghiChu);
        this.soLuong = soLuong;
    }

    public DauSachThem() {
    }

    public DauSachThem(DauSach dauSach, int soLuong){
        super(dauSach.getId(), dauSach.getTen(), dauSach.getTacGia(),
                dauSach.getChuDe(), dauSach.getGia(),
                dauSach.getGhiChu());
        this.soLuong = soLuong;
    }

    public int getSoLuong() {
        return this.soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
