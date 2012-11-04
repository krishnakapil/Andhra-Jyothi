package com.abn.jyothi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.abn.jyothi.parser.AndroidSaxFeedParser;
import com.abn.jyothi.utils.NetworkAsyncTask;
import com.abn.jyothi.utils.Utility;

public class SplashActivity extends Activity 
{
	private Utility util;

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        util = Utility.getInstance();
        
        new CheckNetworkConnectionTask(SplashActivity.this, "", "", false).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        //getMenuInflater().inflate(R.menu.activity_splash, menu);
        return true;
    }
    
    
    private class CheckNetworkConnectionTask extends NetworkAsyncTask
    {

		public CheckNetworkConnectionTask(Context context, String title,
				String message, boolean showDialog) 
		{
			super(context, title, message, showDialog);
		}

		@Override
		protected void perform() 
		{
			try 
			{
				AndroidSaxFeedParser parser = new AndroidSaxFeedParser(Utility.TOP_NEWS_URL);
				Utility.getInstance().rssDataMesseges.put("Top News", parser.parse());
				
				parser = new AndroidSaxFeedParser(Utility.FEATURED_NEWS_URL);
				Utility.getInstance().rssDataMesseges.put("Featured News", parser.parse());
				
				parser = new AndroidSaxFeedParser(Utility.BREAKING_NEWS_URL);
				Utility.getInstance().rssDataMesseges.put("Breaking News", parser.parse());
				
				parser = new AndroidSaxFeedParser(Utility.POLITICAL_NEWS_URL);
				Utility.getInstance().rssDataMesseges.put("Political News", parser.parse());
				
				parser = new AndroidSaxFeedParser(Utility.GENERAL_NEWS_URL);
				Utility.getInstance().rssDataMesseges.put("General News", parser.parse());
				
				parser = new AndroidSaxFeedParser(Utility.EDUCATION_NEWS_URL);
				Utility.getInstance().rssDataMesseges.put("Education", parser.parse());
				
				parser = new AndroidSaxFeedParser(Utility.CINEMA_NEWS_URL);
				Utility.getInstance().rssDataMesseges.put("Cinema News", parser.parse());
			} 
			catch (Exception e) 
			{
				Utility.getInstance().showToastLong(SplashActivity.this, SplashActivity.this.getResources().getString(com.abn.jyothi.R.string.general_error));
			}
		}

		@Override
		protected void postSuccess() 
		{
			Log.d("ABN", "DONE SPLASH");
			
			finish();
			Intent intent = new Intent(SplashActivity.this, HomeScreenActivity.class);
			startActivity(intent);
		}
    	
    }
    
}
