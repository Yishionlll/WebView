package com.example.yishion.webview;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsListView;
import android.widget.ProgressBar;

/**
 * Created by Yishion on 2017/4/11.
 */

public class ProgressWebView extends WebView{

    private Context context;
    private ProgressBar progressBar;
    private OnWebCallBack onWebCallBack;  //回调

    public ProgressWebView(Context context)
    {
        this(context,null);
    }

    public ProgressWebView(Context context,AttributeSet attrs)
    {
        this(context,attrs,android.R.attr.webTextViewStyle);
    }

    public ProgressWebView(Context context,AttributeSet attrs,int defStyle)
    {
        super(context,attrs,defStyle);
        this.context = context;
        init();
        setWebViewClient(new MyWebViewClient());
        setWebChromeClient(new WebChromeClient());
    }

    /**
     * 设置ProgressBar
     */

    void init()
    {
        progressBar = new ProgressBar(context,null,android.R.attr.progressBarStyleHorizontal);
        progressBar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,20,0,0));
        addView(progressBar);

    }

    public class WebChromeClient extends android.webkit.WebChromeClient
    {
        @Override
        public  void onProgressChanged(WebView view,int newProgress)
        {
            if (newProgress == 100)
            {
                progressBar.setVisibility(GONE);
            }
            else
            {
                progressBar.setVisibility(VISIBLE);
                progressBar.setProgress(newProgress);
            }
            super.onProgressChanged(view,newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view,String title)
        {
            super.onReceivedTitle(view,title);
            if(onWebCallBack != null)
            {
                //获取标题
                onWebCallBack.getTitle(title);
            }
        }

    }

    /**
     * 不重写的话，会跳到手机浏览器中
     * @author Yishion

     */

    public class MyWebViewClient extends WebViewClient
    {
        @Override
        public void onReceivedError(WebView view,int errorCode,String description,String failingUrl)
        {
            //Handle the
            goBack();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view,String url)
        {
            view.loadUrl(url);
            return true;

        }

        @Override
        public void onPageFinished(WebView view,String url)
        {
            super.onPageFinished(view,url);
        }

        @Override
        public void onPageStarted(WebView view,String url,Bitmap favicon)
        {
            if(onWebCallBack != null)
            {
                //获取WebView的地址
                onWebCallBack.getUrl(url);
            }
        }

    }

    @Override
    protected void onScrollChanged(int l,int t,int oldl,int oldt)
    {
        LayoutParams lp =(LayoutParams) progressBar.getLayoutParams();
        lp.x = l;
        lp.y = t;
        progressBar.setLayoutParams(lp);
        super.onScrollChanged(l,t,oldl,oldt);
    }

    /**
     * 设置WebView的回调器
     * @param onWebCallBack
     */

    void setOnWebCallBack(OnWebCallBack onWebCallBack)
    {
        this.onWebCallBack = onWebCallBack;
    }
}

interface OnWebCallBack
{
    /**
     * 获取标题
     * @param title
     */
    void getTitle(String title);


    /**
     * 获得WebView地址
     * @param url
     */
    void getUrl(String url);
}
