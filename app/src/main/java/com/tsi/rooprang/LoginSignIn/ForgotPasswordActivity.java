package com.tsi.rooprang.LoginSignIn;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tsi.rooprang.DTO.login.Forgot_Pass_DTO;
import com.tsi.rooprang.R;
import com.tsi.rooprang.retrofit.BaseUrl;
import com.tsi.rooprang.utils.My_Application;
import com.tsi.rooprang.utils.Utility;


import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    TextView join_us, usepassword;
    Button send_otp_login;
    EditText emailedit, phone;
    private Intent intent;
    ProgressDialog mProgressDialog;

    private Context context;

    Toolbar toolbar;
    private String Phone, Email, Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pwd);

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
        send_otp_login = (Button) findViewById(R.id.send_otp_login);
        phone = (EditText) findViewById(R.id.phoneedit_signin);

       /* content = new SpannableString(login.getText().toString());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        login.setText(content);*/
    }

    private void clickListeners() {
        send_otp_login.setOnClickListener(this);
    }

    private void loginSubmit() {
        Phone = phone.getText().toString();
        //     ConfirmPassword = confirm_password_field.getText().toString();
        if (Phone.length() != 10) {
            phone.setError("Mobile Number should be  10 digit");
            phone.requestFocus();
        } else {
            call_forgot_password_api(Phone);
        }
    }

    private void call_forgot_password_api(final String phone) {
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();

        try {
            if (Utility.isConnectingToInternet(ForgotPasswordActivity.this)) {
        StringRequest req = new StringRequest(Request.Method.POST, BaseUrl.getMethodUrl(BaseUrl.FORGOT_PASS),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (mProgressDialog.isShowing()) {
                            mProgressDialog.dismiss();
                        }
                        String vMessage = "";
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject != null) {
                                Forgot_Pass_DTO forgot_pass_dto = new Forgot_Pass_DTO();
                                forgot_pass_dto.setResult(jsonObject.getString("result") != null ? jsonObject.getString("result") : null);
                                forgot_pass_dto.setOtp(Integer.valueOf(jsonObject.getString("otp") != null ? jsonObject.getString("otp") : null));

                                if (jsonObject.get("result").equals("success")) {

                                    intent = new Intent(getApplicationContext(), OTPActivity.class);
                                    intent.putExtra("otp", forgot_pass_dto.getOtp().toString().trim());

                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(ForgotPasswordActivity.this, "Invailid phone number !", Toast.LENGTH_SHORT).show();
                                }
                            }

                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        if (mProgressDialog.isShowing()) {
                            mProgressDialog.dismiss();
                        }
                        try {
                            if (error == null || error.networkResponse == null) {
                                return;
                            }
                            String body;
                            try {
                                body = new String(error.networkResponse.data, "UTF-8");
                                JSONObject jsonObject = new JSONObject(body.toString());
                                if (jsonObject.has("message")) {
                                    //Toast.makeText(getApplicationContext(), "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                } else if (jsonObject.has("error")) {
                                    //    Toast.makeText(getApplicationContext(), "" + jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                                }
                                System.out.println("body..." + body.toString());
                            } catch (UnsupportedEncodingException e) {

                            }
                        } catch (Exception e) {

                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("phone", String.valueOf(phone));
                return params;
            }
        };
        // Adding request to request queue
        try {
            My_Application.mInstance.addToRequestQueue(req);
        } catch (Exception e) {

        }

    }else {
        Toast.makeText(ForgotPasswordActivity.this, "Please Connect Internet !", Toast.LENGTH_SHORT).show();
    }
} catch (Exception e) {
        e.printStackTrace();
        }


//        Call<Forgot_Pass_DTO> forgotPassDtoCall = apiService.api_forgot_password(phone);
//        forgotPassDtoCall.enqueue(new Callback<Forgot_Pass_DTO>() {
//            @Override
//            public void onResponse(Call<Forgot_Pass_DTO> call, Response<Forgot_Pass_DTO> response) {
//                Forgot_Pass_DTO forgot_pass_dto = response.body();
//                Log.e("onSuccess", " " + forgot_pass_dto.getResult());
//
//                /*
//                if (forgot_pass_dto.getResult().equals("success")) {
//                    intent = new Intent(getApplicationContext(), OTPActivity.class);
//                    intent.putExtra("otp", forgot_pass_dto.getOtp().toString().trim());
//                    startActivity(intent);
//                    finish();
//                } */
//            }
//            @Override
//            public void onFailure(Call<Forgot_Pass_DTO> call, Throwable t) {
//                Log.e("onFailure", " " + t.getMessage());
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.send_otp_login:
                loginSubmit();
                break;


        }
    }
}
