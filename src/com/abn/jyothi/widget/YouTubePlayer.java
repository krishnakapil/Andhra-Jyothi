package com.abn.jyothi.widget;

import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.abn.jyothi.R;
import com.abn.jyothi.parser.ImageLoader;
import com.abn.jyothi.utils.NetworkAsyncTask;
import com.abn.jyothi.utils.RestClient;

public class YouTubePlayer extends RelativeLayout 
{

	private View lay;
	
	private VideoView mVideo;
	private Button mPlayBtn;
	private TextView mLoadingTxt;
	private ImageView mimgView;
	
	private String imgUrl = "";
	private String rstpUrl = "";
	private String videoId = "";
	
	private Context mContext;
	
	private String utubeUrl = "";
	
	public ImageLoader imageLoader; 
	
	public YouTubePlayer(Context context) 
	{
		super(context);
		LoadData(context);
	}

	public YouTubePlayer(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		LoadData(context);
	}
	
	public YouTubePlayer(Context context, AttributeSet attrs, int defStyle) 
	{
		super(context, attrs, defStyle);	
		LoadData(context);
	}
	
	private void LoadData(Context context)
	{
		mContext = context;
		
		lay = inflate(context, R.layout.youtube_player_layout, null);
		this.addView(lay);
		
		imageLoader=new ImageLoader(context);
		
		setControls();
	}
	
	private void setControls()
	{
		Log.d("ABN","SET CONTROLS");
		mVideo = (VideoView)lay.findViewById(R.id.videoView1);
		
		mimgView = (ImageView)lay.findViewById(R.id.imageView1);
		
		mPlayBtn = (Button)lay.findViewById(R.id.playBtn);
		
		mLoadingTxt = (TextView)lay.findViewById(R.id.loadingTxt);
		
		mPlayBtn.setVisibility(View.VISIBLE);
		mLoadingTxt.setVisibility(View.GONE);
	}
	
	
	private void playVideo()
	{
		try 
		{
			new loadYoutubeDataTask(mContext, "", "", false, videoId).execute();
		} 
		catch (Exception e) 
		{
			Log.d("ABN","YOUTUBE LOAD ERROR");
		}
		
	}
	
	public void initPlayer(String url)
	{
		try 
		{
			utubeUrl = url;
			
			mPlayBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) 
				{
					/*mPlayBtn.setVisibility(View.GONE);
					playVideo();*/
					
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setData(Uri.parse(utubeUrl));
					mContext.startActivity(intent);
				}
			});
			
			videoId = extractYoutubeId(url);
			
			imgUrl = "http://i.ytimg.com/vi/" + videoId + "/hqdefault.jpg";
			
			imageLoader.DisplayImage(imgUrl, mimgView, "");
			
			Log.d("ABN","VIDEO ID " + videoId + " img url " +imgUrl);
		} 
		catch (Exception e) 
		{
			Log.d("ABN","YOUTUBE LOAD ERROR");
		}
		
		
	}
	
	
	private class loadYoutubeDataTask extends NetworkAsyncTask
	{
		private String videoId;

		public loadYoutubeDataTask(Context context, String title,String message, boolean showDialog,String vidId) 
		{
			super(context, title, message, showDialog);
			videoId = vidId;
			mLoadingTxt.setVisibility(View.VISIBLE);
		}

		@Override
		protected void perform() 
		{
			String localUrl = "http://gdata.youtube.com/feeds/api/videos/" + videoId + "?v=2";
			
			RestClient rs = new RestClient(localUrl);
			
			try 
		    {
				rs.Execute(RestClient.GET);
				
				//Log.d("ABN","RESPONSE " + localUrl + "  " + rs.getResponse());
				rstpUrl = parseXml(rs.getResponse());
				Log.d("ABN","RSTP " + parseXml(rs.getResponse()));
			} 
		    catch (Exception e) 
		    {
		    	Log.d("ABN","ERROR " + e.getMessage());
				e.printStackTrace();
			}
		}

		@Override
		protected void postSuccess() 
		{
			mLoadingTxt.setVisibility(View.GONE);
			mimgView.setVisibility(View.GONE);
			
			mVideo.setVideoPath(rstpUrl);
			mVideo.setMediaController(new MediaController(mContext));
			//mVideo.requestFocus();
			mVideo.start();
		}
		
	}
	
	
	private String parseXml(String data)
	{
		Document doc = getDomElement(data);
		NodeList list = doc.getElementsByTagName("media:content");
		String cursor = "null";
		for (int i = 0; i < list.getLength(); i++) 
		{
			Node node = list.item(i);
			if (node != null) 
			{
				NamedNodeMap nodeMap = node.getAttributes();
				HashMap<String, String> maps = new HashMap<String, String>();
				for (int j = 0; j < nodeMap.getLength(); j++) 
				{
					Attr att = (Attr) nodeMap.item(j);
					maps.put(att.getName(), att.getValue());
				}
				if (maps.containsKey("yt:format")) 
				{
					String f = (String) maps.get("yt:format");
					if (maps.containsKey("url")) 
					{
						cursor = (String) maps.get("url");
					}
					if (f.equals("1"))
						return cursor;
				}
			}
		}
		
		return cursor;
		
	}

	
	
	private Document getDomElement(String xml)
	{
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
 
            DocumentBuilder db = dbf.newDocumentBuilder();
 
            InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(xml));
                doc = db.parse(is); 
 
            } catch (ParserConfigurationException e) {
                Log.e("Error: ", e.getMessage());
                return null;
            } catch (SAXException e) {
                Log.e("Error: ", e.getMessage());
                return null;
            } catch (IOException e) {
                Log.e("Error: ", e.getMessage());
                return null;
            }
                // return DOM
            return doc;
    }
	
	private String extractYoutubeId(String url) throws MalformedURLException 
	{
		String id = "";
		
		if(!url.contains("embed"))
		{
			String query = new URL(url).getQuery();
			String[] param = query.split("&");
			
			for (String row : param) {
				String[] param1 = row.split("=");
				if (param1[0].equals("v")) {
					id = param1[1];
				}
			}
		}
		else
		{
			id = url.substring(url.lastIndexOf("/") + 1);
		}
		
		return id;
	}
	
}
