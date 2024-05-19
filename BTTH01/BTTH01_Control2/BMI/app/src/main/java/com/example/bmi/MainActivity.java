package com.example.bmi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    EditText edtName, edtHeight, edtWeight, edtBMI, edtDiagnostic;
    Button btnBMI;

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

        edtName = (EditText) findViewById(R.id.edtName);
        edtHeight = (EditText) findViewById(R.id.edtHeight);
        edtWeight = (EditText) findViewById(R.id.edtWeight);
        edtBMI = (EditText) findViewById(R.id.edtBMI);
        edtDiagnostic = (EditText) findViewById(R.id.edtDiagnostic);

        btnBMI = (Button) findViewById(R.id.btnBMI);

        btnBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double height = Double.parseDouble(edtHeight.getText()+"");
                double weight = Double.parseDouble(edtWeight.getText()+"");
                double BMI = weight/(height*height);
                String diagnostic = "";

                if(BMI < 18){
                    diagnostic = "Người gầy";
                }
                else if(BMI < 25){
                    diagnostic = "Người bình thường";
                }
                else if(BMI < 30){
                    diagnostic = "Người béo cấp độ I";
                }
                else if(BMI < 35){
                    diagnostic = "Người béo cấp độ II";
                }
                else{
                    diagnostic = "Người béo cấp độ III";
                }

                DecimalFormat dcf = new DecimalFormat("#.00");
                edtBMI.setText(dcf.format(BMI));
                edtDiagnostic.setText(diagnostic);
            }
        });
    }
}