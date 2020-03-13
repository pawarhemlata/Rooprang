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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.tsi.rooprang.Activity.MainActivity;
import com.tsi.rooprang.DTO.login.Login_DTO;
import com.tsi.rooprang.DTO.login.Login_Details_DTO;
import com.tsi.rooprang.R;
import com.tsi.rooprang.Session.SessionManager;
import com.tsi.rooprang.retrofit.ApiClient;
import com.tsi.rooprang.retrofit.ApiInterface;
import com.tsi.rooprang.retrofit.NewApiClient;
import com.tsi.rooprang.utils.Utility;

import retrofit2.Call;
import retrofit2.Callback;


public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView sign_up, use_otp, tv_forgot_pass;
    private Button btn_signin;
    private EditText phone_edit, edt_password;
    private Intent intent;
    private Context context;
    ProgressDialog mProgressDialog;
    private String vPhone = "", vPassword = "";
    private SessionManager sessionManager;
    String come_from = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mProgressDialog = new ProgressDialog(this);
        FirebaseApp.initializeApp(this);
        sessionManager = new SessionManager(this);
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(SignInActivity.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String device_token = instanceIdResult.getToken();
                sessionManager.setDeviceToken(device_token);
            }
        });
        if (getIntent() != null) {
            come_from = getIntent().getStringExtra("come_from");
        }
        initialViews();
        findIntialViews();

        clickListeners();
    }

    private void initialViews() {
        context = this;
        //    sessionManager = new UserSession(context);
    }

    private void findIntialViews() {
        btn_signin = (Button) findViewById(R.id.btn_signin);

        phone_edit = (EditText) findViewById(R.id.phone_edit);
        edt_password = (EditText) findViewById(R.id.edt_password);
        sign_up = (TextView) findViewById(R.id.sign_up);

        tv_forgot_pass = (TextView) findViewById(R.id.tv_forgot_pass);


    }

    private void clickListeners() {
        btn_signin.setOnClickListener(this);
        sign_up.setOnClickListener(this);

        tv_forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(context, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void signin() {
        vPhone = phone_edit.getText().toString().trim();
        vPassword = edt_password.getText().toString().trim();

        if (vPhone.isEmpty()) {
            phone_edit.setError("Please enter valid mobile number !");
            phone_edit.requestFocus();
        } else {
            try {
                if (Utility.isConnectingToInternet(SignInActivity.this)) {

                    mProgressDialog.setIndeterminate(true);
                    mProgressDialog.setMessage("Loading...");
                    mProgressDialog.show();
                    Log.e("device_token", sessionManager.getDeviceToken());
                    ApiInterface apiService = NewApiClient.getClient().create(ApiInterface.class);


                    final Call<Login_DTO> call = apiService.api_login(vPhone, vPassword, sessionManager.getDeviceToken());
                    call.enqueue(new Callback<Login_DTO>() {

                        @Override
                        public void onResponse(Call<Login_DTO> call, retrofit2.Response<Login_DTO> response) {
                            if (mProgressDialog.isShowing()) {
                                mProgressDialog.dismiss();
                            }
                            Login_DTO login_dto = response.body();
                            Log.e("responsess", response.body().getResult());
                            if (login_dto.getResult().equals("success")) {
                                Log.e("response", response.body().getResult());
                                Login_Details_DTO login_details_dto = login_dto.getData();
                                if (login_details_dto != null) {
                                    SessionManager.getInstance(context).set_data_in_session(SessionManager.IS_LOGIN, "true");
                                    SessionManager.getInstance(context).set_data_in_session(SessionManager.KEY_NAME, login_details_dto.getName());
                                    SessionManager.getInstance(context).set_data_in_session(SessionManager.KEY_USERID, login_details_dto.getUserId());
                                    SessionManager.getInstance(context).set_data_in_session(SessionManager.KEY_EMAIL, login_details_dto.getEmail());
                                    SessionManager.getInstance(context).set_data_in_session(SessionManager.KEY_PHONE, login_details_dto.getPhone());

                                    if (come_from != null) {
                                        if (come_from.equalsIgnoreCase("product_discription")) {
                                            finish();
                                        } else if (come_from.equalsIgnoreCase("wishlist")) {
                                            intent = new Intent(context, MainActivity.class);
                                            intent.putExtra("login","go_to_wishlist");
                                            startActivity(intent);

                                        } else if (come_from.equalsIgnoreCase("cart_fragment")) {
                                            intent = new Intent(context, MainActivity.class);
                                            intent.putExtra("login","go_to_cart");
                                            startActivity(intent);
                                        }
                                    } else {
                                        intent = new Intent(context, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }


                                }
                            } else {
                                Toast.makeText(SignInActivity.this, login_dto.getResult().toString(), Toast.LENGTH_SHORT).show();
                            }


                        }

                        @Override
                        public void onFailure(Call<Login_DTO> call, Throwable t) {
                            if (mProgressDialog.isShowing()) {
                                mProgressDialog.dismiss();
                            }
                            Log.e("failuer", "onFailure: ", t);
                        }
                    });

                } else {
                    Toast.makeText(SignInActivity.this, "Please Connect Internet !", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_signin:
                signin();
                break;

            case R.id.sign_up:
                intent = new Intent(context, RegistrationScreen.class);
                startActivity(intent);
                break;


        }

    }
}
