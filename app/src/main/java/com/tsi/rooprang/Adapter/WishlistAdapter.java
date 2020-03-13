package com.tsi.rooprang.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.tsi.rooprang.Activity.ProductDescriptionActivity;
import com.tsi.rooprang.DTO.DataModel.WishlistDataModel;
import com.tsi.rooprang.DTO.wishlist.ShowWish_Detail_DTO;
import com.tsi.rooprang.R;

import java.util.ArrayList;
import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.MyViewHolder> {

    private List<ShowWish_Detail_DTO> dataModelList;
    public Context context;
    Bitmap _bitmap;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView img,wish,wishfill;
        public RelativeLayout rel_layout;
        public LinearLayout linear_wishlist;
        public TextView cardcategory,cardprice,orgprice,offpercent,description;

        public MyViewHolder(View view) {
            super(view);
            img = (ImageView) view.findViewById(R.id.cardimage);
            wish = (ImageView) view.findViewById(R.id.cardwish);
            wishfill = (ImageView) view.findViewById(R.id.cardwishfill);
            cardcategory = (TextView) view.findViewById(R.id.cardcategory);
            cardprice = (TextView) view.findViewById(R.id.cardprice);
            orgprice = (TextView) view.findViewById(R.id.card_orignal_price);
            linear_wishlist =  view.findViewById(R.id.linear_wishlist);
            offpercent = (TextView) view.findViewById(R.id.card_off);
        }
    }

    public WishlistAdapter(Context context, ArrayList<ShowWish_Detail_DTO> dataModelList) {
        this.dataModelList = dataModelList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ShowWish_Detail_DTO dataModel = dataModelList.get(position);
        holder.cardcategory.setText(dataModel.getProductName());
        holder.orgprice.setPaintFlags(holder.orgprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.cardprice.setText(dataModel.getSalePrice());
        holder.orgprice.setText(dataModel.getRegularPrice());
        holder.offpercent.setText(dataModel.getDiscount()+ "%");


       /* if (dataModel.getImage() != null) {
            Picasso.with(context).load(dataModel.getImage()).placeholder(R.drawable.no_image).fit().into(holder.img, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {
                    pbbanner.setVisibility(View.GONE);
                }

                @Override
                public void onError() {
                    pbbanner.setVisibility(View.GONE);
                }
            });
        }*/

        Glide.with(context).load(dataModel.getImage()).into(holder.img);
/*
        holder.wish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.wishfill.setVisibility(View.VISIBLE);
                holder.wish.setVisibility(View.INVISIBLE);
            }
        });
        holder.wishfill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.wish.setVisibility(View.VISIBLE);
                holder.wishfill.setVisibility(View.INVISIBLE);
            }
        });*/
        holder.linear_wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDescriptionActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("product_id", dataModelList.get(position).getProductId());
                intent.putExtra("Sub_cat_id", dataModelList.get(position).getSubCategoryId());
                intent.putExtra("product_title", dataModelList.get(position).getProductName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }
}