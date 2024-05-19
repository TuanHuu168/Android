package com.example.th1_dialog;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

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


    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder b =new
                AlertDialog.Builder(MainActivity.this);
        b.setTitle("Thoát ứng dụng");
        b.setMessage("Bạn thực sự muốn thoát ứng dụng?");
        b.setPositiveButton("Đúng vậy", new DialogInterface.
                OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                finish();
            }});
        b.setNegativeButton("Ôi không đâu", new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.cancel();
                    }
                });
        AlertDialog dialog = b.create();
        dialog.show();

        // Tim kiem cac nut
        Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        Button negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);

        // Dat mau chu va do bong cho cac nut
        positiveButton.setTextColor(Color.parseColor("#50CAFF"));
        positiveButton.setTextSize(16.0f);
        positiveButton.setShadowLayer(7.0f, 0.0f, 8.0f, Color.parseColor("#80808080"));

        negativeButton.setTextColor(Color.parseColor("#FFA5BB"));
        negativeButton.setTextSize(16.0f);
        negativeButton.setShadowLayer(7.0f, 0.0f, 8.0f, Color.parseColor("#80808080"));
    }
}