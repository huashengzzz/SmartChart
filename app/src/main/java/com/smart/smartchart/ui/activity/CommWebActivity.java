package com.smart.smartchart.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.smart.smartchart.R;
import com.smart.smartchart.ui.base.BaseSwipeBackActivity;
import com.smart.smartchart.utils.CommonUtils;
import com.smart.smartchart.utils.ViewUtil;

import butterknife.BindView;

/**
 * Created by Gs on 2017/4/21.
 */

public class CommWebActivity extends BaseSwipeBackActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.webview)
    WebView mWebView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private String url;
    private  WebSettings settings;

    public static void launchCommWebActivity(Context context, String title, String url) {
        Intent intent = new Intent(context, CommWebActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_comm_web;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        initToolbar();
        setupWebView();
    }

    private void setupWebView() {
        url = getIntent().getStringExtra("url");

        settings = mWebView.getSettings();
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setUseWideViewPort(true);//将图片调整到适合webview的大小
        settings.setLoadWithOverviewMode(true);//将图片调整到适合webview的大小
        settings.setSupportZoom(true);//将图片调整到适合webview的大小
        settings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        settings.setBuiltInZoomControls(false);//防止界面退出后网页加减按钮还没消失导致程序崩溃//设置内置的缩放控件。若为false，则该WebView不可缩放
        settings.setJavaScriptEnabled(true);

        settings.setAllowFileAccess(true); // 允许访问文件
        settings.setSavePassword(true);
        settings.setSaveFormData(true);// 保存表单数据
        settings.setDomStorageEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setSupportMultipleWindows(true);// 新加
        settings.setPluginState(WebSettings.PluginState.ON);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//android 7.0第二次界面跳转加载的会是一个对象，不是一个链接
                    view.loadUrl(request.getUrl().toString());
                } else {
                    view.loadUrl(request.toString());
                }

                return true;
            }
            //默认webview是不支持https的
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                if (error.getPrimaryError() == SslError.SSL_DATE_INVALID//证书的日期是无效的
                        || error.getPrimaryError() == SslError.SSL_EXPIRED//证书已经过期
                        || error.getPrimaryError() == SslError.SSL_INVALID//一个通用的错误发生
                        || error.getPrimaryError() == SslError.SSL_UNTRUSTED) {//不受信任的证书颁发机构
                    handler.proceed();
                } else {
                    handler.cancel();
                }

                super.onReceivedSslError(view, handler, error);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (!isActive) {
                    return;
                }
                if (newProgress == 100) {
                    ViewUtil.gone(progressBar);
                } else {
                    if (progressBar.getVisibility() == View.GONE) {
                        ViewUtil.visible(progressBar);
                    }
                    progressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        mWebView.addJavascriptInterface(new PreviewJsInterface(this), "PreviewJSBridge");
        mWebView.loadUrl(url);

    }

    public class PreviewJsInterface {
        private Context mContext;

        public PreviewJsInterface(Context mContext) {
            this.mContext = mContext;
        }

        @JavascriptInterface
        public void preView(String url) {
//            Intent intent = new Intent(mContext, PreviewActivity.class);
//            ArrayList<String> list = new ArrayList<>();
//            list.add(url);
//            intent.putStringArrayListExtra("list", list);
//            mContext.startActivity(intent);
        }

    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.icon_btn_back);
        Intent intent = getIntent();
        if (intent.hasExtra("title")) {
            title.setText(intent.getStringExtra("title"));
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtils.isSingleActivity(CommWebActivity.this)) {
                    Intent intent = new Intent(CommWebActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        settings.setJavaScriptEnabled(true);
        try {
            mWebView.getClass().getMethod("onResume").invoke(mWebView, (Object[]) null);//（低版本测试正常）
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            mWebView.getClass().getMethod("onPause").invoke(mWebView, (Object[]) null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        settings.setJavaScriptEnabled(false);// 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (!TextUtils.isEmpty(url)){
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                    return true;
                } else {
                    if (CommonUtils.isSingleActivity(this)) {
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }
    //还可以监听应用是否切换到后台 webView.pauseTimers()
    //这个方法不仅仅针对当前的webview而是全局的全应用程序的webview
    //它会暂停所有webview的layout，parsing，javascripttimer。降低CPU功耗。

    @Override
    protected void onDestroy() {
        if (mWebView != null) {//防止内存泄露 webview默认在创建的时候拥有当前activity的context，最好是的方式是动态添加webview
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();
            mWebView.removeAllViews();
            ((ViewGroup)mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }
}
