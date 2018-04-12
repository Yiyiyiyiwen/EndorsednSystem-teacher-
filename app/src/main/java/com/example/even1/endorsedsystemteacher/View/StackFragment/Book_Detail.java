package com.example.even1.endorsedsystemteacher.View.StackFragment;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.even1.endorsedsystemteacher.View.MyClass.Do_Homework;
import com.example.even1.endorsedsystemteacher.View.MyClass.Homework_detail;
import com.example.even1.endorsedsystemteacher.R;
import com.example.even1.endorsedsystemteacher.View.Reading;
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

public class Book_Detail extends AppCompatActivity implements View.OnClickListener{

    private TextView bookname,writer,brief,bookintro,homework;
    private ImageView all,pic;
    private ListView listView;
    private Toolbar toolbar;
    private ScrollView scrollView;
    private List<Map<String,Object>> list = new ArrayList<>();
    public int IS_FINISH,SetBookInfor;
    private String title;
    private int mid;
    private String mbookname,mwriter,mbrief,mintroduce,img;
    private int rank;
    private String imgurl;
    private Homework_detail homework_detail;
    private Button startread;

    private android.os.Handler handler = new android.os.Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == IS_FINISH) {
                SimpleAdapter simpleAdapter = new SimpleAdapter(Book_Detail.this,list,R.layout.catalog_item,
                        new String[]{"number","content"},
                        new int[]{R.id.number,R.id.content});
                listView.setAdapter(simpleAdapter);
                setListViewHeightBasedOnChildren(listView);
            }
            if(msg.what == SetBookInfor){
                bookname.setText("《"+mbookname+"》");
                writer.setText(mwriter);
                brief.setText(mbrief);
                bookintro.setText(mintroduce);
                Glide
                        .with(Book_Detail.this)
                        .load(imgurl)
                        .into(pic);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book__detail);

        bookintro = (TextView)findViewById(R.id.bookintro);
        bookname = (TextView)findViewById(R.id.bookname);
        writer = (TextView)findViewById(R.id.writer);
        brief = (TextView)findViewById(R.id.brief);
        all = (ImageView)findViewById(R.id.all);
        listView = (ListView)findViewById(R.id.listview);
        scrollView = (ScrollView)findViewById(R.id.scrollView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        pic = (ImageView)findViewById(R.id.pic);
        homework = (TextView)findViewById(R.id.homework);

        startread = (Button)findViewById(R.id.startread);
        init();
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    private void init() {
        toolbar.setTitle("书本详情");
        Bundle bundle=getIntent().getExtras();
        int bookid = bundle.getInt("bookid");
        Toast.makeText(this, "bookid----------"+bookid, Toast.LENGTH_SHORT).show();
        getchapter(bookid);
        getbookinfor(bookid);
        all.setOnClickListener(new View.OnClickListener() {
            Boolean flag = true;
            @Override
            public void onClick(View v) {
                if(flag){
                    flag = false;
                    bookintro.setEllipsize(null);
                    bookintro.setSingleLine(flag);
                    all.setImageResource(R.mipmap.up);
                }else{
                    flag = true;
                    bookintro.setEllipsize(TextUtils.TruncateAt.END);
                    bookintro.setSingleLine(flag);
                    all.setImageResource(R.mipmap.down);
                }
            }
        });
        scrollView.smoothScrollTo(0,0);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        startread.setOnClickListener(this);
        homework.setOnClickListener(this);
    }

    private void getbookinfor(final int bookid) {
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
                        mid = js.getInt("id");
                        if(mid==bookid){
                            mbookname = js.getString("bookname");
                            mwriter = js.getString("writer");
                            mbrief = js.getString("brief");
                            mintroduce = js.getString("introduce");
                            img = js.getString("img");
                            imgurl = "http://118.25.100.167"+img;
                            System.out.println("bookname-------------"+mbookname);
                        }
                        Message msg = Message.obtain();
                        msg.obj = bytes;
                        msg.what = SetBookInfor;
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
                Toast.makeText(getApplicationContext(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void setListViewHeightBasedOnChildren(ListView listView){
        ListAdapter listAdapter = listView.getAdapter();
        if(listAdapter==null){
            return;
        }
        int totalHeight=0;
        for(int i=0;i<listAdapter.getCount();i++){
            View listItem = listAdapter.getView(i,null,listView);
            listItem.measure(0,0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight()*(listAdapter.getCount()-1));
        listView.setLayoutParams(params);
    }

    private void getchapter(int bookid){
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://118.25.100.167/android/chapter.action?bookid="+bookid;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                try {
                    String resultDate = new String(bytes,"utf-8");
                    JSONArray array = new JSONArray(resultDate);
                    for(int j=0;j<array.length();j++){
                        JSONObject js = array.getJSONObject(j);
                        title = js.getString("title");
                        rank = js.getInt("rank");
                        System.out.println("title-------------"+title+"rank--------------------"+rank);
                        Map<String,Object> map = new HashMap<>();
                        map.put("content",title);
                        map.put("number","第000"+rank+"回");
                        list.add(map);
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
                Toast.makeText(getApplicationContext(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.startread:
                Intent intent = new Intent(this, Reading.class);
                startActivity(intent);
                break;
            case R.id.homework:
                Intent intent2 = new Intent(this, Do_Homework.class);
                startActivity(intent2);
        }
    }
}
