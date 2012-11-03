package com.abn.jyothi;

import com.abn.jyothi.utils.NetworkAsyncTask;
import com.abn.jyothi.utils.Utility;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;

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
				Thread.sleep(3000);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
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
