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

public class EmployeeDetailActivity extends AppCompatActivity {

    ImageView imgEmployee;
    TextView EmployeeName, EmployeeId, EmployeePosition, EmployeeMail, EmployeeNumber, UnitId;
    ImageButton btnEdit, btnDelete;

    String empId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_employee_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EmployeeName = findViewById(R.id.lblEmployeeName);
        EmployeeId = findViewById(R.id.lblEmployeeId);
        EmployeePosition = findViewById(R.id.lblEmployeePosition);
        EmployeeMail = findViewById(R.id.lblEmployeeMail);
        EmployeeNumber = findViewById(R.id.lblEmployeeNumber);
        UnitId = findViewById(R.id.lblUnitId);
        imgEmployee = findViewById(R.id.imgEmployee);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        String fullName = intent.getStringExtra("fullName");
        String empPosition = intent.getStringExtra("position");
        empId = intent.getStringExtra("employeeId");
        String empEmail = intent.getStringExtra("email");
        String empPhoneNumber = intent.getStringExtra("phoneNumber");
        String empProfilePicture = intent.getStringExtra("profilePicture");
        String empUnitId = intent.getStringExtra("unitId");

        EmployeeName.setText(fullName);
        EmployeeId.setText("Mã nhân viên: " + empId);
        EmployeePosition.setText("Chức vụ nhân viên: " + empPosition);
        EmployeeMail.setText("Email nhân viên: " + empEmail);
        EmployeeNumber.setText("SĐT nhân viên: " + empPhoneNumber);
        UnitId.setText("Mã đơn vị: " + empUnitId);

        // Lấy tên đơn vị từ Firebase
        DataBaseHelper dataBaseHelper = new DataBaseHelper();
        dataBaseHelper.getUnitReference().child(empUnitId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String unitName = dataSnapshot.child("fullName").getValue(String.class);
                    UnitId.setText("Tên đơn vị: " + unitName);
                } else {
                    UnitId.setText("Đơn vị không tồn tại");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(EmployeeDetailActivity.this, "Lỗi khi lấy tên đơn vị: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Sử dụng Glide để load ảnh
        Glide.with(this)
                .load(empProfilePicture)
                .placeholder(R.drawable.user)
                .error(R.drawable.user)
                .into(imgEmployee);

        btnEdit.setOnClickListener(v -> {
            Intent editIntent = new Intent(EmployeeDetailActivity.this, EditEmployeeActivity.class);
            editIntent.putExtra("employeeId", empId);
            editIntent.putExtra("fullName", fullName);
            editIntent.putExtra("position", empPosition);
            editIntent.putExtra("email", empEmail);
            editIntent.putExtra("phoneNumber", empPhoneNumber);
            editIntent.putExtra("profilePicture", empProfilePicture);
            editIntent.putExtra("unitId", empUnitId);
            startActivity(editIntent);
            finish();
        });

        btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(EmployeeDetailActivity.this)
                    .setTitle("Xóa nhân viên")
                    .setMessage("Bạn có chắc chắn muốn xóa nhân viên này không?")
                    .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dataBaseHelper.deleteEmployee(empId);
                            Toast.makeText(EmployeeDetailActivity.this, "Nhân viên đã được xóa", Toast.LENGTH_SHORT).show();
                            finish(); // Đóng activity sau khi xóa
                        }
                    })
                    .setNegativeButton("Không", null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        });
    }
}
