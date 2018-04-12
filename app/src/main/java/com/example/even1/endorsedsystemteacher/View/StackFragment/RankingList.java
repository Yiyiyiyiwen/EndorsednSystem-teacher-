package com.example.even1.endorsedsystemteacher.View.StackFragment;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.even1.endorsedsystemteacher.Adapter.HorizonListviewAdapter;
import com.example.even1.endorsedsystemteacher.R;
import com.example.even1.endorsedsystemteacher.View.CustomizedView.HorizontalListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RankingList extends Fragment{

    private GridView gridView,gridView2;
    private int[] head ={R.mipmap.head3,R.mipmap.head4,R.mipmap.head5};
    private int[] rank = {R.mipmap.one,R.mipmap.two,R.mipmap.three};
    private String[] name = {"阅读大王","阅读中王","阅读小王"};

    private int[] bhead ={R.mipmap.bookshelfitem1,R.mipmap.bookshelfitem2,R.mipmap.bookshelfitem3};
    private int[] brank = {R.mipmap.one,R.mipmap.two,R.mipmap.three};
    private String[] bname = {"福尔摩斯探案集","昆虫记","家"};

    private ImageView goodreaders;
    private ImageView goodbook;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_ranking_list,container,false);


        gridView = (GridView)view.findViewById(R.id.gridview);
        gridView2 = (GridView)view.findViewById(R.id.bookgridview);

        goodreaders = (ImageView)view.findViewById(R.id.goodreaders);
        goodbook = (ImageView)view.findViewById(R.id.goodbooks);

        initView2();
        return view;
    }
    private void initView2() {

        SimpleAdapter gridadapter1 = new SimpleAdapter(getActivity(),getData(bhead,bname,brank),R.layout.good_books_item,
                new String []{"head","name","rank"},
                new int[]{R.id.head,R.id.name,R.id.rank});
        SimpleAdapter gridadapter2 = new SimpleAdapter(getActivity(),getData(head,name,rank),R.layout.good_readers_item,
                new String []{"head","name","rank"},
                new int[]{R.id.head,R.id.name,R.id.rank});
        gridView.setAdapter(gridadapter2);
        gridView2.setAdapter(gridadapter1);

        goodreaders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Good_Readers.class));
            }
        });
        goodbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Good_Books.class));
            }
        });
    }


    private List<Map<String,Object>> getData(int[]head,String[]name,int[]rank){
        List<Map<String,Object>> list = new ArrayList<>();
        for(int i=0;i<name.length;i++){
            Map<String,Object> map = new HashMap<>();
            map.put("head",head[i]);
            map.put("name",name[i]);
            map.put("rank",rank[i]);
            list.add(map);
        }
        return list;

    }


    public class MyAdapter extends BaseAdapter {

        private final Context context;
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
                    intent.setClass(context,Good_Books.class);
                    context.startActivity(intent);
                }
            });
            HorizontalListView mListView = (HorizontalListView) convertView.findViewById(R.id.horizontal_listview);
            mListView.setAdapter(adapter);
            return convertView;
        }

    }



}
