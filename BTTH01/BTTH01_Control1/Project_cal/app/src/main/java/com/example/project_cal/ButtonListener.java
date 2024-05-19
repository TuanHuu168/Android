package com.example.project_cal;

import android.view.View;
import android.widget.Toast;

public class ButtonListener implements View.OnClickListener{
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnClass){
            Toast.makeText(v.getContext(), "Xin chao", Toast.LENGTH_SHORT);
        }
    }
}
