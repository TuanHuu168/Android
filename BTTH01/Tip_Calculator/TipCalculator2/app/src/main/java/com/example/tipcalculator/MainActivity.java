package com.example.tipcalculator;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    //Khai bao bien
    private TextView lblBillAmount;
    private EditText edBillAmount;
    private TextView lblTipPercent;
    private TextView lblPercent;
    private Button btnPercentDecrease;
    private Button btnPercentIncrease;
    private TextView lblTip;
    private TextView valTip;
    private TextView lblTotal;
    private TextView valTotal;


    private String billAmountString = "";
    private float tipPercent = 0.15f;

    private SharedPreferences savedValues;
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
        //Lien ket cac bien voi UI
        lblBillAmount = findViewById(R.id.lblBillAmount);
        edBillAmount = findViewById(R.id.edBillAmount);
        lblTipPercent = findViewById(R.id.lblTipPercent);
        lblPercent = findViewById(R.id.lblPercent);
        btnPercentDecrease = findViewById(R.id.btnPercentDecrease);
        btnPercentIncrease = findViewById(R.id.btnPercentIncrease);
        lblTip = findViewById(R.id.lblTip);
        valTip = findViewById(R.id.valTip);
        lblTotal = findViewById(R.id.lblTotal);
        valTotal = findViewById(R.id.valTotal);

        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);

        //Su kien lang nghe
        edBillAmount.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //Khi nguoi dung an DONE hoac khi khong co hanh dong cu the nao
                if(actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_UNSPECIFIED){
                    calculateAndDisplay();
                }
                return false;
            }
        });

        btnPercentDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tipPercent <= 0.01f){
                    Toast.makeText(MainActivity.this, "Tip khong the nho hon 0", Toast.LENGTH_SHORT).show();
                    return;
                }
                tipPercent -= 0.01f;
                calculateAndDisplay();
            }
        });

        btnPercentIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipPercent += 0.01f;
                calculateAndDisplay();
            }
        });
    }

    @Override
    public void onPause() {
        //Luu gia tri vao SharePreferences
        SharedPreferences.Editor editor = savedValues.edit();
        editor.putString("billAmountString", billAmountString);
        editor.putFloat("tipPercent", tipPercent);
        editor.commit();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        //Load du lieu
        billAmountString = savedValues.getString("billAmountString", "");
        tipPercent = savedValues.getFloat("tipPercent", 0.15f);
        //Gan gia tri vao editText
        edBillAmount.setText(billAmountString);
        //Hien thi lai ket qua
        calculateAndDisplay();
    }

    public void calculateAndDisplay(){
        //Lay gia tri bill
        billAmountString = edBillAmount.getText().toString();
        float billAmount;
        if(billAmountString.equals("")){
            billAmount = 0;
        }
        else{
            billAmount = Float.parseFloat(billAmountString);
        }

        //Tinh tip va tong
        float tipAmount = billAmount * tipPercent;
        float totalAmount = billAmount + tipAmount;

        //Hien thi ket qua
        NumberFormat currency = NumberFormat.getCurrencyInstance(); //Lay dinh dang tien te dia phuong
        valTip.setText(currency.format(tipAmount));
        valTotal.setText(currency.format(totalAmount));

        //Lay dinh dang %
        NumberFormat percent = NumberFormat.getPercentInstance();
        lblPercent.setText(percent.format(tipPercent));
    }
}