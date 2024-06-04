package com.example.projectgk;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class NhanVienActivity extends AppCompatActivity {
    private TextView txtUserID;
    private TextView txtContactName;
    private TextView txtJob;
    private TextView txtEmail;
    private TextView txtPhoneNumber;
    private TextView txtWorkID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_nhan_vien);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ các biến TextView
        txtUserID = findViewById(R.id.txtUserID);
        txtContactName = findViewById(R.id.txtContactName);
        txtJob = findViewById(R.id.txtJob);
        txtEmail = findViewById(R.id.txtEmail);
        txtPhoneNumber = findViewById(R.id.txtPhoneNumber);
        txtWorkID = findViewById(R.id.txtWorkID);

        NhanVien nhanVien = (NhanVien) getIntent().getSerializableExtra("nhanvien");
        if (nhanVien != null) {
            txtUserID.setText("Mã Nhân Viên: " + nhanVien.getMaNhanVien());
            txtContactName.setText(nhanVien.getHoVaTen());
            txtJob.setText("Chức Vụ: " + nhanVien.getChucVu());
            txtEmail.setText("Email: " + nhanVien.getEmail());
            txtPhoneNumber.setText("Số Điện Thoại: " + nhanVien.getSoDienThoai());
            txtWorkID.setText("Mã Đơn Vị: " + nhanVien.getMaDonVi());
        }
    }
}