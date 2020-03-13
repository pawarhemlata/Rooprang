package com.tsi.rooprang.UserProfile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.tsi.rooprang.Account.Saved_Address_Activity;
import com.tsi.rooprang.DTO.Address_DTO.Add_Address_DTO;
import com.tsi.rooprang.DTO.cart.Cart_DTO;
import com.tsi.rooprang.R;
import com.tsi.rooprang.Session.SessionManager;
import com.tsi.rooprang.retrofit.ApiInterface;
import com.tsi.rooprang.retrofit.NewApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class AddUserAddressActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText tv_address_name, tv_address_pincode, tv_address, tv_address_landmark, tv_address_city, tv_address_state, tv_address_mobile;
    Button saveadd;
    ProgressDialog mProgressDialog;
    ApiInterface apiService;
    String vFull_Name = "", vPincode = "", vAddress = "", vLandmark = "", vCity = "", vState = "", vMobile = "", user_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_address);

        apiService = NewApiClient.getClient().create(ApiInterface.class);
        mProgressDialog = new ProgressDialog(AddUserAddressActivity.this);
        user_id = SessionManager.getInstance(this).get_data_from_session(SessionManager.KEY_USERID);
        tv_address_name = findViewById(R.id.tv_address_name);
        tv_address_pincode = findViewById(R.id.tv_address_pincode);
        tv_address = findViewById(R.id.tv_address);
        tv_address_landmark = findViewById(R.id.tv_address_landmark);
        tv_address_city = findViewById(R.id.tv_address_city);
        tv_address_state = findViewById(R.id.tv_address_state);
        tv_address_mobile = findViewById(R.id.tv_address_mobile);
        saveadd = findViewById(R.id.saveadd);

        toolbar = findViewById(R.id.tool);
        toolbar.setTitle(getResources().getString(R.string.add_addresses));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vailid()) {
                    add_address(user_id);
                }
            }
        });
    }

    private boolean vailid() {
        boolean is_vailid = false;
        vFull_Name = tv_address_name.getText().toString().trim();
        vPincode = tv_address_pincode.getText().toString().trim();
        vAddress = tv_address.getText().toString().trim();
        vLandmark = tv_address_landmark.getText().toString().trim();
        vCity = tv_address_city.getText().toString().trim();
        vState = tv_address_state.getText().toString().trim();
        vMobile = tv_address_mobile.getText().toString().trim();
        if (vFull_Name.isEmpty()) {
            is_vailid = false;
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        } else if (vPincode.isEmpty()) {
            is_vailid = false;
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        } else if (vAddress.isEmpty()) {
            is_vailid = false;
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        } else if (vLandmark.isEmpty()) {
            is_vailid = false;
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        } else if (vCity.isEmpty()) {
            is_vailid = false;
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        } else if (vState.isEmpty()) {
            is_vailid = false;
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        } else if (vMobile.isEmpty()) {
            is_vailid = false;
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        } else {
            is_vailid = true;
        }
        return is_vailid;
    }

    public void add_address(String user_id) {
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();

        Call<Add_Address_DTO> add_address_dtoCall = apiService.add_address_api(user_id, vFull_Name, vPincode, vAddress, vLandmark, vCity, vState, vMobile);
        add_address_dtoCall.enqueue(new Callback<Add_Address_DTO>() {
            @Override
            public void onResponse(Call<Add_Address_DTO> call, Response<Add_Address_DTO> response) {
                Add_Address_DTO add_address_dto = response.body();
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                if (add_address_dto.getMessage().equalsIgnoreCase("success")) {
                    Toast.makeText(AddUserAddressActivity.this, add_address_dto.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    // Put the String to pass back into an Intent and close this activity
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Add_Address_DTO> call, Throwable t) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                Log.e("failure", t + "");
            }
        });
    }

}
