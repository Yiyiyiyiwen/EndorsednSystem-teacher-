package com.example.even1.endorsedsystemteacher.View.MyHome;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.even1.endorsedsystemteacher.R;
import com.example.even1.endorsedsystemteacher.View.CustomizedView.MyListview;
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

import static com.example.even1.endorsedsystemteacher.View.StackFragment.Book_Detail.setListViewHeightBasedOnChildren;

public class mMessage extends Fragment {

    private MyListview listView;
    private List<Integer> mHomework = new ArrayList<>();
    private int cid,mid;
    private int id,uid,bookid,chapterid,oid;
    private String brief,ctime,endtime;
    private String mbookname;
    List<String> bookname = new ArrayList<>();
    public int IS_FINISH;
    List<Map<String,Object>> list = new ArrayList<>();
    private List<String>mBookname = new ArrayList<>();
    private List<Map<String,Object>> book = new ArrayList<>();
    private List<Map<String,Object>>mbookid = new ArrayList<>();
    private android.os.Handler handler = new android.os.Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == IS_FINISH) {
                SimpleAdapter adapter = new SimpleAdapter(getContext(),list,R.layout.message_item,
                        new String[]{"title","time","head","content"},
                        new int[]{R.id.title,R.id.time,R.id.head,R.id.content});
                listView.setAdapter(adapter);
                //setListViewHeight.setListViewHeightBasedOnChildren(listView,0);
                setListViewHeightBasedOnChildren(listView);
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_m_message,container,false);

        listView = (MyListview)view.findViewById(R.id.listview);
        init();
        return view;
    }

    private void init() {
        SharedPreferences sp = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        mid = sp.getInt("id",999);
        getbookcase();
    }

    private void inithomework(int cid) {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://118.25.100.167/android/homework.action?cid="+cid;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                try {
                    String resultDate = new String(bytes,"utf-8");
                    JSONArray array = new JSONArray(resultDate);
                    for(int j=0;j<array.length();j++){
                        JSONObject js = array.getJSONObject(j);
                        id = js.getInt("id");
                        uid = js.getInt("uid");
                        bookid = js.getInt("bookid");

                        System.out.println("bookid------------------"+bookid);
                        chapterid = js.getInt("chapterid");
                        oid = js.getInt("oid");
                        brief = js.getString("brief");
                        //时间戳转换
                        ctime = mInfor.stampToDate(js.getString("ctime"));
                        endtime = mInfor.stampToDate(js.getString("endtime"));

                        Map<String,Object> map = new HashMap<>();
                        map.put("bookid",bookid);
                        map.put("brief",brief);
                        map.put("ctime",ctime);
                        map.put("endtime",endtime);
                        map.put("chapter",chapterid);
                        mbookid.add(map);
                    }

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getbookname();
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getbookname() {
        int position;
        int mposition;
        System.out.println(mbookid.size());
        for(mposition=0;mposition<mbookid.size();mposition++){
            for(position=0;position<book.size();position++){
                if(book.get(position).get("id")==mbookid.get(mposition).get("bookid")){
                    mBookname.add(String.valueOf(book.get(position).get("bookname")));
                    Map<String,Object> map = new HashMap<>();
                    map.put("head",R.mipmap.green);
                    map.put("title","阅读《"+String.valueOf(book.get(position).get("bookname"))+"》第"+mbookid.get(mposition).get("chapter")+"章");
                    map.put("time",mbookid.get(mposition).get("ctime"));
                    map.put("content",mbookid.get(mposition).get("brief"));
                    list.add(map);

                    System.out.println("count------------------"+mposition+"mbookname-------------"+String.valueOf(book.get(position).get("bookname")));
                    Message msg = Message.obtain();
                    msg.what = IS_FINISH;
                    handler.sendMessage(msg);
                }
                if(list.size()==mbookid.size()){
                    break;
                }
            }


        }
    }

    public void getbookcase(){
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
                        int id = js.getInt("id");
                        mbookname = js.getString("bookname");
                        Map<String,Object> map = new HashMap<>();
                        map.put("id",id);
                        map.put("bookname",mbookname);
                        book.add(map);
                        System.out.println("j------------------"+j+"mbookname-------------"+mbookname);
                    }

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                inithomework(3);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
