package com.example.even1.endorsedsystemteacher.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.even1.endorsedsystemteacher.R;

import java.util.ArrayList;
import java.util.HashMap;

public class HorizonListviewAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater = null;
	private ArrayList<HashMap<String, Object>> mylist = new ArrayList<>();

	public HorizonListviewAdapter(Context context, ArrayList<HashMap<String, Object>> mylist) {
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.mylist = mylist;
	}

	@Override
	public int getCount() {
		return mylist.size();
	}

	@Override
	public Object getItem(int position) {
		return mylist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_infor, null);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.image = (ImageView) convertView.findViewById(R.id.pic);
			holder.item = (LinearLayout) convertView.findViewById(R.id.ll_item);
			convertView.setTag(holder);

		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.name.setText((CharSequence) mylist.get(position).get("name"));
	    //holder.image.setImageResource(goods.get(position).getImageId());
		Glide
				.with(context)
				.load(mylist.get(position).get("pic"))
				.into(holder.image);
	    /*holder.item.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent ev) {
				Intent intent = new Intent(context,Book_Detail.class);
				intent.putExtra("bookname",bookname);
				intent.putExtra("writer",writer);
				intent.putExtra("brief",brief);
				intent.putExtra("introduce",introduce);
				context.startActivity(intent);
				return true;
			}

		});*/
			
		return convertView;
	}

	public class ViewHolder {
		public LinearLayout item;
		TextView name;
		ImageView image;
	}
	
}
