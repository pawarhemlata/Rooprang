package com.tsi.rooprang.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.tsi.rooprang.Account.Help_Activity;
import com.tsi.rooprang.Account.Saved_Address_Activity;
import com.tsi.rooprang.Account.SavedCards_Activity;
import com.tsi.rooprang.Account.YourOrderActivity;
import com.tsi.rooprang.Activity.MainActivity;
import com.tsi.rooprang.Account.ProfileDetailsActivity;
import com.tsi.rooprang.LoginSignIn.SignInActivity;
import com.tsi.rooprang.R;
import com.tsi.rooprang.Session.SessionManager;
import com.tsi.rooprang.utils.LocaleHelper;

import static com.tsi.rooprang.Activity.MainActivity.bottomNavigation;

public class AccountFragment extends Fragment {


    private Button signin_account;
    private TextView tv_username, usermobileno;
    private RelativeLayout rl_img;
    ImageView imageView;
    TextView tv_order, change_language, tv_wishlist, tv_profiledetails, tv_savedaddress, tv_savedcards, tv_reset_pwd, tv_help, tv_feedback, tv_share, tv_rate;
    Button signout;
    Switch switch_change_lang;
    private String mLanguageCode = "en";
    private Context context;
    private String url = "";
    private String mCountryCode = "US";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        rl_img = view.findViewById(R.id.rl_img);
        tv_username = view.findViewById(R.id.tv_username);
        usermobileno = view.findViewById(R.id.usermobileno);
        signin_account = view.findViewById(R.id.signin_account);
        imageView = view.findViewById(R.id.userimage);
        tv_order = view.findViewById(R.id.order);
        tv_wishlist = view.findViewById(R.id.wishlist);
        tv_profiledetails = view.findViewById(R.id.profiledetails);
        tv_savedaddress = view.findViewById(R.id.savedaddress);
        tv_savedcards = view.findViewById(R.id.savedcards);

        tv_help = view.findViewById(R.id.help);
        tv_feedback = view.findViewById(R.id.feedback);
        tv_share = view.findViewById(R.id.share);
        tv_rate = view.findViewById(R.id.rate);
        signout = view.findViewById(R.id.signout);
        change_language = view.findViewById(R.id.change_language);
        switch_change_lang = view.findViewById(R.id.switch_change_lang);
        LocaleHelper.setApplicationLanguage(context, SessionManager.getInstance(context).get_data_from_session(SessionManager.KEY_LANGUAGE),SessionManager.getInstance(context).get_data_from_session(SessionManager.KEY_LANG_COUNTRY));

        if (SessionManager.getInstance(context).get_data_from_session(SessionManager.KEY_LANGUAGE).equalsIgnoreCase("en")) {
            switch_change_lang.setChecked(false);

        } else {
            switch_change_lang.setChecked(true);

        }

        if (SessionManager.getInstance(context).get_data_from_session(SessionManager.KEY_USERID).equals("")) {
            tv_username.setText("Hey");
        } else {
            usermobileno.setVisibility(View.VISIBLE);
            signin_account.setVisibility(View.GONE);
            signout.setVisibility(View.VISIBLE);
            tv_username.setText("Hey " + SessionManager.getInstance(context).get_data_from_session(SessionManager.KEY_NAME));
            usermobileno.setText(SessionManager.getInstance(context).get_data_from_session(SessionManager.KEY_PHONE));
        }


        tv_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SessionManager.getInstance(context).get_data_from_session(SessionManager.IS_LOGIN).equalsIgnoreCase("true")) {
                    Intent intent = new Intent(getActivity(), YourOrderActivity.class);
                    startActivity(intent);
                } else {
                     Toast.makeText(context, "Please Sign In First", Toast.LENGTH_SHORT).show();

                }
            }
        });

        /*change_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangeLanguageActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });*/

        tv_wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SessionManager.getInstance(getActivity()).get_data_from_session(SessionManager.IS_LOGIN).equalsIgnoreCase("true")) {
                    bottomNavigation.setSelectedItemId(R.id.navwish);
                    WishlistFragment wishlistFragment = new WishlistFragment();
                    FragmentManager fragmentManager2 = getFragmentManager();
                    FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                    fragmentTransaction2.setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out);
                    fragmentTransaction2.replace(R.id.container, wishlistFragment);

                    fragmentTransaction2.commit();
                } else {
                    Toast.makeText(getActivity(), "Please Sign In First", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tv_profiledetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SessionManager.getInstance(context).get_data_from_session(SessionManager.IS_LOGIN).equalsIgnoreCase("true")) {
                    Intent intent = new Intent(getActivity(), ProfileDetailsActivity.class);
                    startActivity(intent);
                } else {
                     Toast.makeText(context, "Please Sign In First", Toast.LENGTH_SHORT).show();

                }
            }
        });

        tv_savedaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SessionManager.getInstance(context).get_data_from_session(SessionManager.IS_LOGIN).equalsIgnoreCase("true")) {
                    Intent intent = new Intent(getActivity(), Saved_Address_Activity.class);
                    intent.putExtra("come_frag", "come_frag");
                    startActivity(intent);
                } else {
                    Toast.makeText(context, "Please Sign In First", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tv_savedcards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SessionManager.getInstance(context).get_data_from_session(SessionManager.KEY_USERID).equals("")) {
                    Toast.makeText(context, "Please SignIn First", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getActivity(), SavedCards_Activity.class);
                    startActivity(intent);
                }
            }
        });

        tv_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Help_Activity.class);
                startActivity(intent);
            }
        });

        tv_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*    Intent intent = new Intent(getActivity(), Feedback_Activity.class);
                startActivity(intent);*/
            }
        });

        tv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Rooprang");
                String shareMessage = "\nLet me recommend you this application\n\n";
                shareMessage = shareMessage + " http://www.rooprangstores.com/" + "";
                intent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(intent, "choose one"));

                startActivity(intent);
            }
        });

        tv_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   Intent intent = new Intent(getActivity(), Rate_Activity.class);
                startActivity(intent);*/
            }
        });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileDetailsActivity.class);
                startActivity(intent);
            }
        });

        signin_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), SignInActivity.class);

                startActivity(intent);
            }
        });

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManager.getInstance(context).clearPrefs();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        changelanguage();
        return view;

    }

    private void changelanguage() {
        switch_change_lang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if (buttonView.isChecked()) {

                    mLanguageCode = "hi";
                    mCountryCode="IN";
                    LocaleHelper.setApplicationLanguage(context, mLanguageCode,mCountryCode);
                    SessionManager.getInstance(context).set_data_in_session(SessionManager.KEY_LANGUAGE, mLanguageCode);
                    SessionManager.getInstance(context).set_data_in_session(SessionManager.KEY_LANG_COUNTRY, mCountryCode);

                    Toast.makeText(context, "" + switch_change_lang.getText(), Toast.LENGTH_SHORT).show();

                } else {
                    mLanguageCode = "en";
                    mCountryCode="US";
                    LocaleHelper.setApplicationLanguage(context, mLanguageCode,mCountryCode);
                    SessionManager.getInstance(context).set_data_in_session(SessionManager.KEY_LANGUAGE, mLanguageCode);
                    SessionManager.getInstance(context).set_data_in_session(SessionManager.KEY_LANG_COUNTRY, mCountryCode);
                    Toast.makeText(context, "" + switch_change_lang.getText(), Toast.LENGTH_SHORT).show();

                }
                Intent i = new Intent(context, MainActivity.class);
                startActivity(i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK));
            }

        });
    }

}
