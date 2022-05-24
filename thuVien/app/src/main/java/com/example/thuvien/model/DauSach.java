package com.example.thuvien.model;

import java.io.Serializable;

public class DauSach implements Serializable {
    private int id;
    private String ten;
    private String tacGia;
    private String chuDe;
    private Double gia;
    private String ghiChu;

    public DauSach() {
    }

    public DauSach(String ten, String tacGia, String chuDe, Double gia, String ghiChu) {
        this.ten = ten;
        this.tacGia = tacGia;
        this.chuDe = chuDe;
        this.gia = gia;
        this.ghiChu = ghiChu;
    }

    public DauSach(int id, String ten, String tacGia, String chuDe, Double gia, String ghiChu) {
        this.id = id;
        this.ten = ten;
        this.tacGia = tacGia;
        this.chuDe = chuDe;
        this.gia = gia;
        this.ghiChu = ghiChu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public String getChuDe() {
        return chuDe;
    }

    public void setChuDe(String chuDe) {
        this.chuDe = chuDe;
    }

    public Double getGia() {
        return gia;
    }

    public void setGia(Double gia) {
        this.gia = gia;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
