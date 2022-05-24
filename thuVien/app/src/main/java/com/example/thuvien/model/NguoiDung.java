package com.example.thuvien.model;


import java.io.Serializable;

public class NguoiDung implements Serializable {
    private int id;
    private String name;
    private String diaChi;
    private String soDienThoai;
    private String cmnd;
    private String username;
    private String password;
    private String role;

    public NguoiDung() {
    }

    public NguoiDung(int id, String name, String diaChi, String soDienThoai,
                     String cmnd, String username, String password, String role) {
        this.id = id;
        this.name = name;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
        this.cmnd = cmnd;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "NguoiDung{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", diaChi='" + diaChi + '\'' +
                ", soDienThoai='" + soDienThoai + '\'' +
                ", cmnd='" + cmnd + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
