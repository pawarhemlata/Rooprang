package com.tsi.rooprang.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tsi.rooprang.Account.YourOrderDetailActivity;
import com.tsi.rooprang.DTO.DataModel.ProductDataModel;
import com.tsi.rooprang.DTO.orderhistory.OrderHistoryDetail_DTO;
import com.tsi.rooprang.DTO.orderhistory.OrderedProduct;
import com.tsi.rooprang.R;

import java.util.ArrayList;
import java.util.List;

public class YourOrderAdapter extends RecyclerView.Adapter<YourOrderAdapter.MyViewHolder> {

    private List<OrderHistoryDetail_DTO> dataModelList;
    public Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView img_order_detail;
        public LinearLayout linear_cart;
        public TextView tv_order_title, tv_order_qty, tv_order_price, tv_orderoriginal_price, tv_view_order;

        public MyViewHolder(View view) {
            super(view);
            img_order_detail = (ImageView) view.findViewById(R.id.img_order_detail);
            tv_order_title = (TextView) view.findViewById(R.id.tv_order_title);
            tv_order_qty = (TextView) view.findViewById(R.id.tv_order_qty);
            tv_order_price = (TextView) view.findViewById(R.id.tv_order_price);
            tv_orderoriginal_price = (TextView) view.findViewById(R.id.tv_orderoriginal_price);
            tv_view_order = (TextView) view.findViewById(R.id.tv_view_order);
        }
    }

    public YourOrderAdapter(Context context, List<OrderHistoryDetail_DTO> dataModelList) {
        this.dataModelList = dataModelList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_yourorder_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.tv_order_title.setText("ORDER_ID#" + dataModelList.get(position).getOrderNo());
        holder.tv_order_price.setText(dataModelList.get(position).getGrandTotal());
        holder.tv_order_qty.setText("Qty " + dataModelList.get(position).getOrderedProducts().size());
        holder.tv_orderoriginal_price.setText(dataModelList.get(position).getTotalItemPrice());


        for (int i = 0; i < dataModelList.get(position).getOrderedProducts().size(); i++) {
            Glide.with(context).load(dataModelList.get(position).getOrderedProducts().get(0).getImage()).into(holder.img_order_detail);
            Log.e("prod_img", dataModelList.get(position).getOrderedProducts().get(0).getImage() + "");

            holder.tv_view_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<OrderedProduct> orderProductList = new ArrayList();
                    orderProductList.addAll(dataModelList.get(position).getOrderedProducts());
                    Log.e("orderProductList", orderProductList + "");
                    Intent intent = new Intent(context, YourOrderDetailActivity.class);
                    intent.putExtra("orderProductList", orderProductList);
                    intent.putExtra("orderNo", dataModelList.get(position).getOrderNo());
                    intent.putExtra("username", dataModelList.get(position).getFullName());
                    intent.putExtra("full_address", dataModelList.get(position).getFullAddress());
                    intent.putExtra("landmark", dataModelList.get(position).getLandmark());
                    intent.putExtra("city", dataModelList.get(position).getCity());
                    intent.putExtra("state", dataModelList.get(position).getState());
                    intent.putExtra("grand_total", dataModelList.get(position).getGrandTotal());
                    intent.putExtra("total_item_price", dataModelList.get(position).getTotalItemPrice());
                    intent.putExtra("discount", dataModelList.get(position).getDiscount());
                    intent.putExtra("pincode", dataModelList.get(position).getPincode());
                    intent.putExtra("payment_type", dataModelList.get(position).getPincode());
                    intent.putExtra("pincode", dataModelList.get(position).getPincode());
                    intent.putExtra("payment_type", dataModelList.get(position).getPaymentType());
                    intent.putExtra("order_status", dataModelList.get(position).getOrderSatus());
                    intent.putExtra("ordered_date", dataModelList.get(position).getOrderredDate());
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }
}