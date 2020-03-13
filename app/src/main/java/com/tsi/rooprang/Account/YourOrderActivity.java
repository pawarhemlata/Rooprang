package com.tsi.rooprang.Account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.tsi.rooprang.Adapter.YourOrderAdapter;
import com.tsi.rooprang.DTO.DataModel.ProductDataModel;
import com.tsi.rooprang.DTO.Sub_cat_dtos.Sub_Cat_Details_DTO;
import com.tsi.rooprang.DTO.Sub_cat_dtos.Sub_Category_DTO;
import com.tsi.rooprang.DTO.orderhistory.OrderHistoryDetail_DTO;
import com.tsi.rooprang.DTO.orderhistory.OrderHistory_DTO;
import com.tsi.rooprang.R;
import com.tsi.rooprang.Session.SessionManager;
import com.tsi.rooprang.retrofit.ApiClient;
import com.tsi.rooprang.retrofit.ApiInterface;
import com.tsi.rooprang.retrofit.NewApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YourOrderActivity extends AppCompatActivity {
    private ArrayList<OrderHistoryDetail_DTO> orderHistoryDetail_dtos;
    private YourOrderAdapter yourOrderAdapter;
    GridLayoutManager mlayoutOrder;
    private RecyclerView rv_your_order;
    Toolbar toolbar;
    private Context context;
    ProgressDialog mProgressDialog;
    ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_order);

        context = this;

        rv_your_order = findViewById(R.id.rv_your_order);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Order Description");

        apiInterface = NewApiClient.getClient().create(ApiInterface.class);
        mProgressDialog = new ProgressDialog(context);

        orderHistoryDetail_dtos = new ArrayList<>();
        yourOrderAdapter = new YourOrderAdapter(getApplicationContext(), orderHistoryDetail_dtos);
        mlayoutOrder = new GridLayoutManager(getApplicationContext(), 1);
        rv_your_order.setLayoutManager(mlayoutOrder);
        rv_your_order.setItemAnimator(new DefaultItemAnimator());
        rv_your_order.setAdapter(yourOrderAdapter);

        getOrders(SessionManager.getInstance(context).get_data_from_session(SessionManager.KEY_USERID));
    }

    private void getOrders(String userId) {
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();
        final Call<OrderHistory_DTO> orderHistory_dtoCall = apiInterface.api_order_history(userId);
        orderHistory_dtoCall.enqueue(new Callback<OrderHistory_DTO>() {
            @Override
            public void onResponse(Call<OrderHistory_DTO> call, Response<OrderHistory_DTO> response) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }

                OrderHistory_DTO orderHistory_dto = response.body();
                if (orderHistory_dto.getMessage().equals("success")) {
                    orderHistoryDetail_dtos.addAll(orderHistory_dto.getData());
                    yourOrderAdapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onFailure(Call<OrderHistory_DTO> call, Throwable t) {
                Log.e("repsonse", t + "");
            }
        });

    }
}
