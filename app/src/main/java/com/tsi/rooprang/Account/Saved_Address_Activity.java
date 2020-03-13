package com.tsi.rooprang.Account;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tsi.rooprang.Activity.InitialScreenActivity;
import com.tsi.rooprang.Activity.PaymentWebActivity;
import com.tsi.rooprang.Adapter.CartAdapter;
import com.tsi.rooprang.Adapter.UserAddressAdapter;
import com.tsi.rooprang.DTO.Address_DTO.Delete_Address_DTO;
import com.tsi.rooprang.DTO.Address_DTO.Show_Address_DTO;
import com.tsi.rooprang.DTO.Address_DTO.Show_Address_Details_DTO;
import com.tsi.rooprang.DTO.DataModel.ProductDataModel;
import com.tsi.rooprang.DTO.Payment_dto.Order_Placed_DTO;
import com.tsi.rooprang.DTO.cart.Cart_DTO;
import com.tsi.rooprang.R;
import com.tsi.rooprang.Session.SessionManager;
import com.tsi.rooprang.UserProfile.AddUserAddressActivity;
import com.tsi.rooprang.retrofit.ApiInterface;
import com.tsi.rooprang.retrofit.NewApiClient;
import com.tsi.rooprang.utility.AvenuesParams;
import com.tsi.rooprang.utility.ServiceUtility;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Saved_Address_Activity extends AppCompatActivity {
    public static Saved_Address_Activity saved_address_activity;
    Toolbar toolbar;
    Button save;
    UserAddressDatabaseHelper dbHelper;
    private RecyclerView rv_save_address;
    private UserAddressAdapter userAddressAdapter;
    private List<Show_Address_Details_DTO> show_address_details_dtos;
    GridLayoutManager addressmanager;
    Button btn_alert_dialog;
    Button alert_cancel, alert_ok;
    private RecyclerView.LayoutManager layoutManager;
    public Button btn_continue;
    FloatingActionButton btn_add_address;
    RelativeLayout layout1, layout2;
    ProgressDialog mProgressDialog;
    ApiInterface apiService;
    String user_id = "";
    // private List<UserAddressDataModel> movieList = new ArrayList<>();
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;
    String product_id = "", sale_price = "", qty = "", discount = "", pay_mode = "", pay_type = "";
    String order_id;
    String grand_total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savedaddress);
        saved_address_activity = this;
        apiService = NewApiClient.getClient().create(ApiInterface.class);
        mProgressDialog = new ProgressDialog(Saved_Address_Activity.this);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.Saved_addresses));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rv_save_address = (RecyclerView) findViewById(R.id.recycler_address);
        btn_continue = (Button) findViewById(R.id.btn_continue);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            product_id = bundle.getString("product_ids");
            sale_price = bundle.getString("sale_price");
            qty = bundle.getString("qty");
            discount = bundle.getString("discount");
        }

        if (getIntent() != null) {
            String xx = getIntent().getStringExtra("come_frag");
            if (xx != null) {
                if (xx.equalsIgnoreCase("come_frag")) {
                    btn_continue.setVisibility(View.INVISIBLE);
                } else {
                    btn_continue.setVisibility(View.VISIBLE);
                }
            } else {
                btn_continue.setVisibility(View.VISIBLE);
            }
        }

        btn_add_address = findViewById(R.id.btn_add_address);
        user_id = SessionManager.getInstance(this).get_data_from_session(SessionManager.KEY_USERID);

        show_address_details_dtos = new ArrayList<>();
        userAddressAdapter = new UserAddressAdapter(getApplicationContext(), show_address_details_dtos);
        addressmanager = new GridLayoutManager(getApplicationContext(), 1);
        rv_save_address.setLayoutManager(addressmanager);
        rv_save_address.setItemAnimator(new DefaultItemAnimator());
        userAddressAdapter.notifyDataSetChanged();
        rv_save_address.setAdapter(userAddressAdapter);

        show_address(user_id);

        btn_add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Saved_Address_Activity.this, AddUserAddressActivity.class);
                startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);

            }
        });
    }

    public void button_click(String address_id) {
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder builder = new AlertDialog.Builder(Saved_Address_Activity.this);
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.payment_alert_layout, null);
                builder.setView(dialogView);

                RadioGroup radiogrp_Payment_option;

                radiogrp_Payment_option = dialogView.findViewById(R.id.radiogrp_Payment_option);
                alert_cancel = dialogView.findViewById(R.id.alert_dept_btn_cancel);
                alert_ok = dialogView.findViewById(R.id.alert_dept_btn_ok);

                final AlertDialog dialog = builder.create();

                radiogrp_Payment_option.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton rb = (RadioButton) group.findViewById(checkedId);
                        pay_mode = rb.getText().toString();
                        if (pay_mode.equalsIgnoreCase(getString(R.string.cod))) {
                            pay_type = "1";
                        } else if (pay_mode.equalsIgnoreCase(getString(R.string.online_pay))) {
                            pay_type = "2";
                        }
                        // Toast.makeText(Saved_Address_Activity.this, rb.getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                });

                alert_cancel.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                      /*  Intent intent = new Intent(Saved_Address_Activity.this, AddUserAddressActivity.class);
                        startActivity(intent);*/

                    }
                });

                alert_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (pay_type.equals("1")) {
                            call_order_api(product_id, sale_price, qty, discount, address_id, pay_type);
                        } else {
                            call_order_api_online(product_id, sale_price, qty, discount, address_id, pay_type);
                        }


                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    public void show_address(String user_id) {
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();
        Call<Show_Address_DTO> show_address_dtoCall = apiService.show_address_api(user_id);

        show_address_dtoCall.enqueue(new Callback<Show_Address_DTO>() {
            @Override
            public void onResponse(Call<Show_Address_DTO> call, Response<Show_Address_DTO> response) {
                Show_Address_DTO show_address_dto = response.body();
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                if (show_address_details_dtos != null) {
                    show_address_details_dtos.clear();
                }
                if (show_address_dto.getMessage().equalsIgnoreCase("success")) {
                    if (show_address_dto.getData() != null) {
                        show_address_details_dtos.addAll(show_address_dto.getData());
                        userAddressAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<Show_Address_DTO> call, Throwable t) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                Log.e("failure", t + "");
            }
        });
    }

    public void call_order_api(String productId, String sale_price, String qty, String discount, String address_id, String pay_type) {
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();
        Call<Order_Placed_DTO> order_placed_dtoCall = apiService.call_apply_order_api(user_id, address_id, pay_type, productId, qty, sale_price, discount);
        order_placed_dtoCall.enqueue(new Callback<Order_Placed_DTO>() {
            @Override
            public void onResponse(Call<Order_Placed_DTO> call, Response<Order_Placed_DTO> response) {

                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                Order_Placed_DTO order_placed_dto = response.body();
                if (order_placed_dto.getMessage().equalsIgnoreCase("success")) {
                    Toast.makeText(Saved_Address_Activity.this, order_placed_dto.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), OrderSummaryActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("product_ids", product_id);
                    bundle.putString("sale_price", sale_price);
                    bundle.putString("qty", qty);
                    bundle.putString("discount", discount);
                    bundle.putString("address_id", address_id);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();


                } else {
                    Toast.makeText(Saved_Address_Activity.this, order_placed_dto.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }


            @Override
            public void onFailure(Call<Order_Placed_DTO> call, Throwable t) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                Log.e("failure", t + "");
            }
        });
    }

    public void call_order_api_online(String productId, String sale_price, String qty, String discount, String address_id, String pay_type) {
       /* mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();*/
        Call<Order_Placed_DTO> order_placed_dtoCall = apiService.call_apply_order_api(user_id, address_id, pay_type, productId, qty, sale_price, discount);
        order_placed_dtoCall.enqueue(new Callback<Order_Placed_DTO>() {
            @Override
            public void onResponse(Call<Order_Placed_DTO> call, Response<Order_Placed_DTO> response) {

               /* if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }*/

                Order_Placed_DTO order_placed_dto = response.body();

                if (order_placed_dto.getMessage().equalsIgnoreCase("success")) {
                    Toast.makeText(Saved_Address_Activity.this, order_placed_dto.getMessage(), Toast.LENGTH_SHORT).show();
                    order_id = order_placed_dto.getOrderId() != null ? order_placed_dto.getOrderId() : "";
                    grand_total = order_placed_dto.getGrandTotal() != null ? order_placed_dto.getGrandTotal() : "";

                    String vAccessCode = "AVMQ90HA11CI68QMIC";
                    String vMerchantId = "246181";
                    String vCurrency = "INR";
                    String vAmount = grand_total;
                    if (!vAccessCode.equals("") && !vMerchantId.equals("") && !vCurrency.equals("") && !vAmount.equals("")) {
                        Intent intent = new Intent(Saved_Address_Activity.this, PaymentWebActivity.class);
                        intent.putExtra(AvenuesParams.ACCESS_CODE, "AVMQ90HA11CI68QMIC");
                        intent.putExtra(AvenuesParams.WORKING_KEY, "933C33B98A2E813598CA60B6193F3535");
                        intent.putExtra(AvenuesParams.MERCHANT_ID, "246181");
                        intent.putExtra(AvenuesParams.ORDER_ID, "123456");
                        intent.putExtra(AvenuesParams.CURRENCY, "INR");
                        intent.putExtra(AvenuesParams.AMOUNT, grand_total);
                        intent.putExtra(AvenuesParams.REDIRECT_URL, "http://www.thelittleshoppers.com/HDFC_andriod/ccavResponseHandler.php");
                        intent.putExtra(AvenuesParams.CANCEL_URL, "http://thelittleshoppers.com/HDFC_andriod/payment-cancel.php");
                        intent.putExtra(AvenuesParams.RSA_KEY_URL, "http://www.thelittleshoppers.com/HDFC_andriod/GetRSA.php");
                        startActivity(intent);
                    } else {
                        Toast.makeText(Saved_Address_Activity.this, "All parameters are mandatory.", Toast.LENGTH_LONG).show();
                    }
                }


            }


            @Override
            public void onFailure(Call<Order_Placed_DTO> call, Throwable t) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                Log.e("failure", t + "");
            }
        });
    }

    // This method is called when the second activity finishes
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that it is the SecondActivity with an OK result
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if (userAddressAdapter != null) {
                    if (show_address_details_dtos != null) {
                        show_address_details_dtos.clear();
                    }
                    show_address(user_id);
                }
            }
        }
    }
}


