package com.example.even1.endorsedsystemteacher.View.MyClass;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.even1.endorsedsystemteacher.View.MyHome.mInfor;
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

public class Class_Homework extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Toolbar toolbar;
    private ListView listView;
    private TextView Ttitle;
    private int id,uid,bookid,chapterid,oid;
    private String brief,ctime,endtime;
    private String mbookname,jianjie;
    List<Map<String,Object>> list = new ArrayList<>();
    List<Map<String,Object>> infor = new ArrayList<>();

    private List<Map<String,Object>> book = new ArrayList<>();
    private List<Map<String,Object>>mbookid = new ArrayList<>();
    private List<String>mBookname = new ArrayList<>();
    private int choose,mid;
    public int IS_FINISH;

    private android.os.Handler handler = new android.os.Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == IS_FINISH) {
                SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),list,R.layout.homework_item,
                        new String[]{"title","time","pic"},
                        new int[]{R.id.title,R.id.time,R.id.pic});
                listView.setAdapter(adapter);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class__homework);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        listView = (ListView)findViewById(R.id.listview);
        Ttitle = (TextView) findViewById(R.id.title);

        Bundle bundle=getIntent().getExtras();
        choose = bundle.getInt("choose");
        init();
    }

    private void init() {
        Ttitle.setText("班级作业");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Bundle bundle=getIntent().getExtras();
        mid=bundle.getInt("id");
        getbookcase();
        listView.setOnItemClickListener(this);
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

                        Map<String,Object> informap = new HashMap<>();
                        informap.put("id",id);
                        informap.put("uid",uid);
                        informap.put("bookid",bookid);
                        informap.put("chapterid",chapterid);
                        informap.put("oid",oid);
                        informap.put("brief",brief);
                        informap.put("ctime",ctime);
                        informap.put("endtime",endtime);
                        infor.add(informap);
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
                Toast.makeText(getApplicationContext(), "请求失败", Toast.LENGTH_SHORT).show();
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
                    map.put("pic",R.mipmap.homework_book);
                    map.put("title","阅读《"+String.valueOf(book.get(position).get("bookname"))+"》第"+mbookid.get(mposition).get("chapter")+"章");
                    map.put("time",mbookid.get(mposition).get("ctime"));
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
                inithomework(mid);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(choose==1){
            Intent workintent = new Intent(this,Homework_detail.class);
            Bundle bundle = new Bundle();
            bundle.putInt("id", (Integer) infor.get(position).get("id"));
            bundle.putInt("uid", (Integer) infor.get(position).get("uid"));
            bundle.putInt("bookid", (Integer) infor.get(position).get("bookid"));
            bundle.putInt("chapterid", (Integer) infor.get(position).get("chapterid"));
            bundle.putInt("oid", (Integer) infor.get(position).get("oid"));
            bundle.putString("jianjie", (String) list.get(position).get("title"));
            bundle.putString("brief", (String) infor.get(position).get("brief"));
            bundle.putString("ctime", (String) infor.get(position).get("ctime"));
            bundle.putString("endtime", (String) infor.get(position).get("endtime"));
            workintent.putExtras(bundle);
            startActivity(workintent);
        }
        else if(choose==2){
            Intent intent = new Intent(this,MyGrade.class);
            startActivity(intent);
        }
        else if(choose==3){
            Intent intent = new Intent(this,Submit_Condition.class);
            Bundle bundle = new Bundle();
            bundle.putInt("id", (Integer) infor.get(position).get("id"));
            bundle.putString("jianjie", (String) list.get(position).get("title"));
            bundle.putString("brief", (String) infor.get(position).get("brief"));
            bundle.putString("endtime", (String) infor.get(position).get("endtime"));
            intent.putExtras(bundle);
            startActivity(intent);

            SharedPreferences sp = getSharedPreferences("Homework", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit(); //SharedPreferences 本身不能读写数据，需要使用Editor
            editor.putInt("id", (Integer) infor.get(position).get("id"));
            editor.putString("endtime", String.valueOf(infor.get(position).get("endtime")));
            editor.commit(); //提交
        }
    }



    }
