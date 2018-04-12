package com.example.even1.endorsedsystemteacher.View.MyClass;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.even1.endorsedsystemteacher.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.ParseException;

import static android.content.Context.MODE_PRIVATE;

public class Late extends Fragment implements AdapterView.OnItemClickListener {

    private ListView listView;
    private String[] name = {"昌茂","晨濡","彦昌","高朗","学智","旭彬","高朗","云天","昂然","曜曦","子昂"};
    private int yes = 1;
    private int no =0;
    private int id;
    private long endtime;
    private String[] status = {"已批阅","未批阅"};
    private List<Map<String,Object>> list = new ArrayList<>();
    private int uid;
    private String mendtime;

    public int IS_FINISH;

    private android.os.Handler handler = new android.os.Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == IS_FINISH) {
                SimpleAdapter adapter = new SimpleAdapter(getActivity(),list,R.layout.class_item,
                        new String[]{"name","score"},
                        new int[]{R.id.name,R.id.score});
                listView.setAdapter(adapter);
            }

        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_check__homework_detail,container,false);

        listView = (ListView)view.findViewById(R.id.listview);
        init();
        return view;
    }

    private void init() {
        list.clear();

        SharedPreferences sp = getActivity().getSharedPreferences("Homework", MODE_PRIVATE);
        id = sp.getInt("id",999);
        endtime = getStringToDate(sp.getString("endtime",null));
        gethomeworkmember(id);
        listView.setOnItemClickListener(this);
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(),Comment.class);
        startActivity(intent);
    }
    private void gethomeworkmember(int homeworkid) {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://118.25.100.167/android/homeworkmember.action?homeworkid="+homeworkid;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                try {
                    String resultDate = new String(bytes,"utf-8");
                    JSONArray array = new JSONArray(resultDate);
                    for(int j=0;j<array.length();j++){
                        JSONObject js = array.getJSONObject(j);
                        mendtime = js.getString("endtime");
                        int id =js.getInt("id");
                        int type = js.getInt("type");
                        uid = js.getInt("uid");
                        if(Long.valueOf(mendtime)> endtime&&type==1){
                            Map<String,Object> map = new HashMap<>();
                            map.put("name",uid);
                            map.put("score","已批阅");
                            list.add(map);
                            Message msg = Message.obtain();
                            msg.what = IS_FINISH;
                            handler.sendMessage(msg);
                            System.out.println("late--"+"endtime------------------"+mendtime);
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
                Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }public static long getStringToDate(String time) {
        SimpleDateFormat sdf = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
        }
        Date date = new Date();
        try{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                date = sdf.parse(time);
            }
        } catch(ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }
}
