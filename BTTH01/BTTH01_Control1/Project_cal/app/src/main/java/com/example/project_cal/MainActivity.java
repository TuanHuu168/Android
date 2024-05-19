package com.example.project_cal;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText edA, edB, edResult;
    Button btnPlus, btnMinus, btnMulti, btnDiv, btnMemClass;
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

        edA = findViewById(R.id.edA);
        edB = findViewById(R.id.edB);
        edResult = findViewById(R.id.edResult);

        btnPlus = findViewById(R.id.btnPlus);
        btnMinus = findViewById(R.id.btnMinus);
        btnMulti = findViewById(R.id.btnMulti);
        btnDiv = findViewById(R.id.btnDiv);
        btnMemClass = findViewById(R.id.btnClass);

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = Integer.parseInt("0"+edA.getText());
                int b = Integer.parseInt("0"+edB.getText());
                edResult.setText("a + b = " +(a+b));
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = Integer.parseInt("0"+edA.getText());
                int b = Integer.parseInt("0"+edB.getText());
                edResult.setText("a - b = " +(a-b));
            }
        });

        btnMulti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = Integer.parseInt("0"+edA.getText());
                int b = Integer.parseInt("0"+edB.getText());
                edResult.setText("a * b = " +(a*b));
            }
        });

        btnDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = Integer.parseInt("0"+edA.getText());
                int b = Integer.parseInt("0"+edB.getText());
                if (b == 0)
                {
                    edResult.setText("B phai khac 0");
                }
                else
                {
                    edResult.setText("a / b = " +(a/b));
                }
            }
        });
    }


}