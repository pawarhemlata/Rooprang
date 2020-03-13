package com.tsi.rooprang.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
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
import com.tsi.rooprang.Account.ProfileDetailsActivity;
import com.tsi.rooprang.Activity.ProductActivity;
import com.tsi.rooprang.Activity.ProductDescriptionActivity;
import com.tsi.rooprang.DTO.DataModel.DataModelHome3;
import com.tsi.rooprang.DTO.product.Product_Details_DTO;
import com.tsi.rooprang.R;

import java.util.ArrayList;
import java.util.List;

public class SimilarProductAdapter extends RecyclerView.Adapter<SimilarProductAdapter.MyViewHolder> {

    private ArrayList<Product_Details_DTO> dataModelList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;
        public TextView title, price, tv_discount, tv_actual_price;
        public RelativeLayout rel_layout;
        ProgressBar pbbanner;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tv_title_horz);
            price = (TextView) view.findViewById(R.id.tv_price_horz);
            img = (ImageView) view.findViewById(R.id.thumbnail_horz);
            tv_discount = (TextView) view.findViewById(R.id.tv_discount);
            tv_actual_price = (TextView) view.findViewById(R.id.tv_actual_price);
            rel_layout = (RelativeLayout) view.findViewById(R.id.rel);
            pbbanner = view.findViewById(R.id.pbbanner);
        }
    }


    public SimilarProductAdapter(Context context, ArrayList<Product_Details_DTO> dataModelList) {
        this.dataModelList = dataModelList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_product_simillar, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        //DataModelHome3 dataModel = dataModelList.get(position);
        holder.title.setText(dataModelList.get(position).getProductName());
        holder.price.setText("" + dataModelList.get(position).getSalePrice());
        holder.tv_discount.setText(dataModelList.get(position).getDiscount() + "% Off");
        holder.tv_actual_price.setText(dataModelList.get(position).getRegularPrice());
        holder.tv_actual_price.setPaintFlags(holder.tv_actual_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


      /*  if (dataModelList.get(position).getImage() != null) {
            Picasso.with(context).load(dataModelList.get(position).getImage()).placeholder(R.drawable.no_image).fit().into(holder.img, new com.squareup.picasso.Callback() {
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

        Picasso.with(context).load(dataModelList.get(position).getImage()).into(holder.img,
                new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                holder.pbbanner.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                holder.pbbanner.setVisibility(View.GONE);
            }
        });


        holder.rel_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDescriptionActivity.class);
                intent.putExtra("product_id", dataModelList.get(position).getProductId());
                intent.putExtra("Sub_cat_id", dataModelList.get(position).getSubCategoryId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }
}