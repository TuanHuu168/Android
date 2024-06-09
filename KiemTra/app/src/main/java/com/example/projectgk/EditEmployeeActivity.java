package com.example.projectgk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EditEmployeeActivity extends AppCompatActivity {

    ImageView imgEmployee;
    EditText edtEmployeeId, edtEmployeeName, edtEmployeePosition, edtEmployeeMail, edtEmployeeNumber;
    Spinner spinnerUnitId;
    Button btnUpdate;

    String empId;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_employee);

        imgEmployee = findViewById(R.id.imgEmployee);
        edtEmployeeName = findViewById(R.id.edtEmployeeName);
        edtEmployeePosition = findViewById(R.id.edtEmployeePosition);
        edtEmployeeMail = findViewById(R.id.edtEmployeeMail);
        edtEmployeeNumber = findViewById(R.id.edtEmployeeNumber);
        spinnerUnitId = findViewById(R.id.spinnerUnitId);
        btnUpdate = findViewById(R.id.btnUpdate);

        dataBaseHelper = new DataBaseHelper();

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        empId = intent.getStringExtra("employeeId");
        String fullName = intent.getStringExtra("fullName");
        String empPosition = intent.getStringExtra("position");
        String empEmail = intent.getStringExtra("email");
        String empPhoneNumber = intent.getStringExtra("phoneNumber");
        String empProfilePicture = intent.getStringExtra("profilePicture");
        String empUnitId = intent.getStringExtra("unitId");


        edtEmployeeName.setText(fullName);
        edtEmployeePosition.setText(empPosition);
        edtEmployeeMail.setText(empEmail);
        edtEmployeeNumber.setText(empPhoneNumber);
        // Set spinner value based on empUnitId...

        // Sử dụng Glide để load ảnh
        Glide.with(this)
                .load(empProfilePicture)
                .placeholder(R.drawable.user)
                .error(R.drawable.user)
                .into(imgEmployee);

        loadUnitsAndSetSpinner(empUnitId);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedName = edtEmployeeName.getText().toString().trim();
                String updatedPosition = edtEmployeePosition.getText().toString().trim();
                String updatedEmail = edtEmployeeMail.getText().toString().trim();
                String updatedPhoneNumber = edtEmployeeNumber.getText().toString().trim();
                String updatedUnitId = spinnerUnitId.getSelectedItem().toString();

                if (updatedName.isEmpty() || updatedPosition.isEmpty() || updatedEmail.isEmpty() || updatedPhoneNumber.isEmpty() || updatedUnitId.isEmpty()) {
                    Toast.makeText(EditEmployeeActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                Employee updatedEmployee = new Employee(empId, updatedName, updatedPosition, updatedEmail, updatedPhoneNumber, empProfilePicture, updatedUnitId);
                dataBaseHelper.updateEmployee(empId, updatedEmployee);
                Toast.makeText(EditEmployeeActivity.this, "Employee updated successfully", Toast.LENGTH_SHORT).show();
                finish(); // Đóng activity sau khi lưu
            }
        });
    }private void loadUnitsAndSetSpinner(String selectedUnitId) {
        dataBaseHelper.getUnitReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> units = new ArrayList<>();
                for (DataSnapshot unitSnapshot : dataSnapshot.getChildren()) {
                    String unitName = unitSnapshot.child("fullName").getValue(String.class);
                    units.add(unitName);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(EditEmployeeActivity.this, android.R.layout.simple_spinner_item, units);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerUnitId.setAdapter(adapter);

                // Đặt giá trị đã chọn cho spinner
                if (selectedUnitId != null) {
                    int spinnerPosition = adapter.getPosition(selectedUnitId);
                    spinnerUnitId.setSelection(spinnerPosition);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(EditEmployeeActivity.this, "Failed to read data: " + databaseError.toException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}