package com.example.studentmanagement;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Khai báo các biến
    EditText edtClassID, edtClassName, edtClassSize;
    Button btnAdd, btnDel, btnEdit;

    //Khai bo listView
    ListView lvClass;

    ArrayList<String> myList;
    ArrayAdapter<String> myAdapter;
    SQLiteDatabase myDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtClassID = findViewById(R.id.edtClassID);
        edtClassName = findViewById(R.id.edtClassName);
        edtClassSize = findViewById(R.id.edtClassSize);

        btnAdd = findViewById(R.id.btnAdd);
        btnDel = findViewById(R.id.btnDel);
        btnEdit = findViewById(R.id.btnEdit);

        lvClass = findViewById(R.id.lvClass);
        myList = new ArrayList<>();
        myAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myList);
        lvClass.setAdapter(myAdapter);

        // Tạo và mở database Sqlite
        myDatabase = openOrCreateDatabase("qlSinhVien.db", MODE_PRIVATE, null);

        // Tạo table chứa database
        try{
            String sql = "CREATE TABLE Class(classID TEXT PRIMARY KEY, className TEXT, classSize INTEGER)";
            myDatabase.execSQL(sql);
        }
        catch (Exception e){
            Log.e("Error", "Table is existing");
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String classID = edtClassID.getText()+"";
                String className = edtClassName.getText()+"";
                String classSize = edtClassSize.getText()+"";

                // Kiểm tra xem classID và className có trống không
                if (classID.isEmpty() || className.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Class ID and Class Name cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra xem classSize có phải là số không
                int cSize;
                try {
                    cSize = Integer.parseInt(classSize);
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Class Size must be a number", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra xem classID đã tồn tại chưa
                if (isRecordExist(classID, className, cSize, false)) {
                    Toast.makeText(MainActivity.this, "This record is already exists", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Tạo 1 tập hợp lưu trữ key và value. Thường được dùng để truyền data vào và ra khỏi db
                ContentValues myValue = new ContentValues();
                myValue.put("classID", classID);
                myValue.put("className", className);
                myValue.put("classSize", cSize);

                String msg = "";
                if(myDatabase.insert("Class", null, myValue) == -1){
                    msg = "Fail to insert record";
                }
                else{
                    msg = "Insert record successfully";
                }
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                loadData();
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String classID = edtClassID.getText()+"";

                // n là số hàng đã bị xóa khỏi bảng
                int n = myDatabase.delete("Class", "classID = ?", new String[]{classID});
                String msg = "";
                if(n == 0){
                    msg = "No record to delete";
                }
                else{
                    msg = "Delete completed";
                }
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                loadData();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String classID = edtClassID.getText()+"";
                String className = edtClassName.getText()+"";
                String classSize = edtClassSize.getText()+"";

                // Kiểm tra xem classID và className có trống không
                if (classID.isEmpty() || className.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Class ID and Class Name cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra xem classSize có phải là số không
                int cSize;
                try {
                    cSize = Integer.parseInt(classSize);
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Class Size must be a number", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra xem thông tin đã tồn tại chưa
                if (isRecordExist(classID, className, cSize, true)) {
                    Toast.makeText(MainActivity.this, "This record is already exists", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Tạo 1 tập hợp lưu trữ key và value. Thường được dùng để truyền data vào và ra khỏi db
                ContentValues myValue = new ContentValues();
                myValue.put("className", className);
                myValue.put("classSize", cSize);

                int n = myDatabase.update("Class", myValue, "classID = ?", new String[]{classID});

                String msg = "";
                if(n == 0){
                    msg = "No record to update";
                }
                else{
                    msg = "Update completed";
                }
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                loadData();
            }
        });
        loadData();
    }

    private void loadData(){
        myList.clear();
        Cursor cusor = myDatabase.query("Class", null, null, null, null, null, null);
        cusor.moveToFirst();
        String data = "";
        while (cusor.isAfterLast() == false){
            data = cusor.getString(0) + " - " + cusor.getString(1) + " - " + cusor.getString(2);
            cusor.moveToNext();
            myList.add(data);
        }
        cusor.close();
        myAdapter.notifyDataSetChanged();
    }

    private boolean isRecordExist(String classID, String className, int classSize, boolean checkAll) {
        String sql = "";
        String[] selectionArgs;
        if (checkAll) {
            // Kiểm tra tất cả các thông tin
            sql = "SELECT COUNT(*) FROM Class WHERE classID = ? AND className = ? AND classSize = ?";
            selectionArgs = new String[]{classID, className, String.valueOf(classSize)};
        } else {
            // Chỉ kiểm tra classID
            sql = "SELECT COUNT(*) FROM Class WHERE classID = ?";
            selectionArgs = new String[]{classID};
        }

        Cursor cursor = myDatabase.rawQuery(sql, selectionArgs);
        if (cursor != null) {
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            cursor.close();
            return count > 0;
        }
        return false;
    }
}