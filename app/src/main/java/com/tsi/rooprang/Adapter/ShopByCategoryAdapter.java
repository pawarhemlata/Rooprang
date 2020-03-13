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
import com.squareup.picasso.Picasso;
import com.tsi.rooprang.Activity.ProductActivity;
import com.tsi.rooprang.DTO.Dashboard.ShopByCategoryDTO;
import com.tsi.rooprang.DTO.DataModel.DataModelHome1;
import com.tsi.rooprang.R;

import java.util.List;

public class ShopByCategoryAdapter extends RecyclerView.Adapter<ShopByCategoryAdapter.MyViewHolder> {

    private List<ShopByCategoryDTO> shopByCategoryDTOList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;
        public TextView title;
        public RelativeLayout rel_layout;
        public ProgressBar pbbanner;



        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tv_baby);
            img = (ImageView) view.findViewById(R.id.img_baby);
            /*rel_layout=(RelativeLayout)view.findViewById(R.id.rel_layout);*/
            pbbanner = (ProgressBar) view.findViewById(R.id.pbbanner);


        }
    }


    public ShopByCategoryAdapter(Context context, List<ShopByCategoryDTO> shopByCategoryDTOList) {
        this.shopByCategoryDTOList = shopByCategoryDTOList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_baby_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        ShopByCategoryDTO dataModel = shopByCategoryDTOList.get(position);

        holder.title.setText(dataModel.getSubcategoryName());

        /*if (dataModel.getSubcategoryImage() != null) {
            Picasso.with(context).load(dataModel.getSubcategoryImage()).placeholder(R.drawable.no_image).fit().into(holder.img, new com.squareup.picasso.Callback() {
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

        Glide.with(context).load(dataModel.getSubcategoryImage()).into(holder.img);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent h = new Intent(context, ProductActivity.class);
                h.putExtra("sub_category_id", shopByCategoryDTOList.get(position).getSubcategoryId());
                h.putExtra("item_name", shopByCategoryDTOList.get(position).getSubcategoryName());
                context.startActivity(h);
            }

        });
    }

    @Override
    public int getItemCount() {
        return shopByCategoryDTOList.size();
    }
}