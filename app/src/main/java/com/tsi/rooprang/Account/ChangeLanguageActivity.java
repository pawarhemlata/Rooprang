package com.tsi.rooprang.Account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tsi.rooprang.Activity.MainActivity;
import com.tsi.rooprang.Activity.SplashActivity;
import com.tsi.rooprang.R;
import com.tsi.rooprang.Session.SessionManager;
import com.tsi.rooprang.utils.LocaleHelper;

public class ChangeLanguageActivity extends AppCompatActivity {
    private Context context;
    private RadioGroup radiogroup_language;
    private RadioButton radio_btn_english, radio_btn_hindi, radio_btn_gujrati, radio_btn_language;
    private int selectedId;
    Resources resources;
    TextView nav_home;
    private String mLanguageCode = "en";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_language);

        context = this;
        initViews();
    }

    private void initViews() {
        radiogroup_language = findViewById(R.id.radiogroup_language);
        radio_btn_english = findViewById(R.id.radio_btn_english);
        radio_btn_hindi = findViewById(R.id.radio_btn_hindi);




//        LocaleHelper.setApplicationLanguage(context,SessionManager.getInstance(context).get_data_from_session(SessionManager.KEY_LANGUAGE));

        if (SessionManager.getInstance(context).get_data_from_session(SessionManager.KEY_LANGUAGE).equalsIgnoreCase("en")) {
            radio_btn_english.setChecked(true);
            radio_btn_hindi.setChecked(false);
        } else {
            radio_btn_hindi.setChecked(true);
            radio_btn_english.setChecked(false);
        }


        radiogroup_language.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checkedId = group.getCheckedRadioButtonId();
                radio_btn_language = (RadioButton) findViewById(checkedId);

                Log.e("checkedId", checkedId + "");

                if (radio_btn_language != null) {
                    switch (checkedId) {
                        case R.id.radio_btn_english:
                            mLanguageCode="en";
//                            LocaleHelper.setApplicationLanguage(context,mLanguageCode);
                            SessionManager.getInstance(context).set_data_in_session(SessionManager.KEY_LANGUAGE,mLanguageCode);

                            Toast.makeText(context, "" + radio_btn_language.getText(), Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.radio_btn_hindi:
                            mLanguageCode="hi";
//                            LocaleHelper.setApplicationLanguage(context,mLanguageCode);
                            SessionManager.getInstance(context).set_data_in_session(SessionManager.KEY_LANGUAGE,mLanguageCode);

                            Toast.makeText(context, "" + radio_btn_language.getText(), Toast.LENGTH_SHORT).show();
                            break;

                    }

                }

                recreate();


                Intent i = new Intent(context, MainActivity.class);
                startActivity(i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK));


            }
        });


    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

}
