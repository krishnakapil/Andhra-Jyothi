

package com.abn.jyothi.parser;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class BaseFeedParser  {

	// names of the XML tags
	static final String CHANNEL = "channel";
	static final String PUB_DATE = "pubDate";
	static final  String IMAGEURL = "description";
	static final  String LINK = "link";
	static final  String TITLE = "title";
	static final  String ITEM = "item";
	static final String NEWSDESCRIPTION="encoded";
	static final String IMAGE  = "image";
	
	
	private final URL feedUrl;

	protected BaseFeedParser(String feedUrl){
		try {
			this.feedUrl = new URL(feedUrl);
			
		} catch (MalformedURLException e) {
			
			throw new RuntimeException(e);
		}
	}

	protected InputStream getInputStream() {
		try {
			HttpURLConnection openConnection = (HttpURLConnection) feedUrl.openConnection();
			InputStream inputStream = openConnection.getInputStream();
			
			return inputStream;
		} catch (IOException e) {
			
			throw new RuntimeException(e);
		}
	}
}