package com.tsi.rooprang.UserProfile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.tsi.rooprang.Account.ProfileDetailsActivity;
import com.tsi.rooprang.DTO.login.Login_DTO;
import com.tsi.rooprang.DTO.profile.Change_Pwd_DTO;
import com.tsi.rooprang.LoginSignIn.OTPActivity;
import com.tsi.rooprang.LoginSignIn.SignInActivity;
import com.tsi.rooprang.R;
import com.tsi.rooprang.Session.SessionManager;
import com.tsi.rooprang.retrofit.ApiClient;
import com.tsi.rooprang.retrofit.ApiInterface;
import com.tsi.rooprang.retrofit.NewApiClient;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    EditText et_old_pwd,et_new_pwd,et_new_cnfrm_pwd;
    Button btn_confrm_change_pwd;
    private Intent intent;
    private Context context;
    private String vOldPwd, vPassword, vConfirmPwd;
    private SessionManager sessionManager;

    Toolbar toolbar;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
        initialViews();
        findIntialViews();
        clickListeners();
    }
        private void initialViews() {
            context = this;
            //    sessionManager = new UserSession(context);
        }

        private void findIntialViews() {
            btn_confrm_change_pwd = (Button) findViewById(R.id.btn_confrm_change_pwd);
            et_old_pwd = (EditText) findViewById(R.id.et_old_pwd);
            et_new_pwd = (EditText) findViewById(R.id.et_new_pwd);
            et_new_cnfrm_pwd = (EditText) findViewById(R.id.et_new_cnfrm_pwd);

        }

        private void clickListeners() {
            btn_confrm_change_pwd.setOnClickListener(this);

        }

        private void confirm(){

            vOldPwd = et_old_pwd.getText().toString();
            vPassword = et_new_pwd.getText().toString();
            vConfirmPwd = et_new_cnfrm_pwd.getText().toString();



         if (vPassword.isEmpty()) {
             et_new_pwd.setError("Field can't be empty");

         }else if (!vConfirmPwd.equals(vPassword)) {
             et_new_cnfrm_pwd.setError("Password Doesn't match");
         }else if (vOldPwd.equals(vPassword)) {
             et_new_cnfrm_pwd.setError("your password have to be different from the old password");
         }
        else {
             ApiInterface apiService = NewApiClient.getClient().create(ApiInterface.class);
             Call<Change_Pwd_DTO> call = apiService.api_change_pwd(SessionManager.getInstance(context).get_data_from_session(SessionManager.KEY_USERID),vOldPwd, vPassword
                   );

             call.enqueue(new Callback<Change_Pwd_DTO>() {
                 @Override
                 public void onResponse(Call<Change_Pwd_DTO> call, Response<Change_Pwd_DTO> response) {
                     Log.e("success",response.body().getResult()+"");
                     Change_Pwd_DTO change_pwd_dto = response.body();
                     if (change_pwd_dto.getResult().equals("success")) {

                         Intent intent_go_back = new Intent(context, ProfileDetailsActivity.class);
                         startActivity(intent_go_back);
                     }}

                 @Override

                 public void onFailure(Call<Change_Pwd_DTO> call, Throwable t) {

                 }
             });



         }
    }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.btn_confrm_change_pwd:
                    confirm();
                    break;

            }

    }
}
