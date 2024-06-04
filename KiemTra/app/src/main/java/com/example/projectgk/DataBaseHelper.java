package com.example.projectgk;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DataBaseHelper {
    private DatabaseReference mDatabase;

    public DataBaseHelper() {
        mDatabase = FirebaseDatabase.getInstance("https://giuaky-c029c-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
    }

    // Thêm đơn vị mới vào Firebase
    public void addDonVi(DonVi donVi) {
        mDatabase.child("DonVi").child(donVi.getMaDonVi()).setValue(donVi);
    }

    // Sửa thông tin đơn vị trong Firebase
    public void updateDonVi(String maDonVi, DonVi donVi) {
        mDatabase.child("DonVi").child(maDonVi).setValue(donVi);
    }

    // Xóa đơn vị khỏi Firebase
    public void deleteDonVi(String maDonVi) {
        mDatabase.child("DonVi").child(maDonVi).removeValue();
    }

    // Thêm nhân viên mới vào Firebase
    public void addNhanVien(NhanVien nhanVien) {
        mDatabase.child("NhanVien").child(nhanVien.getMaNhanVien()).setValue(nhanVien);
    }

    // Sửa thông tin nhân viên trong Firebase
    public void updateNhanVien(String maNhanVien, NhanVien nhanVien) {
        mDatabase.child("NhanVien").child(maNhanVien).setValue(nhanVien);
    }

    // Xóa nhân viên khỏi Firebase
    public void deleteNhanVien(String maNhanVien) {
        mDatabase.child("NhanVien").child(maNhanVien).removeValue();
    }

    // Lấy đơn vị từ Firebase
    public DatabaseReference getDonViReference() {
        return mDatabase.child("DonVi");
    }

    // Lấy nhân viên từ Firebase
    public DatabaseReference getNhanVienReference() {
        return mDatabase.child("NhanVien");
    }
}
