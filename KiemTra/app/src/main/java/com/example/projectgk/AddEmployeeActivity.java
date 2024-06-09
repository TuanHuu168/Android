package com.example.projectgk;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class AddEmployeeActivity extends AppCompatActivity {

    ImageView imgEmployee;
    EditText edtEmployeeName, edtEmployeeId, edtEmployeePosition, edtEmployeeMail, edtEmployeeNumber;
    Spinner spinnerUnitId;
    Button btnAdd;

    DataBaseHelper dataBaseHelper;
    Bitmap selectedImageBitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imgEmployee = findViewById(R.id.imgEmployee);
        edtEmployeeName = findViewById(R.id.edtEmployeeName);
        edtEmployeeId = findViewById(R.id.edtEmployeeId);
        edtEmployeePosition = findViewById(R.id.edtEmployeePosition);
        edtEmployeeMail = findViewById(R.id.edtEmployeeMail);
        edtEmployeeNumber = findViewById(R.id.edtEmployeeNumber);
        spinnerUnitId = findViewById(R.id.spinnerUnitId);
        btnAdd = findViewById(R.id.btnAdd);
        dataBaseHelper = new DataBaseHelper();

        loadUnit();

        imgEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (ActivityCompat.checkSelfPermission(AddEmployeeActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AddEmployeeActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                    return;
                }
                startActivityForResult(myIntent, 99);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy dữ liệu từ các trường nhập liệu
                String employeeId = edtEmployeeId.getText().toString().trim();
                String fullName = edtEmployeeName.getText().toString().trim();
                String position = edtEmployeePosition.getText().toString().trim();
                String email = edtEmployeeMail.getText().toString().trim();
                String phoneNumber = edtEmployeeNumber.getText().toString().trim();
                String unitId = spinnerUnitId.getSelectedItem().toString();

                // Kiểm tra nếu có trường nào bị bỏ trống
                if (employeeId.isEmpty() || fullName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || unitId.isEmpty()) {
                    Toast.makeText(AddEmployeeActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (selectedImageBitmap != null) {
                    uploadImageAndSaveEmployee(employeeId, fullName, position, email, phoneNumber, unitId);
                } else {
                    saveEmployee(employeeId, fullName, position, email, phoneNumber, unitId, null);
                }
            }
        });
    }

    private void uploadImageAndSaveEmployee(final String employeeId, final String fullName, final String position, final String email, final String phoneNumber, final String unitId) {
        // Kiểm tra xem ảnh có hợp lệ không
        if (selectedImageBitmap == null) {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
            return;
        }

        // Nén ảnh và upload lên Firebase Storage
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        selectedImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        final StorageReference storageRef = FirebaseStorage.getInstance("gs://giuaky-c029c.appspot.com").getReference().child("employee_images/" + employeeId + ".jpg");
        UploadTask uploadTask = storageRef.putBytes(data);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String imageUrl = uri.toString();
                        Log.d("FirebaseStorage", "Image upload successful, URL: " + imageUrl);
                        Toast.makeText(AddEmployeeActivity.this, "Image upload successful", Toast.LENGTH_SHORT).show();
                        saveEmployee(employeeId, fullName, position, email, phoneNumber, unitId, imageUrl);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("FirebaseStorage", "Failed to get download URL: " + e.getMessage());
                        Toast.makeText(AddEmployeeActivity.this, "Failed to get download URL: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("FirebaseStorage", "Failed to upload image: " + e.getMessage());
                Toast.makeText(AddEmployeeActivity.this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void saveEmployee(String employeeId, String fullName, String position, String email, String phoneNumber, String unitId, String imageUrl) {
        // Tạo đối tượng Employee
        Employee employee = new Employee(employeeId, fullName, position, email, phoneNumber, imageUrl, unitId);

        // Thêm đối tượng Employee vào Firebase
        dataBaseHelper.addEmployee(employee);
        Toast.makeText(AddEmployeeActivity.this, "Employee added successfully", Toast.LENGTH_SHORT).show();
        finish(); // Đóng activity sau khi thêm thành công
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99 && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imgEmployee.setImageBitmap(photo);
            selectedImageBitmap = photo;
        }
    }

    private void loadUnit() {
        dataBaseHelper.getUnitReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> units = new ArrayList<>();
                units.add(""); // Thêm tùy chọn trống đầu tiên
                for (DataSnapshot unitSnapshot : dataSnapshot.getChildren()) {
                    String unitName = unitSnapshot.child("fullName").getValue(String.class);
                    units.add(unitName);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(AddEmployeeActivity.this, android.R.layout.simple_spinner_item, units);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerUnitId.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AddEmployeeActivity.this, "Failed to read data: " + databaseError.toException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
