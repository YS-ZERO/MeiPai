package pri.ys.meipai.ui;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import net.ys.base.BaseActivity;

import pri.ys.meipai.R;

public class InfoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        initView();
    }

    private WebView mWebView;

    private void initView() {
        mWebView = (WebView) findViewById(R.id.activity_info_webview);

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        mWebView.loadUrl(url);
    }


}
