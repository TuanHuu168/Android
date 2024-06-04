package com.example.projectgk;

import java.io.Serializable;

public class NhanVien implements Serializable {
    private String maNhanVien;
    private String hoVaTen;
    private String chucVu;
    private String email;
    private String soDienThoai;
    private String anhDaiDien;
    private String maDonVi;

    // Constructor
    public NhanVien(){}

    public NhanVien(String maNhanVien, String hoVaTen, String chucVu, String email, String soDienThoai, String anhDaiDien, String maDonVi) {
        this.maNhanVien = maNhanVien;
        this.hoVaTen = hoVaTen;
        this.chucVu = chucVu;
        this.email = email;
        this.soDienThoai = soDienThoai;
        this.anhDaiDien = anhDaiDien;
        this.maDonVi = maDonVi;
    }

    // Getters
    public String getMaNhanVien() {
        return maNhanVien;
    }

    public String getHoVaTen() {
        return hoVaTen;
    }

    public String getChucVu() {
        return chucVu;
    }

    public String getEmail() {
        return email;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public String getAnhDaiDien() {
        return anhDaiDien;
    }

    public String getMaDonVi() {
        return maDonVi;
    }

    // Setters
    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public void setHoVaTen(String hoVaTen) {
        this.hoVaTen = hoVaTen;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public void setAnhDaiDien(String anhDaiDien) {
        this.anhDaiDien = anhDaiDien;
    }

    public void setMaDonVi(String maDonVi) {
        this.maDonVi = maDonVi;
    }
}
