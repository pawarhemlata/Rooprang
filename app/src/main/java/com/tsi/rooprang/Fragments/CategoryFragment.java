package com.tsi.rooprang.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.tsi.rooprang.Activity.ProductActivity;
import com.tsi.rooprang.Adapter.CustomExpandableListAdapter;
import com.tsi.rooprang.DTO.parent_all_category_dtos.Category_DTO;
import com.tsi.rooprang.DTO.parent_all_category_dtos.Category_List_DTO;
import com.tsi.rooprang.LoginSignIn.OTPActivity;
import com.tsi.rooprang.R;
import com.tsi.rooprang.retrofit.ApiClient;
import com.tsi.rooprang.retrofit.ApiInterface;
import com.tsi.rooprang.retrofit.NewApiClient;
import com.tsi.rooprang.utils.Utility;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CategoryFragment extends Fragment {

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    ProgressDialog mProgressDialog;

    ApiInterface apiInterface;
    List<Category_List_DTO> category_list_dtos;
    View view;
    private int lastExpandedPosition = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_category, container, false);
        mProgressDialog = new ProgressDialog(getActivity());
        apiInterface = NewApiClient.getClient().create(ApiInterface.class);


        category_list_dtos = new ArrayList<>();
        expandableListView = (ExpandableListView) view.findViewById(R.id.expandableListView);
        expandableListAdapter = new CustomExpandableListAdapter(getActivity(), category_list_dtos);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            expandableListView.setNestedScrollingEnabled(false);
        }
        category_list();

        select_item_from_list();

        return view;
    }

    private void select_item_from_list() {
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
               /* Toast.makeText(getActivity(),
                        category_list_dtos.get(groupPosition).getCategoryName().toString() + "",
                        Toast.LENGTH_SHORT).show();*/

                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    expandableListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {

                /*Toast.makeText(getActivity(),
                        category_list_dtos.get(groupPosition).getCategoryName() + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();*/


            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                String pro_id = category_list_dtos.get(groupPosition).getSubcategory().get(childPosition).getParentSubcategoryId();
                String pro_name = category_list_dtos.get(groupPosition).getSubcategory().get(childPosition).getSubcatName();

                Intent intent = new Intent(getActivity(), ProductActivity.class);
                intent.putExtra("sub_category_id", pro_id);
                intent.putExtra("item_name", pro_name);
                startActivity(intent);


                return false;
            }
        });


    }

    private void category_list() {
        try {
            if (Utility.isConnectingToInternet(getActivity())) {
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();
        final Call<Category_DTO> user_dtoCall = apiInterface.category_api("1");
        user_dtoCall.enqueue(new Callback<Category_DTO>() {
            @Override
            public void onResponse(Call<Category_DTO> call, Response<Category_DTO> response) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                Category_DTO category_dto = response.body();
                if (category_dto.getStatus().equals("1")) {
                    category_list_dtos.addAll(category_dto.getData());
                    expandableListView.setAdapter(expandableListAdapter);
                }

            }

            @Override
            public void onFailure(Call<Category_DTO> call, Throwable t) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                Log.e("onFailure", " " + t.getMessage());
            }
        });       }
            else {
                Toast.makeText(getActivity(), "Please Connect Internet !", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
