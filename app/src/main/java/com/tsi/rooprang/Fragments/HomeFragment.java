package com.tsi.rooprang.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.tsi.rooprang.Activity.MainActivity;
import com.tsi.rooprang.Activity.ProductActivity;
import com.tsi.rooprang.Activity.SubSubCategoryActivity;
import com.tsi.rooprang.Adapter.CategoryAdapter;
import com.tsi.rooprang.Adapter.DealsAdapter;
import com.tsi.rooprang.Adapter.ShopByCategoryAdapter;
import com.tsi.rooprang.Adapter.BloomInStyleAdapter;
import com.tsi.rooprang.Adapter.HomeAdapterTrending;
import com.tsi.rooprang.Adapter.LatestProductAdapter;
import com.tsi.rooprang.Adapter.SlidingImage_Adapter;
import com.tsi.rooprang.Adapter.YourOrderAdapter;
import com.tsi.rooprang.DTO.Dashboard.BloomStyleDTO;
import com.tsi.rooprang.DTO.Dashboard.DealOfTheDay_Detail_DTO;
import com.tsi.rooprang.DTO.Dashboard.DealofDay_DTO;
import com.tsi.rooprang.DTO.Dashboard.HomeDTO;
import com.tsi.rooprang.DTO.Dashboard.Home_All_Banner_Detail_DTO;
import com.tsi.rooprang.DTO.Dashboard.Home_Detail_DTO;
import com.tsi.rooprang.DTO.Dashboard.LatestProduct_Detail_DTO;
import com.tsi.rooprang.DTO.Dashboard.Pager_Detail_DTO;
import com.tsi.rooprang.DTO.Dashboard.ShopByCategoryDTO;
import com.tsi.rooprang.DTO.Dashboard.Trending_DTO;
import com.tsi.rooprang.DTO.Dashboard.Trending_Detail_DTO;


import com.tsi.rooprang.DTO.orderhistory.OrderHistory_DTO;
import com.tsi.rooprang.DTO.parent_all_category_dtos.CategoryHome_DTO;
import com.tsi.rooprang.DTO.parent_all_category_dtos.Category_DTO;
import com.tsi.rooprang.DTO.parent_all_category_dtos.Category_Detail_DTO;
import com.tsi.rooprang.DTO.parent_all_category_dtos.Category_List_DTO;
import com.tsi.rooprang.R;
import com.tsi.rooprang.retrofit.ApiInterface;
import com.tsi.rooprang.retrofit.NewApiClient;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tsi.rooprang.Activity.MainActivity.bottomNavigation;

