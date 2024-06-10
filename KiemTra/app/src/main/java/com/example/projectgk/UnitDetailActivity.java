package com.example.projectgk;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class UnitDetailActivity extends AppCompatActivity {

    ImageView imgUnit;
    TextView UnitName, UnitId, UnitMail, UnitWebsite, UnitAddress, UnitNumber, ParentUnitId;
    ImageButton btnEdit, btnDelete;

    String unitId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_unit_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        UnitName = findViewById(R.id.lblUnitName);
        UnitId = findViewById(R.id.lblUnitId);
        UnitMail = findViewById(R.id.lblUnitEmail);
        UnitWebsite = findViewById(R.id.lblUnitWebsite);
        UnitAddress = findViewById(R.id.lblUnitAddress);
        UnitNumber = findViewById(R.id.lblUnitNumber);
        ParentUnitId = findViewById(R.id.lblParentUnitId);
        imgUnit = findViewById(R.id.imgUnit);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        String fullName = intent.getStringExtra("fullName");
        unitId = intent.getStringExtra("unitId");
        String unitEmail = intent.getStringExtra("email");
        String unitWebsite = intent.getStringExtra("website");
        String unitAddress = intent.getStringExtra("address");
        String unitPhoneNumber = intent.getStringExtra("phoneNumber");
        String unitProfilePicture = intent.getStringExtra("profilePicture");
        String unitParentUnitId = intent.getStringExtra("parentUnitId");

        UnitName.setText(fullName);
        UnitId.setText("Mã đơn vị: " + unitId);
        UnitMail.setText("Email đơn vị: " + unitEmail);
        UnitWebsite.setText("Website đơn vị: " + unitWebsite);
        UnitAddress.setText("Địa chỉ đơn vị: " + unitAddress);
        UnitNumber.setText("SĐT đơn vị: " + unitPhoneNumber);
        ParentUnitId.setText("Tên đơn vị cha: " + unitParentUnitId);

        // Lấy tên đơn vị từ Firebase
        DataBaseHelper dataBaseHelper = new DataBaseHelper();
        dataBaseHelper.getUnitReference().child(unitParentUnitId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String unitName = dataSnapshot.child("fullName").getValue(String.class);
                    ParentUnitId.setText("Tên đơn vị cha: " + unitName);
                } else {
                    ParentUnitId.setText("Không có đơn vị cha");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(UnitDetailActivity.this, "Lỗi khi lấy tên đơn vị: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Sử dụng Glide để load ảnh
        Glide.with(this)
                .load(unitProfilePicture)
                .placeholder(R.drawable.user)
                .error(R.drawable.user)
                .into(imgUnit);

        btnEdit.setOnClickListener(v -> {
            Intent editIntent = new Intent(UnitDetailActivity.this, EditUnitActivity.class);
            editIntent.putExtra("unitId", unitId);
            editIntent.putExtra("fullName", fullName);
            editIntent.putExtra("email", unitEmail);
            editIntent.putExtra("website", unitWebsite);
            editIntent.putExtra("address", unitAddress);
            editIntent.putExtra("phoneNumber", unitPhoneNumber);
            editIntent.putExtra("profilePicture", unitProfilePicture);
            editIntent.putExtra("parentUnitId", unitParentUnitId);
            startActivity(editIntent);
        });

        btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(UnitDetailActivity.this)
                    .setTitle("Xóa đơn vị")
                    .setMessage("Bạn có chắc chắn muốn xóa đơn vị này không?")
                    .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            DataBaseHelper dataBaseHelper = new DataBaseHelper();
                            dataBaseHelper.deleteUnit(unitId);
                            Toast.makeText(UnitDetailActivity.this, "Đơn vị đã được xóa", Toast.LENGTH_SHORT).show();
                            finish(); // Đóng activity sau khi xóa
                        }
                    })
                    .setNegativeButton("Không", null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        });
    }

}
