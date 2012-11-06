package com.abn.jyothi;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

public class EPaperActivity extends SherlockActivity 
{
	String url;
	WebView web;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{

		setTheme(R.style.Theme_Sherlock_Light_DarkActionBar);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.epaper);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		url = getIntent().getStringExtra("epaper");
		Log.i("url","is::"+url);
		web = (WebView) findViewById(R.id.epaperview);
		web.getSettings().setJavaScriptEnabled(true);
		web.getSettings().setBuiltInZoomControls(true);
		web.getSettings().setSupportZoom(true);
		//		web.getSettings().setLoadWithOverviewMode(true);
		//        web.getSettings().setUseWideViewPort(true);
		//  web.getSettings().setPluginState(WebSettings.PluginState.ON);
		// web.getSettings().setPluginsEnabled(true); 
		web.setWebViewClient(new myWebClient());

		web.loadUrl(url);

	}

	public class myWebClient extends WebViewClient
	{
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			Log.v("epaper-----webview","override"+url);
			view.loadUrl(url);
			return true;

		}
	}
	
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
    	Log.d("ABN","MENU ITEM CLICKED " + item.getItemId());
    	
        switch (item.getItemId()) 
        {
            case android.R.id.home:
                // go to previous screen when app icon in action bar is clicked
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

	// To handle "Back" key press event for WebView to go back to previous screen.
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		Log.d("epaper","goback");
		if ((keyCode == KeyEvent.KEYCODE_BACK) && web.canGoBack()) {
			web.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
