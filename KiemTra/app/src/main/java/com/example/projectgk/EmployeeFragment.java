package com.example.projectgk;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EmployeeFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<ListItem> itemList;
    EditText edtSearch;
    Adapter adapter;
    DataBaseHelper dataBaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employee, null);
        recyclerView = view.findViewById(R.id.recyler_view_employee);
        itemList = new ArrayList<>();
        adapter = new Adapter(getContext(), itemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        edtSearch = view.findViewById(R.id.edtSearchEmployee);

        dataBaseHelper = new DataBaseHelper();
        readEmployeeData();

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(edtSearch.getText()+"");
            }
        });

        return view;
    }

    void readEmployeeData() {
        dataBaseHelper.getEmployeeReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itemList.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Employee employee = snapshot.getValue(Employee.class);
                        if (employee != null) {
                            itemList.add(employee);
                        } else {
                            Log.d("Employee", "Employee is null for snapshot: " + snapshot.toString());
                        }
                    }
                } else {
                    Log.d("Employee", "DataSnapshot does not exist or has no children");
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Employee", "Failed to read data", databaseError.toException());
            }
        });
    }

    private void filter(String text){
        List<ListItem> filterList = new ArrayList<>();
        for(ListItem item : itemList){
            if(item.getFullName().toLowerCase().contains(text.toLowerCase())){
                filterList.add(item);
            }
        }
        adapter.filterAdapter(filterList);
    }
}