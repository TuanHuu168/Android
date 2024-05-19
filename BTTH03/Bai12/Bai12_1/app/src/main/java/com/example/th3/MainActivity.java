package com.example.th3;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    TextView lblPosition;

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

        lblPosition = findViewById(R.id.lblPosition);
        // Khoi tao datasource
        final String phoneList[] = {"Iphone 7 Plus", "Xiaomi Note 11T Pro", "Samsung Galaxy J2", "Vivo X80", "Samsung Galaxy Z Flip4", "Samsung Galaxy S22 Ultra 5G", "Huawei Nova 3i", "Huawei P30"};
        // Khai bao adapter va gan datasource vao array adapter
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, phoneList);
        // Khai bao listView va dua data vao listView
        ListView lvProduct = findViewById(R.id.lvProduct);
        lvProduct.setAdapter(adapter1);
        //Viet su kien khi click vao 1 lua chon trong listView
        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lblPosition.setText("Vi tri: " + position + ": " + phoneList[position]);
            }
        });
    }
}