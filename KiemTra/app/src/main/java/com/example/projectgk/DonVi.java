package com.example.projectgk;

import java.io.Serializable;

public class DonVi implements Serializable {
    private String maDonVi;
    private String tenDonVi;
    private String email;
    private String website;
    private String logo;
    private String diaChi;
    private String soDienThoai;
    private String maDonViCha;

    public DonVi() {}

    public DonVi(String maDonVi, String tenDonVi, String email, String website, String logo, String diaChi, String soDienThoai, String maDonViCha) {
        this.maDonVi = maDonVi;
        this.tenDonVi = tenDonVi;
        this.email = email;
        this.website = website;
        this.logo = logo;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
        this.maDonViCha = maDonViCha;
    }

    // Getters
    public String getMaDonVi() {
        return maDonVi;
    }

    public String getTenDonVi() {
        return tenDonVi;
    }

    public String getEmail() {
        return email;
    }

    public String getWebsite() {
        return website;
    }

    public String getLogo() {
        return logo;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public String getMaDonViCha() {
        return maDonViCha;
    }

    // Setters
    public void setMaDonVi(String maDonVi) {
        this.maDonVi = maDonVi;
    }

    public void setTenDonVi(String tenDonVi) {
        this.tenDonVi = tenDonVi;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public void setMaDonViCha(String maDonViCha) {
        this.maDonViCha = maDonViCha;
    }
}
