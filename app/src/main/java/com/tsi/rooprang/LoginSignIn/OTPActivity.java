package com.tsi.rooprang.LoginSignIn;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tsi.rooprang.DTO.login.Resend_OTP;
import com.tsi.rooprang.DTO.login.Verify_OTP;
import com.tsi.rooprang.R;
import com.tsi.rooprang.retrofit.ApiClient;
import com.tsi.rooprang.retrofit.ApiInterface;
import com.tsi.rooprang.retrofit.NewApiClient;
import com.tsi.rooprang.utils.Utility;

import in.aabhasjindal.otptextview.OtpTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OTPActivity extends AppCompatActivity {
    private OtpTextView otpTextView;
    Button done;
    private String vOtp = "", user_id = "";
    ApiInterface apiService;
    private TextView tv_resend_otp;
    private String id_forgot="",id_register="",phone="";
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        apiService = NewApiClient.getClient().create(ApiInterface.class);
        mProgressDialog = new ProgressDialog(this);
        otpTextView = findViewById(R.id.otp_view);
        tv_resend_otp = findViewById(R.id.tv_resend_otp);
        Bundle bundle = getIntent().getExtras();
        if (getIntent() != null) {

            user_id = getIntent().getStringExtra("user_id");
            id_forgot = getIntent().getStringExtra("id_forgot");
            id_register = bundle.getString("id_register");
            phone = bundle.getString("phone");

//            otpTextView.setOTP(vOtp);
        }


        done = findViewById(R.id.btn_done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vOtp = otpTextView.getOTP();
                if (vOtp!=null){
                    call_varify_otp_api();
                }else{
                    Toast.makeText(OTPActivity.this, getResources().getString(R.string.otp), Toast.LENGTH_SHORT).show();
                }

            }
        });



        tv_resend_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call_resend_otp_api();
            }
        });


    }

    private void call_resend_otp_api() {
        final Call<Resend_OTP> resend_otpCall = apiService.api_resend_otp(phone);
        resend_otpCall.enqueue(new Callback<Resend_OTP>() {
            @Override
            public void onResponse(Call<Resend_OTP> call, Response<Resend_OTP> response) {
                Resend_OTP resend_otp = response.body();
                   if (resend_otp.getMessage().equals("success")) {
                       Log.e("Success", " " + resend_otp.getMessage());

                }
            }

            @Override
            public void onFailure(Call<Resend_OTP> call, Throwable t) {
                Log.e("onFailure", " " + t.getMessage());
            }
        });
    }

    private void call_varify_otp_api() {

        try {
            if (Utility.isConnectingToInternet(OTPActivity.this)) {
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setMessage("Loading...");
                mProgressDialog.show();
                final Call<Verify_OTP> verify_otpCall = apiService.api_verify_otp(phone, vOtp);
                verify_otpCall.enqueue(new Callback<Verify_OTP>() {
                    @Override
                    public void onResponse(Call<Verify_OTP> call, Response<Verify_OTP> response) {

                        if (mProgressDialog.isShowing()) {
                            mProgressDialog.dismiss();
                        }
                        Verify_OTP verify_otp = response.body();
                        if (verify_otp.getMessage().equals("success")) {
                            Log.e("Success", " " + verify_otp.getMessage());

                            Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Log.e("failedddd", " " + verify_otp.getMessage());

                            Toast.makeText(OTPActivity.this,  verify_otp.getMessage(), Toast.LENGTH_SHORT).show();
                        }
//                        if (id_forgot.equals(1)) {
//                            // if (verify_otp.getResult().equals("success")) {
//                            Intent intent = new Intent(getApplicationContext(), ResetPasswordActivity.class);
//                            startActivity(intent);
//                            finish();
//                        } */
//else {


                        // if (verify_otp.getResult().equals("success")) {
                        Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                        startActivity(intent);
                        finish();



                    }

                    @Override
                    public void onFailure(Call<Verify_OTP> call, Throwable t) {

                        if (mProgressDialog.isShowing()) {
                            mProgressDialog.dismiss();
                        }
                        Log.e("onFailure", " " + t.getMessage());
                    }
                });
            }
    else {
            Toast.makeText(OTPActivity.this, "Please Connect Internet !", Toast.LENGTH_SHORT).show();
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}}
