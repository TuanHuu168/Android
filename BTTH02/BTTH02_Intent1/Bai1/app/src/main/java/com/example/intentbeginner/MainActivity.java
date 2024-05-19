package com.example.intentbeginner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    //B1. Cac thanh phan can dieu khien
    private Button btnOpenChild;
    private TextView txtRabbit;
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
        //B2. Tham chieu bien toi phan tu giao dien
        btnOpenChild = (Button) findViewById(R.id.btnChild);
        txtRabbit = (TextView) findViewById(R.id.txtRabbit);

        //B3. Xu ly su kien
        btnOpenChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, SubActivity.class);
                String myString = txtRabbit.getText().toString();
                Bundle myBundle = new Bundle();
                myBundle.putString("rabbit", myString);
                myIntent.putExtra("myRabbit", myBundle);
                startActivity(myIntent);
            }
        });

    }
}