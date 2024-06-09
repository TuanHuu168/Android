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

public class EditUnitActivity extends AppCompatActivity {

    ImageView imgUnit;
    EditText edtUnitName, edtUnitMail, edtUnitWebsite, edtUnitAddress, edtUnitNumber;
    Spinner spinnerParentUnitId;
    Button btnUpdate;

    String unitId, unitProfilePicture;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_unit);

        imgUnit = findViewById(R.id.imgUnit);
        edtUnitName = findViewById(R.id.edtUnitName);
        edtUnitMail = findViewById(R.id.edtUnitMail);
        edtUnitWebsite = findViewById(R.id.edtUnitWebsite);
        edtUnitAddress = findViewById(R.id.edtUnitAddress);
        edtUnitNumber = findViewById(R.id.edtUnitNumber);
        spinnerParentUnitId = findViewById(R.id.spinnerParentUnitId);
        btnUpdate = findViewById(R.id.btnUpdate);

        dataBaseHelper = new DataBaseHelper();

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        unitId = intent.getStringExtra("unitId");
        String fullName = intent.getStringExtra("fullName");
        String unitEmail = intent.getStringExtra("email");
        String unitWebsite = intent.getStringExtra("website");
        String unitAddress = intent.getStringExtra("address");
        String unitPhoneNumber = intent.getStringExtra("phoneNumber");
        unitProfilePicture = intent.getStringExtra("profilePicture");
        String unitParentUnitId = intent.getStringExtra("parentUnitId");

        edtUnitName.setText(fullName);
        edtUnitMail.setText(unitEmail);
        edtUnitWebsite.setText(unitWebsite);
        edtUnitAddress.setText(unitAddress);
        edtUnitNumber.setText(unitPhoneNumber);

        // Sử dụng Glide để load ảnh
        Glide.with(this)
                .load(unitProfilePicture)
                .placeholder(R.drawable.user)
                .error(R.drawable.user)
                .into(imgUnit);

        loadUnitsAndSetSpinner(unitParentUnitId);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedName = edtUnitName.getText().toString().trim();
                String updatedEmail = edtUnitMail.getText().toString().trim();
                String updatedWebsite = edtUnitWebsite.getText().toString().trim();
                String updatedAddress = edtUnitAddress.getText().toString().trim();
                String updatedPhoneNumber = edtUnitNumber.getText().toString().trim();
                String updatedParentUnitId = spinnerParentUnitId.getSelectedItem().toString();

                if (updatedName.isEmpty() || updatedEmail.isEmpty() || updatedWebsite.isEmpty() || updatedAddress.isEmpty() || updatedPhoneNumber.isEmpty() || updatedParentUnitId.isEmpty()) {
                    Toast.makeText(EditUnitActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                Unit updatedUnit = new Unit(unitId, updatedName, updatedEmail, updatedWebsite, unitProfilePicture, updatedAddress, updatedPhoneNumber, updatedParentUnitId);
                dataBaseHelper.updateUnit(unitId, updatedUnit);
                Toast.makeText(EditUnitActivity.this, "Unit updated successfully", Toast.LENGTH_SHORT).show();
                finish(); // Đóng activity sau khi lưu
            }
        });
    }

    private void loadUnitsAndSetSpinner(String selectedUnitId) {
        dataBaseHelper.getUnitReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> units = new ArrayList<>();
                for (DataSnapshot unitSnapshot : dataSnapshot.getChildren()) {
                    String unitName = unitSnapshot.child("fullName").getValue(String.class);
                    units.add(unitName);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(EditUnitActivity.this, android.R.layout.simple_spinner_item, units);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerParentUnitId.setAdapter(adapter);

                // Đặt giá trị đã chọn cho spinner
                if (selectedUnitId != null) {
                    int spinnerPosition = adapter.getPosition(selectedUnitId);
                    spinnerParentUnitId.setSelection(spinnerPosition);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(EditUnitActivity.this, "Failed to read data: " + databaseError.toException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
