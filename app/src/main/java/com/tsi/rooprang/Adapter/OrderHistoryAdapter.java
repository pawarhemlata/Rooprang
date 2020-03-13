package com.tsi.rooprang.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tsi.rooprang.Account.YourOrderDetailActivity;
import com.tsi.rooprang.DTO.orderhistory.OrderReturn_DTO;
import com.tsi.rooprang.DTO.orderhistory.OrderedProduct;
import com.tsi.rooprang.R;
import com.tsi.rooprang.Session.SessionManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tsi.rooprang.Account.YourOrderDetailActivity.apiInterface;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.MyViewHolder> {

    private ArrayList<OrderedProduct> dataModelList;
    public Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView img_order_detail;
        public LinearLayout linear_cart;
        public TextView tv_order_title, tv_order_qty, tv_order_price, tv_orderoriginal_price, tv_view_order, tv_order_return_policy;

        public MyViewHolder(View view) {
            super(view);
            img_order_detail = (ImageView) view.findViewById(R.id.img_order_detail);
            tv_order_title = (TextView) view.findViewById(R.id.tv_order_title);
            tv_order_qty = (TextView) view.findViewById(R.id.tv_order_qty);
            tv_order_price = (TextView) view.findViewById(R.id.tv_order_price);
            tv_orderoriginal_price = (TextView) view.findViewById(R.id.tv_order_original_price);
            tv_view_order = (TextView) view.findViewById(R.id.tv_view_order);
            tv_order_return_policy = (TextView) view.findViewById(R.id.tv_order_return_policy);
        }
    }

    public OrderHistoryAdapter(Context context, ArrayList<OrderedProduct> dataModelList) {
        this.dataModelList = dataModelList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_history_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.tv_order_title.setText(dataModelList.get(position).getProductName());
        holder.tv_order_qty.setText("Qty " + dataModelList.get(position).getQty());
        holder.tv_order_price.setText(dataModelList.get(position).getTotalAmount());
        holder.tv_orderoriginal_price.setText(dataModelList.get(position).getAmount());
        Glide.with(context).load(dataModelList.get(position).getImage()).into(holder.img_order_detail);

        if (dataModelList.get(position).getBtnStatus().equals("0") && dataModelList.get(position).getOrderProductStatus().equals("delivered")) {
            holder.tv_order_return_policy.setText("Return Order");
            holder.tv_order_return_policy.setPaintFlags(holder.tv_order_return_policy.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        } else if (dataModelList.get(position).getOrderProductStatus().equals("canceled")) {
            holder.tv_order_return_policy.setClickable(false);
            holder.tv_order_return_policy.setText("Order Cancelled");
        } else if (dataModelList.get(position).getOrderProductStatus().equals("return")) {
            holder.tv_order_return_policy.setClickable(false);
            holder.tv_order_return_policy.setText("Order Returned");
        } else if (dataModelList.get(position).getOrderProductStatus().equals("confirmed")){
            holder.tv_order_return_policy.setText("Cancel");
            holder.tv_order_return_policy.setPaintFlags(holder.tv_order_return_policy.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        } else if (dataModelList.get(position).getOrderProductStatus().equals("pending")) {
//            holder.tv_order_return_policy.setText("pending");
//            holder.tv_order_return_policy.setClickable(false);
        }


        if (holder.tv_order_return_policy.getText().toString().equals("Return Order")) {
            holder.tv_order_return_policy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    YourOrderDetailActivity.mProgressDialog.setIndeterminate(true);
                    YourOrderDetailActivity.mProgressDialog.setMessage("Loading...");
                    YourOrderDetailActivity.mProgressDialog.show();
                    final Call<OrderReturn_DTO> orderReturn_dtoCall = apiInterface.api_order_return(dataModelList.get(position).getOrderProductId(), SessionManager.getInstance(context).get_data_from_session(SessionManager.KEY_USERID));
                    orderReturn_dtoCall.enqueue(new Callback<OrderReturn_DTO>() {
                        @Override
                        public void onResponse(Call<OrderReturn_DTO> call, Response<OrderReturn_DTO> response) {
                            if (YourOrderDetailActivity.mProgressDialog.isShowing()) {
                                YourOrderDetailActivity.mProgressDialog.dismiss();
                            }

                            OrderReturn_DTO orderReturn_dto = response.body();
                            if (orderReturn_dto.getMessage().equals("success")) {
                                holder.tv_order_return_policy.setText("Order Returned");
                                Toast.makeText(context, "Order Returned", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Can\'t Return Order", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<OrderReturn_DTO> call, Throwable t) {
                            Log.e("repsonse", t + "");
                        }
                    });
                }
            });
        } else if (holder.tv_order_return_policy.getText().toString().equals("Cancel")) {
            holder.tv_order_return_policy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    YourOrderDetailActivity.mProgressDialog.setIndeterminate(true);
                    YourOrderDetailActivity.mProgressDialog.setMessage("Loading...");
                    YourOrderDetailActivity.mProgressDialog.show();
                    final Call<OrderReturn_DTO> orderReturn_dtoCall = apiInterface.api_order_cancel(dataModelList.get(position).getOrderProductId(), SessionManager.getInstance(context).get_data_from_session(SessionManager.KEY_USERID));
                    orderReturn_dtoCall.enqueue(new Callback<OrderReturn_DTO>() {
                        @Override
                        public void onResponse(Call<OrderReturn_DTO> call, Response<OrderReturn_DTO> response) {
                            if (YourOrderDetailActivity.mProgressDialog.isShowing()) {
                                YourOrderDetailActivity.mProgressDialog.dismiss();
                            }

                            OrderReturn_DTO orderReturn_dto = response.body();
                            if (orderReturn_dto.getMessage().equals("success")) {
                                holder.tv_order_return_policy.setText("Order Cancelled");

                                Toast.makeText(context, "Order Cancelled", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, orderReturn_dto.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<OrderReturn_DTO> call, Throwable t) {
                            Log.e("repsonse", t + "");
                        }
                    });
                }
            });
        }
        Log.e("amount", dataModelList.get(position).getAmount());
    }


    @Override
    public int getItemCount() {
        return dataModelList.size();
    }
}