package com.example.even1.endorsedsystemteacher.View.mainfragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.even1.endorsedsystemteacher.View.MyClass.AddClass;
import com.example.even1.endorsedsystemteacher.View.MyClass.MyClass_detail;
import com.example.even1.endorsedsystemteacher.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class MyClass extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    private ListView listView;
    private Toolbar toolbar;
    private ImageView imageView;
    private int userid;
    private int cid;
    private List<Map<String,Object>> list = new ArrayList<>();
    private List<Integer> listnumber = new ArrayList<>();
    private List<String>mclassname = new ArrayList<>();
    private List<String>mowner = new ArrayList<>();
    public int IS_FINISH;
    private String classname,owner;

    private android.os.Handler handler = new android.os.Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == IS_FINISH) {
                SimpleAdapter adapter = new SimpleAdapter(getActivity(),list,R.layout.class_item,
                        new String[]{"pic","name"},
                        new int[]{R.id.pic,R.id.name});
                listView.setAdapter(adapter);
                /*MyClassAdapter adapter = new MyClassAdapter(getActivity(),list);
                listView.setAdapter(adapter);*/
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_class,container,false);

        toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        listView = (ListView)view.findViewById(R.id.listview) ;
        imageView = (ImageView)view.findViewById(R.id.add);

        init();

        return view;
    }


    private void init() {
        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("");

        SharedPreferences sp = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        userid = sp.getInt("id",0);
        getclasslist(userid);

        listView.setOnItemClickListener(this);

        imageView.setOnClickListener(this);
    }
    private void getclasslist(int id) {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://118.25.100.167/android/classlist.action?userid="+id;
        Toast.makeText(getActivity(), "uid-------------"+id, Toast.LENGTH_SHORT).show();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                try {
                    String resultDate = new String(bytes,"utf-8");
                    JSONArray array = new JSONArray(resultDate);
                    for(int number=0;number<array.length();number++){
                        JSONObject obj = array.getJSONObject(number);
                        classname = obj.getString("name");
                        Map<String,Object> map = new HashMap<>();
                        map.put("pic",R.mipmap.head);
                        map.put("name",classname);
                        list.add(map);
                        mclassname.add(classname);

                        owner= obj.getString("owner");
                        mowner.add(owner);

                        cid= obj.getInt("id");
                        listnumber.add(cid);
                        System.out.println(
                                "name-------------"+obj.getString("name")
                        );
                        Message msg = Message.obtain();
                        msg.obj = bytes;
                        msg.what = IS_FINISH;
                        handler.sendMessage(msg);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
                System.out.println(
                        "throwable-------------"+throwable+"headers-------------"+headers+"i--------"+i
                );
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(),MyClass_detail.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id", listnumber.get(position));
        bundle.putString("classname",mclassname.get(position));
        bundle.putString("owner",mowner.get(position));
        System.out.println(
                "id-------------"+listnumber.get(position)
        );
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add:
                Intent intent = new Intent(getActivity(), AddClass.class);
                startActivity(intent);
                break;
        }
    }
}
