package com.tsi.rooprang.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.tsi.rooprang.Adapter.WishlistAdapter;
import com.tsi.rooprang.DTO.DataModel.WishlistDataModel;
import com.tsi.rooprang.DTO.Sub_cat_dtos.Sub_Category_DTO;
import com.tsi.rooprang.DTO.wishlist.ShowWish_Detail_DTO;
import com.tsi.rooprang.DTO.wishlist.Show_Wish_DTO;
import com.tsi.rooprang.LoginSignIn.OTPActivity;
import com.tsi.rooprang.LoginSignIn.SignInActivity;
import com.tsi.rooprang.R;
import com.tsi.rooprang.Session.SessionManager;
import com.tsi.rooprang.retrofit.ApiInterface;
import com.tsi.rooprang.retrofit.NewApiClient;
import com.tsi.rooprang.utils.Utility;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishlistFragment extends Fragment {

    private ArrayList<ShowWish_Detail_DTO> showWish_detail_dtos;
    private RecyclerView recyclerView;
    private WishlistAdapter mAdapter;
    ProgressDialog mProgressDialog;
    ApiInterface apiService;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView.LayoutManager mLayoutManager;

    Toolbar tool;

    public static WishlistFragment newInstance(String param1, String param2) {
        WishlistFragment fragment = new WishlistFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wishlist, container,false);
        mProgressDialog = new ProgressDialog(getActivity());
        recyclerView = (RecyclerView) view.findViewById(R.id.wishrecyView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);

        apiService = NewApiClient.getClient().create(ApiInterface.class);
        showWish_detail_dtos = new ArrayList<>();
        mAdapter = new WishlistAdapter(getActivity(), showWish_detail_dtos);
        mLayoutManager = new  GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                try {
                    if (Utility.isConnectingToInternet(getActivity())) {

                        WishlistFragment wishlistFragment = new WishlistFragment();

                        FragmentManager fragmentManager2 = getFragmentManager();
                        FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                        fragmentTransaction2.setCustomAnimations(android.R.anim.fade_in,
                                android.R.anim.fade_out);
                        fragmentTransaction2.replace(R.id.container, wishlistFragment);

                        fragmentTransaction2.commit();
                    }else {
                        Toast.makeText(getActivity(), "Please Connect Internet !", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }}
        });
        showWish();




        return view;
    }



    private void showWish() {
        try {
            if (Utility.isConnectingToInternet(getActivity())) {
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setMessage("Loading...");
                mProgressDialog.show();

                Call<Show_Wish_DTO> show_wish_dtoCall = apiService.api_show_wishlist(SessionManager.getInstance(getContext()).get_data_from_session(SessionManager.KEY_USERID));
                show_wish_dtoCall.enqueue(new Callback<Show_Wish_DTO>() {
                    @Override
                    public void onResponse(Call<Show_Wish_DTO> call, Response<Show_Wish_DTO> response) {
                        Show_Wish_DTO show_wish_dto = response.body();
                        if (mProgressDialog.isShowing()) {
                            mProgressDialog.dismiss();
                        }
                        if (show_wish_dto.getMessage().equals("success")) {

                            showWish_detail_dtos.addAll(show_wish_dto.getData());


                            mAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<Show_Wish_DTO> call, Throwable t) {
                        if (mProgressDialog.isShowing()) {
                            mProgressDialog.dismiss();
                        }
                        Log.e("onFailure", " " + t.getMessage());
                    }
                });
            } else {
                Toast.makeText(getActivity(), "Please Connect Internet !", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}