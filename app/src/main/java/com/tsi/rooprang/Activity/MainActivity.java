package com.tsi.rooprang.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tsi.rooprang.Fragments.AccountFragment;
import com.tsi.rooprang.Fragments.AddressBottomFragment;
import com.tsi.rooprang.Fragments.CartFragment;
import com.tsi.rooprang.Fragments.CategoryFragment;
import com.tsi.rooprang.Fragments.HomeFragment;
import com.tsi.rooprang.Fragments.WishlistFragment;
import com.tsi.rooprang.LoginSignIn.SignInActivity;
import com.tsi.rooprang.NotificationScreen.Notification;
import com.tsi.rooprang.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tsi.rooprang.Session.SessionManager;
import com.tsi.rooprang.utils.LocaleHelper;

public class MainActivity extends AppCompatActivity implements AddressBottomFragment.BottomSheetListener {
    public static Toolbar toolbar;
    public static BottomNavigationView bottomNavigation;
    TextView search, notification;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    public static MainActivity mainActivity;
    private Activity activity;
    String go_to_wishlist = "", go_to_cart = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity = this;
        activity = this;

        if (getIntent() != null) {
            go_to_wishlist = getIntent().getStringExtra("login") != null ? getIntent().getStringExtra("login") : "";
            go_to_cart = getIntent().getStringExtra("login") != null ? getIntent().getStringExtra("login") : "";

        }
//        LocaleHelper.setApplicationLanguage(context, SessionManager.getInstance(context).get_data_from_session(SessionManager.KEY_LANGUAGE),SessionManager.getInstance(MainActivity.this).get_data_from_session(SessionManager.KEY_LANG_COUNTRY));
        Log.e("id", SessionManager.getInstance(this).get_data_from_session(SessionManager.KEY_USERID) + "");
        search = findViewById(R.id.search);
        notification = findViewById(R.id.notification);
        toolbar = activity.findViewById(R.id.main_toolbar);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });


        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Notification.class);
                startActivity(intent);
            }
        });


        bottomNavigation = findViewById(R.id.bottomnavigationview);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);


        toolbar.setTitle(R.string.home);

        if (go_to_wishlist != null) {
            if (go_to_wishlist.equals("go_to_wishlist")) {
                WishlistFragment wishlistFragment = new WishlistFragment();
                toolbar.setTitle(R.string.wishlist);
                FragmentManager fragmentManager2 = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                fragmentTransaction2.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction2.replace(R.id.container, wishlistFragment);
                fragmentTransaction2.commit();
                bottomNavigation.setSelectedItemId(R.id.navwish);
            } else {
                HomeFragment fragment = new HomeFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.commit();
                bottomNavigation.setSelectedItemId(R.id.navhome);
            }
        } else if (go_to_cart != null) {
            if (go_to_wishlist.equals("go_to_cart")) {
                CartFragment cartFragment = new CartFragment();
                toolbar.setTitle(R.string.cart);
                FragmentManager fragmentManager3 = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction4 = fragmentManager3.beginTransaction();
                fragmentTransaction4.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction4.replace(R.id.container, cartFragment);
                fragmentTransaction4.addToBackStack(null);
                fragmentTransaction4.commit();
                bottomNavigation.setSelectedItemId(R.id.navcart);
            } else {
                HomeFragment fragment = new HomeFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.commit();
                bottomNavigation.setSelectedItemId(R.id.navhome);
            }
        } else {
            HomeFragment fragment = new HomeFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out);
            fragmentTransaction.replace(R.id.container, fragment);
            fragmentTransaction.commit();
            bottomNavigation.setSelectedItemId(R.id.navhome);
        }

    }


    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navhome:

                    toolbar.setTitle(getResources().getString(R.string.home));

                    HomeFragment fragment = new HomeFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out);
                    fragmentTransaction.replace(R.id.container, fragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navcategory:
                    CategoryFragment categoryFragment = new CategoryFragment();
                    toolbar.setTitle(R.string.categry);
                    FragmentManager f = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction1 = f.beginTransaction();
                    fragmentTransaction1.setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out);
                    fragmentTransaction1.replace(R.id.container, categoryFragment);

                    fragmentTransaction1.commit();
                    return true;
                case R.id.navwish:

                    if (SessionManager.getInstance(MainActivity.this).get_data_from_session(SessionManager.IS_LOGIN).equalsIgnoreCase("true")) {

                        WishlistFragment wishlistFragment = new WishlistFragment();
                        toolbar.setTitle(R.string.wishlist);
                        FragmentManager fragmentManager2 = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                        fragmentTransaction2.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                        fragmentTransaction2.replace(R.id.container, wishlistFragment);
                        fragmentTransaction2.commit();
                    } else {
                        Toast.makeText(MainActivity.this, "Please Login First", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                        intent.putExtra("come_from", "wishlist");
                        startActivity(intent);
                    }

                    return true;

                case R.id.navaccount:

                    AccountFragment accountFragment = new AccountFragment();
                    toolbar.setTitle(R.string.account);
                    FragmentManager fragmentManager1 = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction3 = fragmentManager1.beginTransaction();
                    fragmentTransaction3.setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out);
                    fragmentTransaction3.replace(R.id.container, accountFragment);


                    fragmentTransaction3.commit();
                    return true;

                case R.id.navcart:

                    if (SessionManager.getInstance(MainActivity.this).get_data_from_session(SessionManager.IS_LOGIN).equalsIgnoreCase("true")) {

                        CartFragment cartFragment = new CartFragment();
                        toolbar.setTitle(R.string.cart);
                        FragmentManager fragmentManager3 = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction4 = fragmentManager3.beginTransaction();
                        fragmentTransaction4.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                        fragmentTransaction4.replace(R.id.container, cartFragment);
                        fragmentTransaction4.addToBackStack(null);
                        fragmentTransaction4.commit();

                    } else {
                        Toast.makeText(MainActivity.this, "Please Login First", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                        intent.putExtra("come_from", "cart_fragment");
                        startActivity(intent);


                    }

                    return true;
            }


            return false;
        }
    };

    @Override
    public void onButtonClicked(String text) {

    }


    @Override
    public void onBackPressed() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure, You wanted to exit");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }
}
