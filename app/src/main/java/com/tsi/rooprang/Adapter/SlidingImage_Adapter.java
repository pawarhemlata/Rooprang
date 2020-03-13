package com.tsi.rooprang.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.tsi.rooprang.Activity.ProductActivity;
import com.tsi.rooprang.DTO.Dashboard.Home_All_Banner_Detail_DTO;
import com.tsi.rooprang.DTO.Dashboard.Pager_Detail_DTO;
import com.tsi.rooprang.R;

import java.util.ArrayList;

public class SlidingImage_Adapter extends PagerAdapter {


    private ArrayList<Pager_Detail_DTO> pager_detail_dtos;
    private LayoutInflater inflater;
    private Context context;
    public SlidingImage_Adapter(Context context, ArrayList<Pager_Detail_DTO> pager_detail_dtos) {
        this.context = context;
        this.pager_detail_dtos = pager_detail_dtos;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return pager_detail_dtos.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image_slider);

        Pager_Detail_DTO pager_detail_dto = pager_detail_dtos.get(position);

       /* if (pager_detail_dto.getBanner() != null) {
            Picasso.with(context).load(pager_detail_dto.getBanner()).placeholder(R.drawable.no_image).fit().into(imageView, new com.squareup.picasso.Callback() {
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


        Glide.with(context).load(pager_detail_dto.getBanner()).into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductActivity.class);
                intent.putExtra("item_name", pager_detail_dtos.get(position).getBanner_name() != null ? pager_detail_dtos.get(position).getBanner_name() : "");
                intent.putExtra("sub_category_id", pager_detail_dtos.get(position).getSubcategoryId() != null ? pager_detail_dtos.get(position).getSubcategoryId() : "");
                context.startActivity(intent);
            }
        });

        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}
