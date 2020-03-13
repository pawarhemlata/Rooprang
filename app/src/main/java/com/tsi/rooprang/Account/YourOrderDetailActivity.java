package com.tsi.rooprang.Account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tsi.rooprang.Adapter.OrderHistoryAdapter;
import com.tsi.rooprang.Adapter.YourOrderAdapter;
import com.tsi.rooprang.DTO.orderhistory.OrderHistoryDetail_DTO;
import com.tsi.rooprang.DTO.orderhistory.OrderedProduct;
import com.tsi.rooprang.R;
import com.tsi.rooprang.retrofit.ApiInterface;
import com.tsi.rooprang.retrofit.NewApiClient;

import java.util.ArrayList;
import java.util.List;

public class YourOrderDetailActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView tv_shipping_status, tv_shipping_date, tv_order_title, tv_order_qty, tv_order_price, tv_order_original_price, txt_paymethod_order,
            tv_name_order, tv_address_order, tv_landmark_order, tv_city_order, tv_state_order, tv_pincode_order, tv_item_price_detail, tv_discount_detail,
            tv_shpping_fee_detail, tv_total_price_detail;
    ImageView img_order_detail;
    private String orderNo, username, full_address, landmark, city, state, pincode, grand_total, total_item_price, discount, payment_type, order_status, ordered_date;
    private ArrayList<OrderedProduct> orderProductList;
    private OrderHistoryAdapter orderHistoryAdapter;
    LinearLayoutManager mlayoutOrder;
    private RecyclerView rv_order_history;
    private Activity activity;
    private Context context;
    public static ProgressDialog mProgressDialog;
    public static ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_order_detail);

        activity = this;
        context = this;

        apiInterface = NewApiClient.getClient().create(ApiInterface.class);
        mProgressDialog = new ProgressDialog(context);

        if (getIntent() != null) {
            orderNo = getIntent().getStringExtra("orderNo");
            username = getIntent().getStringExtra("username");
            full_address = getIntent().getStringExtra("full_address");
            landmark = getIntent().getStringExtra("landmark");
            city = getIntent().getStringExtra("city");
            state = getIntent().getStringExtra("state");
            pincode = getIntent().getStringExtra("pincode");
            grand_total = getIntent().getStringExtra("grand_total");
            total_item_price = getIntent().getStringExtra("total_item_price");
            discount = getIntent().getStringExtra("discount");
            payment_type = getIntent().getStringExtra("payment_type");
            order_status = getIntent().getStringExtra("order_status");
            ordered_date = getIntent().getStringExtra("ordered_date");
            orderProductList = (ArrayList<OrderedProduct>) getIntent().getSerializableExtra("orderProductList");
        }

        toolbar = activity.findViewById(R.id.toolbar);
        toolbar.setTitle("#" + orderNo);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rv_order_history = findViewById(R.id.rv_order_history);
        img_order_detail = findViewById(R.id.img_order_detail);
        tv_shipping_status = findViewById(R.id.tv_shipping_status);
        tv_shipping_date = findViewById(R.id.tv_shipping_date);
        tv_order_title = findViewById(R.id.tv_order_title);
        tv_order_qty = findViewById(R.id.tv_order_qty);
        tv_order_price = findViewById(R.id.tv_order_price);
        tv_order_original_price = findViewById(R.id.tv_order_original_price);
        txt_paymethod_order = findViewById(R.id.txt_paymethod_order);
        tv_name_order = findViewById(R.id.tv_name_order);
        tv_address_order = findViewById(R.id.tv_address_order);
        tv_landmark_order = findViewById(R.id.tv_landmark_order);
        tv_city_order = findViewById(R.id.tv_city_order);
        tv_state_order = findViewById(R.id.tv_state_order);
        tv_pincode_order = findViewById(R.id.tv_pincode_order);
        tv_item_price_detail = findViewById(R.id.tv_item_price_detail);
        tv_discount_detail = findViewById(R.id.tv_discount_detail);
        tv_shpping_fee_detail = findViewById(R.id.tv_shpping_fee_detail);
        tv_total_price_detail = findViewById(R.id.tv_total_price_detail);

//        Order List
        orderHistoryAdapter = new OrderHistoryAdapter(getApplicationContext(), orderProductList);
        mlayoutOrder = new LinearLayoutManager(getApplicationContext());
        rv_order_history.setLayoutManager(mlayoutOrder);
        rv_order_history.setItemAnimator(new DefaultItemAnimator());
        rv_order_history.setAdapter(orderHistoryAdapter);

//      Details
        tv_name_order.setText(username);
        tv_address_order.setText(full_address);
        tv_landmark_order.setText(landmark);
        tv_city_order.setText(city);
        tv_state_order.setText(state);
        tv_pincode_order.setText(pincode);
        tv_item_price_detail.setText(total_item_price);
        tv_total_price_detail.setText(grand_total);
        tv_discount_detail.setText(discount + "%");
        txt_paymethod_order.setText(payment_type);
        tv_shipping_status.setText(order_status);
        tv_shipping_date.setText(ordered_date);
    }
}
