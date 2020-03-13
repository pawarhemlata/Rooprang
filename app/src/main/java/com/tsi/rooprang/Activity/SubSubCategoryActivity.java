package com.tsi.rooprang.Activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tsi.rooprang.Adapter.SubSubCategoryAdapter;
import com.tsi.rooprang.DTO.Search.SearchDto;
import com.tsi.rooprang.DTO.Sub_cat_dtos.Sub_Cat_Details_DTO;
import com.tsi.rooprang.DTO.Sub_cat_dtos.Sub_Category_DTO;

import com.tsi.rooprang.R;
import com.tsi.rooprang.retrofit.ApiInterface;
import com.tsi.rooprang.retrofit.NewApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubSubCategoryActivity extends AppCompatActivity implements  SubSubCategoryAdapter.ContactsAdapterListener
{
    Toolbar toolbar;
    private Context mContext;
    List<SearchDto> productPojoList;
    EditText etsearch;
    private ArrayList<Sub_Cat_Details_DTO> categoryModelArrayList;
    private SubSubCategoryAdapter categoryAdapter;
    private RecyclerView rvSubCategory;
    private ImageView ivSubCategoryImage;
    ImageView back, ivclose;
    RecyclerView.LayoutManager mLayoutManager;
    String item_name = "", item_pos = "", name = "";
    private ApiInterface apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_sub_category);

        toolbar = findViewById(R.id.toolbar);
        etsearch = findViewById(R.id.etsearch);
        ivclose = findViewById(R.id.ivclose);



        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rvSubCategory = (RecyclerView) findViewById(R.id.rvSubCategory);
        ivSubCategoryImage = findViewById(R.id.ivSubCategoryImage);
        categoryModelArrayList = new ArrayList<>();


        categoryAdapter = new SubSubCategoryAdapter(SubSubCategoryActivity. this, categoryModelArrayList,SubSubCategoryActivity.this);
        mLayoutManager = new GridLayoutManager(mContext, 2);
        rvSubCategory.setLayoutManager(mLayoutManager);
        rvSubCategory.scheduleLayoutAnimation();
        rvSubCategory.setItemAnimator(new DefaultItemAnimator());
        rvSubCategory.setAdapter(categoryAdapter);


        apiService = NewApiClient.getClient().create(ApiInterface.class);

        Bundle bundle = getIntent().getExtras();
        if (getIntent() != null) {
            item_name = getIntent().getStringExtra("item_name");
            item_pos = bundle.getString("item_position");


            Log.e("item_name", item_name + "");

            if (item_pos.equals("2")) {
                ivSubCategoryImage.setImageResource(R.drawable.expectingmother);
            } else if (item_pos.equals("3")) {
                ivSubCategoryImage.setImageResource(R.drawable.newborn);
            } else if (item_pos.equals("4")) {
                ivSubCategoryImage.setImageResource(R.drawable.baby_boy);
            } else if (item_pos.equals("5")) {
                ivSubCategoryImage.setImageResource(R.drawable.baby_girl);
            }

            toolbar.setTitle(item_name);
            //  searchApi();
            //  initialViews();
        }
        callApi();

    }

    private void callApi() {
        final Call<Sub_Category_DTO> sub_category_dtoCall = apiService.api_sub_category(item_pos);
        sub_category_dtoCall.enqueue(new Callback<Sub_Category_DTO>() {
            @Override
            public void onResponse(Call<Sub_Category_DTO> call, Response<Sub_Category_DTO> response) {
               /* if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }*/
                Sub_Category_DTO sub_category_dto = response.body();
                if (sub_category_dto.getMessage().equals("success")) {
                    categoryModelArrayList.addAll(sub_category_dto.getData());
                    categoryAdapter.notifyDataSetChanged();
                }

                etsearch.addTextChangedListener(new TextWatcher()
                {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
                    {}
                    @Override
                    public void onTextChanged(CharSequence s, int i, int i1, int i2)
                    {
                        categoryAdapter.getFilter().filter(s);
                    }
                    @Override
                    public void afterTextChanged(Editable editable)
                    {}
                });

            }

            @Override
            public void onFailure(Call<Sub_Category_DTO> call, Throwable t) {

                Log.e("repsonse", t + "");
            }
        });
    }

    @Override
    public void onContactSelected(Sub_Cat_Details_DTO contact) {

    }
}