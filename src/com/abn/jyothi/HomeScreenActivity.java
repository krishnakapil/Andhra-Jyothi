package com.abn.jyothi;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;

import com.abn.jyothi.parser.NewsMessage;
import com.abn.jyothi.tabs.NewsTabsFragment;
import com.abn.jyothi.utils.Utility;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.actionbarsherlock.view.Window;

public class HomeScreenActivity extends SherlockFragmentActivity 
{
	private static final int RESULT_CLOSE_ALL = 0;
	private TabHost mTabHost;
	private ViewPager  mViewPager;
	private TabsAdapter mTabsAdapter;
	private HorizontalScrollView mHorzScroll;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		setTheme(R.style.Theme_Sherlock_Light_DarkActionBar);
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		
		setSupportProgressBarIndeterminateVisibility(false);
		
		setContentView(R.layout.activity_home_screen);
		
		getSupportActionBar().setSubtitle(getResources().getString(R.string.title_action));
		
		setControls();
		
		if (savedInstanceState != null) 
		{
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
        }
		
		Bundle b = getIntent().getExtras();
		
		if(b != null)
		{
			mTabHost.setCurrentTabByTag(b.getString("category"));
			int position = mTabHost.getCurrentTab();
			mViewPager.setCurrentItem(position);
			mHorzScroll.scrollTo(position * mTabHost.getCurrentTabView().getWidth(), 0);
		}
		
	}

	@Override
    protected void onSaveInstanceState(Bundle outState) 
	{
        super.onSaveInstanceState(outState);
        outState.putString("tab", mTabHost.getCurrentTabTag());
    }
	
	
	private void setControls()
	{
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		
		mTabHost = (TabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup();
        

        mViewPager = (ViewPager)findViewById(R.id.pager);
        
        mHorzScroll = (HorizontalScrollView)findViewById(R.id.horzScrollView);

        mTabsAdapter = new TabsAdapter(this, mTabHost, mViewPager,mHorzScroll);

        mTabsAdapter.addTab(mTabHost.newTabSpec("Top News").setIndicator("  Top News  "),
        		NewsTabsFragment.class, Utility.TOP_NEWS_URL);
        mTabsAdapter.addTab(mTabHost.newTabSpec("Featured News").setIndicator("  Featured News  "),
        		NewsTabsFragment.class, Utility.FEATURED_NEWS_URL);
        mTabsAdapter.addTab(mTabHost.newTabSpec("Breaking News").setIndicator("  Breaking News  "),
        		NewsTabsFragment.class, Utility.BREAKING_NEWS_URL);
        mTabsAdapter.addTab(mTabHost.newTabSpec("Political News").setIndicator("  Political News  "),
        		NewsTabsFragment.class, Utility.POLITICAL_NEWS_URL);
        mTabsAdapter.addTab(mTabHost.newTabSpec("General News").setIndicator("  General News  "),
        		NewsTabsFragment.class, Utility.GENERAL_NEWS_URL);
        mTabsAdapter.addTab(mTabHost.newTabSpec("Education").setIndicator("  Education  "),
        		NewsTabsFragment.class, Utility.EDUCATION_NEWS_URL);
        mTabsAdapter.addTab(mTabHost.newTabSpec("Cinema News").setIndicator("  Cinema News  "),
        		NewsTabsFragment.class, Utility.CINEMA_NEWS_URL);
        
	}



	public static class TabsAdapter extends FragmentPagerAdapter
	implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener 
	{
		private final Context mContext;
		private final TabHost mTabHost;
		private final ViewPager mViewPager;
		private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();
		private final HorizontalScrollView mHorzScroll;

		static final class TabInfo 
		{
			private final String tag;
			private final Class<?> clss;
			private final Bundle args;

			TabInfo(String _tag, Class<?> _class, Bundle _args) 
			{
				tag = _tag;
				clss = _class;
				args = _args;
			}
		}

		static class DummyTabFactory implements TabHost.TabContentFactory 
		{
			private final Context mContext;

			public DummyTabFactory(Context context) 
			{
				mContext = context;
			}

			@Override
			public View createTabContent(String tag) 
			{
				View v = new View(mContext);
				v.setMinimumWidth(0);
				v.setMinimumHeight(0);
				return v;
			}
		}

		public TabsAdapter(FragmentActivity activity, TabHost tabHost, ViewPager pager,HorizontalScrollView hscroll) 
		{
			super(activity.getSupportFragmentManager());
			mContext = activity;
			mTabHost = tabHost;
			mViewPager = pager;
			mHorzScroll = hscroll;
			mTabHost.setOnTabChangedListener(this);
			mViewPager.setAdapter(this);
			mViewPager.setOnPageChangeListener(this);
		}

		public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, String feedUrl) 
		{
			tabSpec.setContent(new DummyTabFactory(mContext));
			String tag = tabSpec.getTag();
			
			Bundle b = new Bundle();
	        b.putString("feedUrl", feedUrl);
	        b.putString("category", tag);
	        
			TabInfo info = new TabInfo(tag, clss, b);
			mTabs.add(info);
			mTabHost.addTab(tabSpec);
			notifyDataSetChanged();
		}

		@Override
		public int getCount() 
		{
			return mTabs.size();
		}

		@Override
		public Fragment getItem(int position) 
		{
			TabInfo info = mTabs.get(position);
			return Fragment.instantiate(mContext, info.clss.getName(), info.args);
		}

		@Override
		public void onTabChanged(String tabId) 
		{
			int position = mTabHost.getCurrentTab();
			mViewPager.setCurrentItem(position);
		}
		
		public String getTag()
		{			
			return mTabHost.getCurrentTabTag();
		}

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) 
		{
			
		}

		@Override
		public void onPageSelected(int position) 
		{
			// Unfortunately when TabHost changes the current tab, it kindly
			// also takes care of putting focus on it when not in touch mode.
			// The jerk.
			// This hack tries to prevent this from pulling focus out of our
			// ViewPager.
			
			TabWidget widget = mTabHost.getTabWidget();
			int oldFocusability = widget.getDescendantFocusability();
			widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
			mTabHost.setCurrentTab(position);
			widget.setDescendantFocusability(oldFocusability);
			
			mHorzScroll.scrollTo(position * mTabHost.getCurrentTabView().getWidth(), 0);			
		}

		@Override
		public void onPageScrollStateChanged(int state) 
		{
		}
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) 
	{		
		menu.add("Refresh")
        .setIcon(R.drawable.ic_refresh)
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        SubMenu subMenu1 = menu.addSubMenu("More");
        subMenu1.add("More Links");
        subMenu1.add("Quit");

        MenuItem subMenu1Item = subMenu1.getItem();
        subMenu1Item.setIcon(android.R.drawable.ic_menu_more);
        subMenu1Item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        return super.onCreateOptionsMenu(menu);
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		Log.d("ABN","OPTION SELECTED " + item.getTitle());
		
		if(item.getTitle().toString().equalsIgnoreCase("Refresh"))
			refreshData();
		else if(item.getTitle().toString().equalsIgnoreCase("More Links"))
			showMoreLinks();
		else if(item.getTitle().toString().equalsIgnoreCase("Quit"))
			shutdownActivity();
		
        return false;
    }
	
	
	private void refreshData()
	{
		int position = mTabHost.getCurrentTab();
		
		Utility.getInstance().rssDataMesseges.get(mTabsAdapter.getTag()).clear();
		Utility.getInstance().rssDataMesseges.put(mTabsAdapter.getTag(),null);
		
		mTabsAdapter.notifyDataSetChanged();
		mViewPager.setAdapter(mTabsAdapter);
		mViewPager.setCurrentItem(position);
	}
	
	
	private void showMoreLinks()
	{
		
	}
	

	private void shutdownActivity() {
		setResult(RESULT_CLOSE_ALL);
		finish();

	}
	
	
	
}
