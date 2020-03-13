package com.tsi.rooprang.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.tsi.rooprang.R;
import java.util.ArrayList;

public class BannerSliderAdapter extends PagerAdapter {
    private ArrayList<String> arrayList;
    private ArrayList<String> arrayListBanner;
    private ArrayList<String> arrayListpage_Banner_Id;
    private ArrayList<String> arrayListsub_Sub_Category_Id;
    private LayoutInflater inflater;
    private Context context;
    private static final String TAG = "BannerSliderAdapter";
 //   ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public BannerSliderAdapter(Context context, ArrayList<String> arrayList, ArrayList<String> arrayListpage_Banner_Id, ArrayList<String> arrayListsub_Sub_Category_Id) {
        this.context = context;
        this.arrayList = arrayList;
        this.arrayListpage_Banner_Id = arrayListpage_Banner_Id;
        this.arrayListsub_Sub_Category_Id = arrayListsub_Sub_Category_Id;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        View imageLayout = inflater.inflate(R.layout.item_slider_layout, view, false);
        assert imageLayout != null;

        final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.mipmap.ic_launcher);
        requestOptions.error(R.mipmap.ic_launcher);

       /* Picasso.with(context).load(arrayList.get(position)).placeholder(R.drawable.no_image).fit().into(imageView, new Callback()
        {
            @Override
            public void onSuccess()
            {
                pbbanner.setVisibility(View.GONE);
            }
            @Override
            public void onError()
            {
                pbbanner.setVisibility(View.GONE);
            }
        });*/
        Glide.with(context).load(arrayList.get(position)).into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*context.startActivity(new Intent(context, ProductListActivity.class)
                        .putExtra("page_banner_id",arrayListpage_Banner_Id.get(position))
                        .putExtra("subcategory_id",arrayListsub_Sub_Category_Id.get(position))
                        .putExtra("banner",arrayList.get(position))
                        .putExtra("page","HomeFragment")
                        .putExtra("keyword","1")
                );*/
            }
        });

        view.addView(imageLayout, 0);
        imageLayout.setClipToOutline(true);

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
