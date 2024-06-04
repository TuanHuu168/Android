package com.example.projectgk;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DonViActivity extends AppCompatActivity {
    private TextView txtUnitID;
    private TextView txtContactName;
    private TextView txtEmail;
    private TextView txtWebsite;
    private TextView txtLogo;
    private TextView txtAddress;
    private TextView txtPhoneUnit;
    private TextView txtParentUnit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_don_vi);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ các biến TextView
        txtUnitID = findViewById(R.id.txtUnitID);
        txtContactName = findViewById(R.id.txtContactName);
        txtEmail = findViewById(R.id.txtEmail);
        txtWebsite = findViewById(R.id.txtWebsite);
        txtLogo = findViewById(R.id.txtLogo);
        txtAddress = findViewById(R.id.txtAddress);
        txtPhoneUnit = findViewById(R.id.txtPhoneUnit);
        txtParentUnit = findViewById(R.id.txtParentUnit);

        DonVi donVi = (DonVi) getIntent().getSerializableExtra("donvi");
        if (donVi != null) {
            txtUnitID.setText("Mã Đơn Vị: " + donVi.getMaDonVi());
            txtContactName.setText(donVi.getTenDonVi());
            txtEmail.setText("Email: " + donVi.getEmail());
            txtWebsite.setText("Website: " + donVi.getWebsite());
            txtLogo.setText("Logo: " + donVi.getLogo());
            txtAddress.setText("Địa Chỉ: " + donVi.getDiaChi());
            txtPhoneUnit.setText("Số Điện Thoại: " + donVi.getSoDienThoai());
            txtParentUnit.setText("Mã Đơn Vị Cha: " + donVi.getMaDonViCha());
        }
    }
}