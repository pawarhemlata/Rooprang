package com.tsi.rooprang.Account;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.tsi.rooprang.R;

public class Rate_Activity extends AppCompatActivity {
    Toolbar tool;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        tool = findViewById(R.id.toolbar);
        tool.setTitle("Rate_Activity");
        tool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