/*
package com.tsi.rooprang.Account;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tsi.rooprang.Adapter.CartAdapter;
import com.tsi.rooprang.Adapter.UserAddressAdapter;
import com.tsi.rooprang.DTO.Address_DTO.Delete_Address_DTO;
import com.tsi.rooprang.DTO.Address_DTO.Show_Address_DTO;
import com.tsi.rooprang.DTO.Address_DTO.Show_Address_Details_DTO;
import com.tsi.rooprang.DTO.DataModel.ProductDataModel;
import com.tsi.rooprang.DTO.Payment_dto.Order_Placed_DTO;
import com.tsi.rooprang.DTO.cart.Cart_DTO;
import com.tsi.rooprang.R;
import com.tsi.rooprang.Session.SessionManager;
import com.tsi.rooprang.UserProfile.AddUserAddressActivity;
import com.tsi.rooprang.retrofit.ApiInterface;
import com.tsi.rooprang.retrofit.NewApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Saved_Address_Activity extends AppCompatActivity {
    public static Saved_Address_Activity saved_address_activity;
    Toolbar toolbar;
    Button save;
    UserAddressDatabaseHelper dbHelper;
    private RecyclerView rv_save_address;
    private UserAddressAdapter userAddressAdapter;
    private List<Show_Address_Details_DTO> show_address_details_dtos;
    GridLayoutManager addressmanager;
    Button btn_alert_dialog;
    Button alert_cancel, alert_ok;
    private RecyclerView.LayoutManager layoutManager;
    public Button btn_continue;
    FloatingActionButton btn_add_address;
    RelativeLayout layout1, layout2;
    ProgressDialog mProgressDialog;
    ApiInterface apiService;
    String user_id = "";
    // private List<UserAddressDataModel> movieList = new ArrayList<>();
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;
    String product_id = "", sale_price = "", qty = "", discount = "", pay_mode = "", pay_type = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savedaddress);
        saved_address_activity = this;
        apiService = NewApiClient.getClient().create(ApiInterface.class);
        mProgressDialog = new ProgressDialog(Saved_Address_Activity.this);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.Saved_addresses));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rv_save_address = (RecyclerView) findViewById(R.id.recycler_address);
        btn_continue = (Button) findViewById(R.id.btn_continue);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            product_id = bundle.getString("product_ids");
            sale_price = bundle.getString("sale_price");
            qty = bundle.getString("qty");
            discount = bundle.getString("discount");
        }

        if (getIntent() != null) {
            String xx = getIntent().getStringExtra("come_frag");
            if (xx != null) {
                if (xx.equalsIgnoreCase("come_frag")) {
                    btn_continue.setVisibility(View.INVISIBLE);
                } else {
                    btn_continue.setVisibility(View.VISIBLE);
                }
            } else {
                btn_continue.setVisibility(View.VISIBLE);
            }
        }

        btn_add_address = findViewById(R.id.btn_add_address);
        user_id = SessionManager.getInstance(this).get_data_from_session(SessionManager.KEY_USERID);

        show_address_details_dtos = new ArrayList<>();
        userAddressAdapter = new UserAddressAdapter(getApplicationContext(), show_address_details_dtos);
        addressmanager = new GridLayoutManager(getApplicationContext(), 1);
        rv_save_address.setLayoutManager(addressmanager);
        rv_save_address.setItemAnimator(new DefaultItemAnimator());
        userAddressAdapter.notifyDataSetChanged();
        rv_save_address.setAdapter(userAddressAdapter);

        show_address(user_id);


        btn_add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Saved_Address_Activity.this, AddUserAddressActivity.class);
                startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);

            }
        });
    }

    public void button_click(String address_id) {
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder builder = new AlertDialog.Builder(Saved_Address_Activity.this);
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.payment_alert_layout, null);
                builder.setView(dialogView);

                RadioGroup radiogrp_Payment_option;

                radiogrp_Payment_option = dialogView.findViewById(R.id.radiogrp_Payment_option);
                alert_cancel = dialogView.findViewById(R.id.alert_dept_btn_cancel);
                alert_ok = dialogView.findViewById(R.id.alert_dept_btn_ok);

                final AlertDialog dialog = builder.create();

                radiogrp_Payment_option.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton rb = (RadioButton) group.findViewById(checkedId);
                        pay_mode = rb.getText().toString();
                        if (pay_mode.equalsIgnoreCase(getString(R.string.cod))) {
                            pay_type = "1";
                        } else if (pay_mode.equalsIgnoreCase(getString(R.string.online_pay))) {
                            pay_type = "2";
                        }
                        // Toast.makeText(Saved_Address_Activity.this, rb.getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                });

                alert_cancel.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                      */
