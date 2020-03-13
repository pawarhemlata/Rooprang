package com.tsi.rooprang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tsi.rooprang.DTO.DataModel.ProductDataModel;
import com.tsi.rooprang.R;

import java.util.List;

public class OrderSummaryAdapter extends RecyclerView.Adapter<OrderSummaryAdapter.MyViewHolder> {

    private List<ProductDataModel> dataModelList;
    public Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView img_order_detail;
        public LinearLayout linear_cart;
        public TextView tv_order_title,tv_order_qty,tv_order_price,tv_order_original_price;

        public MyViewHolder(View view) {
            super(view);
            img_order_detail = (ImageView) view.findViewById(R.id.img_order_detail);
            tv_order_title = (TextView) view.findViewById(R.id.tv_order_title);
            tv_order_qty = (TextView) view.findViewById(R.id.tv_order_qty);
            tv_order_price = (TextView) view.findViewById(R.id.tv_order_price);
            tv_order_original_price = (TextView) view.findViewById(R.id.tv_order_original_price);
        }
    }

    public OrderSummaryAdapter(Context context, List<ProductDataModel> dataModelList) {
        this.dataModelList = dataModelList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_details_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


    }

    @Override
    public int getItemCount() {
        return 3;
    }
}