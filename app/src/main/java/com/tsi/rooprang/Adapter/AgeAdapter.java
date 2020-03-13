package com.tsi.rooprang.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tsi.rooprang.Activity.ProductActivity;
import com.tsi.rooprang.Activity.ProductDescriptionActivity;
import com.tsi.rooprang.DTO.DataModel.DataModelCategory;
import com.tsi.rooprang.DTO.DataModel.ProductDataModel;
import com.tsi.rooprang.DTO.singleproduct.ProductAvailabelAge;
import com.tsi.rooprang.R;

import java.util.ArrayList;
import java.util.List;

public class AgeAdapter extends RecyclerView.Adapter<AgeAdapter.MyViewHolder> {
    private ArrayList<ProductAvailabelAge> productAvailabelAges;
    private Context context;
    int age_position = -1;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_age;

        public MyViewHolder(View view) {
            super(view);

            tv_age = (TextView) view.findViewById(R.id.tv_age);

        }
    }

    public AgeAdapter(Context context, ArrayList<ProductAvailabelAge> productAvailabelAges) {
        this.productAvailabelAges = productAvailabelAges;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_age_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ProductAvailabelAge dataModel = productAvailabelAges.get(position);

        holder.tv_age.setText(productAvailabelAges.get(position).getAgeName());
        if (age_position == position) {
            holder.tv_age.setBackgroundColor(Color.parseColor("#f49cc2"));
            holder.tv_age.setTextColor(Color.parseColor("#ffffff"));
        } else {
            holder.tv_age.setBackgroundResource(R.drawable.backg_ground);
            holder.tv_age.setTextColor(Color.parseColor("#f49cc2"));
        }

        holder.tv_age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ProductDescriptionActivity.productDescriptionActivity != null) {

                    ProductDescriptionActivity.vAge_id = productAvailabelAges.get(position).getProductAvalAgeId();
                    age_position = position;
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productAvailabelAges.size();
    }
}