package com.abn.jyothi.tabs;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.abn.jyothi.PageActivity;
import com.abn.jyothi.R;
import com.abn.jyothi.parser.AndroidSaxFeedParser;
import com.abn.jyothi.parser.LazyAdapter;
import com.abn.jyothi.parser.NewsMessage;
import com.abn.jyothi.utils.NetworkAsyncTask;
import com.abn.jyothi.utils.Utility;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class NewsTabsFragment extends SherlockFragment 
{
	private View mainView;
	
	ListView contentList;
	LazyAdapter categoryAdapter;
	
	String feedUrl;
	String category;
	
	List<String> ImagesUrlArray=new ArrayList<String>();
	List<String> largeImagesUrlArray=new ArrayList<String>();
	
	List<NewsMessage> rssData;
	

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) 
	{
		mainView = inflater.inflate(R.layout.news_fragment, container, false);
		
		Bundle recdData = getArguments();
		feedUrl = recdData.getString("feedUrl");
		category = recdData.getString("category");
		
		setControls();
		
		return mainView;
	}
	
	private void setControls()
	{
		contentList=(ListView)mainView.findViewById(R.id.list);       
		contentList.setPadding(0,0, 0,0);
		
		rssData = Utility.getInstance().rssDataMesseges.get(category);
		
		Log.d("ABN","Loading RSS DATA : " + category + "  " + (rssData == null || rssData.size() == 0));
		
		if(rssData == null || rssData.size() == 0)
			new parseDataTask(getActivity(),"Loading News!", "Please Wait...", false).execute();
		else
		{
			categoryAdapter=new LazyAdapter(getActivity(), rssData,category);
			contentList.setAdapter(categoryAdapter);
		}
		
		contentList.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {		

				Intent intent= new Intent(getActivity(),PageActivity.class);
				Bundle b=new Bundle();
				b.putInt("position", position);
				b.putString("category", category);
				intent.putExtras(b);      
				startActivity(intent); 

			}
		});
	}
	
	private class parseDataTask extends NetworkAsyncTask
	{

		public parseDataTask(Context context, String title, String message,
				boolean showDialog) 
		{
			super(context, title, message, showDialog);
			getSherlockActivity().setSupportProgressBarIndeterminateVisibility(true);
		}

		@Override
		protected void perform() 
		{
			AndroidSaxFeedParser parser = new AndroidSaxFeedParser(feedUrl);
			rssData = parser.parse();
			
			Utility.getInstance().rssDataMesseges.put(category, rssData);
		}

		@Override
		protected void postSuccess() 
		{
			getSherlockActivity().setSupportProgressBarIndeterminateVisibility(false);
			
			if(rssData == null)
			{
				Utility.getInstance().showToastLong(getActivity(), getActivity().getResources().getString(com.abn.jyothi.R.string.general_error));
			}
			else
			{
				categoryAdapter=new LazyAdapter(getActivity(), rssData,category);
				contentList.setAdapter(categoryAdapter);
			}
		}
		
	}
	
	
	
}
