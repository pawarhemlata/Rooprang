package com.tsi.rooprang.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tsi.rooprang.Activity.ProductDescriptionActivity;
import com.tsi.rooprang.DTO.singleproduct.ProductAvailabelAge;
import com.tsi.rooprang.DTO.singleproduct.ProductAvailabelSize;
import com.tsi.rooprang.R;

import java.util.ArrayList;

public class SizeAdapter extends RecyclerView.Adapter<SizeAdapter.MyViewHolder> {
    private ArrayList<ProductAvailabelSize> productAvailabelSizes;
    private Context context;
    int size_position = -1;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_size;

        public MyViewHolder(View view) {
            super(view);

            tv_size = (TextView) view.findViewById(R.id.tv_size);

        }
    }

    public SizeAdapter(Context context, ArrayList<ProductAvailabelSize> productAvailabelSizes) {
        this.productAvailabelSizes = productAvailabelSizes;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_size_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ProductAvailabelSize dataModel = productAvailabelSizes.get(position);

        holder.tv_size.setText(dataModel.getSizeNumber());

        if (size_position == position) {
            holder.tv_size.setBackgroundColor(Color.parseColor("#f49cc2"));
            holder.tv_size.setTextColor(Color.parseColor("#ffffff"));
        } else {
            holder.tv_size.setBackgroundResource(R.drawable.backg_ground);
            holder.tv_size.setTextColor(Color.parseColor("#f49cc2"));
        }
        holder.tv_size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ProductDescriptionActivity.productDescriptionActivity != null) {
                    ProductDescriptionActivity.vSize_id = dataModel.getProductAvailSizeId();
                    size_position = position;
                    notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return productAvailabelSizes.size();
    }
}