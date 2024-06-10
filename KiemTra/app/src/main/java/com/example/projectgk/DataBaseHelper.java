package com.example.projectgk;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DataBaseHelper {
    private DatabaseReference mDatabase;

    public DataBaseHelper() {
        mDatabase = FirebaseDatabase.getInstance("https://giuaky-c029c-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
    }

    // Thêm đơn vị mới vào Firebase
    public void addUnit(Unit unit) {
        mDatabase.child("Unit").child(unit.getUnitId()).setValue(unit);
    }

    // Sửa thông tin đơn vị trong Firebase
    public void updateUnit(String unitId, Unit unit) {
        mDatabase.child("Unit").child(unitId).setValue(unit);
    }

    // Xóa đơn vị khỏi Firebase
    public void deleteUnit(String unitId) {
        mDatabase.child("Unit").child(unitId).removeValue();
    }

    // Thêm nhân viên mới vào Firebase
    public void addEmployee(Employee employee) {
        mDatabase.child("Employee").child(employee.getEmployeeId()).setValue(employee);
    }

    // Sửa thông tin nhân viên trong Firebase
    public void updateEmployee(String employeeId, Employee employee) {
        mDatabase.child("Employee").child(employeeId).setValue(employee);
    }

    // Xóa nhân viên khỏi Firebase
    public void deleteEmployee(String employeeId) {
        mDatabase.child("Employee").child(employeeId).removeValue();
    }

    // Lấy đơn vị từ Firebase
    public DatabaseReference getUnitReference() {
        return mDatabase.child("Unit");
    }

    // Lấy nhân viên từ Firebase
    public DatabaseReference getEmployeeReference() {
        return mDatabase.child("Employee");
    }
}
