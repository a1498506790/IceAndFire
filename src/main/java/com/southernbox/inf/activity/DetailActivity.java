package com.southernbox.inf.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.lidroid.xutils.BitmapUtils;
import com.southernbox.inf.R;
import com.southernbox.inf.bean.ContentBean;
import com.southernbox.inf.js.Js2Java;
import com.southernbox.inf.util.ServerAPI;

public class DetailActivity extends AppCompatActivity {

    private ContentBean.Content content;
    private Toolbar mToolbar;
    private ImageView mImageView;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        content = (ContentBean.Content) getIntent().getSerializableExtra("content");
        initView();
        initData();
    }

    public void initView() {
        mToolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        mImageView = (ImageView) findViewById(R.id.image_view);
        mWebView = (WebView) findViewById(R.id.web_view);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new Js2Java(this), "Android");
    }

    public void initData() {
        mToolbar.setTitle(content.name);
        BitmapUtils bitmapUtils = new BitmapUtils(this);
        bitmapUtils.configDefaultShowOriginal(false);
        bitmapUtils.configDefaultBitmapMaxSize(480, 480);
        bitmapUtils.display(mImageView, ServerAPI.BASE_URL + content.pic);
        mWebView.loadUrl(ServerAPI.BASE_URL + content.htmlUrl);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.setVisibility(View.GONE);
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mWebView.setVisibility(View.GONE);
            onBackPressed();
        }
        return true;
    }
}