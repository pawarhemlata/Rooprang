package com.tsi.rooprang.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.like.LikeButton;
import com.like.OnLikeListener;
import com.tsi.rooprang.Account.OrderSummaryActivity;
import com.tsi.rooprang.Adapter.AgeAdapter;
import com.tsi.rooprang.Adapter.ColorAdapter;
import com.tsi.rooprang.Adapter.OfferAdapter;
import com.tsi.rooprang.Adapter.ProductSliderAdapter2;
import com.tsi.rooprang.Adapter.SimilarProductAdapter;

import com.tsi.rooprang.Adapter.ProductSliderAdapter;
import com.tsi.rooprang.Adapter.SizeAdapter;
import com.tsi.rooprang.DTO.Color.ProductImagesByColorId_DTO;
import com.tsi.rooprang.DTO.Color.ProductImagesByColorId_Detail_DTO;
import com.tsi.rooprang.DTO.DataModel.DataModelHome3;
import com.tsi.rooprang.DTO.cart.Add__To_Cart_DTO;
import com.tsi.rooprang.DTO.product.Product_DTO;
import com.tsi.rooprang.DTO.product.Product_Details_DTO;
import com.tsi.rooprang.DTO.singleproduct.CheckPincode_DTO;
import com.tsi.rooprang.DTO.singleproduct.ProductAvailabelAge;
import com.tsi.rooprang.DTO.singleproduct.ProductAvailabelColor;
import com.tsi.rooprang.DTO.singleproduct.ProductAvailabelSize;
import com.tsi.rooprang.DTO.singleproduct.ProductImage;
import com.tsi.rooprang.DTO.singleproduct.ProductOffers;
import com.tsi.rooprang.DTO.singleproduct.Single_Product_DTO;
import com.tsi.rooprang.DTO.singleproduct.Single_Product_Detail_DTO;
import com.tsi.rooprang.DTO.singleproduct.Update_Cart_DTO;
import com.tsi.rooprang.DTO.wishlist.Add_Wish_DTO;
import com.tsi.rooprang.DTO.wishlist.Remove_wish_DTO;
import com.tsi.rooprang.Fragments.CartFragment;
import com.tsi.rooprang.LoginSignIn.SignInActivity;
import com.tsi.rooprang.R;
import com.tsi.rooprang.Session.SessionManager;
import com.tsi.rooprang.retrofit.ApiInterface;
import com.tsi.rooprang.retrofit.NewApiClient;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


import android.widget.TextView;
import android.widget.Toast;

import at.blogc.android.views.ExpandableTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

import static com.tsi.rooprang.Activity.ProductActivity.productActivity;


public class ProductDescriptionActivity extends AppCompatActivity {
    private static ViewPager mPager, mPager2;
    public static ProductDescriptionActivity productDescriptionActivity;
    int counter = 1;
    int qty = 30;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    Toolbar toolbar;
    private static final Integer[] IMAGES = {R.drawable.cloth, R.drawable.cloth1, R.drawable.cloth3, R.drawable.cloth2, R.drawable.cloth4};
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    CirclePageIndicator indicator;
    private ArrayList<ProductAvailabelSize> size_dtos;
    private ArrayList<ProductAvailabelAge> age_dtos;
    private ArrayList<ProductAvailabelColor> availabelColors_dtos;
    private ArrayList<ProductImage> productImages_dtos;
    private ArrayList<ProductOffers> productOffers_dtos;


    private ArrayList<DataModelHome3> dataModelList2;
    private RecyclerView recyclerView1, sizeselect, age_select, color_select, recycler_offer;
    private SimilarProductAdapter similarProductAdapter;
    private AgeAdapter ageAdapter;
    private OfferAdapter offerAdapter;
    private SizeAdapter sizeAdapter;
    private ColorAdapter colorAdapter;
    RecyclerView.LayoutManager mLayoutManager, ageLayoutManager, sizeLayoutManager, offerLayoutManager, colorLayoutManager;
    Context context;
    //  Spinner sizeselect, age_select, color_select;
    private Button addtocart, iv_toggle;
    private ImageView iv_single_product, ivplus, ivminus;
    private ViewPager viewpager_product;
    private LikeButton btn_wishlist;
    private EditText et_quantity, et_pincode;

