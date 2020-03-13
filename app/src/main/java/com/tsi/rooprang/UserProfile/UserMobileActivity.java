package com.tsi.rooprang.UserProfile;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.tsi.rooprang.R;

public class UserMobileActivity extends AppCompatActivity {
    Toolbar tool;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_mobile);


        tool = findViewById(R.id.toolbar);
        tool.setTitle("Mobile number");
        tool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
