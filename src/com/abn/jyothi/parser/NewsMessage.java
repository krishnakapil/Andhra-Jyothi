

package com.abn.jyothi.parser;




import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class NewsMessage implements Comparable<NewsMessage>,Parcelable{
//	static SimpleDateFormat FORMATTER = 
//		new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");
//	static SimpleDateFormat FORMATTER1 = 
//			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private String title;
	private String link;
	private String description;
	//private Date date;
	private String imageUrl;
	private String newsDesc;
	//private String newsDate;
	private String videoUrl;
	private String image;
	

	

	

	/**
	 * Standard basic constructor for non-parcel
	 * object creation
	 */
	public NewsMessage() { ; };
	
	/**
	 *
	 * Constructor to use when re-constructing object
	 * from a parcel
	 *
	 * @param in a parcel from which to read this object
	 */
	public NewsMessage(Parcel in) {
		readFromParcel(in);
	}
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title.trim();
	}
	// getters and setters omitted for brevity 
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		
			this.link = link;
		
	}

	public String getDescription() {
		return description;
	}

	

	public String getNewsDesc() {
		return newsDesc;
	}

	public void setNewsDesc(String newsDesc) {
		
		this.newsDesc = newsDesc.trim();
		//Log.d("news","content desc"+newsDesc);
		
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String description) {
		this.description=description.trim();
		this.imageUrl =  description.trim();		
		if (description.contains("<img ")){  
			String imgurl=null;
          	String img  = description.substring(description.indexOf("<img ")); 
           img = img.substring(img.indexOf("src=") + 4);
         
          String subUrl=img.substring(0,4);          
          String startImageUrl1=img.substring(1);
         
           if(subUrl.startsWith("http"))
           {
        	  
        	   int newindexOf = img.indexOf(".jpg")+4;  
        	  
        	   img = img.substring(0, newindexOf);
        	   imgurl=img;
        	  
           }
           else if(subUrl.startsWith("'"))
           {
        	   int newindexOf = startImageUrl1.indexOf("'")+1; 
        	   img = img.substring(1, newindexOf);
        	  
        	   imgurl=img;
           }
           
		}
		else
        {
     	   this.imageUrl=null;
        }
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image.trim();
//		String img = image.substring(image.indexOf("url=")+4,image.indexOf(".jpg")+4);
//		this.image = img;
		/*if (image.contains("url")){ 
			Log.v("image-----if","tag is::"+image);
			String imgurl=null;
          	String img  = image.substring(image.indexOf("url")); 
           img = img.substring(img.indexOf("url=") + 5,img.indexOf(".jpg")+4);
           imgurl = img;
           this.image = imgurl;
		}else{
			this.image = null;
		}
		*/
		Log.v("image-----else","tag is::"+image);
	}
	
	
	
	/*public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String newsDesc ) {
		this.newsDesc = newsDesc.trim();
		this.videoUrl = newsDesc.trim();
//		String k = "D:/MDM/get_url.txt";  
//		String filename=k.substring(k.lastIndexOf("/")+1,k.lastIndexOf(".")); 
		
		if(newsDesc.contains("<iframe ")){
			  Log.i("setvideo","if-case");
				String videourl=null;
	          	String video  = newsDesc.substring(newsDesc.indexOf("<iframe ")); 
	           video = video.substring(video.indexOf("src=") + 4);
	           int lastIndex = video.indexOf("embed/")+18;
	           video = video.substring(0, lastIndex);
	           Log.v("video","url is::"+video);
	           videourl = video;
		}else{
			Log.i("setvideo","else-case");
			this.videoUrl=null;
			
		}
	}*/

	/*public String getDate() {
		return FORMATTER1.format(this.date);		 
		
	}

	public void setDate(String date)  {
		
		try
		{
		if(date.contains(","))
		{
		
		while (!date.endsWith("00")){
			date += "0";
		}
		Date d =FORMATTER.parse(date.trim());
		setNewsDate(DateFormat.getDateTimeInstance().format(d));
		}
		else if(date.contains("-"))
		{
				
				Date d =FORMATTER1.parse(date.trim());
				setNewsDate(DateFormat.getDateTimeInstance().format(d));
		}
		}
		catch (ParseException e) {
			
			e.printStackTrace();
		}
			
	}
	
	public String getNewsDate() {
		return newsDate;
	}

	public void setNewsDate(String newsDate) {
		
		this.newsDate = newsDate;
	}
*/
	public NewsMessage copy(){		
		NewsMessage copy = new NewsMessage();
		copy.title = title;
		copy.link = link;		
	//	copy.date = date;
		copy.imageUrl=imageUrl;
		copy.newsDesc=newsDesc;		
		//copy.newsDate=newsDate;
		copy.description=description;
		copy.videoUrl = videoUrl;
		copy.image = image;
		return copy;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Title: ");
		sb.append(title);
		sb.append('\n');
		//sb.append("Date: ");
		//sb.append(this.getDate());
		//sb.append('\n');
		sb.append("Link: ");
		sb.append(link);
		sb.append('\n');
		sb.append("ImageUrl: ");
		sb.append(imageUrl);
		sb.append('\n');
		sb.append("NewsDescription: ");
		sb.append(newsDesc);
		sb.append('\n');
		sb.append("VideoUrl: ");
		sb.append(videoUrl);
		sb.append('\n');
		sb.append("Image: ");
		sb.append(image);
		
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		//result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result
				+ ((imageUrl == null) ? 0 : imageUrl.hashCode());
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((newsDesc == null) ? 0 : newsDesc.hashCode());
		result = prime * result + ((videoUrl== null) ? 0 : videoUrl.hashCode());
		result = prime * result + ((image== null) ? 0 : image.hashCode());
		
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NewsMessage other = (NewsMessage) obj;
		/*if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;*/
		if (imageUrl == null) {
			if (other.imageUrl != null)
				return false;
		} else if (!imageUrl.equals(other.imageUrl))
			return false;
		if (newsDesc == null) {
			if (other.newsDesc != null)
				return false;
		} else if (!newsDesc.equals(other.newsDesc))
			return false;
		if (link == null) {
			if (other.link != null)
				return false;
		} else if (!link.equals(other.link))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (videoUrl == null) {
			if (other.videoUrl != null)
				return false;
		} else if (!videoUrl.equals(other.videoUrl))
			return false;
		if (image == null) {
			if (other.image != null)
				return false;
		} else if (!image.equals(other.image))
			return false;
		
		return true;
		
	}

	public int compareTo(NewsMessage another) {
		if (another == null) return 1;
		// sort descending, most recent first
		//return another.date.compareTo(date);
		return 0;
	}
	

	public int describeContents() {
		
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		
		// We just need to write each field into the
		// parcel. When we read from parcel, they
		// will come back in the same order		
		dest.writeString(title);
		//dest.writeString(newsDate);
		dest.writeString(imageUrl);
		dest.writeString(newsDesc);
		dest.writeString(link);
		dest.writeString(description);
		dest.writeString(videoUrl);
		dest.writeString(image);
	
	}
	
	/**
	 *
	 * Called from the constructor to create this
	 * object from a parcel.
	 *
	 * @param in parcel from which to re-create object
	 */
	private void readFromParcel(Parcel in) {
		 
		// We just need to read back each
		// field in the order that it was
		// written to the parcel
		title = in.readString();
		//newsDate = in.readString();
		imageUrl=in.readString();
		newsDesc=in.readString();
		link=in.readString();
		description=in.readString();
		videoUrl = in.readString();
	    image = in.readString();
		
	}
	   
	/**
    *
    * This field is needed for Android to be able to
    * create new objects, individually or as arrays.
    *
    * This also means that you can use use the default
    * constructor to create the object and use another
    * method to hyrdate it as necessary.
    *
    * I just find it easier to use the constructor.
    * It makes sense for the way my brain thinks ;-)
    *
    */
	public static final Parcelable.Creator<NewsMessage> CREATOR =
		    	new Parcelable.Creator<NewsMessage>() {
		            public NewsMessage createFromParcel(Parcel in) {
		                return new NewsMessage(in);
		            }
		 
		            public NewsMessage[] newArray(int size) {
		                return new NewsMessage[size];
		            }
		        };

	
            }
