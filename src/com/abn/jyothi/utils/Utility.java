package com.abn.jyothi.utils;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import com.abn.jyothi.parser.NewsMessage;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class Utility 
{
	
	public static String TOP_NEWS_URL = "http://www.abnandhrajyothy.com/category/top-stories/feed";
	public static String FEATURED_NEWS_URL = "http://www.abnandhrajyothy.com/category/featured-stories/feed";
	public static String BREAKING_NEWS_URL ="http://www.abnandhrajyothy.com/category/breaking-news/feed";
	public static String POLITICAL_NEWS_URL = "http://www.abnandhrajyothy.com/category/political-news/feed";
	public static String GENERAL_NEWS_URL = "http://www.abnandhrajyothy.com/category/general-news/feed";
	public static String EDUCATION_NEWS_URL   ="http://www.abnandhrajyothy.com/category/education/feed";
	public static String CINEMA_NEWS_URL = "http://www.abnandhrajyothy.com/category/cinema-news/feed";
	
	private static Utility instance;
	
	public HashMap<String, List<NewsMessage>> rssDataMesseges;
	
	private Utility()
	{
		rssDataMesseges = new HashMap<String, List<NewsMessage>>();
	}
	
	public enum Result {SUCCESS,ERROR,NETWORK_ERROR};
	
	public static synchronized Utility getInstance()
	{
		if (instance == null) 
		{
			instance = new Utility();
		}
		return instance;
	}
	
	
	
	public void showToastLong(Context context,String msg)
	{
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}
	
	
	public void showToastShort(Context context,String msg)
	{
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
	
	public void CopyStream(InputStream is, OutputStream os)
    {
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
              int count=is.read(bytes, 0, buffer_size);
              if(count==-1)
                  break;
              os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }
	
}
