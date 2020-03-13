package com.tsi.rooprang.Adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.tsi.rooprang.Activity.ProductDescriptionActivity;
import com.tsi.rooprang.Adapter.DealsAdapter;
import com.tsi.rooprang.DTO.Dashboard.LatestProduct_Detail_DTO;
import com.tsi.rooprang.DTO.DataModel.DataModelLatest;
import com.tsi.rooprang.R;

import java.util.ArrayList;


public class LatestProductAdapter extends RecyclerView.Adapter<LatestProductAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private static final String TAG = "LatestProductAdapter";
    private ArrayList<LatestProduct_Detail_DTO> latestProductModelArrayList;

    public LatestProductAdapter(Context ctx, ArrayList<LatestProduct_Detail_DTO> latestProductModelArrayList) {

        inflater = LayoutInflater.from(ctx);
        this.context = ctx;
        this.latestProductModelArrayList = latestProductModelArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.home_latest_layout, parent, false);
        MyViewHolder holder1 = new MyViewHolder(view);

        return holder1;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tvProductDiscount.setPaintFlags(holder.tvProductDiscount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tvoff.setText(latestProductModelArrayList.get(position).getDiscount());
        holder.tvProductName.setText(latestProductModelArrayList.get(position).getProductName());
        holder.tvprice.setText("₹" + latestProductModelArrayList.get(position).getSalePrice());

       /* if (latestProductModelArrayList.get(position).getImage() != null) {
            Picasso.with(context).load(latestProductModelArrayList.get(position).getImage()).placeholder(R.drawable.no_image).fit().into(holder.ivProductImage, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {
                    holder.pbbanner.setVisibility(View.GONE);
                }

                @Override
                public void onError() {
                    holder.pbbanner.setVisibility(View.GONE);
                }
            });
        }*/

         Glide.with(context).load(latestProductModelArrayList.get(position).getImage()).into(holder.ivProductImage);
        holder.tvProductDiscount.setText("₹" + latestProductModelArrayList.get(position).getRegularPrice());
        holder.ivProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDescriptionActivity.class);
                intent.putExtra("product_id", latestProductModelArrayList.get(position).getProductId());
                intent.putExtra("product_title", latestProductModelArrayList.get(position).getProductName());
                intent.putExtra("Sub_cat_id", latestProductModelArrayList.get(position).getSubCategoryId());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return latestProductModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProductImage;
        TextView tvProductName, tvProductDiscount, tvoff, tvprice;
        ProgressBar pbbanner;
        RatingBar rbcustomer;
        CardView cvproduct;

        public MyViewHolder(View convertView) {
            super(convertView);
            cvproduct = (CardView) convertView.findViewById(R.id.cvproduct);
            pbbanner = (ProgressBar) convertView.findViewById(R.id.pbbanner);
            ivProductImage = (ImageView) convertView.findViewById(R.id.ivProductImage);
            tvProductName = (TextView) convertView.findViewById(R.id.tvProductName);
            rbcustomer = (RatingBar) convertView.findViewById(R.id.rbcustomer);
            tvProductDiscount = (TextView) convertView.findViewById(R.id.tvProductDiscount);
            tvoff = (TextView) convertView.findViewById(R.id.tvoff);
            tvprice = (TextView) convertView.findViewById(R.id.tvprice);
        }
    }
}