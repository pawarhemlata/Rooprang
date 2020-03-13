package com.tsi.rooprang.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;
import com.tsi.rooprang.Account.Wishlist;
import com.tsi.rooprang.Activity.ProductActivity;
import com.tsi.rooprang.Activity.ProductDescriptionActivity;
import com.tsi.rooprang.DTO.Sub_cat_dtos.Sub_Category_DTO;
import com.tsi.rooprang.DTO.product.Product_Details_DTO;
import com.tsi.rooprang.DTO.wishlist.Add_Wish_DTO;
import com.tsi.rooprang.DTO.wishlist.Remove_wish_DTO;
import com.tsi.rooprang.LoginSignIn.SignInActivity;
import com.tsi.rooprang.R;
import com.tsi.rooprang.Session.SessionManager;
import com.tsi.rooprang.retrofit.ApiInterface;
import com.tsi.rooprang.retrofit.BaseUrl;
import com.tsi.rooprang.retrofit.NewApiClient;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tsi.rooprang.Activity.ProductActivity.productActivity;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private ArrayList<Product_Details_DTO> dataModelList;
    public Context context;
    ApiInterface apiService;
    private String product_id;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;
        LikeButton btn_wishlist;


        public TextView cardcategory, saleprice, orgprice, offpercent;

        public MyViewHolder(View view) {
            super(view);
            apiService = NewApiClient.getClient().create(ApiInterface.class);

            img = (ImageView) view.findViewById(R.id.img_product);
            btn_wishlist = (LikeButton) view.findViewById(R.id.btn_wishlist);
            cardcategory = (TextView) view.findViewById(R.id.tv_product_title);
            saleprice = (TextView) view.findViewById(R.id.tv_product_price);
            orgprice = (TextView) view.findViewById(R.id.tv_product_orignal_price);
            offpercent = (TextView) view.findViewById(R.id.tv_product_off);


            btn_wishlist.bringToFront();
        }
    }

    public ProductAdapter(Context context, ArrayList<Product_Details_DTO> dataModelList) {
        this.dataModelList = dataModelList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_product_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.cardcategory.setText(dataModelList.get(position).getProductName());
        holder.orgprice.setPaintFlags(holder.orgprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.saleprice.setText(dataModelList.get(position).getSalePrice());
        holder.orgprice.setText(dataModelList.get(position).getRegularPrice());
        holder.offpercent.setText(dataModelList.get(position).getDiscount() + "%");

        if (dataModelList.get(position).getWishlistStatus()!=null) {
            if (dataModelList.get(position).getWishlistStatus().equals("0")) {
                holder.btn_wishlist.setLiked(false);
            } else if (dataModelList.get(position).getWishlistStatus().equals("1")) {
                holder.btn_wishlist.setLiked(true);
            }

        }

        product_id = dataModelList.get(position).getProductId();

        /*if (dataModelList.get(position).getImage() != null) {
            Picasso.with(context).load(dataModelList.get(position).getImage()).placeholder(R.drawable.no_image).fit().into(holder.img, new com.squareup.picasso.Callback() {
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

        Glide.with(context).load(dataModelList.get(position).getImage()).into(holder.img);


        holder.btn_wishlist.setOnLikeListener(new OnLikeListener() {

            String user_id = SessionManager.getInstance(context).get_data_from_session(SessionManager.KEY_USERID);
            @Override
            public void liked(LikeButton likeButton) {


                Call<Add_Wish_DTO> add_wish_dtoCall = apiService.api_add_wishlist(user_id,dataModelList.get(position).getProductId());
                add_wish_dtoCall.enqueue(new Callback<Add_Wish_DTO>() {
                    @Override
                    public void onResponse(Call<Add_Wish_DTO> call, Response<Add_Wish_DTO> response) {
                        try {
                            Add_Wish_DTO add_wish_dto = response.body();
                            if (add_wish_dto.getMessage().equals("success")) {

                                Toast.makeText(context, "added to wishlist", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call<Add_Wish_DTO> call, Throwable t) {

                    }
                });
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                Call<Remove_wish_DTO> remove_wish_dtoCall = apiService.api_remove_wishlist(user_id, dataModelList.get(position).getProductId());
                remove_wish_dtoCall.enqueue(new Callback<Remove_wish_DTO>() {
                    @Override
                    public void onResponse(Call<Remove_wish_DTO> call, Response<Remove_wish_DTO> response) {
                        Remove_wish_DTO remove_wish_dto = response.body();
                        if (remove_wish_dto.getMessage().equals("success")) {
                            Toast.makeText(context, "Item removed from Wishlist", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Remove_wish_DTO> call, Throwable t) {
                        Log.e("repsonse", t + "");
                    }
                });
            }
        });


        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDescriptionActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("product_id", dataModelList.get(position).getProductId());
                intent.putExtra("Sub_cat_id", dataModelList.get(position).getSubCategoryId());
                intent.putExtra("product_title", dataModelList.get(position).getProductName());
                context.startActivity(intent);

            }
        });
    }
    private void remove_to_wishlist() {
        Call<Remove_wish_DTO> remove_wish_dtoCall = apiService.api_remove_wishlist(SessionManager.getInstance(context).get_data_from_session(SessionManager.KEY_USERID), product_id);
        remove_wish_dtoCall.enqueue(new Callback<Remove_wish_DTO>() {
            @Override
            public void onResponse(Call<Remove_wish_DTO> call, Response<Remove_wish_DTO> response) {
                Remove_wish_DTO remove_wish_dto = response.body();
                if (remove_wish_dto.getMessage().equals("success")) {
                    Toast.makeText(context, "Item removed from Wishlist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Remove_wish_DTO> call, Throwable t) {
                Log.e("repsonse", t + "");
            }
        });
    }


    private void add_to_wishlist() {
        if (SessionManager.getInstance(context).get_data_from_session(SessionManager.IS_LOGIN).equalsIgnoreCase("true")) {
            Call<Add_Wish_DTO> add_wish_dtoCall = apiService.api_add_wishlist(SessionManager.getInstance(context).get_data_from_session(SessionManager.KEY_USERID), product_id);
            add_wish_dtoCall.enqueue(new Callback<Add_Wish_DTO>() {
                @Override
                public void onResponse(Call<Add_Wish_DTO> call, Response<Add_Wish_DTO> response) {
                    Add_Wish_DTO add_wish_dto = response.body();
                    if (add_wish_dto.getMessage().equals("success")) {
                        Toast.makeText(context, "Item Added in Wishlist", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Add_Wish_DTO> call, Throwable t) {
                    Log.e("repsonse", t + "");
                }
            });

        } else {
            Toast.makeText(context, "Please Login First", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, SignInActivity.class);
            context.startActivity(intent);
            if (ProductActivity.productActivity != null) {
                productActivity.finish();
            }
        }
    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }


}