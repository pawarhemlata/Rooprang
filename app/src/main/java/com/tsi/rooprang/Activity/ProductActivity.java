package com.tsi.rooprang.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.tsi.rooprang.Adapter.ProductAdapter;
import com.tsi.rooprang.DTO.Address_DTO.Add_Address_DTO;
import com.tsi.rooprang.DTO.Fillter.FillterDto;
import com.tsi.rooprang.DTO.product.Product_DTO;
import com.tsi.rooprang.DTO.product.Product_Details_DTO;
import com.tsi.rooprang.Fragments.WishlistFragment;
import com.tsi.rooprang.R;
import com.google.android.material.tabs.TabLayout;
import com.tsi.rooprang.Session.SessionManager;
import com.tsi.rooprang.UserProfile.AddUserAddressActivity;
import com.tsi.rooprang.retrofit.ApiInterface;
import com.tsi.rooprang.retrofit.NewApiClient;
import com.tsi.rooprang.utils.AppHelper;
import com.tsi.rooprang.utils.Utility;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class ProductActivity extends AppCompatActivity {
    TabLayout tabLayout;
    private ViewPager viewPager;
    androidx.appcompat.widget.Toolbar toolbar;
    private String item_name = "";
    private BottomSheetBehavior sheetBehavior_sort_by, sheetBehavior_filter_sheet;
    private TextView open_bottom, openfilterbtm;
    private CardView bottom_sheet_sort_by;
    private RelativeLayout bottom_filter_sheet;
    Button btn1, btn2, btn3;
    ProgressDialog mProgressDialog;
    private ArrayList<Product_Details_DTO> dataModelGridList;
    private ProductAdapter gridAdapter;
    GridLayoutManager mLayoutproduct;
    private RecyclerView recycler_view_product;
    private Context context;
    ApiInterface apiService;
    String vSub_cat_id = "";
    LinearLayout llsort, llfilter;
    public static ProductActivity productActivity;
    int page_count = 1, last = 1;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    public boolean is_no_more_data = false;
    String page = "", keyword = "", brand_id = "", vprice_value = "", vage_name = "", vcolor_name = "", vdiscount_value = "", vsize_number = "";
    String selectedSort = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        mProgressDialog = new ProgressDialog(this);
        productActivity = this;
        context = this;
        apiService = NewApiClient.getClient().create(ApiInterface.class);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.product_pager);
        llfilter = findViewById(R.id.llfilter);
        llsort = findViewById(R.id.llsort);

        bottom_sheet_sort_by = findViewById(R.id.bottom_sheet_sort_by);
        bottom_filter_sheet = findViewById(R.id.bottom_filter_sheet);
        recycler_view_product = findViewById(R.id.recycler_view_product);

        btn1 = findViewById(R.id.txt_relevance);
        btn2 = findViewById(R.id.txt_price_l_h);
        btn3 = findViewById(R.id.txt_price_h_l);

        if (getIntent() != null) {
            item_name = getIntent().getStringExtra("item_name");
            vSub_cat_id = getIntent().getStringExtra("sub_category_id");
            page = getIntent().getStringExtra("page") != null ? getIntent().getStringExtra("page") : "";
            keyword = getIntent().getStringExtra("keyword");
            brand_id = getIntent().getStringExtra("brand_id");

            vprice_value = getIntent().getStringExtra("price_value");
            vage_name = getIntent().getStringExtra("age_name");
            vcolor_name = getIntent().getStringExtra("color_name");
            vdiscount_value = getIntent().getStringExtra("discount_value");
            vsize_number = getIntent().getStringExtra("size_number");

            toolbar.setTitle(item_name);
        }
        dataModelGridList = new ArrayList<>();
        if (dataModelGridList != null) {
            dataModelGridList.clear();
        }
        gridAdapter = new ProductAdapter(context, dataModelGridList);
        mLayoutproduct = new GridLayoutManager(context, 2);
        recycler_view_product.setLayoutManager(mLayoutproduct);
        recycler_view_product.setItemAnimator(new DefaultItemAnimator());
        recycler_view_product.setHasFixedSize(true);
        recycler_view_product.setAdapter(gridAdapter);


        llfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductActivity.this, PFilter.class);
                intent.putExtra("sub_category_id", vSub_cat_id);
                startActivity(intent);
            }
        });
        llsort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.bottamsort, null);
                final CheckBox cbnewest = (CheckBox) view.findViewById(R.id.cbnewest);
                final CheckBox cbhightolow = (CheckBox) view.findViewById(R.id.cbhightolow);
                final CheckBox cblowtohigh = (CheckBox) view.findViewById(R.id.cblowtohigh);
                final CheckBox cbpopularity = (CheckBox) view.findViewById(R.id.cbpopularity);
                RelativeLayout rlpopularity = (RelativeLayout) view.findViewById(R.id.rlpopularity);
                RelativeLayout rllowtohigh = (RelativeLayout) view.findViewById(R.id.rllowtohigh);
                RelativeLayout rlhightolow = (RelativeLayout) view.findViewById(R.id.rlhightolow);
                RelativeLayout rlnewest = (RelativeLayout) view.findViewById(R.id.rlnewest);

                final BottomSheetDialog dialog = new BottomSheetDialog(ProductActivity.this);
                dialog.setContentView(view);
                dialog.show();

                if (!selectedSort.isEmpty()) {
                    if (selectedSort.equalsIgnoreCase("popularity")) {
                        cbpopularity.setChecked(true);
                    }
                    if (selectedSort.equalsIgnoreCase("lowtohigh")) {
                        cblowtohigh.setChecked(true);
                    }
                    if (selectedSort.equalsIgnoreCase("hightolow")) {
                        cbhightolow.setChecked(true);
                    }
                    if (selectedSort.equalsIgnoreCase("newest")) {
                        cbnewest.setChecked(true);
                    }
                }

                cbpopularity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cbpopularity.setChecked(true);
                        cblowtohigh.setChecked(false);
                        cbhightolow.setChecked(false);
                        cbnewest.setChecked(false);
                        dialog.dismiss();
                        selectedSort = "popularity";
                        call_api_sortProduct(page_count, "1");
                    }
                });
                cblowtohigh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cbpopularity.setChecked(false);
                        cblowtohigh.setChecked(true);
                        cbhightolow.setChecked(false);
                        cbnewest.setChecked(false);
                        dialog.dismiss();
                        selectedSort = "lowtohigh";
                        call_api_sortProduct(page_count, "2");
                    }
                });
                cbhightolow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cbpopularity.setChecked(false);
                        cblowtohigh.setChecked(false);
                        cbhightolow.setChecked(true);
                        cbnewest.setChecked(false);
                        dialog.dismiss();
                        selectedSort = "hightolow";
                        call_api_sortProduct(page_count, "3");
                    }
                });
                cbnewest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cbpopularity.setChecked(false);
                        cblowtohigh.setChecked(false);
                        cbhightolow.setChecked(false);
                        cbnewest.setChecked(true);
                        dialog.dismiss();
                        selectedSort = "newest";
                        call_api_sortProduct(page_count, "4");
                    }
                });

                rlpopularity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cbpopularity.setChecked(true);
                        cblowtohigh.setChecked(false);
                        cbhightolow.setChecked(false);
                        cbnewest.setChecked(false);
                        dialog.dismiss();
                        selectedSort = "popularity";
                        call_api_sortProduct(page_count, "1");
                    }
                });
                rllowtohigh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cbpopularity.setChecked(false);
                        cblowtohigh.setChecked(true);
                        cbhightolow.setChecked(false);
                        cbnewest.setChecked(false);
                        dialog.dismiss();
                        selectedSort = "lowtohigh";
                        call_api_sortProduct(page_count, "2");

                    }
                });
                rlhightolow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cbpopularity.setChecked(false);
                        cblowtohigh.setChecked(false);
                        cbhightolow.setChecked(true);
                        cbnewest.setChecked(false);
                        dialog.dismiss();
                        selectedSort = "hightolow";
                        call_api_sortProduct(page_count, "3");
                    }
                });
                rlnewest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cbpopularity.setChecked(false);
                        cblowtohigh.setChecked(false);
                        cbhightolow.setChecked(false);
                        cbnewest.setChecked(true);
                        dialog.dismiss();
                        selectedSort = "newest";
                        call_api_sortProduct(page_count, "4");
                    }


                });

            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        try {
            if (Utility.isConnectingToInternet(ProductActivity.this)) {
                if (page.equalsIgnoreCase("PFilter")) {
                    apply_filter_api(page_count);
                } else {
                    call_api(page_count);
                }
            } else {
                Toast.makeText(ProductActivity.this, "Please Connect Internet !", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        AppHelper.setupLoadMore(recycler_view_product, new AppHelper.OnScrollToEnd() {
            @Override
            public void scrolledToEnd(int lastVisibleItem) {
                if (last != lastVisibleItem) {
                    last = lastVisibleItem;
                    page_count++;
                    try {
                        if (Utility.isConnectingToInternet(ProductActivity.this)) {
                            if (page.equalsIgnoreCase("PFilter")) {
                                apply_filter_api(page_count);
                            } else {
                                call_api(page_count);
                            }


                        } else {
                            Toast.makeText(ProductActivity.this, "Please Connect Internet !", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void call_api(int page_count1) {
        String id = SessionManager.getInstance(this).get_data_from_session(SessionManager.KEY_USERID);
        Call<Product_DTO> categoryDtoCall = apiService.api_product(vSub_cat_id, page_count1 + "", !id.equals("") ? id : "");
        categoryDtoCall.enqueue(new Callback<Product_DTO>() {
            @Override
            public void onResponse(Call<Product_DTO> call, Response<Product_DTO> response) {
                try {
                    Product_DTO category_dto = response.body();
                    if (category_dto.getMessage().equals("success")) {

                        dataModelGridList.addAll(category_dto.getData());
                        if (dataModelGridList != null) {
                            if (gridAdapter != null) {
                                gridAdapter.notifyDataSetChanged();
                            }
                        }
                    } else {
                        if (page_count > 0) {
                            --page_count;
                        }
                        last = 0;

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Product_DTO> call, Throwable t) {
                Log.e("onFailure", " " + t.getMessage());
                if (page_count > 0) {
                    --page_count;
                }
                last = 0;
            }
        });
    }

    private void call_api_sortProduct(int page_count1, String sortID) {
        dataModelGridList.clear();
        String id = SessionManager.getInstance(this).get_data_from_session(SessionManager.KEY_USERID);
        Call<Product_DTO> categoryDtoCall = apiService.api_product_sortProduct(!id.equals("") ? id : "", vSub_cat_id, page_count1 + "", sortID);
        categoryDtoCall.enqueue(new Callback<Product_DTO>() {
            @Override
            public void onResponse(Call<Product_DTO> call, Response<Product_DTO> response) {
                try {
                    Product_DTO category_dto = response.body();
                    if (category_dto.getMessage().equals("success")) {

                        dataModelGridList.addAll(category_dto.getData());
                        if (dataModelGridList != null) {
                            if (gridAdapter != null) {
                                gridAdapter.notifyDataSetChanged();
                            }
                        }
                    } else {
                        if (page_count > 0) {
                            --page_count;
                        }
                        last = 0;

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Product_DTO> call, Throwable t) {
                Log.e("onFailure", " " + t.getMessage());
                if (page_count > 0) {
                    --page_count;
                }
                last = 0;
            }
        });
    }

    public void apply_filter_api(int page_count1) {
//        if (dataModelGridList != null) {
//            dataModelGridList.clear();
//        }
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();

        Call<Product_DTO> add_address_dtoCall = apiService.apply_filter_api(vSub_cat_id, vprice_value, vdiscount_value, brand_id, vsize_number, vage_name,vcolor_name, page_count1 + "");
        add_address_dtoCall.enqueue(new Callback<Product_DTO>() {
            @Override
            public void onResponse(Call<Product_DTO> call, Response<Product_DTO> response) {
                try {
                    Product_DTO category_dto = response.body();
                    if (mProgressDialog != null) {
                        mProgressDialog.dismiss();
                    }
                    if (category_dto.getMessage().equals("success")) {

                        dataModelGridList.addAll(category_dto.getData());
                        if (dataModelGridList != null) {
                            if (gridAdapter != null) {
                                gridAdapter.notifyDataSetChanged();
                            }
                        }
                    } else {
                        if (page_count > 0) {
                            --page_count;

                        }
                        last = 0;
                        Toast.makeText(context, category_dto.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Product_DTO> call, Throwable t) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                if (page_count > 0) {
                    --page_count;
                }
                last = 0;
                Log.e("failure", t + "");
            }
        });
    }

}
