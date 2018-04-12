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

import java.util.List;
import java.util.Map;

/**
 * Created by Even1 on 2018/3/23.
 */

public class MyClassAdapter extends BaseAdapter {
    List<Map<String,Object>> list;
    Context context;

    public MyClassAdapter(Context context,List<Map<String,Object>> list){
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder = new ViewHolder();
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.class_item,null);
            mHolder.pic = (ImageView)convertView.findViewById(R.id.pic);
            mHolder.name = (TextView)convertView.findViewById(R.id.name);
            convertView.setTag(mHolder);

            Glide
                    .with(context)
                    .load(list.get(position).get("pic"))
                    .into(mHolder.pic);
            mHolder.name.setText((CharSequence) list.get(position).get("name"));
        }
        else{
            mHolder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }


    class ViewHolder{
        ImageView pic;
        TextView name;
    }
}