/*  Intent intent = new Intent(Saved_Address_Activity.this, AddUserAddressActivity.class);
                        startActivity(intent);*//*

                        ;

                    }
                });

                alert_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        call_order_api(product_id, sale_price, qty, discount, address_id, pay_type);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    public void show_address(String user_id) {
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();
        Call<Show_Address_DTO> show_address_dtoCall = apiService.show_address_api(user_id);

        show_address_dtoCall.enqueue(new Callback<Show_Address_DTO>() {
            @Override
            public void onResponse(Call<Show_Address_DTO> call, Response<Show_Address_DTO> response) {
                Show_Address_DTO show_address_dto = response.body();
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                if (show_address_details_dtos != null) {
                    show_address_details_dtos.clear();
                }
                if (show_address_dto.getMessage().equalsIgnoreCase("success")) {
                    if (show_address_dto.getData() != null) {
                        show_address_details_dtos.addAll(show_address_dto.getData());
                        userAddressAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<Show_Address_DTO> call, Throwable t) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                Log.e("failure", t + "");
            }
        });
    }

    public void call_order_api(String productId, String sale_price, String qty, String discount, String address_id, String pay_type) {
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();
        Call<Order_Placed_DTO> order_placed_dtoCall = apiService.call_apply_order_api(user_id, address_id, pay_type, productId, qty, sale_price, discount);
        order_placed_dtoCall.enqueue(new Callback<Order_Placed_DTO>() {
            @Override
            public void onResponse(Call<Order_Placed_DTO> call, Response<Order_Placed_DTO> response) {

                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                Order_Placed_DTO order_placed_dto = response.body();
                if (order_placed_dto.getMessage().equalsIgnoreCase("success")) {
                    Toast.makeText(Saved_Address_Activity.this, order_placed_dto.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), OrderSummaryActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("product_ids", product_id);
                    bundle.putString("sale_price", sale_price);
                    bundle.putString("qty", qty);
                    bundle.putString("discount", discount);
                    bundle.putString("address_id", address_id);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Saved_Address_Activity.this, order_placed_dto.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<Order_Placed_DTO> call, Throwable t) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                Log.e("failure", t + "");
            }
        });
    }

    // This method is called when the second activity finishes
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that it is the SecondActivity with an OK result
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "result_okey", Toast.LENGTH_SHORT).show();
                if (userAddressAdapter != null) {
                    if (show_address_details_dtos != null) {
                        show_address_details_dtos.clear();
                    }
                    show_address(user_id);
                }
            }
        }
    }
}
*/
