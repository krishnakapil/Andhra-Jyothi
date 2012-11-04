package com.abn.jyothi;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Html;
import android.util.Log;

import com.abn.jyothi.tabs.PageDetailFragment;
import com.abn.jyothi.utils.Utility;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.actionbarsherlock.widget.ShareActionProvider;

public class PageActivity extends SherlockFragmentActivity 
{
	MyAdapter mAdapter;

    ViewPager mPager;
    
    private String category;
    private static int currPos;
    
    @Override
	public void onCreate(Bundle savedInstanceState) 
	{
		setTheme(R.style.Theme_Sherlock_Light_DarkActionBar);
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		
		setContentView(R.layout.activity_page_view);
		
		Bundle recdData = getIntent().getExtras();
		category = recdData.getString("category");
		currPos = recdData.getInt("position");
		
		getSupportActionBar().setTitle(category);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setSupportProgressBarIndeterminateVisibility(false);
		
		mAdapter = new MyAdapter(PageActivity.this,getSupportFragmentManager(),getApplicationContext(),category);

        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        mPager.setOnPageChangeListener(mAdapter);
        mPager.setCurrentItem(currPos, true);
		
	}
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        // Inflate your menu.
        getSupportMenuInflater().inflate(R.menu.share_action_provider, menu);

        // Set file with share history to the provider and set the share intent.
        MenuItem actionItem = menu.findItem(R.id.menu_item_share_action_provider_action_bar);
        ShareActionProvider actionProvider = (ShareActionProvider) actionItem.getActionProvider();
        actionProvider.setShareHistoryFileName(ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);
        // Note that you can set/change the intent any time,
        // say when the user has selected an image.
        actionProvider.setShareIntent(createShareIntent());

        //XXX: For now, ShareActionProviders must be displayed on the action bar
        // Set file with share history to the provider and set the share intent.
        //MenuItem overflowItem = menu.findItem(R.id.menu_item_share_action_provider_overflow);
        //ShareActionProvider overflowProvider =
        //    (ShareActionProvider) overflowItem.getActionProvider();
        //overflowProvider.setShareHistoryFileName(
        //    ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);
        // Note that you can set/change the intent any time,
        // say when the user has selected an image.
        //overflowProvider.setShareIntent(createShareIntent());

        return true;
    }
    
    private Intent createShareIntent() 
    {
    	Log.d("ABN","CLICKED POS " + currPos);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, Utility.getInstance().rssDataMesseges.get(category).get(currPos).getLink().trim());
        return shareIntent;
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
    
    
    public static class MyAdapter extends FragmentPagerAdapter implements OnPageChangeListener
    {
    	private Context mContext;
    	private String mCategory;
    	private SherlockFragmentActivity activity;
    	
        public MyAdapter(SherlockFragmentActivity act,FragmentManager fm,Context context,String Category) 
        {
            super(fm);
            mContext = context;
            mCategory = Category;
            activity = act;
        }

        @Override
        public int getCount() {
            return Utility.getInstance().rssDataMesseges.get(mCategory).size();
        }

        @Override
        public Fragment getItem(int position) 
        {
        	currPos = position;
        	Bundle b = new Bundle();
        	b.putParcelable("news",Utility.getInstance().rssDataMesseges.get(mCategory).get(position));
            return Fragment.instantiate(mContext, PageDetailFragment.class.getName(), b);
        }

        @Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) 
		{
        	
		}

		@Override
		public void onPageSelected(int position) 
		{
			currPos = position;	
			activity.invalidateOptionsMenu();
			Log.d("ABN","CURRENT POS " + currPos);
		}

		@Override
		public void onPageScrollStateChanged(int state) 
		{
		}
    }

}
