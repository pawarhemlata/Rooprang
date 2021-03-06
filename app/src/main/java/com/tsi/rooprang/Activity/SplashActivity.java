package com.tsi.rooprang.Activity;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.tsi.rooprang.R;

public class SplashActivity extends AppCompatActivity {
 Animation zoomin, zoomout,blink;
    Handler handler;

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        StartAnimations();
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashActivity.this,WelcomeActivity.class);
                startActivity(intent);

                finish();
            //    overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

            }
        },4500);


    }
    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l=(LinearLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);
        zoomin = AnimationUtils.loadAnimation(this, R.anim.zoomin);
        zoomin.reset();
        zoomout = AnimationUtils.loadAnimation(this, R.anim.zoomout);
        zoomout.reset();

        AnimationSet s = new AnimationSet(false);
        s.addAnimation(zoomin);
        s.addAnimation(zoomout);

      /*  anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();*/
        ImageView iv = (ImageView) findViewById(R.id.logo);
        iv.clearAnimation();
        iv.startAnimation(s);




    }
}
