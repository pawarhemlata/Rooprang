package com.tsi.rooprang.Account;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.tsi.rooprang.Fragments.SavedCardBottomFragment;
import com.tsi.rooprang.R;

public class SavedCards_Activity extends AppCompatActivity implements SavedCardBottomFragment.BottomSheetListener {

    Toolbar tool;
    TextView button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savedcards);
        tool = findViewById(R.id.toolbar);
        tool.setTitle("Stored cards");
        tool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        button = findViewById(R.id.fabbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavedCardBottomFragment bottomSheet = new SavedCardBottomFragment();
                bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");
            }
        });



    }

    @Override
    public void onButtonClicked(String text) {

    }
}
