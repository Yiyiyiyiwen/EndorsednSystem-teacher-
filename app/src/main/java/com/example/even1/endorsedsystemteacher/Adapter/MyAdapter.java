package com.example.even1.endorsedsystemteacher.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.even1.endorsedsystemteacher.R;
import com.example.even1.endorsedsystemteacher.View.StackFragment.Book_List;
import com.example.even1.endorsedsystemteacher.View.CustomizedView.HorizontalListView;

/**
 * Created by Even1 on 2018/2/1.
 */

public class MyAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater = null;
    private String [] data;
    private HorizonListviewAdapter adapter;
    public MyAdapter(Context context,String [] data,HorizonListviewAdapter adapter) {
        this.context = context;
        this.adapter =adapter;
        this.data = data;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.item_books, null);
        TextView name = (TextView) convertView.findViewById(R.id.tv_name);
        name.setText(data[position]);
        ImageView all = (ImageView)convertView.findViewById(R.id.all);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context,Book_List.class);
                context.startActivity(intent);
            }
        });
        HorizontalListView mListView = (HorizontalListView) convertView.findViewById(R.id.horizontal_listview);
        mListView.setAdapter(adapter);
        return convertView;
    }

}
