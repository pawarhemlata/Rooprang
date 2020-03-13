package com.tsi.rooprang.Account;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.tsi.rooprang.R;

public class Help_Activity extends AppCompatActivity {
    private Toolbar tool;
    private WebView webView_help;
    private String url=" http://www.rooprangstores.com/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        tool = findViewById(R.id.toolbar);
        tool.setTitle("Help_Activity Center");
        tool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        webView_help=findViewById(R.id.webview_help);
        webView_help.setWebViewClient(new MyBrowser());



        webView_help.getSettings().setLoadsImagesAutomatically(true);
        webView_help.getSettings().setJavaScriptEnabled(true);
        webView_help.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView_help.loadUrl(url);

    }
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
