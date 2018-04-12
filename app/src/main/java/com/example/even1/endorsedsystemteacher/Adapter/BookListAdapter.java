package com.example.even1.endorsedsystemteacher.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.even1.endorsedsystemteacher.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Even1 on 2018/4/3.
 */

public class BookListAdapter extends BaseAdapter {
    private ArrayList<HashMap<String,Object>> myList = new ArrayList<>();
    private Context context;
    private LayoutInflater inflater;

    public BookListAdapter(Context context, ArrayList<HashMap<String,Object>> myList) {
        this.context = context;
        this.myList = myList;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public Object getItem(int position) {
        return myList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.books_item, parent, false);
            viewHolder.pic = (ImageView)convertView.findViewById(R.id.pic);
            viewHolder.name = (TextView)convertView.findViewById(R.id.name);
            viewHolder.intro = (TextView)convertView.findViewById(R.id.intro);
            Glide
                    .with(context)
                    .load(myList.get(position).get("pic"))
                    .into(viewHolder.pic);
            viewHolder.name.setText((CharSequence) myList.get(position).get("name"));
            viewHolder.intro.setText((CharSequence) myList.get(position).get("intro"));
        }
        return convertView;

    }

    static class ViewHolder{
        TextView name,intro;
        ImageView pic;

    }
}
