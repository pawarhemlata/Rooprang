package com.tsi.rooprang.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.tsi.rooprang.Activity.ProductActivity;
import com.tsi.rooprang.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BottomFilterFragment  extends AppCompatActivity {

    private LinearLayout llcategoty, llcategotyvalue;
    ArrayList<String> selectedcategoryvalue = new ArrayList<String>();
    int position;
    Button btapply;
    ImageView back;
    TextView tvclearfilter;
    ProgressDialog progress;
    String cat_id,sub_name,subcat_id,subsubcat_id,page,keyword,price,Brand,Color123,Size,category_price_product,category_Brand_product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_filter_sheet);

        cat_id=getIntent().getStringExtra("cat_id");
        sub_name=getIntent().getStringExtra("sub_name");
        subcat_id=getIntent().getStringExtra("scat_id");
        subsubcat_id=getIntent().getStringExtra("sscat_id");
        page=getIntent().getStringExtra("page");
        keyword=getIntent().getStringExtra("keyword");

        Log.e("homePageCategory", "====>" + cat_id+"<>"+sub_name+"<>"+subcat_id+"<>"+subsubcat_id+"<>"+page+"<>"+keyword);

        tvclearfilter = findViewById(R.id.tvclearfilter);
        back = findViewById(R.id.back);
        btapply = findViewById(R.id.btapply);
        llcategoty = findViewById(R.id.llcategoty);
        llcategotyvalue = findViewById(R.id.llcategotyvalue);

        progress=new ProgressDialog(this);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.setMessage("Loading...");



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(BottomFilterFragment.this, "Apply", Toast.LENGTH_SHORT).show();
              /*  startActivity(new Intent(BottomFilterFragment.this, ProductActivity.class).putExtra("cat_id",cat_id).putExtra("sub_name",sub_name).putExtra("scat_id",subcat_id).putExtra("sscat_id",subsubcat_id).putExtra("page","PFilter").putExtra("keyword",keyword));
                //startActivity(new Intent(PFilter.this, ProductListActivity.class).putExtra("cat_id",cat_id).putExtra("sub_name",sub_name).putExtra("scat_id",subcat_id).putExtra("sscat_id",subsubcat_id).putExtra("page","PFilter").putExtra("keyword",abc));
                finish();*/
            }
        });
        tvclearfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void filterMenuList(){
        progress.show();
        final String URL1 = "http://api.manthanonline.in/filterMenuList.php?cat_id=00210&subcat_id=00214";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("filterMenuList", "====>" + URL1);
                        progress.dismiss();
                        try
                        {
                            JSONObject jsonObject = new JSONObject(response);
                            final String message =jsonObject.getString("message");
                            String status =jsonObject.getString("status");
                            if(status.equalsIgnoreCase("1"))
                            {
                                String result =jsonObject.getString("result");
                                JSONObject jsonObjectresult = new JSONObject(result);
                                String filterName =jsonObjectresult.getString("filterName");

                                if(jsonObjectresult.has("price"))
                                {
                                    price =jsonObjectresult.getString("price");
                                }
                                if(jsonObjectresult.has("Brand"))
                                {
                                    Brand =jsonObjectresult.getString("Brand");
                                }
                                if(jsonObjectresult.has("Color"))
                                {
                                    Color123 =jsonObjectresult.getString("Color");
                                }
                                if(jsonObjectresult.has("Size"))
                                {
                                    Size =jsonObjectresult.getString("Size");
                                }

                                Log.e("filterMenuList", "====>" + filterName);

                                categoryfun(filterName,price,Brand,Color123,Size);
                            }
                            else
                            {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(BottomFilterFragment.this, message+"", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                        catch (Exception e)
                        {
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
                        Toast.makeText(BottomFilterFragment.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return null;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(BottomFilterFragment.this);
        requestQueue.add(stringRequest);
    }

    private void categoryfun(final String filterName,final String price,final String Brand,final String Color123,final String Size) {
        llcategoty.removeAllViews();
        try
        {
            JSONArray jsonArray = new JSONArray(filterName);
            for(int i=0;i<jsonArray.length();i++)
            {
                final String filterName111 =jsonArray.getString(i);
                LayoutInflater li=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = li.inflate(R.layout.dynamic_filter,null);

                final TextView ivsize = view.findViewById(R.id.ivsize);
                ivsize.setText(filterName111);

                if(position==i)
                {
                    ivsize.setBackgroundColor(Color.parseColor("#ffffff"));
                }
                else
                {
                    ivsize.setBackgroundColor(Color.parseColor("#EAEAEA"));
                }

                System.out.println("212121343434werwer="+position+"<>"+filterName111);

                position = 0;

                //categoryfun(filterName,price,Brand);
                categoryvaluefun(filterName111,price,Brand,Color123,Size);

                final int finalI = i;
                ivsize.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        position = finalI;
                        categoryfun(filterName,price,Brand,Color123,Size);
                        categoryvaluefun(filterName111,price,Brand,Color123,Size);
                    }
                });

                llcategoty.addView(view);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void categoryvaluefun(String filterName,String price,String Brand,String Color123,String Size) {
        llcategotyvalue.removeAllViews();
        System.out.println("asasasasasasasqwqww="+filterName);
        try
        {
            if(filterName.equalsIgnoreCase("Brand"))
            {
                JSONArray jsonArray = new JSONArray(Brand);
                final List<String> list = new ArrayList<String>();
                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject  jsonObject = jsonArray.getJSONObject(i);
                    String id =jsonObject.getString("id");
                    String variant_category =jsonObject.getString("variant_category");
                    final String name =jsonObject.getString("name");

                    System.out.println("asasasasasasasqwqww="+name);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LayoutInflater li=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View view = li.inflate(R.layout.dynamic_filter_value,null);

                            final CheckBox ivsize = view.findViewById(R.id.ivsize);
                            ivsize.setText(name);

                            ivsize.setOnClickListener(new View.OnClickListener()
                            {
                                @Override
                                public void onClick(View view)
                                {
                                    if(((CheckBox) view).isChecked())
                                    {
                                        System.out.println("9090909021i="+position+"<>"+name);
                                        selectedcategoryvalue.add(name);
                                        list.add(name);
                                        category_Brand_product = TextUtils.join(", ", list);
                                    }
                                    else
                                    {
                                        System.out.println("9090909021i="+position+"<>"+name);
                                        selectedcategoryvalue.remove(name);
                                        list.remove(name);
                                        category_Brand_product = TextUtils.join(", ", list);
                                    }
                                }
                            });

                            llcategotyvalue.addView(view);
                        }
                    });
                }
            }
            else if(filterName.equalsIgnoreCase("Color"))
            {
                JSONArray jsonArray = new JSONArray(Color123);
                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject  jsonObject = jsonArray.getJSONObject(i);
                    String id =jsonObject.getString("id");
                    String variant_category =jsonObject.getString("variant_category");
                    final String name =jsonObject.getString("name");

                    System.out.println("asasasasasasasqwqww="+name);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LayoutInflater li=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View view = li.inflate(R.layout.dynamic_filter_value,null);

                            final CheckBox ivsize = view.findViewById(R.id.ivsize);
                            ivsize.setText(name);

                            ivsize.setOnClickListener(new View.OnClickListener()
                            {
                                @Override
                                public void onClick(View view)
                                {
                                    if(((CheckBox) view).isChecked())
                                    {
                                        System.out.println("9090909021i="+position+"<>"+name);
                                        selectedcategoryvalue.add(name);
                                    }
                                    else
                                    {
                                        System.out.println("9090909021i="+position+"<>"+name);
                                        selectedcategoryvalue.remove(name);
                                    }
                                }
                            });

                            llcategotyvalue.addView(view);
                        }
                    });
                }
            }
            else if(filterName.equalsIgnoreCase("Size"))
            {
                JSONArray jsonArray = new JSONArray(Size);
                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject  jsonObject = jsonArray.getJSONObject(i);
                    String id =jsonObject.getString("id");
                    String variant_category =jsonObject.getString("variant_category");
                    final String name =jsonObject.getString("name");

                    System.out.println("asasasasasasasqwqww="+name);


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LayoutInflater li=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View view = li.inflate(R.layout.dynamic_filter_value,null);

                            final CheckBox ivsize = view.findViewById(R.id.ivsize);
                            ivsize.setText(name);

                            ivsize.setOnClickListener(new View.OnClickListener()
                            {
                                @Override
                                public void onClick(View view)
                                {
                                    if(((CheckBox) view).isChecked())
                                    {
                                        System.out.println("9090909021i="+position+"<>"+name);
                                        selectedcategoryvalue.add(name);
                                    }
                                    else
                                    {
                                        System.out.println("9090909021i="+position+"<>"+name);
                                        selectedcategoryvalue.remove(name);
                                    }
                                }
                            });

                            llcategotyvalue.addView(view);
                        }
                    });
                }
            }
            else if(filterName.equalsIgnoreCase("price"))
            {
                JSONObject jsonArray = new JSONObject(price);
                final List<String> list = new ArrayList<String>();
                for(int i=0;i<jsonArray.length();i++)
                {
                    final String category = jsonArray.getString(i+"");

                    System.out.println("asSASSSS="+category);

                    LayoutInflater li=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View view = li.inflate(R.layout.dynamic_filter_value,null);

                    final CheckBox ivsize = view.findViewById(R.id.ivsize);
                    ivsize.setText(category);

                    ivsize.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view)
                        {
                            if(((CheckBox) view).isChecked())
                            {
                                System.out.println("9090909021i="+position+"<>"+category);
                                selectedcategoryvalue.add(category);
                                list.add(category);
                                category_price_product = TextUtils.join(", ", list);
                            }
                            else
                            {
                                System.out.println("9090909021i="+position+"<>"+category);
                                selectedcategoryvalue.remove(category);
                                list.remove(category);
                                category_price_product = TextUtils.join(", ", list);
                            }
                        }
                    });

                    llcategotyvalue.addView(view);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("asasasasasasasqwqww="+e.getMessage());
        }
    }
}
