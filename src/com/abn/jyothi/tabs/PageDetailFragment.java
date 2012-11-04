package com.abn.jyothi.tabs;

import java.io.BufferedReader;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.abn.jyothi.R;
import com.abn.jyothi.parser.ImageLoader;
import com.abn.jyothi.parser.NewsMessage;
import com.abn.jyothi.utils.RestClient;
import com.abn.jyothi.utils.Utility;
import com.abn.jyothi.widget.YouTubePlayer;
import com.actionbarsherlock.app.SherlockFragment;

public class PageDetailFragment extends SherlockFragment
{
	private View mainView;
	private NewsMessage news;
	
	private TextView mTitleTxt;
	private TextView mDescTxt;
	
	private ImageView mImgview;
	
	private YouTubePlayer player;
	
	private ImageLoader imageloader;

	
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
		imageloader = new ImageLoader(getActivity());
		
	    String nohtml = news.getNewsDesc().trim().replaceAll("\\<img.*?>","");
	    
		
		Log.d("ABN","VIDEO URL " + nohtml);
		
		mTitleTxt = (TextView)mainView.findViewById(R.id.titleTxt);
		mTitleTxt.setText(news.getTitle());
		
		mImgview = (ImageView)mainView.findViewById(R.id.titleImgView);
		
		if(!news.getImageUrl().equalsIgnoreCase(""))
			imageloader.DisplayImage(news.getImageUrl(), mImgview, "");
		else
			imageloader.DisplayImage(news.getImage(), mImgview, "");
		
		mDescTxt = (TextView)mainView.findViewById(R.id.descTxt);
		mDescTxt.setText(Html.fromHtml(nohtml));
		
		player = (YouTubePlayer)mainView.findViewById(R.id.videoView);
		
		if(!news.getVideoUrl().equals(""))
		{
			player.initPlayer(news.getVideoUrl());
			player.setVisibility(View.VISIBLE);
			mImgview.setVisibility(View.GONE);
		}
		else
		{
			player.setVisibility(View.GONE);
			mImgview.setVisibility(View.VISIBLE);
		}
		
	}
	

	
}
