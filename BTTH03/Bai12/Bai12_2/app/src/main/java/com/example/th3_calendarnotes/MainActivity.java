package com.example.th3_calendarnotes;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    //Khai bao cac bien
    ListView lvNote;
    ArrayList<String> arrayWork;
    ArrayAdapter<String> arrayAdapter;
    EditText edtWork, edtHour, edtMinute;
    TextView lblDate;
    Button btnAddWork;

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

        lvNote = findViewById(R.id.lvNote);
        edtWork = findViewById(R.id.edtWork);
        edtHour = findViewById(R.id.edtHour);
        edtMinute = findViewById(R.id.edtMinute);
        btnAddWork = findViewById(R.id.btnAddWork);
        lblDate = findViewById(R.id.lblDate);

        //Ta khong su dung data co dinh nen khai bao mang rong
        arrayWork = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_item, arrayWork);
        lvNote.setAdapter(arrayAdapter);
        //Lay ngay gio he thong
        Date currentDate = Calendar.getInstance().getTime();
        //Format theo dinh dang dd/mm/yyyy
        java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("dd/MM/yyyy");
        //Hien thi len textView
        lblDate.setText("Hôm nay: " + simpleDateFormat.format(currentDate));
        //Click vao btn them cong viec
        btnAddWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtWork.getText().toString().equals("") || edtHour.getText().toString().equals("") || edtMinute.getText().toString().equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Nhập thiếu thông tin");
                    builder.setMessage("Nhập tất cả thông tin trước khi thêm công việc");
                    builder.setPositiveButton("Tiếp tục", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                }
                //Lay noi dung cong viec va thoi gian
                else{
                    String str = edtWork.getText().toString() + " - " + edtHour.getText().toString() + ":" + edtMinute.getText().toString();
                    //Them data cho listView
                    //update data cho mang
                    arrayWork.add(str);
                    //update lai adapter
                    arrayAdapter.notifyDataSetChanged();
                    edtHour.setText("");
                    edtMinute.setText("");
                    edtWork.setText("");
                }
            }
        });

    }
}