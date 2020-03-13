package com.tsi.rooprang.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.tsi.rooprang.Activity.ProductActivity;
import com.tsi.rooprang.Activity.ProductDescriptionActivity;
import com.tsi.rooprang.Activity.SubSubCategoryActivity;
import com.tsi.rooprang.DTO.Dashboard.DealOfTheDay_Detail_DTO;
import com.tsi.rooprang.DTO.DataModel.DealsModel;
import com.tsi.rooprang.Fragments.HomeFragment;
import com.tsi.rooprang.R;

import java.util.ArrayList;
import java.util.List;


public class DealsAdapter extends RecyclerView.Adapter<DealsAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private static final String TAG = "CategoryAdapter";
    private List<DealOfTheDay_Detail_DTO> dealOfTheDay_detail_dtos;


    public DealsAdapter(Context ctx, List<DealOfTheDay_Detail_DTO> dealOfTheDay_detail_dtos) {

        inflater = LayoutInflater.from(ctx);
        this.context = ctx;
        this.dealOfTheDay_detail_dtos = dealOfTheDay_detail_dtos;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_deals, parent, false);

        MyViewHolder holder1 = new MyViewHolder(view);

        return holder1;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        DealOfTheDay_Detail_DTO dealOfTheDay_detail_dto = dealOfTheDay_detail_dtos.get(position);
        holder.tvProductDiscount.setPaintFlags(holder.tvProductDiscount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tvoff.setText(dealOfTheDay_detail_dtos.get(position).getDiscount() + "%");
        holder.tvProductName.setText(dealOfTheDay_detail_dtos.get(position).getProductName());
        holder.tvprice.setText("₹" + dealOfTheDay_detail_dtos.get(position).getSalePrice());

       /* if (dealOfTheDay_detail_dtos.get(position).getImage() != null) {
            Picasso.with(context).load(dealOfTheDay_detail_dtos.get(position).getImage()).placeholder(R.drawable.no_image).fit().into(holder.ivProductImage, new com.squareup.picasso.Callback() {
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
          Glide.with(context).load(dealOfTheDay_detail_dtos.get(position).getImage()).into(holder.ivProductImage);

        holder.tvProductDiscount.setText("₹" + dealOfTheDay_detail_dtos.get(position).getRegularPrice());

        holder.ivProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDescriptionActivity.class);
                intent.putExtra("product_id", dealOfTheDay_detail_dtos.get(position).getProductId());
                intent.putExtra("product_title", dealOfTheDay_detail_dtos.get(position).getProductName());
                intent.putExtra("Sub_cat_id", dealOfTheDay_detail_dtos.get(position).getSubCategoryId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dealOfTheDay_detail_dtos.size();
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