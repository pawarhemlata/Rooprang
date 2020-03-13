package com.tsi.rooprang.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tsi.rooprang.Activity.ProductDescriptionActivity;
import com.tsi.rooprang.DTO.singleproduct.ProductAvailabelAge;
import com.tsi.rooprang.DTO.singleproduct.ProductAvailabelColor;
import com.tsi.rooprang.R;

import java.util.ArrayList;
import java.util.Iterator;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.tsi.rooprang.Activity.ProductDescriptionActivity.productDescriptionActivity;
import static com.tsi.rooprang.Activity.ProductDescriptionActivity.vColorChartId;
import static com.tsi.rooprang.Activity.ProductDescriptionActivity.vColor_id;
import static com.tsi.rooprang.Activity.ProductDescriptionActivity.vProductColorChartId;
import static com.tsi.rooprang.Activity.ProductDescriptionActivity.vProductId;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.MyViewHolder> {
    private ArrayList<ProductAvailabelColor> productAvailabelColors;
    private Context context;
    String vcolor;
    int color_position = -1;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView img_color;

        public MyViewHolder(View view) {
            super(view);

            img_color = view.findViewById(R.id.img_color);

        }
    }

    public ColorAdapter(Context context, ArrayList<ProductAvailabelColor> productAvailabelColors) {
        this.productAvailabelColors = productAvailabelColors;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_color_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ProductAvailabelColor dataModel = productAvailabelColors.get(position);
        vcolor = productAvailabelColors.get(position).getColorcode() != null ? productAvailabelColors.get(position).getColorcode() : "#ffffff";
        int color = Color.parseColor(vcolor);
        holder.img_color.setColorFilter(color);

        if (color_position == position) {
            holder.img_color.setBorderColor(Color.parseColor("#f49cc2"));
        } else {
            holder.img_color.setBorderColor(Color.parseColor("#ffffff"));
        }

        holder.img_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productDescriptionActivity != null) {
                    vColor_id = productAvailabelColors.get(position).getColorcode();
                    vColorChartId = productAvailabelColors.get(position).getColorChartId();
                    vProductId = productAvailabelColors.get(position).getProductId();
                    vProductColorChartId = productAvailabelColors.get(position).getProductColorChartId();
                    ((ProductDescriptionActivity)productDescriptionActivity).showProductImagesByColorId(vProductId,vColorChartId
                    );
                    color_position = position;
                    notifyDataSetChanged();

                }
            }
        });

    }
    @Override
    public int getItemCount() {
        return productAvailabelColors.size();
    }
}