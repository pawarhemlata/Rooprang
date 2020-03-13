package com.tsi.rooprang.Account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.tsi.rooprang.Activity.MainActivity;
import com.tsi.rooprang.Adapter.OrderSummaryAdapter;
import com.tsi.rooprang.DTO.DataModel.ProductDataModel;
import com.tsi.rooprang.R;

import java.util.ArrayList;
import java.util.List;

public class OrderSummaryActivity extends AppCompatActivity {
    private List<ProductDataModel> productDataModel;
    private OrderSummaryAdapter orderSummaryAdapter;
    GridLayoutManager mlayoutOrder;
    private RecyclerView rv_product_order;
    Toolbar toolbar;
    TextView tv_city_order, tv_state_order, tv_name_order, tv_phone_order, tv_address_order,
            tv_landmark_order, tv_total_price, tv_discount, tv_shpping_fee, tv_total_fee;
    String product_id = "", sale_price = "", qty = "", discount = "", address_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);
        rv_product_order = findViewById(R.id.rv_product_order);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.Order_Summary));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent setIntent = new Intent(OrderSummaryActivity.this, MainActivity.class);
                setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(setIntent);
                finish();
            }
        });
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            product_id = bundle.getString("product_ids");
            sale_price = bundle.getString("sale_price");
            qty = bundle.getString("qty");
            discount = bundle.getString("discount");
            address_id = bundle.getString("address_id");
        }

        productDataModel = new ArrayList<>();
        orderSummaryAdapter = new OrderSummaryAdapter(getApplicationContext(), productDataModel);
        mlayoutOrder = new GridLayoutManager(getApplicationContext(), 1);
        rv_product_order.setLayoutManager(mlayoutOrder);
        rv_product_order.setItemAnimator(new DefaultItemAnimator());
        rv_product_order.setAdapter(orderSummaryAdapter);

        tv_city_order = findViewById(R.id.tv_city_order);
        tv_state_order = findViewById(R.id.tv_state_order);
        tv_phone_order = findViewById(R.id.tv_phone_order);
        tv_name_order = findViewById(R.id.tv_name_order);
        tv_address_order = findViewById(R.id.tv_address_order);
        tv_landmark_order = findViewById(R.id.tv_landmark_order);
        tv_total_price = findViewById(R.id.tv_item_price);
        tv_discount = findViewById(R.id.tv_discount);
        tv_shpping_fee = findViewById(R.id.tv_shpping_fee);
        tv_total_fee = findViewById(R.id.tv_total_price);
    }

    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(OrderSummaryActivity.this, MainActivity.class);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
        finish();
    }
}
