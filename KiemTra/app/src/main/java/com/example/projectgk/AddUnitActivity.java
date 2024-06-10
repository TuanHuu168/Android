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

public class AddUnitActivity extends AppCompatActivity {

    ImageView imgUnit;
    EditText edtUnitName, edtUnitId, edtUnitMail, edtUnitWebsite, edtUnitAddress, edtUnitNumber;
    Spinner spinnerUnitId;
    Button btnAdd;

    DataBaseHelper dataBaseHelper;
    Bitmap selectedImageBitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_unit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imgUnit = findViewById(R.id.imgUnit);
        edtUnitName = findViewById(R.id.edtUnitName);
        edtUnitId = findViewById(R.id.edtUnitId);
        edtUnitMail = findViewById(R.id.edtUnitMail);
        edtUnitWebsite = findViewById(R.id.edtUnitWebsite);
        edtUnitAddress = findViewById(R.id.edtUnitAddress);
        edtUnitNumber = findViewById(R.id.edtUnitNumber);
        spinnerUnitId = findViewById(R.id.spinnerParentUnitId);
        btnAdd = findViewById(R.id.btnAdd);
        dataBaseHelper = new DataBaseHelper();

        loadUnit();

        imgUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (ActivityCompat.checkSelfPermission(AddUnitActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AddUnitActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                    return;
                }
                startActivityForResult(myIntent, 99);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy dữ liệu từ các trường nhập liệu
                String unitId = edtUnitId.getText().toString().trim();
                String fullName = edtUnitName.getText().toString().trim();
                String email = edtUnitMail.getText().toString().trim();
                String website = edtUnitWebsite.getText().toString().trim();
                String address = edtUnitAddress.getText().toString().trim();
                String phoneNumber = edtUnitNumber.getText().toString().trim();
                String parentUnitId = spinnerUnitId.getSelectedItem().toString();

                // Kiểm tra nếu có trường nào bị bỏ trống
                if (unitId.isEmpty() || fullName.isEmpty() || email.isEmpty() || address.isEmpty() || phoneNumber.isEmpty()) {
                    Toast.makeText(AddUnitActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (selectedImageBitmap != null) {
                    uploadImageAndSaveUnit(unitId, fullName, email, website, address, phoneNumber, parentUnitId);
                } else {
                    saveUnit(unitId, fullName, email, website, null, address, phoneNumber, parentUnitId);
                }
            }
        });
    }

    private void uploadImageAndSaveUnit(final String unitId, final String fullName, final String email, final String website, final String address, final String phoneNumber, final String parentUnitId) {
        // Kiểm tra xem ảnh có hợp lệ không
        if (selectedImageBitmap == null) {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
            return;
        }

        // Nén ảnh và upload lên Firebase Storage
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        selectedImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        final StorageReference storageRef = FirebaseStorage.getInstance("gs://giuaky-c029c.appspot.com").getReference().child("unit_images/" + unitId + ".jpg");
        UploadTask uploadTask = storageRef.putBytes(data);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String imageUrl = uri.toString();
                        Log.d("FirebaseStorage", "Image upload successful, URL: " + imageUrl);
                        Toast.makeText(AddUnitActivity.this, "Image upload successful", Toast.LENGTH_SHORT).show();
                        saveUnit(unitId, fullName, email, website, imageUrl, address, phoneNumber, parentUnitId);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("FirebaseStorage", "Failed to get download URL: " + e.getMessage());
                        Toast.makeText(AddUnitActivity.this, "Failed to get download URL: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("FirebaseStorage", "Failed to upload image: " + e.getMessage());
                Toast.makeText(AddUnitActivity.this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveUnit(String unitId, String fullName, String email, String website, String imageUrl, String address, String phoneNumber, String parentUnitId) {
        // Tạo đối tượng Unit
        Unit unit = new Unit(unitId, fullName, email, website, imageUrl, address, phoneNumber, parentUnitId);

        // Thêm đối tượng Unit vào Firebase
        dataBaseHelper.addUnit(unit);
        Toast.makeText(AddUnitActivity.this, "Unit added successfully", Toast.LENGTH_SHORT).show();
        finish(); // Đóng activity sau khi thêm thành công
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99 && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imgUnit.setImageBitmap(photo);
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
                ArrayAdapter<String> adapter = new ArrayAdapter<>(AddUnitActivity.this, android.R.layout.simple_spinner_item, units);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerUnitId.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AddUnitActivity.this, "Failed to read data: " + databaseError.toException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
