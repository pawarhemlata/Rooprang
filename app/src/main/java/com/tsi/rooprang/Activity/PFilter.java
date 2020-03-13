package com.tsi.rooprang.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tsi.rooprang.Fragments.BottomFilterFragment;
import com.tsi.rooprang.R;
import com.tsi.rooprang.retrofit.BaseUrl;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tsi.rooprang.Account.Saved_Address_Activity.saved_address_activity;

public class PFilter extends AppCompatActivity {

    private LinearLayout llcategoty, llcategotyvalue;
    ArrayList<String> selectedcategoryvalue = new ArrayList<String>();
    int position;
    Button btapply;
    ImageView back;
    TextView tvclearfilter;
    ProgressDialog progress;
    String product_id, price_value, category, age_chart_id, discount_value, color_name, age_name, size_number, brand_id, cat_id, sub_name, subcat_id, subsubcat_id, page, keyword, price, Brand, Color123, Colorr, Discount_f, Age_f, Size_f, category_price_product, category_Brand_product,
            category_Size_product, category_Age_product, category_Color_product, category_Discount_product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_filter_sheet);

        if (getIntent() != null) {
            subcat_id = getIntent().getStringExtra("sub_category_id");
        }
        tvclearfilter = findViewById(R.id.tvclearfilter);
        back = findViewById(R.id.back);
        btapply = findViewById(R.id.btapply);
        llcategoty = findViewById(R.id.llcategoty);
        llcategotyvalue = findViewById(R.id.llcategotyvalue);

        progress = new ProgressDialog(this);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.setMessage("Loading...");

        filterMenuList();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PFilter.this, ProductActivity.class)
                        .putExtra("brand_id", category_Brand_product)
                        .putExtra("price_value", category_price_product)
                        .putExtra("page", "PFilter")
                        .putExtra("keyword", keyword)
                        .putExtra("age_name", category_Age_product)
                        .putExtra("color_name", category_Color_product)
                        .putExtra("discount_value", category_Discount_product)
                        .putExtra("size_number", category_Size_product)
                        .putExtra("sub_category_id", subcat_id));

                System.out.println("price_value=" + price_value);
                //startActivity(new Intent(PFilter.this, ProductListActivity.class).putExtra("cat_id",cat_id).putExtra("sub_name",sub_name).putExtra("scat_id",subcat_id).putExtra("sscat_id",subsubcat_id).putExtra("page","PFilter").putExtra("keyword",abc));
                finish();
            }
        });
        tvclearfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void filterMenuList() {
        progress.show();
        final String URL1 = BaseUrl.getMethodUrl(BaseUrl.FILTER_MENU) + "subcategory_id=" + subcat_id;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("filterMenuList", "====>" + URL1);
                        progress.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            final String message = jsonObject.getString("message");
                            String status = jsonObject.getString("status");
                            if (status.equalsIgnoreCase("1")) {
                                String result = jsonObject.getString("data");
                                JSONObject jsonObjectresult = new JSONObject(result);
                                String filterName = jsonObjectresult.getString("filterName");

                                if (jsonObjectresult.has("Color")) {
                                    Colorr = jsonObjectresult.getString("Color");
                                }
                                if (jsonObjectresult.has("price")) {
                                    price = jsonObjectresult.getString("price");
                                }
                               if (jsonObjectresult.has("Brand")) {
                                    Brand = jsonObjectresult.getString("Brand");
                                }
                                if (jsonObjectresult.has("Size")) {
                                    Size_f = jsonObjectresult.getString("Size");
                                }
                                if (jsonObjectresult.has("Age")) {
                                    Age_f = jsonObjectresult.getString("Age");
                                }
                                if (jsonObjectresult.has("Discount")) {
                                    Discount_f = jsonObjectresult.getString("Discount");
                                }


                                Log.e("filterMenuList", "====>" + filterName);

                                categoryfun(filterName,Colorr, price, Brand, Size_f, Age_f, Discount_f);
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(PFilter.this, message + "", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("filterMenuList", "====>" + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress.dismiss();
                        Log.e("TAG", "onErrorResponse: " + error.getMessage());
                        Toast.makeText(PFilter.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // params.put("subcategory_id", "25");
                return null;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(PFilter.this);
        requestQueue.add(stringRequest);
    }

    private void categoryfun(String filterName, String Colorr,String price, String Brand, String Size_f, String Age_f, String Discount_f) {
        llcategoty.removeAllViews();

        try {
            JSONArray jsonArray = new JSONArray(filterName);
            for (int i = 0; i < jsonArray.length(); i++) {
                final String filterName111 = jsonArray.getString(i);
                LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = li.inflate(R.layout.dynamic_filter, null);

                final TextView ivsize = view.findViewById(R.id.ivsize);
                ivsize.setText(filterName111);

                if (position == i) {
                    ivsize.setBackgroundColor(Color.parseColor("#ffffff"));
                } else {
                    ivsize.setBackgroundColor(Color.parseColor("#EAEAEA"));
                }

             /*   position = 0;*/

                categoryvaluefun("price", Colorr,price, Brand, Size_f, Age_f, Discount_f);

                final int finalI = i;
                ivsize.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        position = finalI;

                        categoryfun(filterName,Colorr, price, Brand, Size_f, Age_f, Discount_f);
                        categoryvaluefun(filterName111, Colorr,price, Brand, Size_f, Age_f, Discount_f);
                    }
                });

                // llcategotyvalue.addView(view);
                llcategoty.addView(view);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void categoryvaluefun(String filterName, String Colorr, String price, String Brand, String Size_f, String Age_f, String Discount_f) {
        llcategotyvalue.removeAllViews();
      //  selectedcategoryvalue.add(price_value);
        System.out.println("asasasasasasasqwqwwBrand=" + filterName);
        try {
            if (filterName.equalsIgnoreCase("price")) {
                JSONArray jsonArray = new JSONArray(price);
                final List<String> list = new ArrayList<String>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    //  JSONObject  category = jsonArray.getJSONObject(i);
                    // final String category = jsonArray.getString(Integer.parseInt(i+""));
                    final String price_value = jsonArray.getString(Integer.parseInt(i + ""));

                    System.out.println("asSASSSS=" + price_value);

                    LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View view = li.inflate(R.layout.dynamic_filter_value, null);

                    final CheckBox ivsize = view.findViewById(R.id.ivsize);
                    ivsize.setText(price_value);

                    ivsize.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (((CheckBox) view).isChecked()) {
                                System.out.println("9090909021i=" + position + "<>" + price_value);
                                selectedcategoryvalue.add(price_value);
                                list.add(price_value);
                                category_price_product = TextUtils.join(", ", list);
                            } else {
                                System.out.println("9090909021i=" + position + "<>" + price_value);
                                selectedcategoryvalue.remove(price_value);
                                list.remove(price_value);
                                category_price_product = TextUtils.join(", ", list);
                            }
                        }
                    });
                    llcategotyvalue.addView(view);
                }
            }
            if (filterName.equalsIgnoreCase("Brand")) {
                JSONArray jsonArray = new JSONArray(Brand);
                final List<String> list = new ArrayList<String>();
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    brand_id = jsonObject.getString("brand_id");
                    final String brand_name = jsonObject.getString("brand_name");

                    System.out.println("asasasasasasasqwqww=" + brand_name);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View view = li.inflate(R.layout.dynamic_filter_value, null);
                            /* llcategoty.setBackgroundColor(Color.parseColor("#ffffff"));*/
                            final CheckBox ivsize = view.findViewById(R.id.ivsize);
                            ivsize.setText(brand_name);

                            ivsize.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (((CheckBox) view).isChecked()) {

                                        System.out.println("9090909021i=" + position + "<>" + brand_name);
                                        selectedcategoryvalue.add(brand_name);
                                        list.add(brand_name);
                                        category_Brand_product = TextUtils.join(", ", list);


                                       /* System.out.println("9090909021i="+position+"<>"+brand_name);
                                        selectedcategoryvalue.add(brand_name);*/
                                    } else {
                                        System.out.println("9090909021i=" + position + "<>" + brand_name);
                                        selectedcategoryvalue.remove(brand_name);
                                        category_Brand_product = TextUtils.join(", ", list);
                                    }
                                }
                            });

                            llcategotyvalue.addView(view);
                        }
                    });
                }
            }
            if (filterName.equalsIgnoreCase("Size")) {
                JSONArray jsonArray_Size = new JSONArray(Size_f);
                final List<String> list = new ArrayList<String>();
                for (int i = 0; i < jsonArray_Size.length(); i++) {
                    JSONObject jsonObject_Size = jsonArray_Size.getJSONObject(i);
                    String size_chart_id = jsonObject_Size.getString("size_chart_id");
                    final String size_number = jsonObject_Size.getString("size_number");

                    System.out.println("asasasasasasasqwqww=" + size_number);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View view = li.inflate(R.layout.dynamic_filter_value, null);
                            //  llcategoty.setBackgroundColor(Color.parseColor("#ffffff"));
                            final CheckBox ivsize = view.findViewById(R.id.ivsize);
                            ivsize.setText(size_number);

                            ivsize.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (((CheckBox) view).isChecked()) {

                                        System.out.println("9090909021i=" + position + "<>" + size_number);
                                        selectedcategoryvalue.add(size_number);
                                        list.add(size_number);
                                        category_Size_product = TextUtils.join(", ", list);


                                       /* System.out.println("9090909021i="+position+"<>"+brand_name);
                                        selectedcategoryvalue.add(brand_name);*/
                                    } else {
                                        System.out.println("9090909021i=" + position + "<>" + size_number);
                                        selectedcategoryvalue.remove(size_number);
                                        category_Size_product = TextUtils.join(", ", list);
                                    }
                                }
                            });

                            llcategotyvalue.addView(view);
                        }
                    });
                }
            }
            if (filterName.equalsIgnoreCase("Age")) {
                JSONArray jsonArray = new JSONArray(Age_f);
                final List<String> list = new ArrayList<String>();
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    //   String age_chart_id =jsonObject.getString("age_chart_id");
                    final String age_name = jsonObject.getString("age_name");

                    System.out.println("asasasasasasasqwqww=" + age_name);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View view = li.inflate(R.layout.dynamic_filter_value, null);
                            // llcategoty.setBackgroundColor(Color.parseColor("#ffffff"));
                            final CheckBox ivsize = view.findViewById(R.id.ivsize);
                            ivsize.setText(age_name);

                            ivsize.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (((CheckBox) view).isChecked()) {

                                        System.out.println("9090909021i=" + position + "<>" + age_name);
                                        selectedcategoryvalue.add(age_name);
                                        list.add(age_name);
                                        category_Age_product = TextUtils.join(", ", list);


                                       /* System.out.println("9090909021i="+position+"<>"+brand_name);
                                        selectedcategoryvalue.add(brand_name);*/
                                    } else {
                                        System.out.println("9090909021i=" + position + "<>" + age_name);
                                        selectedcategoryvalue.remove(age_name);
                                        category_Age_product = TextUtils.join(", ", list);

                                    }
                                }
                            });

                            llcategotyvalue.addView(view);
                        }
                    });
                }
            }

            if (filterName.equalsIgnoreCase("Discount")) {
                JSONArray jsonArray = new JSONArray(Discount_f);
                final List<String> list = new ArrayList<String>();
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String discount_id = jsonObject.getString("discount_id");
                    final String discount_value = jsonObject.getString("discount_value");

                    System.out.println("asasasasasasasqwqww=" + discount_value);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View view = li.inflate(R.layout.dynamic_filter_value, null);
                            //     llcategoty.setBackgroundColor(Color.parseColor("#ffffff"));
                            final CheckBox ivsize = view.findViewById(R.id.ivsize);
                            ivsize.setText(discount_value);

                            ivsize.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (((CheckBox) view).isChecked()) {

                                        System.out.println("9090909021i=" + position + "<>" + discount_value);
                                        selectedcategoryvalue.add(discount_value);
                                        list.add(discount_value);
                                        category_Discount_product = TextUtils.join(", ", list);


                                       /* System.out.println("9090909021i="+position+"<>"+brand_name);
                                        selectedcategoryvalue.add(brand_name);*/
                                    } else {
                                        System.out.println("9090909021i=" + position + "<>" + discount_value);
                                        selectedcategoryvalue.remove(discount_value);
                                        category_Discount_product = TextUtils.join(", ", list);
                                    }
                                }
                            });

                            llcategotyvalue.addView(view);
                        }
                    });
                }
            }

            if (filterName.equalsIgnoreCase("Color")) {
                JSONArray jsonArray = new JSONArray(Colorr);
                final List<String> list = new ArrayList<String>();
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String color_chart_id = jsonObject.getString("color_chart_id");
                    final String color_name = jsonObject.getString("color_name");
                    //    final String color_code = jsonObject.getString("color_code");

                    System.out.println("asasasasasasasqwqww=" + color_name);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View view = li.inflate(R.layout.dynamic_filter_value, null);
                            //  llcategoty.setBackgroundColor(Color.parseColor("#ffffff"));
                            final CheckBox ivsize = view.findViewById(R.id.ivsize);
                            ivsize.setText(color_name);

                            ivsize.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (((CheckBox) view).isChecked()) {

                                        System.out.println("9090909021i=" + position + "<>" + color_name);
                                        selectedcategoryvalue.add(color_chart_id);
                                        list.add(color_chart_id);
                                        category_Color_product = TextUtils.join(", ", list);


                                       /* System.out.println("9090909021i="+position+"<>"+brand_name);
                                        selectedcategoryvalue.add(brand_name);*/
                                    } else {
                                        System.out.println("9090909021i=" + position + "<>" + color_name);
                                        selectedcategoryvalue.remove(color_name);
                                        category_Color_product = TextUtils.join(", ", list);
                                    }
                                }
                            });

                            llcategotyvalue.addView(view);
                        }
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("asasasasasasasqwqww=" + e.getMessage());
        }
    }
}