    private ExpandableTextView tv_product_description;
    private TextView tv_product_title, tv_product_short_disc, tv_product_sale_price, tv_product_orignal_price, tv_product_off;
    private LinearLayout lay_goto_card, product_lay1;
    private RelativeLayout product_lay;
    private String vProduct_Id = "", vpincode = "", vorderId = "1", vImg, vTitle = "", vSub_Cat_Id = "",
            vSubtotal = "", vSaleprice = "", vOriginalprice = "", vOffprice = "", vProductDescription = "", vAvailable_in_Cart = "",
            vQty = "", vdiscount = "", vSub_cat_id = "", vShipping_charges = "";
    public static String vAge_id = "", vSize_id = "", vColor_id = "", vPos_Color = "", vColorChartId = "", vProductId = "", page = "", vProductColorChartId = "";
    ArrayList<Single_Product_Detail_DTO> single_product_detail_dtos;
    ApiInterface apiService;
    ProgressDialog mProgressDialog;
    TextView tv_availiblity, tv_ratting, tv_total_ratting, tv_submit, tv_ship_charge;

    private RelativeLayout rl_size, rl_age, rl_color, rl_offers;
    private View view_three;
    private ArrayList<Product_Details_DTO> dataModelGridList;
    private ArrayList<ProductImagesByColorId_Detail_DTO> productImagescolor_dtos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_description);
        productDescriptionActivity = this;
        apiService = NewApiClient.getClient().create(ApiInterface.class);

        context = productDescriptionActivity;

        tv_submit = findViewById(R.id.tv_submit);
        et_pincode = findViewById(R.id.et_pincode);
        sizeselect = findViewById(R.id.sizeselect);
        recycler_offer = findViewById(R.id.recycler_offer);
        age_select = findViewById(R.id.age_select);
        color_select = findViewById(R.id.color_select);
        addtocart = (Button) findViewById(R.id.add_cart);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        lay_goto_card = (LinearLayout) findViewById(R.id.lay_goto_card);
        product_lay1 = (LinearLayout) findViewById(R.id.product_lay1);
        product_lay = (RelativeLayout) findViewById(R.id.product_lay);
        viewpager_product = findViewById(R.id.viewpager_product);
        tv_product_description = findViewById(R.id.tv_product_description);
        tv_product_off = findViewById(R.id.tv_product_off);
        tv_product_title = findViewById(R.id.tv_product_title);
        tv_product_short_disc = findViewById(R.id.tv_product_short_disc);
        tv_product_sale_price = findViewById(R.id.tv_product_sale_price);
        tv_product_orignal_price = findViewById(R.id.tv_product_orignal_price);
        btn_wishlist = findViewById(R.id.btn_wishlist);
        iv_toggle = findViewById(R.id.iv_toggle);
        ivplus = findViewById(R.id.ivplus);
        et_quantity = findViewById(R.id.et_quantity);
        ivminus = findViewById(R.id.ivminus);
        tv_availiblity = findViewById(R.id.tv_availiblity);
        tv_ratting = findViewById(R.id.ratting);
        tv_total_ratting = findViewById(R.id.total_ratting);
        tv_ship_charge = findViewById(R.id.tv_ship_charge);

        rl_size = findViewById(R.id.rl_size);
        rl_color = findViewById(R.id.rl_color);
        rl_offers = findViewById(R.id.rl_offers);
        rl_age = findViewById(R.id.rl_age);
        view_three = findViewById(R.id.view_three);

        rl_size.setVisibility(View.GONE);
        rl_color.setVisibility(View.GONE);
        rl_offers.setVisibility(View.GONE);
        rl_age.setVisibility(View.GONE);
        view_three.setVisibility(View.GONE);

        vAge_id = "";
        vSize_id = "";
        vColor_id = "";
        toolbar.setTitle("Product Description");
        tv_product_orignal_price.setPaintFlags(tv_product_orignal_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        mProgressDialog = new ProgressDialog(this);

        if (getIntent() != null) {
            vProduct_Id = getIntent().getStringExtra("product_id");
            vTitle = getIntent().getStringExtra("product_title");
            vSub_Cat_Id = getIntent().getStringExtra("Sub_cat_id");
            Log.e("product_id", vProduct_Id + "");

        }
        btn_wishlist.bringToFront();
        vTitle = tv_product_title.getText().toString().trim();
//        vSaleprice = tv_product_sale_price.getText().toString().trim();
        vOriginalprice = tv_product_orignal_price.getText().toString().trim();
        vOffprice = tv_product_off.getText().toString().trim();
        vSubtotal = tv_product_sale_price.getText().toString().trim();
        vOffprice = tv_product_off.getText().toString().trim();

        et_quantity.setText(String.valueOf(counter));

        //  vQty = qtyselect.getSelectedItem().toString().trim();


        vProductDescription = tv_product_description.getText().toString().trim();
        tv_product_description.setAnimationDuration(750L);
        tv_product_description.setInterpolator(new OvershootInterpolator());
        tv_product_description.setExpandInterpolator(new OvershootInterpolator());
        tv_product_description.setCollapseInterpolator(new OvershootInterpolator());

        iv_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                iv_toggle.setBackgroundResource(tv_product_description.isExpanded() ? R.drawable.ic_expand : R.drawable.ic_collapse);
                tv_product_description.toggle();
            }
        });

        ivplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (counter < qty) {
                    counter++;
                    et_quantity.setText(String.valueOf(counter));

                } else {
                    Toast.makeText(ProductDescriptionActivity.this, qty + " Max Quantity.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ivminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (counter != 1) {
                    counter--;
                    et_quantity.setText(String.valueOf(counter));
                } else {
                    //  Toast.makeText(ProductDescriptionActivity.this, "No Quantity.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_wishlist.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                add_to_wishlist();
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                remove_to_wishlist();
            }
        });
        setGridDataHorz(1);
        set_data_from_api();
        check_pincode_from_api();

    }

    private void check_pincode_from_api() {

        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vpincode = et_pincode.getText().toString();
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setMessage("Loading...");
                mProgressDialog.show();
                final Call<CheckPincode_DTO> checkPincode_dtoCall = apiService.api_check_pincode(vProduct_Id, vpincode);
                checkPincode_dtoCall.enqueue(new Callback<CheckPincode_DTO>() {
                    @Override
                    public void onResponse(Call<CheckPincode_DTO> call, Response<CheckPincode_DTO> response) {
                        CheckPincode_DTO checkPincode_dto = response.body();
                        if (mProgressDialog.isShowing()) {
                            mProgressDialog.dismiss();
                        }
                        if (checkPincode_dto.getStatus().equals("1")) {
                            tv_availiblity.setVisibility(View.VISIBLE);
                            tv_ship_charge.setVisibility(View.VISIBLE);
                            String message = checkPincode_dto.getMessage();
                            tv_availiblity.setText(R.string.available + " | " + checkPincode_dto.getDelivery() + ",\n" + vpincode + " " + checkPincode_dto.getCity());
                            tv_availiblity.setTextColor(Color.parseColor("#4da5bd"));
                            tv_ship_charge.setText(getResources().getString(R.string.shipping_charges) + " :- " + checkPincode_dto.getShipping_charge() + "/-");
                            vShipping_charges = checkPincode_dto.getShipping_charge() != null ? checkPincode_dto.getShipping_charge() : "0";
                        } else {
                            tv_availiblity.setVisibility(View.VISIBLE);
                            tv_ship_charge.setVisibility(View.GONE);
                            String message = checkPincode_dto.getMessage();
                            tv_availiblity.setText(R.string.product_not_shipped_this_place);
                            tv_availiblity.setTextColor(Color.parseColor("#F01414"));
                        }
                    }

                    @Override
                    public void onFailure(Call<CheckPincode_DTO> call, Throwable t) {
                        if (mProgressDialog.isShowing()) {
                            mProgressDialog.dismiss();
                        }
                        Log.e("onFailure", " " + t.getMessage());
                    }
                });

            }
        });

    }

    private void set_data_from_api() {
        String id = SessionManager.getInstance(ProductDescriptionActivity.this).get_data_from_session(SessionManager.KEY_USERID);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();
        Call<Single_Product_DTO> singleProductDtoCall = apiService.api_single_product(!id.equals("") ? id : "", vProduct_Id);
        singleProductDtoCall.enqueue(new Callback<Single_Product_DTO>() {
            @Override
            public void onResponse(Call<Single_Product_DTO> call, Response<Single_Product_DTO> response) {
                Single_Product_DTO single_product_dto = response.body();
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                if (single_product_dto.getStatus().equals("1")) {
                    single_product_detail_dtos = new ArrayList<>();
                    if (single_product_detail_dtos.size() > 0) {
                        single_product_detail_dtos.clear();
                    }
                    single_product_detail_dtos.addAll(single_product_dto.getData());
                    if (single_product_detail_dtos.size() > 0 || !single_product_detail_dtos.equals("")) {
                        single_product_detail_dtos.get(0).getProductId();
                        single_product_detail_dtos.get(0).getAddedBy();
                        vSub_cat_id = single_product_detail_dtos.get(0).getSubCategoryId();
                        if (single_product_detail_dtos.get(0).getWishlist().getWishlistStatus().equals("1")) {
                            btn_wishlist.setLiked(true);
                        } else {
                            btn_wishlist.setLiked(false);
                        }
                        if (single_product_detail_dtos.get(0).getCartlist() != null) {
                            vAvailable_in_Cart = single_product_detail_dtos.get(0).getCartlist().getCartStatus() != null ? single_product_detail_dtos.get(0).getCartlist().getCartStatus() : "";
                            if (vAvailable_in_Cart.equals("1")) {
                                addtocart.setText(getResources().getString(R.string.goto_cart));
                            } else {
                                addtocart.setText(getResources().getString(R.string.addcart));

                            }
                        }

                        tv_ratting.setText(single_product_detail_dtos.get(0).getRating() != null ? single_product_detail_dtos.get(0).getRating() : "");
                        tv_total_ratting.setText(single_product_detail_dtos.get(0).getTotalRating() != null ? single_product_detail_dtos.get(0).getTotalRating() + " Ratings" : "");
                        tv_product_title.setText(single_product_detail_dtos.get(0).getProductName() != null ? single_product_detail_dtos.get(0).getProductName() : "");
                        tv_product_sale_price.setText(single_product_detail_dtos.get(0).getSalePrice() != null ? single_product_detail_dtos.get(0).getSalePrice() : "");
                        tv_product_orignal_price.setText(single_product_detail_dtos.get(0).getRegularPrice() != null ? single_product_detail_dtos.get(0).getRegularPrice() : "");
                        tv_product_off.setText(single_product_detail_dtos.get(0).getDiscount() != null ? single_product_detail_dtos.get(0).getDiscount() + "% off" : "");
                        tv_product_short_disc.setText(single_product_detail_dtos.get(0).getShortDescription() != null ? single_product_detail_dtos.get(0).getShortDescription() : "");


                        String dis = single_product_detail_dtos.get(0).getDescription() != null ? single_product_detail_dtos.get(0).getDescription() : "";
                        tv_product_description.setText(Html.fromHtml(dis));
                        vSaleprice = tv_product_sale_price.getText().toString().trim();

                        single_product_detail_dtos.get(0).getStock();
                        single_product_detail_dtos.get(0).getImage();
                        single_product_detail_dtos.get(0).getDiscount();
                        single_product_detail_dtos.get(0).getRating();
                        single_product_detail_dtos.get(0).getDeliveryBy();
                        single_product_detail_dtos.get(0).getExtraCharges();

                        size_dtos = new ArrayList<>();
                        sizeAdapter = new SizeAdapter(ProductDescriptionActivity.this, size_dtos);
                        sizeLayoutManager = new LinearLayoutManager(ProductDescriptionActivity.this, LinearLayoutManager.HORIZONTAL, false);
                        sizeselect.setLayoutManager(sizeLayoutManager);
                        sizeselect.setItemAnimator(new DefaultItemAnimator());
                        sizeselect.setAdapter(sizeAdapter);

                        age_dtos = new ArrayList<>();
                        ageAdapter = new AgeAdapter(ProductDescriptionActivity.this, age_dtos);
                        ageLayoutManager = new LinearLayoutManager(ProductDescriptionActivity.this, LinearLayoutManager.HORIZONTAL, false);
                        age_select.setLayoutManager(ageLayoutManager);
                        age_select.setItemAnimator(new DefaultItemAnimator());
                        age_select.setAdapter(ageAdapter);

                        productOffers_dtos = new ArrayList<>();
                        offerAdapter = new OfferAdapter(ProductDescriptionActivity.this, productOffers_dtos);
                        offerLayoutManager = new GridLayoutManager(ProductDescriptionActivity.this, 1);
                        recycler_offer.setLayoutManager(offerLayoutManager);
                        recycler_offer.setItemAnimator(new DefaultItemAnimator());
                        recycler_offer.setAdapter(offerAdapter);


                        availabelColors_dtos = new ArrayList<>();
                        colorAdapter = new ColorAdapter(ProductDescriptionActivity.this, availabelColors_dtos);
                        colorLayoutManager = new LinearLayoutManager(ProductDescriptionActivity.this, LinearLayoutManager.HORIZONTAL, true);
                        color_select.setLayoutManager(colorLayoutManager);
                        color_select.setItemAnimator(new DefaultItemAnimator());
                        color_select.setAdapter(colorAdapter);


                        productImages_dtos = new ArrayList<>();

                        if (size_dtos != null) {
                            size_dtos.addAll(single_product_detail_dtos.get(0).getProductAvailabelSize());
                            if (size_dtos.size() > 0) {
                                rl_size.setVisibility(View.VISIBLE);
                            }
                        }
                        if (age_dtos != null) {
                            age_dtos.addAll(single_product_detail_dtos.get(0).getProductAvailabelAge());
                            if (age_dtos.size() > 0) {
                                rl_age.setVisibility(View.VISIBLE);
                            }
                        }
                        if (availabelColors_dtos != null) {
                            availabelColors_dtos.addAll(single_product_detail_dtos.get(0).getProductAvailabelColor());
                            if (availabelColors_dtos.size() > 0) {
                                rl_color.setVisibility(View.VISIBLE);
                            }
                        }
                        if (productImages_dtos != null) {
                            productImages_dtos.addAll(single_product_detail_dtos.get(0).getProductImages());
                            init(productImages_dtos);
                        }
                        if (productOffers_dtos != null) {
                            productOffers_dtos.addAll(single_product_detail_dtos.get(0).getProductOffers());
                            if (productOffers_dtos.size() > 0) {
                                rl_offers.setVisibility(View.VISIBLE);
                                view_three.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                } else {
                    Toast.makeText(ProductDescriptionActivity.this, single_product_dto.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Single_Product_DTO> call, Throwable t) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                Log.e("onFailure", " " + t.getMessage());
            }
        });

        //  vQty = qtyselect.getSelectedItem().toString().trim();
        dataModelGridList = new ArrayList<>();
        productImagescolor_dtos = new ArrayList<>();
        if (dataModelGridList != null) {
            dataModelGridList.clear();
        }
        recyclerView1 = (RecyclerView) findViewById(R.id.recycle_similar);
        similarProductAdapter = new SimilarProductAdapter(ProductDescriptionActivity.this, dataModelGridList);
        mLayoutManager = new LinearLayoutManager(ProductDescriptionActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView1.setLayoutManager(mLayoutManager);
        recyclerView1.scheduleLayoutAnimation();
        recyclerView1.setItemAnimator(new DefaultItemAnimator());
        recyclerView1.setAdapter(similarProductAdapter);
        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SessionManager.getInstance(ProductDescriptionActivity.this).get_data_from_session(SessionManager.IS_LOGIN).equalsIgnoreCase("true")) {
                    if (SessionManager.getInstance(ProductDescriptionActivity.this).get_data_from_session(SessionManager.KEY_USERID) != null) {
                        String user_id = SessionManager.getInstance(ProductDescriptionActivity.this).get_data_from_session(SessionManager.KEY_USERID);
                        if (vAvailable_in_Cart.equals("1")) {
                            update_cart_api(user_id);
                        } else {
                            go_to_cart(user_id);
                        }
                    }
                } else {
                    Intent intent = new Intent(ProductDescriptionActivity.this, SignInActivity.class);
                    intent.putExtra("come_from", "product_discription");
                    startActivity(intent);
                }
            }
        });

    }

    private void update_cart_api(String user_id) {
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();
        vQty = String.valueOf(counter);
        Call<Update_Cart_DTO> cartDtoCall = apiService.api_update_cart_api(user_id, vProduct_Id, vSize_id, vAge_id, vProductColorChartId, vQty, vpincode, vShipping_charges);
        cartDtoCall.enqueue(new Callback<Update_Cart_DTO>() {
            @Override
            public void onResponse(Call<Update_Cart_DTO> call, Response<Update_Cart_DTO> response) {
                Update_Cart_DTO update_cart_dto = response.body();
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                if (update_cart_dto.getMessage().equalsIgnoreCase("success")) {
                    lay_goto_card.setVisibility(View.VISIBLE);
                    CartFragment cartFragment = new CartFragment();
                    toolbar.setTitle(R.string.cart);
                    FragmentManager fragmentManager3 = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction4 = fragmentManager3.beginTransaction();
                    fragmentTransaction4.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                    fragmentTransaction4.replace(R.id.container_new, cartFragment);
                    fragmentTransaction4.commit();
                } else {
                    Toast.makeText(ProductDescriptionActivity.this, update_cart_dto.getMessage().toString().trim(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Update_Cart_DTO> call, Throwable t) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                Log.e("failure", t + "");
            }
        });
    }

    private void go_to_cart(String user_id) {
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();
        vQty = String.valueOf(counter);
        Call<Add__To_Cart_DTO> cartDtoCall = apiService.api_cart(user_id, vSub_cat_id, vProduct_Id, vAge_id, vSize_id, vProductColorChartId,
                vQty, vSaleprice, vpincode, vShipping_charges);

        cartDtoCall.enqueue(new Callback<Add__To_Cart_DTO>() {
            @Override
            public void onResponse(Call<Add__To_Cart_DTO> call, Response<Add__To_Cart_DTO> response) {
                Add__To_Cart_DTO cart_dto = response.body();
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                if (cart_dto.getMessage().equals("success")) {
                    lay_goto_card.setVisibility(View.VISIBLE);
                    CartFragment cartFragment = new CartFragment();
                    toolbar.setTitle(R.string.cart);
                    FragmentManager fragmentManager3 = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction4 = fragmentManager3.beginTransaction();
                    fragmentTransaction4.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                    fragmentTransaction4.replace(R.id.container_new, cartFragment);
                    fragmentTransaction4.commit();
                } else {
                    Toast.makeText(ProductDescriptionActivity.this, cart_dto.getMessage().toString().trim(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Add__To_Cart_DTO> call, Throwable t) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                Log.e("failure", t + "");
            }
        });
    }

    private void setGridDataHorz(int page_count1) {
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();
        String id = SessionManager.getInstance(this).get_data_from_session(SessionManager.KEY_USERID);
        Call<Product_DTO> categoryDtoCall = apiService.api_product(vSub_Cat_Id, page_count1 + "", !id.equals("") ? id : "");
        categoryDtoCall.enqueue(new Callback<Product_DTO>() {
            @Override
            public void onResponse(Call<Product_DTO> call, Response<Product_DTO> response) {
                try {
                    Product_DTO category_dto = response.body();
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    if (category_dto.getMessage().equals("success")) {
                        dataModelGridList.addAll(category_dto.getData());
                        if (dataModelGridList != null) {
                            Iterator<Product_Details_DTO> iterator = dataModelGridList.iterator();
                            while (iterator.hasNext()) {
                                Product_Details_DTO value = iterator.next();
                                if (vProduct_Id.equals(value.getProductId())) {
                                    iterator.remove();
                                    break;
                                } else {
                                    if (similarProductAdapter != null) {
                                        //gridAdapter.notifyDataSetChanged();
                                        similarProductAdapter.notifyDataSetChanged();
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
            public void onFailure(Call<Product_DTO> call, Throwable t) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                Log.e("onFailure", " " + t.getMessage());
            }
        });
    }

    private void init(final ArrayList<ProductImage> productImages_dtos) {
        if (productImages_dtos != null) {
            mPager = (ViewPager) findViewById(R.id.viewpager_product);
            mPager.setAdapter(new ProductSliderAdapter(ProductDescriptionActivity.this, this.productImages_dtos));
            indicator = (CirclePageIndicator) findViewById(R.id.indicator_product);
            indicator.setViewPager(mPager);

            final float density = getResources().getDisplayMetrics().density;
//Set circle indicator radius
            indicator.setRadius(5 * density);
            NUM_PAGES = IMAGES.length;

            // Auto start of viewpager
            final Handler handler = new Handler();
            final Runnable Update = new Runnable() {
                public void run() {
                    if (currentPage == NUM_PAGES) {
                        currentPage = 0;
                    }
                    mPager.setCurrentItem(currentPage++, true);
                }
            };

            Timer swipeTimer = new Timer();
            swipeTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(Update);
                }
            }, 3000, 5000);

            // TabPagerAdapter listener over indicator

            indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                    currentPage = position;
                }

                @Override
                public void onPageScrolled(int pos, float arg1, int arg2) {
                }

                @Override
                public void onPageScrollStateChanged(int pos) {
                }
            });
        }
    }

    private void init2(final ArrayList<ProductImagesByColorId_Detail_DTO> productImagesByColorId_detail_dtos) {
        if (productImagesByColorId_detail_dtos != null) {
            mPager = (ViewPager) findViewById(R.id.viewpager_product);
            mPager.setAdapter(new ProductSliderAdapter2(ProductDescriptionActivity.this, this.productImagescolor_dtos));
            indicator = (CirclePageIndicator) findViewById(R.id.indicator_product);
            indicator.setViewPager(mPager);

            final float density = getResources().getDisplayMetrics().density;
            indicator.setRadius(5 * density);
            NUM_PAGES = IMAGES.length;

// Auto start of viewpager
            final Handler handler = new Handler();
            final Runnable Update = new Runnable() {
                public void run() {
                    if (currentPage == NUM_PAGES) {
                        currentPage = 0;
                    }
                    mPager.setCurrentItem(currentPage++, true);
                }
            };

            Timer swipeTimer = new Timer();
            swipeTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(Update);
                }
            }, 3000, 5000);

// TabPagerAdapter listener over indicator

            indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                    currentPage = position;
                }

                @Override
                public void onPageScrolled(int pos, float arg1, int arg2) {
                }

                @Override
                public void onPageScrollStateChanged(int pos) {
                }
            });
        }
    }

    private void remove_to_wishlist() {
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();
        Call<Remove_wish_DTO> remove_wish_dtoCall = apiService.api_remove_wishlist(SessionManager.getInstance(ProductDescriptionActivity.this).get_data_from_session(SessionManager.KEY_USERID), vProduct_Id);
        remove_wish_dtoCall.enqueue(new Callback<Remove_wish_DTO>() {
            @Override
            public void onResponse(Call<Remove_wish_DTO> call, Response<Remove_wish_DTO> response) {
                Remove_wish_DTO remove_wish_dto = response.body();
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                if (remove_wish_dto.getMessage().equals("success")) {
                    Toast.makeText(ProductDescriptionActivity.this, "Item removed from Wishlist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Remove_wish_DTO> call, Throwable t) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                Log.e("repsonse", t + "");
            }
        });
    }

    private void add_to_wishlist() {
        if (SessionManager.getInstance(ProductDescriptionActivity.this).get_data_from_session(SessionManager.IS_LOGIN).equalsIgnoreCase("true")) {
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.show();
            Call<Add_Wish_DTO> add_wish_dtoCall = apiService.api_add_wishlist(SessionManager.getInstance(ProductDescriptionActivity.this).get_data_from_session(SessionManager.KEY_USERID), vProduct_Id);
            add_wish_dtoCall.enqueue(new Callback<Add_Wish_DTO>() {
                @Override
                public void onResponse(Call<Add_Wish_DTO> call, Response<Add_Wish_DTO> response) {

                    Add_Wish_DTO add_wish_dto = response.body();
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    if (add_wish_dto.getMessage().equals("success")) {
                        Toast.makeText(ProductDescriptionActivity.this, "Item Added in Wishlist", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Add_Wish_DTO> call, Throwable t) {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    Log.e("repsonse", t + "");
                }
            });

        } else {
            Toast.makeText(ProductDescriptionActivity.this, "Please Login First", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ProductDescriptionActivity.this, SignInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            if (ProductActivity.productActivity != null) {
                productActivity.finish();
            }
        }
    }

    //
    public void showProductImagesByColorId(String vProductId, String vColorChartId) {
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();
        productImagescolor_dtos.clear();
        productImages_dtos.clear();
        Call<ProductImagesByColorId_DTO> productImagesByColorId_dtoCall = apiService.ProductImagesByColorId_api(vProductId, vColorChartId);
        productImagesByColorId_dtoCall.enqueue(new Callback<ProductImagesByColorId_DTO>() {
            @Override
            public void onResponse(Call<ProductImagesByColorId_DTO> call, Response<ProductImagesByColorId_DTO> response) {
                ProductImagesByColorId_DTO productImagesByColorId_dtoCall = response.body();
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                if (productImagesByColorId_dtoCall.getMessage().equals("success")) {
                    productImagescolor_dtos.clear();
                    productImages_dtos.clear();
                    List<ProductImagesByColorId_Detail_DTO> productImagesByColorId_detail_dto = productImagesByColorId_dtoCall.getData();

                    for (int i = 0; i < productImagesByColorId_detail_dto.size(); i++) {
                        productImagesByColorId_detail_dto.get(i).getProductImage();

                    }
                    productImagescolor_dtos.addAll(productImagesByColorId_detail_dto);

                }
                if (productImagescolor_dtos != null) {
                    init2(productImagescolor_dtos);
                }
            }

            @Override
            public void onFailure(Call<ProductImagesByColorId_DTO> call, Throwable t) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                Log.e("repsonse", t + "");
            }
        });
    }


}
