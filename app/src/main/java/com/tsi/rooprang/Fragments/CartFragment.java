package com.tsi.rooprang.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tsi.rooprang.Account.Saved_Address_Activity;
import com.tsi.rooprang.Adapter.CartAdapter;
import com.tsi.rooprang.DTO.cart.Cart_DTO;
import com.tsi.rooprang.DTO.cart.Cart_Details_DTO;
import com.tsi.rooprang.DTO.cart.Cart_Total_amount_DTO;
import com.tsi.rooprang.R;
import com.tsi.rooprang.Session.SessionManager;
import com.tsi.rooprang.retrofit.ApiInterface;
import com.tsi.rooprang.retrofit.NewApiClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment {
    public static CartFragment cartFragment;
    private View view;
    private Context context;
    private List<Cart_Details_DTO> cart_details_dtos;
    private CartAdapter cartadapter;


    TextView deliver_to, tv_shipping_charge;
    GridLayoutManager mLayoutproduct;
    private RecyclerView recycle_view_cart;
    public static Button shopping, proceed_to_checkout;
    ProgressDialog mProgressDialog;
    ApiInterface apiService;
    private TextView tv_sales_price, tv_discount, tv_promo_discount, tv_total_discount, tv_pay_amount;
    Cart_DTO cart_dto;
   public static LinearLayout lay_no_item;
    public static RelativeLayout lay_main;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cartFragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cart, container, false);
        context = getActivity();
        apiService = NewApiClient.getClient().create(ApiInterface.class);

        recycle_view_cart = (RecyclerView) view.findViewById(R.id.recycle_view_cart);
        deliver_to = (TextView) view.findViewById(R.id.deliver_to);
        tv_sales_price = (TextView) view.findViewById(R.id.tv_sales_price);
        tv_discount = (TextView) view.findViewById(R.id.tv_discount);
        tv_promo_discount = (TextView) view.findViewById(R.id.tv_promo_discount);
        tv_total_discount = (TextView) view.findViewById(R.id.tv_total_discount);
        tv_pay_amount = (TextView) view.findViewById(R.id.tv_pay_amount);
        tv_shipping_charge = (TextView) view.findViewById(R.id.tv_shipping_charge);
        lay_no_item = view.findViewById(R.id.lay_no_item);
        lay_main = view.findViewById(R.id.lay_main);

        proceed_to_checkout = (Button) view.findViewById(R.id.proceed_to_checkout);
        mProgressDialog = new ProgressDialog(getActivity());

        cart_details_dtos = new ArrayList<>();

        if (cart_details_dtos != null) {
            cart_details_dtos.clear();
        }
        cartadapter = new CartAdapter(context, cart_details_dtos);
        mLayoutproduct = new GridLayoutManager(context, 1);
        recycle_view_cart.setLayoutManager(mLayoutproduct);
        recycle_view_cart.setItemAnimator(new DefaultItemAnimator());
        recycle_view_cart.setAdapter(cartadapter);
        cartadapter.notifyDataSetChanged();

        if (SessionManager.getInstance(context).get_data_from_session(SessionManager.KEY_USERID) != null) {
            String user_id = SessionManager.getInstance(context).get_data_from_session(SessionManager.KEY_USERID);
            Log.e("cart..", "cart....." + user_id);
            _cart_details(user_id);
        }
        deliver_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressBottomFragment bottomSheet = new AddressBottomFragment();
                bottomSheet.show(getFragmentManager(), "");
            }
        });

        return view;

    }

    public void _cart_details(String user_id) {

        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();
        Call<Cart_DTO> cartDtoCall = apiService.api_cart_details_api(user_id);
        cartDtoCall.enqueue(new Callback<Cart_DTO>() {
            @Override
            public void onResponse(Call<Cart_DTO> call, Response<Cart_DTO> response) {
                Cart_DTO cart_dto = response.body();
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                if (cart_dto.getMessage().equalsIgnoreCase("success")) {
                    lay_main.setVisibility(View.VISIBLE);
                    lay_no_item.setVisibility(View.GONE);
                    if (cart_dto.getData() != null) {
                        if (cart_details_dtos != null) {
                            cart_details_dtos.clear();
                        }
                        cart_details_dtos.addAll(cart_dto.getData());
                        cartadapter.notifyDataSetChanged();
                    }

                } else {
                    lay_main.setVisibility(View.GONE);
                    lay_no_item.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Cart_DTO> call, Throwable t) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                Log.e("failure", t + "");
            }
        });
    }

    public void cart_amount_calculation(String user_id, String product_id, String sale_price, String qty, String discount) {

        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();
        Call<Cart_Total_amount_DTO> cartDtoCall = apiService.api_cart_total_amount(user_id, product_id, sale_price, qty, discount);

        cartDtoCall.enqueue(new Callback<Cart_Total_amount_DTO>() {
            @Override
            public void onResponse(Call<Cart_Total_amount_DTO> call, Response<Cart_Total_amount_DTO> response) {
                Cart_Total_amount_DTO cart_total_amount_dto = response.body();
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                if (cart_total_amount_dto.getMessage().equalsIgnoreCase("success")) {
                    tv_sales_price.setText(cart_total_amount_dto.getTotalSalePrice() != null ? cart_total_amount_dto.getTotalSalePrice() + "/-" : "0");
                    tv_discount.setText(cart_total_amount_dto.getDiscount() != null ? "- " + cart_total_amount_dto.getDiscount() + "/-" : "0");
                    tv_promo_discount.setText(cart_total_amount_dto.getPromoCodeDiscount() != null ? "- " + cart_total_amount_dto.getPromoCodeDiscount() + "/-" : "0");
                    tv_total_discount.setText(cart_total_amount_dto.getTotalDiscount() != null ? "- " + cart_total_amount_dto.getTotalDiscount() + "/-" : "0");
                    tv_pay_amount.setText(cart_total_amount_dto.getPayAmount() != null ? cart_total_amount_dto.getPayAmount() + "/-" : "0");
                    tv_shipping_charge.setText(cart_total_amount_dto.getShipping_charges() != null ? cart_total_amount_dto.getShipping_charges() + "/-" : "0");
                }
            }

            @Override
            public void onFailure(Call<Cart_Total_amount_DTO> call, Throwable t) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                Log.e("failure", t + "");
            }
        });
    }
}
