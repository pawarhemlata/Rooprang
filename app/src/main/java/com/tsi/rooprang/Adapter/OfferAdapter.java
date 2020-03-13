package com.tsi.rooprang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tsi.rooprang.DTO.singleproduct.ProductAvailabelAge;
import com.tsi.rooprang.DTO.singleproduct.ProductOffers;
import com.tsi.rooprang.R;

import java.util.ArrayList;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.MyViewHolder> {
    private ArrayList<ProductOffers> productOffers;
    private Context context;




    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_offer;

        public MyViewHolder(View view) {
            super(view);

            tv_offer = (TextView) view.findViewById(R.id.tv_offer);

        }
    }

    public OfferAdapter(Context context, ArrayList<ProductOffers> productOffers) {
        this.productOffers = productOffers;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_offer_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        ProductOffers  dataModel = productOffers.get(position);

        holder.tv_offer.setText(dataModel.getOfferName());

    }

    @Override
    public int getItemCount() {
       return productOffers.size();
    }
}