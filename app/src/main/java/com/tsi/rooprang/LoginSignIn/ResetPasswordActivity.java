package com.tsi.rooprang.LoginSignIn;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.tsi.rooprang.Account.ProfileDetailsActivity;
import com.tsi.rooprang.Activity.ProductActivity;
import com.tsi.rooprang.DTO.login.ResetPwd_DTO;
import com.tsi.rooprang.DTO.profile.Change_Pwd_DTO;
import com.tsi.rooprang.LoginSignIn.SignInActivity;
import com.tsi.rooprang.R;
import com.tsi.rooprang.Session.SessionManager;
import com.tsi.rooprang.retrofit.ApiClient;
import com.tsi.rooprang.retrofit.ApiInterface;
import com.tsi.rooprang.utils.Utility;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    EditText passwordedit,cnfrmpasswordedit;
    Button change_pwd;
    private Intent intent;
    ProgressDialog mProgressDialog;
    private Context context;
    private String vPassword, vConfirmPwd;
    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pwd);
        mProgressDialog = new ProgressDialog(this);

        initialViews();
        findIntialViews();

        clickListeners();
    }
        private void initialViews() {
            context = this;
            //    sessionManager = new UserSession(context);
        }

        private void findIntialViews() {
            change_pwd = (Button) findViewById(R.id.change_pwd);

            passwordedit = (EditText) findViewById(R.id.pwd);
            cnfrmpasswordedit = (EditText) findViewById(R.id.cnfrm_pwd);




        }

        private void clickListeners() {
            change_pwd.setOnClickListener(this);

        }

        private void confirm(){

            vPassword = passwordedit.getText().toString();
            vConfirmPwd = cnfrmpasswordedit.getText().toString();



         if (vPassword.isEmpty()) {
        passwordedit.setError("Field can't be empty");


    }
        else if (!vConfirmPwd.equals(vPassword)) {
        cnfrmpasswordedit.setError("Password Doesn't match");
    }
        else {

             intent = new Intent(context, SignInActivity.class);
             startActivity(intent);

         }

            try {
                if (Utility.isConnectingToInternet(ResetPasswordActivity.this)) {

                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                    mProgressDialog.setIndeterminate(true);
                    mProgressDialog.setMessage("Loading...");
                    mProgressDialog.show();
                    Call<ResetPwd_DTO> resetPwd_dtoCall = apiService.api_reset_pwd(SessionManager.getInstance(context).get_data_from_session(SessionManager.KEY_USERID), vPassword
                    );
                    resetPwd_dtoCall.enqueue(new Callback<ResetPwd_DTO>() {
                        @Override
                        public void onResponse(Call<ResetPwd_DTO> call, Response<ResetPwd_DTO> response) {
                            if (mProgressDialog.isShowing()) {
                                mProgressDialog.dismiss();
                            }
                            Log.e("success", response.body().getResult() + "");
                            ResetPwd_DTO change_pwd_dto = response.body();
                            if (change_pwd_dto.getResult().equals("success")) {
                                Intent intent_go_back = new Intent(context, SignInActivity.class);
                                startActivity(intent_go_back);
                            }
                        }

                        @Override
                        public void onFailure(Call<ResetPwd_DTO> call, Throwable t) {
                            if (mProgressDialog.isShowing()) {
                                mProgressDialog.dismiss();
                            }
                        }


                    });
                }else {
                    Toast.makeText(ResetPasswordActivity.this, "Please Connect Internet !", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }




    }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {


                case R.id.change_pwd:
                    confirm();
                    break;




            }

    }
}
