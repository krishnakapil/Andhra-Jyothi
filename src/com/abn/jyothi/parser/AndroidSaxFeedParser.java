

package com.abn.jyothi.parser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.util.Log;
import android.util.Xml;

public class AndroidSaxFeedParser extends BaseFeedParser {

	static final String RSS = "rss";
	public AndroidSaxFeedParser(String feedUrl) {
		super(feedUrl);
	}

	public List<NewsMessage> parse() {
		final NewsMessage currentMessage = new NewsMessage();
		RootElement root = new RootElement(RSS);
		final List<NewsMessage> messages = new ArrayList<NewsMessage>();
		Element channel = root.getChild(CHANNEL);
		Element item = channel.getChild(ITEM);
		item.setEndElementListener(new EndElementListener(){
			public void end() {
				
				messages.add(currentMessage.copy());
			}
		});
		item.getChild(TITLE).setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {
				currentMessage.setTitle(body);
				Log.v("sax","title");
			}
		});
		item.getChild(LINK).setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {
				currentMessage.setLink(body);
				Log.v("sax","link");
			}
		});
		item.getChild(IMAGEURL).setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {	
				
				currentMessage.setImageUrl(body);
			}
		});
		item.getChild(IMAGE).setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {	
				Log.v("sax","image");
				currentMessage.setImage(body);
			}
		});
		item.getChild("http://purl.org/rss/1.0/modules/content/",NEWSDESCRIPTION).setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {
			
				currentMessage.setNewsDesc(body);
				
			}
		});
		/*item.getChild(PUB_DATE).setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {
				currentMessage.setDate(body);
			}
		});*/
		try {
			Xml.parse(this.getInputStream(), Xml.Encoding.UTF_8, root.getContentHandler());
			     Log.v("sax","if");
		     }catch (MalformedURLException e) {
			      Log.e("SAX XML", "sax mulformed error", e);
			      e.printStackTrace();
			  } catch (SAXParseException sx) {
				  Log.e("SAX XML", "sax parser error", sx);
				  sx.printStackTrace();
			  } catch(SAXException se) { 
		         Log.e("SAX XML", "sax error", se); 
		         se.printStackTrace();
		      } catch(IOException ioe) { 
		         Log.e("SAX XML", "sax parse io error", ioe);
		         ioe.printStackTrace();
		      } catch (Exception e) {
			     Log.v("sax","else");
		         return null;
//			//throw new RuntimeException(e);
		}
		return messages;
	}
}
