package com.tsi.rooprang.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.tsi.rooprang.Activity.ProductActivity;
import com.tsi.rooprang.DTO.Dashboard.BloomStyleDTO;
import com.tsi.rooprang.R;

import java.util.List;

public class BloomInStyleAdapter extends RecyclerView.Adapter<BloomInStyleAdapter.MyViewHolder> {

    private List<BloomStyleDTO> bloomStyleDTOS;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;
        public TextView title;
        public RelativeLayout rel_layout;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tv_kids);
            img = (ImageView) view.findViewById(R.id.img_kids);
            /*rel_layout=(RelativeLayout)view.findViewById(R.id.rel_layout);*/
        }
    }


    public BloomInStyleAdapter(Context context, List<BloomStyleDTO> bloomStyleDTOS) {
        this.bloomStyleDTOS = bloomStyleDTOS;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_kids_layout, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        BloomStyleDTO bloomStyleDTO = bloomStyleDTOS.get(position);

        holder.title.setText(bloomStyleDTO.getSubcategoryName());

       /* if (bloomStyleDTO.getSubcategoryImage() != null) {
            Picasso.with(context).load(bloomStyleDTO.getSubcategoryImage()).placeholder(R.drawable.no_image).fit().into(holder.img, new Callback() {
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


        Glide.with(context).load(bloomStyleDTO.getSubcategoryImage()).into(holder.img);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ProductActivity.class);
                intent.putExtra("sub_category_id", bloomStyleDTOS.get(position).getSubcategoryId());
                intent.putExtra("item_name", bloomStyleDTOS.get(position).getSubcategoryName());
                context.startActivity(intent);
            }

        });
    }

    @Override
    public int getItemCount() {
        return bloomStyleDTOS.size();
    }
}