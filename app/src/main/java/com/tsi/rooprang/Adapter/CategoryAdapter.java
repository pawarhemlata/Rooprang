package com.tsi.rooprang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tsi.rooprang.DTO.parent_all_category_dtos.Category_Detail_DTO;
import com.tsi.rooprang.DTO.parent_all_category_dtos.Category_List_DTO;
import com.tsi.rooprang.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    private Context mContext;
    private List<Category_Detail_DTO> category_list_dtoList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView iv_category;
        TextView tv_category;

        public MyViewHolder(View view) {
            super(view);
            iv_category = view.findViewById(R.id.iv_category);
            tv_category = view.findViewById(R.id.tv_category);
        }
    }


    public CategoryAdapter(Context mContext, List<Category_Detail_DTO> category_list_dtoList) {
        this.mContext = mContext;
        this.category_list_dtoList = category_list_dtoList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_row_category, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.tv_category.setText(category_list_dtoList.get(position).getCategoryName());
        Glide.with(mContext).load(category_list_dtoList.get(position).getCategoryImage()).into(holder.iv_category);

    }


    @Override
    public int getItemCount() {
        return category_list_dtoList.size();
    }

}
