package com.example.even1.endorsedsystemteacher.View.StackFragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.even1.endorsedsystemteacher.Adapter.HorizonListviewAdapter;
import com.example.even1.endorsedsystemteacher.R;
import com.example.even1.endorsedsystemteacher.View.CustomizedView.HorizontalListView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

public class ReadingIN extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private HorizontalListView mListView,mListView2,mListView3;
    private String[] str = {"小学","初中","高中"};
    private HorizonListviewAdapter mAdapter;
    private ArrayList<HashMap<String, Object>> myList1 = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> myList2 = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> myList3 = new ArrayList<>();
    private LinearLayout primary,middle,high;
    private int id,rank;
    private String writer,brief,introduce,type;
    private String bookname,img;
    private SharedPreferences pref;
    public int PRIMARY,MIDDLE,HIGH;
    private ArrayList<Integer> empty = new ArrayList<>();
    private ArrayList<Integer> primaryid = new ArrayList<>();
    private ArrayList<Integer> middleid = new ArrayList<>();
    private ArrayList<Integer> highid = new ArrayList<>();

    private android.os.Handler handler = new android.os.Handler(){
        public void handleMessage(Message msg){
            if(msg.what==PRIMARY){
                mAdapter = new HorizonListviewAdapter(getContext(),myList1);
                mListView.setAdapter(mAdapter);
            }
            if(msg.what==MIDDLE){
                mAdapter = new HorizonListviewAdapter(getContext(),myList2);
                mListView2.setAdapter(mAdapter);
            }
            if(msg.what==HIGH){
                mAdapter = new HorizonListviewAdapter(getContext(),myList3);
                mListView3.setAdapter(mAdapter);
            }

        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bookrecommend,container,false);
        mListView = (HorizontalListView) view.findViewById(R.id.horizontal_listview);
        mListView2 = (HorizontalListView) view.findViewById(R.id.horizontal_listview2);
        mListView3 = (HorizontalListView) view.findViewById(R.id.horizontal_listview3);

        primary = (LinearLayout)view.findViewById(R.id.primary);
        middle = (LinearLayout)view.findViewById(R.id.middle);
        high = (LinearLayout)view.findViewById(R.id.high);

        primary.setOnClickListener(this);
        middle.setOnClickListener(this);
        high.setOnClickListener(this);

        myList1.clear();
        myList2.clear();
        myList3.clear();
        initView2();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),Book_Detail.class);
                Bundle bundle = new Bundle();
                bundle.putInt("bookid", (Integer) myList1.get(position).get("id"));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mListView2.setOnItemClickListener(this);
        mListView3.setOnItemClickListener(this);
        return view;
    }
    private void initView2() {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://118.25.100.167/android/book.action";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                try {
                    String resultDate = new String(bytes,"utf-8");
                    JSONArray array = new JSONArray(resultDate);
                    for(int j=0;j<array.length();j++){
                        JSONObject js = array.getJSONObject(j);
                        id = js.getInt("id");
                        bookname = js.getString("bookname");
                        writer = js.getString("writer");
                        brief = js.getString("brief");
                        introduce = js.getString("introduce");
                        img = "http://118.25.100.167"+js.getString("img");
                        type = js.getString("types");
                        String typearray[] = type.split(",");
                        System.out.println("bookname------------------"+bookname+"type-------------------"+typearray[0]+typearray[1]);
                        for(int number=0;number<typearray.length;number++){
                            if(Integer.parseInt(typearray[number])==2){{
                                for(int count=0;count<typearray.length;count++){
                                    if(Integer.parseInt(typearray[count])==1){
                                        HashMap<String,Object> map = new HashMap<>();
                                        map.put("id",id);
                                        map.put("pic",img);
                                        map.put("name",bookname);
                                        myList1.add(map);
                                        primaryid.add(id);
                                        Message msg = Message.obtain();
                                        msg.obj = bytes;
                                        msg.what = PRIMARY;
                                        handler.sendMessage(msg);
                                        System.out.println(
                                                "Readingout---------id-------------"+id
                                                        +"bookname-------------"+bookname+"writer--------"+writer+"img------------------"+img
                                                        +"type------------------"+type
                                        );
                                    }
                                    if(Integer.parseInt(typearray[count])==4){
                                        HashMap<String,Object> map = new HashMap<>();
                                        map.put("id",id);
                                        map.put("pic",img);
                                        map.put("name",bookname);
                                        myList2.add(map);
                                        middleid.add(id);
                                        Message msg = Message.obtain();
                                        msg.obj = bytes;
                                        msg.what = MIDDLE;
                                        handler.sendMessage(msg);
                                        System.out.println(
                                                "Readingin---------id-------------"+id
                                                        +"bookname-------------"+bookname+"writer--------"+writer+"img------------------"+img
                                                        +"type------------------"+type
                                        );
                                    }
                                    if(Integer.parseInt(typearray[count])==5){
                                        HashMap<String,Object> map = new HashMap<>();
                                        map.put("id",id);
                                        map.put("pic",img);
                                        map.put("name",bookname);
                                        myList3.add(map);
                                        highid.add(id);
                                        Message msg = Message.obtain();
                                        msg.obj = bytes;
                                        msg.what = HIGH;
                                        handler.sendMessage(msg);
                                        System.out.println(
                                                "Readingin---------id-------------"+id
                                                        +"bookname-------------"+bookname+"writer--------"+writer+"img------------------"+img
                                                        +"type------------------"+type
                                        );
                                    }

                                }

                            }}
                        }
                    }

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(getActivity(), "请求失败你傻子", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.primary:
                intent = new Intent(getActivity(),Book_List.class);
                intent.putIntegerArrayListExtra("mbookid",primaryid);
                startActivity(intent);
                break;
            case R.id.middle:
                intent = new Intent(getActivity(),Book_List.class);
                intent.putIntegerArrayListExtra("mbookid",middleid);
                startActivity(intent);
                break;
            case R.id.high:
                intent = new Intent(getActivity(),Book_List.class);
                intent.putIntegerArrayListExtra("mbookid",highid);
                startActivity(intent);
                break;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.horizontal_listview:
                Intent intent = new Intent(getActivity(),Book_Detail.class);
                Bundle bundle = new Bundle();
                bundle.putInt("bookid", (Integer) myList1.get(position).get("id"));
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.horizontal_listview2:
                Intent intent2 = new Intent(getActivity(),Book_Detail.class);
                Bundle bundle2 = new Bundle();
                bundle2.putInt("bookid", (Integer) myList2.get(position).get("id"));
                intent2.putExtras(bundle2);
                startActivity(intent2);
                break;
            case R.id.horizontal_listview3:
                Intent intent3 = new Intent(getActivity(),Book_Detail.class);
                Bundle bundle3 = new Bundle();
                bundle3.putInt("bookid", (Integer) myList3.get(position).get("id"));
                intent3.putExtras(bundle3);
                startActivity(intent3);
                break;
        }


    }
}
