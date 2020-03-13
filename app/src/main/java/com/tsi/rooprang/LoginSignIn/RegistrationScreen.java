package com.tsi.rooprang.LoginSignIn;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tsi.rooprang.Activity.ProductActivity;
import com.tsi.rooprang.DTO.signup.SignUp_DTO;
import com.tsi.rooprang.R;
import com.tsi.rooprang.Session.SessionManager;
import com.tsi.rooprang.retrofit.ApiInterface;
import com.tsi.rooprang.retrofit.NewApiClient;
import com.tsi.rooprang.utils.Utility;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;

public class RegistrationScreen extends AppCompatActivity implements View.OnClickListener {
    TextView sign_in, skip;
    Button send;
    ApiInterface apiService;
    ProgressDialog mProgressDialog;
    EditText nameedit, emailedit, phoneedit, passwordedit, cnfrmpasswordedit;
    private Intent intent_goto_signin, intent_goto_otp;
    private Context context;
    private String Name, Phone, Email, Password, ConfirmPwd;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                 //   "(?=.*[a-zA-Z])" +      //any letter
                  //  "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_screen);
        mProgressDialog = new ProgressDialog(this);
        apiService = NewApiClient.getClient().create(ApiInterface.class);
        initialViews();
        findIntialViews();
        registerSubmit();
        clickListeners();

    }

    private void initialViews() {
        context = this;


        //    sessionManager = new UserSession(context);
    }

    private void findIntialViews() {
        send = (Button) findViewById(R.id.send_otp);
        nameedit = (EditText) findViewById(R.id.nameedit);
        emailedit = (EditText) findViewById(R.id.emailedit);
        phoneedit = (EditText) findViewById(R.id.phoneedit);
        passwordedit = (EditText) findViewById(R.id.passwordedit);
        cnfrmpasswordedit = (EditText) findViewById(R.id.cnfrm_passwordedit);
        sign_in = (TextView) findViewById(R.id.sign_in);


    }

    private void clickListeners() {
        send.setOnClickListener(this);
        sign_in.setOnClickListener(this);

    }

    private void registerSubmit() {
        Name = nameedit.getText().toString();

        Email = emailedit.getText().toString();


        Phone = phoneedit.getText().toString();
        Password = passwordedit.getText().toString();
        ConfirmPwd = cnfrmpasswordedit.getText().toString();


        if (Name.isEmpty()) {
            nameedit.setError("Name is required");
            nameedit.requestFocus();
        } else if (Email.isEmpty()) {
            emailedit.setError("Email is required");
            emailedit.requestFocus();
        }else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            emailedit.setError("Please enter a valid email address");
            emailedit.requestFocus();
        } else if (Phone.length() != 10) {
            phoneedit.setError("Mobile Number should be  10 digit");
            phoneedit.requestFocus();
        } else if (Password.isEmpty()) {
            passwordedit.setError("Field can't be empty");

        } else if (!PASSWORD_PATTERN.matcher(Password).matches()) {
            passwordedit.setError("Password too weak");
        } else if (!ConfirmPwd.equals(Password)) {
            cnfrmpasswordedit.setError("Password Doesn't match");
        } else {
            try {
                if (Utility.isConnectingToInternet(RegistrationScreen.this)) {
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.show();

            final Call<SignUp_DTO> call = apiService.api_signup(Name, Email, Phone, Password);
            call.enqueue(new Callback<SignUp_DTO>() {

                @Override
                public void onResponse(Call<SignUp_DTO> call, retrofit2.Response<SignUp_DTO> response) {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    SignUp_DTO signUp_dto = response.body();
                    Log.e("Success", signUp_dto.getMessage() + "");
                    Log.e("Success", signUp_dto.getResult() + "");
                    Toast.makeText(RegistrationScreen.this, signUp_dto.getMessage(), Toast.LENGTH_SHORT).show();
                    if (signUp_dto.getResult().equals("success")) {

                        intent_goto_otp = new Intent(context, OTPActivity.class);
                        intent_goto_otp.putExtra("otp", signUp_dto.getOtp().toString().trim());
                        intent_goto_otp.putExtra("user_id", "");
                        intent_goto_otp.putExtra("id_register", "2");
                        intent_goto_otp.putExtra("phone", Phone);
                        startActivity(intent_goto_otp);

                    }

                }

                @Override
                public void onFailure(Call<SignUp_DTO> call, Throwable t) {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    Log.e("onfailure", t + "");

                }


            });
        }else {
                Toast.makeText(RegistrationScreen.this, "Please Connect Internet !", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }}}



    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.send_otp:

                registerSubmit();

                break;

            case R.id.sign_in:
                intent_goto_signin = new Intent(context, SignInActivity.class);
                startActivity(intent_goto_signin);
                finish();


        }
    }
}