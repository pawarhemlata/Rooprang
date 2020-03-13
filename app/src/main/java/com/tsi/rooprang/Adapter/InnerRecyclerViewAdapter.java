package com.tsi.rooprang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.tsi.rooprang.DTO.parent_all_category_dtos.Category_List_DTO;
import com.tsi.rooprang.DTO.parent_all_category_dtos.Subcategory;
import com.tsi.rooprang.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InnerRecyclerViewAdapter extends RecyclerView.Adapter<InnerRecyclerViewAdapter.ViewHolder> {
    private List<Category_List_DTO> listDataHeader1;
    Context context;
    int groupPosition;
    Subcategory subcategory;


    public InnerRecyclerViewAdapter(Context context, List<Category_List_DTO> listDataHeader1, int groupPosition, Subcategory subcategory) {
        this.listDataHeader1 = listDataHeader1;
        this.context = context;
        this.groupPosition = groupPosition;
        this.subcategory = subcategory;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView ivSubCategory;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvCategorytitle);
            ivSubCategory = itemView.findViewById(R.id.ivSubCategory);


        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_all_sub_category, parent, false);
        InnerRecyclerViewAdapter.ViewHolder vh = new InnerRecyclerViewAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Subcategory childText = listDataHeader1.get(groupPosition).getSubcategory().get(position);

        holder.name.setText(childText.getSubcatName());

     /*   if (childText.getSubcatImage() != null) {
            Picasso.with(context).load(childText.getSubcatImage()).placeholder(R.drawable.no_image).fit().into(holder.ivSubCategory, new com.squareup.picasso.Callback() {
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
         Glide.with(context).load(childText.getSubcatImage() != null ? childText.getSubcatImage() : "").into(holder.ivSubCategory);
/*
    holder.name.setText(childText);
    holder.ivSubCategory.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(context, "Clicked on "+childText, Toast.LENGTH_LONG).show();
        }
    });*/
    }

    @Override
    public int getItemCount() {
        return listDataHeader1.size();
    }

}