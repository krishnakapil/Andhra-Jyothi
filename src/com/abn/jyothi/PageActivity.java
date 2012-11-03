package com.abn.jyothi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.abn.jyothi.tabs.PageDetailFragment;
import com.abn.jyothi.utils.Utility;
import com.actionbarsherlock.R;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;

public class PageActivity extends SherlockFragmentActivity 
{
	MyAdapter mAdapter;

    ViewPager mPager;
    
    private String category;
    private int currPos;
    
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
		
		mAdapter = new MyAdapter(getSupportFragmentManager(),getApplicationContext(),category);

        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        mPager.setCurrentItem(currPos, true);
		
	}
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        switch (item.getItemId()) 
        {
            case android.R.id.home:
                // go to previous screen when app icon in action bar is clicked
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    
    public static class MyAdapter extends FragmentPagerAdapter 
    {
    	private Context mContext;
    	private String mCategory;
    	
        public MyAdapter(FragmentManager fm,Context context,String Category) 
        {
            super(fm);
            mContext = context;
            mCategory = Category;
        }

        @Override
        public int getCount() {
            return Utility.getInstance().rssDataMesseges.get(mCategory).size();
        }

        @Override
        public Fragment getItem(int position) 
        {
        	Bundle b = new Bundle();
        	b.putParcelable("news",Utility.getInstance().rssDataMesseges.get(mCategory).get(position));
            return Fragment.instantiate(mContext, PageDetailFragment.class.getName(), b);
        }
    }

}
