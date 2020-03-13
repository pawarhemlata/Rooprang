package com.tsi.rooprang.Adapter;

import android.content.Context;
import android.content.Intent;
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
import com.tsi.rooprang.Activity.ProductDescriptionActivity;
import com.tsi.rooprang.DTO.Dashboard.Trending_Detail_DTO;
import com.tsi.rooprang.DTO.DataModel.DataModelHome3;
import com.tsi.rooprang.DTO.wishlist.Add_Wish_DTO;
import com.tsi.rooprang.DTO.wishlist.Remove_wish_DTO;
import com.tsi.rooprang.LoginSignIn.SignInActivity;
import com.tsi.rooprang.R;
import com.tsi.rooprang.Session.SessionManager;
import com.tsi.rooprang.retrofit.ApiInterface;
import com.tsi.rooprang.retrofit.NewApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeAdapterTrending extends RecyclerView.Adapter<HomeAdapterTrending.MyViewHolder> {

    private List<Trending_Detail_DTO> trending_detail_dtos;
    private Context context;
    ApiInterface apiService;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView img_trendy;
        public TextView title, tv_price_trendy;
        public RelativeLayout rel_layout;
        private LikeButton btn_wishlist;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tv_trendy);
            tv_price_trendy = (TextView) view.findViewById(R.id.tv_price_trendy);
            img_trendy = (ImageView) view.findViewById(R.id.img_trendy);
            btn_wishlist = (LikeButton) view.findViewById(R.id.btn_wishlist);
            btn_wishlist.bringToFront();
            /*rel_layout=(RelativeLayout)view.findViewById(R.id.rel_layout);*/


        }
    }


    public HomeAdapterTrending(Context context, List<Trending_Detail_DTO> trending_detail_dtos) {
        this.trending_detail_dtos = trending_detail_dtos;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_trendy_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Trending_Detail_DTO trending_detail_dto = trending_detail_dtos.get(position);

        holder.title.setText(trending_detail_dto.getProductName());
        holder.tv_price_trendy.setText("Rs." + trending_detail_dto.getRegularPrice());
        apiService = NewApiClient.getClient().create(ApiInterface.class);


       /* if (trending_detail_dto.getImage() != null) {
            Picasso.with(context).load(trending_detail_dto.getImage()).placeholder(R.drawable.no_image).fit().into(holder.img_trendy, new com.squareup.picasso.Callback() {
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

        Glide.with(context).load(trending_detail_dto.getImage()).into(holder.img_trendy);

        holder.img_trendy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDescriptionActivity.class);
                intent.putExtra("product_id", trending_detail_dtos.get(position).getProductId());
                intent.putExtra("product_title", trending_detail_dtos.get(position).getProductName());
                intent.putExtra("Sub_cat_id", trending_detail_dtos.get(position).getSubCategoryId());
                context.startActivity(intent);
            }

        });


        holder.btn_wishlist.setOnLikeListener(new OnLikeListener() {

            String user_id = SessionManager.getInstance(context).get_data_from_session(SessionManager.KEY_USERID);

            @Override
            public void liked(LikeButton likeButton) {
                if (SessionManager.getInstance(context).get_data_from_session(SessionManager.IS_LOGIN).equalsIgnoreCase("true")) {

                    Call<Add_Wish_DTO> add_wish_dtoCall = apiService.api_add_wishlist(user_id, trending_detail_dtos.get(position).getProductId());
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
                } else {
                    Toast.makeText(context, "Please Login First", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, SignInActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                if (SessionManager.getInstance(context).get_data_from_session(SessionManager.IS_LOGIN).equalsIgnoreCase("true")) {

                    Call<Remove_wish_DTO> remove_wish_dtoCall = apiService.api_remove_wishlist(user_id, trending_detail_dtos.get(position).getProductId());
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
                } else {
                    Toast.makeText(context, "Please Login First", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, SignInActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return trending_detail_dtos.size();
    }
}