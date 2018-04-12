package com.example.even1.endorsedsystemteacher.View.StackFragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Message;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.even1.endorsedsystemteacher.Adapter.BookListAdapter;
import com.example.even1.endorsedsystemteacher.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

public class Book_List extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private Toolbar toolbar;
    private ListView listview;
    private TextView title,empty;
    private int id;
    private String img,bookname,writer,brief,introduce,type,imgurl;
    private ArrayList<HashMap<String, Object>> myList = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> mybooklist = new ArrayList<>();
    private BookListAdapter adapter;
    public int IS_FINISH;
    private ArrayList<Integer>mbookid = new ArrayList<>();

    private android.os.Handler handler = new android.os.Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == IS_FINISH) {
                adapter = new BookListAdapter(getApplicationContext(),mybooklist);
                listview.setAdapter(adapter);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book__list);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        listview = (ListView)findViewById(R.id.listview);
        title = (TextView)findViewById(R.id.title);
        empty = (TextView)findViewById(R.id.empty);

        Intent intent = getIntent();
        mbookid = intent.getIntegerArrayListExtra("mbookid");
        init();
    }

    private void init() {
        title.setText("书单详情");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getbookcase();
        listview.setOnItemClickListener(this);
    }

    private void getbookcase(){
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
                        img = js.getString("img");
                        imgurl = "http://118.25.100.167"+img;
                        bookname = js.getString("bookname");
                        writer = js.getString("writer");
                        brief = js.getString("brief");
                        introduce = js.getString("introduce");
                        HashMap<String,Object> map = new HashMap<>();
                        map.put("pic",imgurl);
                        map.put("name",bookname);
                        map.put("intro",introduce);
                        map.put("id",id);
                        myList.add(map);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getbook();
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getbook() {
        int count = 0;
        System.out.println(mbookid.size());
        for(int position=0;position<myList.size();position++){
            HashMap<String,Object> map = new HashMap<>();
            if(mbookid.size()==0){
                empty.setText("书库中还没有书哦");
                break;
            }
            if(myList.get(position).get("id")==mbookid.get(count)){
                map.put("pic",myList.get(position).get("pic"));
                map.put("name",myList.get(position).get("name"));
                map.put("intro",myList.get(position).get("intro"));
                mybooklist.add(map);
                count++;
                System.out.println("count-----------------"+count);
                System.out.println(position);
                System.out.println("bookname-------------"+position+":"+myList.get(position).get("name"));
                Message msg = Message.obtain();
                msg.what = IS_FINISH;
                handler.sendMessage(msg);
            }
            if(count==mbookid.size()){
                break;
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem item = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        //设置搜索栏的默认提示
        searchView.setQueryHint("请输入书本名称");
        //默认刚进去就打开搜索栏
        searchView.setIconified(true);
        //设置输入文本的EditText
        SearchView.SearchAutoComplete et = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
        //设置搜索栏的默认提示，作用和setQueryHint相同
        et.setHint("请输入书本名称");
        //设置提示文本的颜色
        et.setHintTextColor(Color.WHITE);
        //设置输入文本的颜色
        et.setTextColor(Color.WHITE);
        //设置提交按钮是否可见
        //searchView.setSubmitButtonEnabled(true);

        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this,Book_Detail.class);
        Bundle bundle = new Bundle();
        bundle.putInt("bookid", (Integer) myList.get(position).get("id"));
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
