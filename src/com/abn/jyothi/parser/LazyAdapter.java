package com.abn.jyothi.parser;

import java.util.ArrayList;
import java.util.List;

import com.abn.jyothi.R;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LazyAdapter extends BaseAdapter {

	List<String> ImagesUrlArray=new ArrayList<String>();
	private Activity activity;
	private List<NewsMessage> data;
	private static LayoutInflater inflater=null;
	public ImageLoader imageLoader; 
	public String category;



	//   TextView count;
	public LazyAdapter(Activity a, List<NewsMessage> d,String category) {
		activity = a;
		data=d;
		this.category=category;
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader=new ImageLoader(activity.getApplicationContext());

	}

	@Override
	public int getViewTypeCount() {
		return 2; //return 2, you have two types that the getView() method will return, normal(0) and for the last row(1)
	}
	@Override
	public int getItemViewType(int position) {
		return (position == 0) ? 1 : 0; //if we are at the first position then return 1, for any other position return 0
	}


	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi=convertView;
		ViewHolder holder;

		NewsMessage categoriContent = data.get(position);


		if(convertView==null){
			if(position == 0){
				Log.v("1st pos","lazy adapter");
				holder = new ViewHolder();
				vi = inflater.inflate(R.layout.list_row_fst, null);

				holder.title = (TextView)vi.findViewById(R.id.title_fst); // title
				holder.thumb_image=(ImageView)vi.findViewById(R.id.list_image_fst); // thumb image
				// mFirstItem = vi;

			}else{
				Log.v("else---","lazy adapter");
				holder = new ViewHolder();
				vi = inflater.inflate(R.layout.list_row, null);

				holder.title = (TextView)vi.findViewById(R.id.title); // title
				TextView count = (TextView)vi.findViewById(R.id.count);
				holder.thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image

				Log.v("settag","holder");
			}
			vi.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		// Setting all values in listview
		holder.title.setText(categoriContent.getTitle());
		//  String imageUrl =categoriContent.getImageUrl(); 
		String imageUrl =categoriContent.getImage();

		//  count.setVisibility(View.GONE);
		imageLoader.DisplayImage(imageUrl, holder.thumb_image, this.category);


		return vi;

	}

	static class ViewHolder {
		TextView title;

		ImageView thumb_image;
	}

}






