package com.tsi.rooprang.Adapter;

import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.tsi.rooprang.DTO.singleproduct.ProductImage;
import com.tsi.rooprang.R;

import java.util.ArrayList;

public class ProductSliderAdapter extends PagerAdapter {


    private ArrayList<ProductImage> IMAGES;
    private LayoutInflater inflater;
    private Context context;



    public ProductSliderAdapter(Context context, ArrayList<ProductImage> IMAGES) {
        this.context = context;
        this.IMAGES = IMAGES;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return IMAGES.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.product_slider_layout, view, false);

        ProgressBar pbbanner;



        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.product_slider);
        pbbanner = imageLayout.findViewById(R.id.pbbanner);



        Picasso.with(context).load(IMAGES.get(position).getProductImage()).into(imageView,
                new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                     pbbanner.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                      pbbanner.setVisibility(View.GONE);
                    }
                });


      //  Glide.with(context).load(IMAGES.get(position).getProductImage()).error(R.mipmap.no_image).into(imageView);

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
