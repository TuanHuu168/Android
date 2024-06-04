package com.example.projectgk;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText searchInput;
    private Button btnNhanVien, btnDonVi;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private DataBaseHelper dbHelper;

    private List<NhanVien> nhanVienList = new ArrayList<>();
    private List<DonVi> donViList = new ArrayList<>();
    private List<String> currentList = new ArrayList<>();
    private Map<String, Object> objectMap = new HashMap<>();  // Khai báo objectMap

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ
        searchInput = findViewById(R.id.searchInput);
        btnNhanVien = findViewById(R.id.btnNhanVien);
        btnDonVi = findViewById(R.id.btnDonVi);
        listView = findViewById(R.id.listView);

        dbHelper = new DataBaseHelper();

        loadNhanVienData();

        btnNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadNhanVienData();
            }
        });

        btnDonVi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDonViData();
            }
        });

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String key = currentList.get(position);
                Object item = objectMap.get(key);
                Intent intent;
                if (item instanceof NhanVien) {
                    intent = new Intent(MainActivity.this, NhanVienActivity.class);
                    intent.putExtra("nhanvien", (NhanVien) item);
                } else if (item instanceof DonVi) {
                    intent = new Intent(MainActivity.this, DonViActivity.class);
                    intent.putExtra("donvi", (DonVi) item);
                } else {
                    return;
                }
                startActivity(intent);
            }
        });
    }

    private void loadNhanVienData() {
        nhanVienList.clear();
        dbHelper.getNhanVienReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    NhanVien nhanVien = snapshot.getValue(NhanVien.class);
                    nhanVienList.add(nhanVien);
                }
                updateList(nhanVienList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void loadDonViData() {
        donViList.clear();
        dbHelper.getDonViReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DonVi donVi = snapshot.getValue(DonVi.class);
                    donViList.add(donVi);
                }
                updateList(donViList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void updateList(List<?> list) {
        currentList.clear();
        objectMap.clear();  // Làm sạch objectMap

        for (Object item : list) {
            String displayString;
            if (item instanceof NhanVien) {
                NhanVien nhanVien = (NhanVien) item;
                displayString = nhanVien.getHoVaTen() + " - " + nhanVien.getSoDienThoai();
                objectMap.put(displayString, nhanVien);
            } else if (item instanceof DonVi) {
                DonVi donVi = (DonVi) item;
                displayString = donVi.getTenDonVi() + " - " + donVi.getEmail();
                objectMap.put(displayString, donVi);
            } else {
                continue;
            }
            currentList.add(displayString);
        }

        adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, currentList);
        listView.setAdapter(adapter);
    }

    private void filterData(String query) {
        List<String> filteredList = new ArrayList<>();
        for (String item : currentList) {
            if (item.toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(item);
            }
        }

        adapter.clear();
        adapter.addAll(filteredList);
        adapter.notifyDataSetChanged();
    }
}
