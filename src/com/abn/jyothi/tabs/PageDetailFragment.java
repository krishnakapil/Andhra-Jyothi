package com.abn.jyothi.tabs;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;

import com.abn.jyothi.R;
import com.abn.jyothi.parser.NewsMessage;
import com.actionbarsherlock.app.SherlockFragment;

public class PageDetailFragment extends SherlockFragment 
{
	private View mainView;
	private WebView webView;
	private NewsMessage news;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		mainView = inflater.inflate(R.layout.page_detail_view, container, false);
		
		Bundle recdData = getArguments();
		news = recdData.getParcelable("news");
		
		setControls();
		
		return mainView;
	}
	
	
	private void setControls()
	{
		webView = (WebView) mainView.findViewById(R.id.webView1);

		 webView.getSettings().setPluginState(PluginState.ON);
		 webView.getSettings().setJavaScriptEnabled(true);
		 webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		 //webView.getSettings().setPluginsEnabled(true);
		 webView.getSettings().setSupportMultipleWindows(false);
		 webView.getSettings().setSupportZoom(false);
		 webView.setVerticalScrollBarEnabled(false);
		 webView.setHorizontalScrollBarEnabled(false);
		 webView.getSettings().setAllowFileAccess(true);
		 webView.setWebChromeClient(new WebChromeClient());
		 
		 
		 
		 webView.loadDataWithBaseURL(null,"<html><h3>"+news.getTitle()+"</h3>"+"<body>"+news.getNewsDesc()+"</body></html>", "text/html", "UTF-8",null);
	}
	
}
