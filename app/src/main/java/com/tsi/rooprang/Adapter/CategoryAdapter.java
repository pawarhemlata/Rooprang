package com.tsi.rooprang.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.tsi.rooprang.Activity.MainActivity;
import com.tsi.rooprang.Activity.SubSubCategoryActivity;
import com.tsi.rooprang.DTO.parent_all_category_dtos.Category_Detail_DTO;
import com.tsi.rooprang.DTO.parent_all_category_dtos.Category_List_DTO;
import com.tsi.rooprang.Fragments.CategoryFragment;
import com.tsi.rooprang.Fragments.HomeFragment;
import com.tsi.rooprang.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.tsi.rooprang.Activity.MainActivity.bottomNavigation;
import static com.tsi.rooprang.Activity.MainActivity.mainActivity;
import static com.tsi.rooprang.Fragments.HomeFragment.homeFragment;

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
        Category_Detail_DTO category_detail_dto = category_list_dtoList.get(position);
        try {
            holder.tv_category.setText(category_detail_dto.getCategoryName() != null ? category_detail_dto.getCategoryName() : "");
            if (category_list_dtoList.get(position).getCategoryImage() != null) {
                Glide.with(mContext).load(category_list_dtoList.get(position).getCategoryImage()).error(R.drawable.no_image).into(holder.iv_category);
            }


            holder.iv_category.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (category_list_dtoList.get(position).getParentCategoryId().equals("1")) {
                        ((HomeFragment) homeFragment).click_on_all();
                    } else {
                        Intent intent = new Intent(mContext, SubSubCategoryActivity.class);
                        intent.putExtra("item_name", category_list_dtoList.get(position).getCategoryName());
                        intent.putExtra("item_position", category_list_dtoList.get(position).getParentCategoryId());
                        mContext.startActivity(intent);

                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public int getItemCount() {
        return category_list_dtoList.size();
    }

}
