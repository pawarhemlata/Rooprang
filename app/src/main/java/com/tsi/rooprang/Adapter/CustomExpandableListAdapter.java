package com.tsi.rooprang.Adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.tsi.rooprang.DTO.parent_all_category_dtos.Category_List_DTO;
import com.tsi.rooprang.DTO.parent_all_category_dtos.Subcategory;
import com.tsi.rooprang.R;


public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<Category_List_DTO> listDataHeader;


    public CustomExpandableListAdapter(Context context, List<Category_List_DTO> listDataHeader) {
        this._context = context;
        this.listDataHeader = listDataHeader;
    }

    @Override
    public List<Subcategory> getChild(int groupPosition, int childPosititon) {
        return this.listDataHeader.get(groupPosition).getSubcategory();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final Subcategory childText = listDataHeader.get(groupPosition).getSubcategory().get(childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.catg_child_item, null);
        }
 /*      RecyclerView recycler_categ = (RecyclerView) convertView.findViewById(R.id.recycler_categ);
        InnerRecyclerViewAdapter sbc=new InnerRecyclerViewAdapter(_context, listDataHeader,groupPosition,childText);
        recycler_categ .setLayoutManager(new GridLayoutManager(_context,2));
        recyclerView.setAdapter(sbc);*/
      //  pbbanner =  convertView.findViewById(R.id.pbbanner);


        TextView item_txt_sub_cat_name = (TextView) convertView.findViewById(R.id.tv_categry_child);
        ImageView iv_categry_child = (ImageView) convertView.findViewById(R.id.iv_categry_child);
        item_txt_sub_cat_name.setText(childText.getSubcatName());
      /*  if (childText.getSubcatImage() != null) {
            Picasso.with(_context).load(childText.getSubcatImage()).placeholder(R.drawable.no_image).fit().into(iv_categry_child, new com.squareup.picasso.Callback() {
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

         Glide.with(_context).load(childText.getSubcatImage() != null ? childText.getSubcatImage() : "").into(iv_categry_child);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listDataHeader.get(groupPosition).getSubcategory().size();
    }

    @Override
    public Category_List_DTO getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        Category_List_DTO headerTitle = getGroup(groupPosition);


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.catg_group_item, null);
        }
        ImageView iv_indigator = (ImageView) convertView.findViewById(R.id.iv_indigator);
        if (isExpanded) {
            iv_indigator.setImageResource(R.drawable.minus_icon_pink);
        } else {
            iv_indigator.setImageResource(R.drawable.add_icon_pink);
        }
        TextView item_txt_category_name = (TextView) convertView.findViewById(R.id.tv_catgry_group);

        item_txt_category_name.setText(headerTitle.getCategoryName());
        notifyDataSetChanged();
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}