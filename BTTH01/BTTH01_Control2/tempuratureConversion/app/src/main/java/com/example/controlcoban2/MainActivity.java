package com.example.controlcoban2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    EditText edFahrenheit, edCelcius;
    Button btnFC, btnCF, btnClear;
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

        edFahrenheit = (EditText) findViewById(R.id.edFahrenheit);
        edCelcius = (EditText) findViewById(R.id.edCelcius);
        btnCF = (Button) findViewById(R.id.btnCF);
        btnFC = (Button) findViewById(R.id.btnFC);
        btnClear = (Button) findViewById(R.id.btnClear);

        btnCF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DecimalFormat dcf = new DecimalFormat("#.00");
                String CString = edCelcius.getText()+"";
                if(CString.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập độ F", Toast.LENGTH_SHORT).show();
                }
                else{
                    int C = Integer.parseInt(CString);
                    edFahrenheit.setText(""+dcf.format(C*1.8+32));
                }

            }
        });

        btnFC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DecimalFormat dcf = new DecimalFormat("#.00");
                String FString = edFahrenheit.getText()+"";
                if(FString.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập độ C", Toast.LENGTH_SHORT).show();
                }
                else{
                    int F = Integer.parseInt(FString);
                    edCelcius.setText(""+dcf.format((F-32)/1.8));
                }

            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edFahrenheit.setText("");
                edCelcius.setText("");
            }
        });
    }
}