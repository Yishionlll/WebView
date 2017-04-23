package com.example.yishion.webview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView title_tv;
    private TextView url_tv;
    private ProgressWebView webView;
    private String url = "http://www.baidu.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title_tv = (TextView) findViewById(R.id.tv);
        url_tv = (TextView) findViewById(R.id.url);

        webView = (ProgressWebView) findViewById(R.id.web);

        //设置WebView的回调函数，获得url
        webView.setOnWebCallBack(new OnWebCallBack() {
            @Override
            public void getTitle(String title) {
                title_tv.setText(title);
            }

            //获取当前的web的URL地址
            @Override
            public void getUrl(String url)
            {
                url_tv.setText(url);

            }


        });

        webView.loadUrl(url);

    }
}