public class HomeFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    RelativeLayout r1;
    private String vexpectingmother, vall, vboy, vgirl, vnewborn;
    int[] animationList = {R.anim.layout_animation_up_to_down,
            R.anim.layout_animation_right_to_left,
            R.anim.layout_animation_down_to_up, R.anim.layout_animation_left_to_right};


    int i = 0;
    ProgressDialog progress;
    private View view;
    private Context context;
    private String vid = "0";
    public TextView view_all_baby, view_all_trendy, view_all_kids, txt_dod_view_all, view_all_latest;
    private TextView tv_banner_five, tv_banner_four, tv_banner_three, tv_banner_two, tv_banner_one, tv_categry_expecting, tv_categry_new_born, tv_categry_baby_boy, tv_categry_baby_girl, tv_categry_all;
    private ImageView iv_stripe, iv_party, iv_summer, iv_categry_all, iv_categry_expecting, iv_categry_new_born, iv_categry_baby_boy, iv_categry_baby_girl, iv_sale_banner;
    private ImageView iv_banner_one, iv_banner_two, iv_banner_three;
    /////////

    private List<ShopByCategoryDTO> shopByCategoryDTOS;
    private List<BloomStyleDTO> bloomStyleDTOS;

    private RecyclerView recycler_view_shop_by_category, recycler_view__bloom, rvDeals, recycler_view_kids, recycler_view_trendy, rv_latest_product, recycler_brand_latest, recycler_categry;

    ////////////
    private ShopByCategoryAdapter shopByCategoryAdapter;
    private BloomInStyleAdapter bloomInStyleAdapter;
    private HomeAdapterTrending mAdaptertrendy;
    private DealsAdapter dealsAdapter;
    private LatestProductAdapter mAdapterLatest;
    RecyclerView.LayoutManager mLayoutManager, mLayoutManagerKids, mLayoutManagertrendy, mLayoutManagerbrand, mlayoutmanagercategory, mLayoutManagerDeal;

    CirclePageIndicator indicator;
    ApiInterface apiService;
    private String vSub_cat_id_DOD = "", vSub_cat_id_Letest = "", vSub_cat_id_Tranding = "";
    SliderView slider_pager;

    private RecyclerView recycler_cat;
    ArrayList<Category_Detail_DTO> category_list_dtos;
    private CategoryAdapter categoryAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);
        context = getActivity();
        apiService = NewApiClient.getClient().create(ApiInterface.class);

        progress = new ProgressDialog(getActivity());
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.setMessage("Loading...");

        initialViewId();
        set_all_adapters();
        categoryclick();

        initialData();
        call_home_api();

        category_list_dtos = new ArrayList<>();
        getCategory();

        return view;
    }

    private void getCategory() {

        final Call<CategoryHome_DTO> user_dtoCall = apiService.all_category_api();
        user_dtoCall.enqueue(new Callback<CategoryHome_DTO>() {
            @Override
            public void onResponse(Call<CategoryHome_DTO> call, Response<CategoryHome_DTO> response) {

                CategoryHome_DTO category_dto = response.body();
                if (category_dto.getStatus().equals("1")) {
                    category_list_dtos.addAll(category_dto.getData());

                    categoryAdapter = new CategoryAdapter(context, category_list_dtos);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                    recycler_cat.setLayoutManager(mLayoutManager);
                    recycler_cat.setItemAnimator(new DefaultItemAnimator());
                    recycler_cat.setAdapter(categoryAdapter);

                    categoryAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<CategoryHome_DTO> call, Throwable t) {
                Log.e("onFailure", " " + t.getMessage());
            }
        });

    }

    private void set_all_adapters() {

        /////shopbycategry
        shopByCategoryDTOS = new ArrayList<>();
        shopByCategoryAdapter = new ShopByCategoryAdapter(context, shopByCategoryDTOS);
        mLayoutManager = new GridLayoutManager(context, 2);
        recycler_view_shop_by_category.setLayoutManager(mLayoutManager);
        recycler_view_shop_by_category.scheduleLayoutAnimation();
        recycler_view_shop_by_category.setItemAnimator(new DefaultItemAnimator());
        recycler_view_shop_by_category.setAdapter(shopByCategoryAdapter);


        bloomStyleDTOS = new ArrayList<>();
        bloomInStyleAdapter = new BloomInStyleAdapter(context, bloomStyleDTOS);
        mLayoutManagerKids = new GridLayoutManager(context, 2);
        recycler_view__bloom.setLayoutManager(mLayoutManagerKids);
        recycler_view__bloom.scheduleLayoutAnimation();
        recycler_view__bloom.setItemAnimator(new DefaultItemAnimator());
        recycler_view__bloom.setAdapter(bloomInStyleAdapter);


        trending_detail_dtos = new ArrayList<>();
        mAdaptertrendy = new HomeAdapterTrending(context, trending_detail_dtos);
        mLayoutManagerKids = new GridLayoutManager(context, 2);
        recycler_view_trendy.setLayoutManager(mLayoutManagerKids);
        recycler_view_trendy.scheduleLayoutAnimation();
        recycler_view_trendy.setItemAnimator(new DefaultItemAnimator());
        recycler_view_trendy.setAdapter(mAdaptertrendy);


        dealOfTheDay_detail_dtos = new ArrayList<>();
        dealsAdapter = new DealsAdapter(context, dealOfTheDay_detail_dtos);
        mLayoutManagerDeal = new GridLayoutManager(context, 2);
        rvDeals.setLayoutManager(mLayoutManagerDeal);
        rvDeals.scheduleLayoutAnimation();
        rvDeals.setItemAnimator(new DefaultItemAnimator());
        rvDeals.setAdapter(dealsAdapter);
    }

    ArrayList<DealOfTheDay_Detail_DTO> dealOfTheDay_detail_dtos = new ArrayList<>();
    ArrayList<Home_All_Banner_Detail_DTO> home_all_banner_detail_dtos = new ArrayList<>();
    ArrayList<LatestProduct_Detail_DTO> latestProduct_detail_dtos = new ArrayList<>();
    ArrayList<Trending_Detail_DTO> trending_detail_dtos = new ArrayList<>();
    ArrayList<Pager_Detail_DTO> pager_detail_dtos = new ArrayList<>();


    private void call_home_api() {
        progress.show();
        Call<HomeDTO> homeDTOCall = apiService.api_home();
        homeDTOCall.enqueue(new Callback<HomeDTO>() {
            @Override
            public void onResponse(Call<HomeDTO> call, Response<HomeDTO> response) {
                try {
                    HomeDTO homeDTO = response.body();
                    if (homeDTO.getMessage().equals("success")) {
                        progress.dismiss();
                        Log.e("repsonse", homeDTO.getMessage() + "");
                        if (homeDTO != null) {
                            Home_Detail_DTO home_detail_dto = new Home_Detail_DTO();
                            home_detail_dto = homeDTO.getData();
                            if (home_detail_dto != null) {
                                DealofDay_DTO dealofDay_dto = new DealofDay_DTO();
                                dealofDay_dto = home_detail_dto.getDealofDayDTO();
                                if (dealofDay_dto != null) {
                                    vSub_cat_id_DOD = String.valueOf(dealofDay_dto.getSubCategoryId());
                                    dealOfTheDay_detail_dtos.addAll(dealofDay_dto.getData());//// deal of the day data set in list
                                    if (dealOfTheDay_detail_dtos != null) {
                                        for (int j = 0; j < dealOfTheDay_detail_dtos.size(); j++) {
                                            dealsAdapter.notifyDataSetChanged();
                                        }
                                    }


                                    shopByCategoryDTOS.addAll(home_detail_dto.getShopByCategoryDTO());
                                    if (shopByCategoryDTOS != null) {
                                        for (int j = 0; j < shopByCategoryDTOS.size(); j++) {
                                            shopByCategoryAdapter.notifyDataSetChanged();
                                        }
                                    }
                                    home_all_banner_detail_dtos.addAll(home_detail_dto.getAllHomeBannerData());
                                    if (home_all_banner_detail_dtos != null) {
                                        for (int j = 0; j < home_all_banner_detail_dtos.size(); j++) {
                                            home_all_banner_detail_dtos.get(0).getBannerName();
                                            Intent intent = new Intent(getActivity(), ProductActivity.class);
                                            if (j == 0) {
                                                Glide.with(context).load(home_all_banner_detail_dtos.get(0).getBannerImage()).into(iv_summer);
                                                tv_banner_one.setText(home_all_banner_detail_dtos.get(0).getBannerName());
                                                iv_summer.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        intent.putExtra("sub_category_id", home_all_banner_detail_dtos.get(0).getSubcategoryId());
                                                        intent.putExtra("item_name", home_all_banner_detail_dtos.get(0).getBannerName());
                                                        startActivity(intent);
                                                    }
                                                });

                                            } else if (j == 1) {
                                                Glide.with(context).load(home_all_banner_detail_dtos.get(1).getBannerImage()).into(iv_banner_one);
                                                //  tv_banner_two.setText(home_all_banner_detail_dtos.get(1).getBannerName());
                                                iv_banner_one.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        intent.putExtra("sub_category_id", home_all_banner_detail_dtos.get(1).getSubcategoryId());
                                                        intent.putExtra("item_name", home_all_banner_detail_dtos.get(1).getBannerName());
                                                        startActivity(intent);
                                                    }
                                                });

                                            } else if (j == 2) {
                                                Glide.with(context).load(home_all_banner_detail_dtos.get(2).getBannerImage()).into(iv_banner_two);
                                                // tv_banner_one.setText(home_all_banner_detail_dtos.get(2).getBannerName());
                                                iv_banner_two.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        intent.putExtra("sub_category_id", home_all_banner_detail_dtos.get(2).getSubcategoryId());
                                                        intent.putExtra("item_name", home_all_banner_detail_dtos.get(2).getBannerName());
                                                        startActivity(intent);
                                                    }
                                                });

                                            } else if (j == 3) {
                                                Glide.with(context).load(home_all_banner_detail_dtos.get(3).getBannerImage()).into(iv_party);
                                                tv_banner_two.setText(home_all_banner_detail_dtos.get(3).getBannerName());
                                                iv_party.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        intent.putExtra("sub_category_id", home_all_banner_detail_dtos.get(3).getSubcategoryId());
                                                        intent.putExtra("item_name", home_all_banner_detail_dtos.get(3).getBannerName());
                                                        startActivity(intent);
                                                    }
                                                });

                                            } else if (j == 4) {
                                                Glide.with(context).load(home_all_banner_detail_dtos.get(4).getBannerImage()).into(iv_stripe);
                                                tv_banner_three.setText(home_all_banner_detail_dtos.get(4).getBannerName());
                                                iv_stripe.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        intent.putExtra("sub_category_id", home_all_banner_detail_dtos.get(4).getSubcategoryId());
                                                        intent.putExtra("item_name", home_all_banner_detail_dtos.get(4).getBannerName());
                                                        startActivity(intent);
                                                    }
                                                });

                                            } else if (j == 5) {
                                                Glide.with(context).load(home_all_banner_detail_dtos.get(5).getBannerImage()).into(iv_sale_banner);
                                                tv_banner_five.setText(home_all_banner_detail_dtos.get(5).getBannerName());
                                                iv_sale_banner.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        intent.putExtra("sub_category_id", home_all_banner_detail_dtos.get(5).getSubcategoryId());
                                                        intent.putExtra("item_name", home_all_banner_detail_dtos.get(5).getBannerName());
                                                        startActivity(intent);
                                                    }
                                                });

                                            }
                                        }
                                    }
                                }


                                bloomStyleDTOS.addAll(home_detail_dto.getBloomStyleData());
                                if (bloomStyleDTOS != null) {
                                    for (int j = 0; j < bloomStyleDTOS.size(); j++) {
                                        bloomInStyleAdapter.notifyDataSetChanged();
                                    }
                                }

                                pager_detail_dtos.addAll(home_detail_dto.getPagerData());
                                if (pager_detail_dtos != null) {
                                    for (int j = 0; j < pager_detail_dtos.size(); j++) {
                                        slider_pager.setSliderAdapter(new SlidingImage_Adapter(context, pager_detail_dtos));
                                        slider_pager.setIndicatorAnimation(IndicatorAnimations.SLIDE); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
                                        slider_pager.setSliderTransformAnimation(SliderAnimations.CUBEINROTATIONTRANSFORMATION);
                                        slider_pager.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
                                        slider_pager.setIndicatorSelectedColor(Color.WHITE);
                                        slider_pager.setIndicatorUnselectedColor(Color.GRAY);
                                        slider_pager.startAutoCycle();
                                    }
                                }

                                Trending_DTO trending_dto = new Trending_DTO();
                                trending_dto = home_detail_dto.getTrendingDTO();

                                vSub_cat_id_Tranding = String.valueOf(trending_dto.getSubCategoryId());
                                trending_detail_dtos.addAll(trending_dto.getData());
                                if (trending_detail_dtos != null) {
                                    for (int j = 0; j < trending_detail_dtos.size(); j++) {
                                        mAdaptertrendy.notifyDataSetChanged();
                                    }
                                }

                                latestProduct_detail_dtos.addAll(home_detail_dto.getLatestProductData());
                                if (latestProduct_detail_dtos != null) {
                                    for (int j = 0; j < latestProduct_detail_dtos.size(); j++) {
                                        mAdapterLatest.notifyDataSetChanged();
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<HomeDTO> call, Throwable t) {
                Log.e("falure", t + "");
            }
        });
    }

    private void categoryclick() {
        txt_dod_view_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = getString(R.string.dod);
                Intent intent = new Intent(context, ProductActivity.class);
                intent.putExtra("sub_category_id", vSub_cat_id_DOD);
                intent.putExtra("item_name", name);
                context.startActivity(intent);
            }
        });
        view_all_trendy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = getString(R.string.Trending_now);
                Intent intent = new Intent(context, ProductActivity.class);
                intent.putExtra("sub_category_id", vSub_cat_id_Tranding);
                intent.putExtra("item_name", name);
                context.startActivity(intent);
            }
        });
        view_all_latest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = getString(R.string.letest_products);
                Intent intent = new Intent(context, ProductActivity.class);
                intent.putExtra("item_name", name);
                context.startActivity(intent);
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////////////
        iv_categry_baby_girl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vgirl = tv_categry_baby_girl.getText().toString();
                Intent intent = new Intent(context, SubSubCategoryActivity.class);
                intent.putExtra("item_name", vgirl);
                intent.putExtra("item_position", "5");
                context.startActivity(intent);
            }
        });
        iv_categry_new_born.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vnewborn = tv_categry_new_born.getText().toString();
                Intent intent = new Intent(context, SubSubCategoryActivity.class);
                intent.putExtra("item_name", vnewborn);
                intent.putExtra("item_position", "3");
                context.startActivity(intent);
            }
        });
        iv_categry_expecting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vexpectingmother = tv_categry_expecting.getText().toString();

                Intent intent = new Intent(context, SubSubCategoryActivity.class);
                intent.putExtra("item_name", vexpectingmother);
                intent.putExtra("item_position", "2");
                context.startActivity(intent);
            }
        });
        iv_categry_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNavigation.setSelectedItemId(R.id.navcategory);
                FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.container, new CategoryFragment());
                fragmentTransaction.commit();
            }
        });
        iv_categry_baby_boy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vboy = tv_categry_baby_boy.getText().toString();
                Intent intent = new Intent(context, SubSubCategoryActivity.class);
                intent.putExtra("item_name", vboy);
                intent.putExtra("item_position", "4");
                context.startActivity(intent);
            }
        });

    }

    private void initialData() {


        final LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getActivity(), animationList[i]);
        mAdaptertrendy = new HomeAdapterTrending(context, trending_detail_dtos);
        mAdapterLatest = new LatestProductAdapter(context, latestProduct_detail_dtos);


        mlayoutmanagercategory = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recycler_cat.setLayoutManager(mLayoutManagertrendy);
        recycler_cat.setLayoutAnimation(controller);
        recycler_cat.scheduleLayoutAnimation();
        recycler_cat.setItemAnimator(new DefaultItemAnimator());
        recycler_cat.setAdapter(mAdaptertrendy);


        mLayoutManager = new GridLayoutManager(context, 2);
        recycler_view_shop_by_category.setLayoutManager(mLayoutManager);
        recycler_view_shop_by_category.setLayoutAnimation(controller);
        recycler_view_shop_by_category.scheduleLayoutAnimation();
        recycler_view_shop_by_category.setItemAnimator(new DefaultItemAnimator());
        recycler_view_shop_by_category.setAdapter(shopByCategoryAdapter);

        mLayoutManagertrendy = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recycler_view_trendy.setLayoutManager(mLayoutManagertrendy);
        recycler_view_trendy.setLayoutAnimation(controller);
        recycler_view_trendy.scheduleLayoutAnimation();
        recycler_view_trendy.setItemAnimator(new DefaultItemAnimator());
        recycler_view_trendy.setAdapter(mAdaptertrendy);


        mLayoutManagerbrand = new GridLayoutManager(context, 2);
        rv_latest_product.setLayoutManager(mLayoutManagerbrand);
        rv_latest_product.setLayoutAnimation(controller);
        rv_latest_product.scheduleLayoutAnimation();
        rv_latest_product.setItemAnimator(new DefaultItemAnimator());
        rv_latest_product.setAdapter(mAdapterLatest);


        ////////////////////////////////////////////////////////////////////////////////////////////////////
        iv_banner_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ProductActivity.class);
                context.startActivity(intent);
            }
        });

        iv_banner_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ProductActivity.class);

                context.startActivity(intent);
            }
        });
        iv_banner_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ProductActivity.class);

                context.startActivity(intent);
            }
        });

        final Animation animation = new AlphaAnimation((float) 0.6, 0);
        animation.setDuration(1500);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);
        iv_sale_banner.startAnimation(animation);

    }

    private void initialViewId() {

        r1 = (RelativeLayout) view.findViewById(R.id.r1);
        slider_pager = (SliderView) view.findViewById(R.id.slider_pager);

        rvDeals = (RecyclerView) view.findViewById(R.id.rvDeals);
        recycler_cat = (RecyclerView) view.findViewById(R.id.recycler_cat);
        recycler_view__bloom = (RecyclerView) view.findViewById(R.id.recycler_view__bloom);
        recycler_view_shop_by_category = (RecyclerView) view.findViewById(R.id.recycler_view_shop_by_category);
        recycler_view_trendy = (RecyclerView) view.findViewById(R.id.recycler_view_trendy);
        rv_latest_product = (RecyclerView) view.findViewById(R.id.rv_latest_product);

        tv_banner_five = (TextView) view.findViewById(R.id.tv_banner_five);
        tv_banner_four = (TextView) view.findViewById(R.id.tv_banner_four);
        tv_banner_three = (TextView) view.findViewById(R.id.tv_banner_three);
        tv_banner_two = (TextView) view.findViewById(R.id.tv_banner_two);
        tv_banner_one = (TextView) view.findViewById(R.id.tv_banner_one);
        view_all_baby = (TextView) view.findViewById(R.id.view_all_baby);
        view_all_kids = (TextView) view.findViewById(R.id.view_all_kids);

        view_all_latest = (TextView) view.findViewById(R.id.view_all_latest);
        txt_dod_view_all = (TextView) view.findViewById(R.id.txt_dod_view_all);

        view_all_trendy = (TextView) view.findViewById(R.id.view_all_trendy);
        iv_categry_baby_girl = view.findViewById(R.id.iv_categry_baby_girl);
        iv_categry_all = view.findViewById(R.id.iv_categry_all);
        iv_categry_baby_boy = view.findViewById(R.id.iv_categry_baby_boy);
        iv_categry_expecting = view.findViewById(R.id.iv_categry_expecting);
        iv_categry_new_born = view.findViewById(R.id.iv_categry_new_born);
        tv_categry_all = view.findViewById(R.id.tv_categry_all);
        tv_categry_baby_boy = view.findViewById(R.id.tv_categry_baby_boy);
        tv_categry_baby_girl = view.findViewById(R.id.tv_categry_baby_girl);
        tv_categry_expecting = view.findViewById(R.id.tv_categry_expecting);
        tv_categry_new_born = view.findViewById(R.id.tv_categry_new_born);
        iv_sale_banner = view.findViewById(R.id.iv_sale_banner);
        iv_banner_one = view.findViewById(R.id.iv_banner_one);
        iv_banner_two = view.findViewById(R.id.iv_banner_two);
        iv_summer = view.findViewById(R.id.iv_summer);
        iv_party = view.findViewById(R.id.iv_party);
        iv_stripe = view.findViewById(R.id.iv_stripe);
        iv_banner_three = view.findViewById(R.id.iv_banner_three);

    }
}