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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UnitFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<ListItem> itemList;
    EditText edtSearch;
    Adapter adapter;
    DataBaseHelper dataBaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_unit, null);
        recyclerView = view.findViewById(R.id.recyler_view_unit);
        itemList = new ArrayList<>();
        adapter = new Adapter(getContext(), itemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        edtSearch = view.findViewById(R.id.edtSearchUnit);
        dataBaseHelper = new DataBaseHelper();
        readUnitData();

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

    void readUnitData() {
        dataBaseHelper.getUnitReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itemList.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Unit unit = snapshot.getValue(Unit.class);
                        if (unit != null) {
                            itemList.add(unit);
                        } else {
                            Log.d("Unit", "Unit is null for snapshot: " + snapshot.toString());
                        }
                    }
                } else {
                    Log.d("Unit", "DataSnapshot does not exist or has no children");
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Unit", "Failed to read data", databaseError.toException());
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