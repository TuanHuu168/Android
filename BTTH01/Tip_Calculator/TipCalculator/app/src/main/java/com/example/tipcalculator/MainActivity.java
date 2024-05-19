package com.example.tipcalculator;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    int percentValue = 15;
    Double billAmount = 0.00;
    Button decreaseBtn;
    Button increaseBtn;
    TextView editBillAmount;
    TextView valuePercent;
    TextView valueTip;
    TextView valueTotal;
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

        decreaseBtn = findViewById(R.id.btnDecrease);
        increaseBtn = findViewById(R.id.btnIncrease);
        editBillAmount = findViewById(R.id.editBillAmount);
        valuePercent = findViewById(R.id.valuePercent);
        valueTip = findViewById(R.id.valueTip);
        valueTotal = findViewById(R.id.valueTotal);

        editBillAmount.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    billAmount = Double.parseDouble(editBillAmount.getText().toString());
                    updateTip();
                    updateTotal(billAmount);
                    return true;
                }
                return false;
            }
        });

        //Giảm
        decreaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billAmount = Double.parseDouble(editBillAmount.getText().toString());
                percentValue--;
                if(percentValue < 1){
                    Toast.makeText(getApplicationContext(), "Tip không được nhỏ hơn 1", Toast.LENGTH_SHORT).show();
                    percentValue++;
                    return;
                }
                updatePercent();
                updateTip();
                updateTotal(billAmount);
            }
        });

        //Tăng
        increaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billAmount = Double.parseDouble(editBillAmount.getText().toString());
                percentValue++;
                updatePercent();
                updateTip();
                updateTotal(billAmount);
            }
        });
    }
    //set lại giá trị trong percent
    private void updatePercent() {
        valuePercent.setText(String.format("%d%%", percentValue));
    }

    private void updateTip() {
        valueTip.setText(String.format("$%.2f", billAmount*percentValue/100));
    }

    private void updateTotal(Double bill) {
        valueTotal.setText(String.format("$%.2f", billAmount*percentValue/100 + bill));
    }

}