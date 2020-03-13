package com.tsi.rooprang.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.tsi.rooprang.Activity.ProductActivity;
import com.tsi.rooprang.Activity.SubSubCategoryActivity;
import com.tsi.rooprang.DTO.Sub_cat_dtos.Sub_Cat_Details_DTO;
import com.tsi.rooprang.R;
import java.util.ArrayList;
import java.util.List;

public class SubSubCategoryAdapter extends RecyclerView.Adapter<SubSubCategoryAdapter.MyViewHolder> implements Filterable{

    private List<Sub_Cat_Details_DTO> contactList;
    private List<Sub_Cat_Details_DTO> contactListFiltered;
    ContactsAdapterListener listener;
    private Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;
        public TextView iv_text;
        public LinearLayout layout_cat_list;
        ProgressBar pbbanner;

        public MyViewHolder(View view) {
            super(view);
            pbbanner = (ProgressBar) view.findViewById(R.id.pbbanner);
            img = (ImageView) view.findViewById(R.id.ivSubCategory);
            iv_text = (TextView) view.findViewById(R.id.tvCategorytitle);
            layout_cat_list= view.findViewById(R.id.linearCategory);
        }
    }


    public SubSubCategoryAdapter(Context context, ArrayList<Sub_Cat_Details_DTO> categoryModelArrayList, ContactsAdapterListener stringListener) {
        this.contactList = categoryModelArrayList;
        this.context = context;
        this.listener = stringListener;
        this.contactListFiltered = categoryModelArrayList;



    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sub_category, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


// DataCatModel dataModel = dataModelList.get(position);

        final Sub_Cat_Details_DTO singleton = contactListFiltered.get(position);


        if(!singleton.getImage().isEmpty())
        {
            Picasso.with(context).load(singleton.getImage()).placeholder(R.drawable.no_image).fit().into(holder.img, new Callback()
            {
                @Override
                public void onSuccess()
                {
                    holder.pbbanner.setVisibility(View.GONE);
                }
                @Override
                public void onError()
                {
                    holder.pbbanner.setVisibility(View.GONE);
                }
            });
        }
        else
        {
            holder.pbbanner.setVisibility(View.GONE);
        }
//holder.iv_text.setTypeface(MontReg);

        holder.iv_text.setText(singleton.getName());
        holder.layout_cat_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductActivity.class);
                intent.putExtra("item_name", contactList.get(position).getName());
                intent.putExtra("sub_category_id", contactList.get(position).getParentSubCategoryId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
/* context.startActivity(new Intent(context, SubSubCategoryActivity.class).
putExtra("item_name",singleton.getName()).
putExtra("product_id",singleton.getParentSubCategoryId()).*/

            }
        });
    }

/*
Glide.with(context).load(contactList.get(position).getImage()).into(holder.img);
holder.iv_text.setText(contactList.get(position).getName());
holder.layout_cat_list.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
Intent intent = new Intent(context, ProductActivity.class);
intent.putExtra("item_name", contactList.get(position).getName());
intent.putExtra("product_id", contactList.get(position).getParentSubCategoryId());
intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
context.startActivity(intent);


}
});
}*/

    @Override
    public int getItemCount()
    {
        return contactListFiltered.size();
    }

    @Override
    public Filter getFilter()
    {
        return new Filter()
        {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence)
            {
                String charString = charSequence.toString();
                if (charString.isEmpty())
                {
                    contactListFiltered = contactList;
                }
                else
                {
                    List<Sub_Cat_Details_DTO> filteredList = new ArrayList<>();
                    for (Sub_Cat_Details_DTO row : contactList)
                    {
                        if(row.getName()==null) {}
                        else
                        {
                            if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getName().contains(charSequence))
                            {
                                filteredList.add(row);
                            }
                        }
                    }
                    contactListFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults)
            {
                contactListFiltered = (ArrayList<Sub_Cat_Details_DTO>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ContactsAdapterListener
    {
        void onContactSelected(Sub_Cat_Details_DTO contact);
    }
}