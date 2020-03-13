package com.tsi.rooprang.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.tsi.rooprang.Account.Saved_Address_Activity;
import com.tsi.rooprang.Activity.ProductDescriptionActivity;
import com.tsi.rooprang.DTO.cart.Cart_Details_DTO;
import com.tsi.rooprang.DTO.cart.Remove_Cart_DTO;
import com.tsi.rooprang.Fragments.CartFragment;
import com.tsi.rooprang.R;
import com.tsi.rooprang.Session.SessionManager;
import com.tsi.rooprang.retrofit.ApiInterface;
import com.tsi.rooprang.retrofit.NewApiClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tsi.rooprang.Fragments.CartFragment.cartFragment;
import static com.tsi.rooprang.Fragments.CartFragment.lay_main;
import static com.tsi.rooprang.Fragments.CartFragment.lay_no_item;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    private List<Cart_Details_DTO> cart_details_dtos;
    public Context context;
    ApiInterface apiService;
    AlertDialog.Builder builder;
    StringBuilder sb, sb_sale, sb_qty, sb_dis;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView img, del, wishfill;
        public LinearLayout linear_cart;
        public TextView cardcategory, cardprice, orgprice, offpercent, description;
        private TextView tv_quantity_item;

        public MyViewHolder(View view) {
            super(view);
            img = (ImageView) view.findViewById(R.id.cartimage);
            linear_cart = view.findViewById(R.id.linear_cart);
            del = (ImageView) view.findViewById(R.id.cartdelete);
            cardcategory = (TextView) view.findViewById(R.id.cartcategory);
            cardprice = (TextView) view.findViewById(R.id.cartprice);
            orgprice = (TextView) view.findViewById(R.id.cart_orignal_price);
            offpercent = (TextView) view.findViewById(R.id.cart_off);
            tv_quantity_item = view.findViewById(R.id.tv_quantity_item);
        }
    }

    public CartAdapter(Context context, List<Cart_Details_DTO> cart_details_dtos) {
        this.cart_details_dtos = cart_details_dtos;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_cart, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        apiService = NewApiClient.getClient().create(ApiInterface.class);
        builder = new AlertDialog.Builder(context);
        Cart_Details_DTO cart_details_dto = cart_details_dtos.get(position);
        holder.cardcategory.setText(cart_details_dtos.get(position).getProductName());
        holder.orgprice.setPaintFlags(holder.orgprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.cardprice.setText(cart_details_dtos.get(position).getSalePrice());
        holder.orgprice.setText(cart_details_dtos.get(position).getSalePrice());
        holder.offpercent.setText(cart_details_dtos.get(position).getDiscount() + "% off");


       /* if (cart_details_dtos.get(position).getImage() != null) {
            Picasso.with(context).load(cart_details_dtos.get(position).getImage()).placeholder(R.drawable.no_image).fit().into(holder.img, new com.squareup.picasso.Callback() {
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

        Glide.with(context).load(cart_details_dtos.get(position).getImage()).into(holder.img);
        holder.tv_quantity_item.setText(context.getResources().getString(R.string.quantity) + " " + cart_details_dtos.get(position).getQty());

        sb = new StringBuilder();
        sb_sale = new StringBuilder();
        sb_qty = new StringBuilder();
        sb_dis = new StringBuilder();
        if (cart_details_dtos != null) {
            String product_id = "", sale_price = "", qty = "", discount = "";
            String vpro_id = "", vsal_price = "", vqty_ = "", vdisc = "";

            for (int i = 0; i < cart_details_dtos.size(); i++) {
                product_id = cart_details_dtos.get(i).getProductId();
                sale_price = cart_details_dtos.get(i).getSalePrice();
                qty = cart_details_dtos.get(i).getQty();
                discount = cart_details_dtos.get(i).getDiscount();


                if (sb.length() > 0) {
                    sb.append(',');
                }
                if (sb_sale.length() > 0) {
                    sb_sale.append(',');
                }
                if (sb_qty.length() > 0) {
                    sb_qty.append(',');
                }
                if (sb_dis.length() > 0) {
                    sb_dis.append(',');
                }
                sb.append(product_id);
                sb_sale.append(sale_price);
                sb_qty.append(qty);
                sb_dis.append(discount);

                vpro_id = sb.toString();
                vsal_price = sb_sale.toString();
                vqty_ = sb_qty.toString();
                vdisc = sb_dis.toString();
            }
            String pro_id = vpro_id, sal_price = vsal_price, qty_ = vqty_, disc = vdisc;
           String user_id= SessionManager.getInstance(context).get_data_from_session(SessionManager.KEY_USERID);
            Log.e("product......", "prod......" + pro_id + " " + sal_price + " " + qty_ + " " + disc);
            if (cart_details_dtos.size() > 0) {
                if (cartFragment != null) {
                    cartFragment.cart_amount_calculation(user_id, pro_id, sal_price, qty_, disc);

                    cartFragment.proceed_to_checkout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(context, Saved_Address_Activity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("product_ids", pro_id);
                            bundle.putString("sale_price", sal_price);
                            bundle.putString("qty", qty_);
                            bundle.putString("discount", disc);
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                        }
                    });
                }
            } else {
                lay_main.setVisibility(View.GONE);
                lay_no_item.setVisibility(View.VISIBLE);

            }

        }


        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setMessage(context.getResources().getString(R.string.delete_item_from_cart))
                        .setCancelable(false)
                        .setPositiveButton(context.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                remove_api(cart_details_dtos.get(position).getUserId(), cart_details_dtos.get(position).getProductId(), position);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDescriptionActivity.class);
                intent.putExtra("product_id", cart_details_dtos.get(position).getProductId());
                intent.putExtra("Sub_cat_id", cart_details_dtos.get(position).getSubCategoryId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    private void remove_api(String userId, String productId, int position) {
        ProgressDialog mProgressDialog = new ProgressDialog(context);

        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();

        Call<Remove_Cart_DTO> cartDtoCall = apiService.remove_cart_api(userId, productId);

        cartDtoCall.enqueue(new Callback<Remove_Cart_DTO>() {
            @Override
            public void onResponse(Call<Remove_Cart_DTO> call, Response<Remove_Cart_DTO> response) {
                Remove_Cart_DTO remove_cart_dto = response.body();
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                if (remove_cart_dto.getMessage().equalsIgnoreCase("success")) {
                    cart_details_dtos.remove(position);
                    notifyDataSetChanged();
                    if (cart_details_dtos.size() == 0) {
                        lay_main.setVisibility(View.GONE);
                        lay_no_item.setVisibility(View.VISIBLE);
                    }else {
                        lay_main.setVisibility(View.VISIBLE);
                        lay_no_item.setVisibility(View.GONE);
                    }


                }
            }

            @Override
            public void onFailure(Call<Remove_Cart_DTO> call, Throwable t) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                Log.e("failure", t + "");
            }
        });
    }

    @Override
    public int getItemCount() {
        return cart_details_dtos.size();
    }
}