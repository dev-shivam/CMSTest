package com.shivamsatija.csmtest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Web Activity for CMS on HTML
 */

public class CMSWebviewActivity extends AppCompatActivity {

    public static final String PARAM_CMS_URL = "PARAM_CMS_URL";
    private String TEST_STATE = AppConstants.TEST_STATE.INSTRUCTIONS.getValue();

    WebView cmsWebView;

    ProgressBar pb_cms;

    private boolean isForceExit = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_cms_web);

        if (!getIntent().hasExtra(PARAM_CMS_URL)) {
            Toast.makeText(this, "Error loading data!!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        cmsWebView = findViewById(R.id.wv_cms);
        pb_cms = findViewById(R.id.pb_cms);

        setUpUI();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setUpUI() {
        //  Base Settings for webview
        cmsWebView.getSettings().setJavaScriptEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        // Webview Cookies
        CookieManager.getInstance().setAcceptCookie(true);
        CookieManager.allowFileSchemeCookies();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(cmsWebView, true);
        }

        // Webview Add-Ons
        cmsWebView.setWebViewClient(new CMSWebViewClient());
        cmsWebView.setWebChromeClient(new MyWebChromeClient());
        cmsWebView.addJavascriptInterface(new CMSInterface(), "mobile");
        cmsWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        cmsWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        cmsWebView.getSettings().setDomStorageEnabled(true);
        cmsWebView.getSettings().setPluginState(WebSettings.PluginState.ON_DEMAND);
        cmsWebView.getSettings().setAppCacheEnabled(true);

        if (Build.VERSION.SDK_INT >= 19) {
            cmsWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        else if(Build.VERSION.SDK_INT >=11 && Build.VERSION.SDK_INT < 19) {
            cmsWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        // Load URL
        cmsWebView.loadUrl(getIntent().getStringExtra(PARAM_CMS_URL));
    }

    private class MyWebChromeClient extends WebChromeClient{
        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            Log.d("MSG",consoleMessage.message());
            return super.onConsoleMessage(consoleMessage);
        }
    }

    private class CMSWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            pb_cms.setVisibility(View.GONE);
            super.onPageFinished(view, url);
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            Log.d("ERROR",error.getDescription().toString());
            super.onReceivedError(view, request, error);
        }
    }

    private class CMSInterface {

        public CMSInterface() {

        }

        @JavascriptInterface
        public void setTestState(String state) {

            if (state.equals(AppConstants.TEST_STATE.INSTRUCTIONS.getValue())) {
                TEST_STATE = AppConstants.TEST_STATE.INSTRUCTIONS.getValue();
            } else if (state.equals(AppConstants.TEST_STATE.TEST.getValue())) {
                TEST_STATE = AppConstants.TEST_STATE.TEST.getValue();
            } else if (state.equals(AppConstants.TEST_STATE.REPORT.getValue())) {
                TEST_STATE = AppConstants.TEST_STATE.REPORT.getValue();
            } else if (state.equals(AppConstants.TEST_STATE.SOLUTIONS.getValue())) {
                TEST_STATE = AppConstants.TEST_STATE.SOLUTIONS.getValue();
            } else {
                // If test is opened from notification, then there will be no back stack.
                if (isTaskRoot()) {
                    /*Intent intent = new Intent(CMSWebviewActivity.this, CheckUserActivity.class);
                    startActivity(intent);*/
                }
                isForceExit = false;
                CMSWebviewActivity.this.finish();
            }
        }
    }

    /**
     * Function call to notify user actions on the web
     *
     * @param type :  AppConstant.WEB_TYPE
     */

    private void updateWebInterface(String type) {
        cmsWebView.loadUrl("javascript:onMobileUpdate('" + type + "')");
    }

    @Override
    public void onBackPressed() {
        updateWebInterface(AppConstants.WEB_TYPE.QUIT.getValue());
    }

    @Override
    protected void onPause() {
        if (TEST_STATE.equals(AppConstants.TEST_STATE.TEST.getValue())) {
            updateWebInterface(AppConstants.WEB_TYPE.BACKGROUND.getValue());
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (TEST_STATE.equals(AppConstants.TEST_STATE.TEST.getValue())) {
            updateWebInterface(AppConstants.WEB_TYPE.FOREGROUND.getValue());
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (isForceExit) {
            updateWebInterface(AppConstants.WEB_TYPE.FORCE_QUIT.getValue());
        }
        super.onDestroy();
    }



}

