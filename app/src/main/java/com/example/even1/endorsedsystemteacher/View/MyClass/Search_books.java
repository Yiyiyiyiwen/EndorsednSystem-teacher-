package com.example.even1.endorsedsystemteacher.View.MyClass;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


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

import static com.example.even1.endorsedsystemteacher.View.StackFragment.Book_Detail.setListViewHeightBasedOnChildren;

public class Search_books extends AppCompatActivity implements SearchView.OnQueryTextListener, AdapterView.OnItemClickListener {

    private ListView showlistview;
    private TextView bookname;
    private ArrayList<String> showlist= new ArrayList<>();;
    private ArrayAdapter listadapter;

    private SearchView searchView;

    public int IS_FINISH;
    private android.os.Handler handler = new android.os.Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == IS_FINISH) {
                ArrayAdapter showadapter=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,showlist);
                showlistview.setAdapter(showadapter);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_books);

        bookname = (TextView)findViewById(R.id.name) ;
        showlistview = (ListView)findViewById(R.id.showlistview);
        searchView = (SearchView)findViewById(R.id.searchview);
        init();
    }


    private void init() {
        getbookcase();

        showlistview.setTextFilterEnabled(true);
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("查找书名");
        searchView.setIconifiedByDefault(true);

        showlistview.setOnItemClickListener(this);
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
                        String bookname = js.getString("bookname");
                        showlist.add(bookname);

                        Message msg = Message.obtain();
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
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty((newText))){
            showlistview.clearTextFilter();
        }else{
            showlistview.setFilterText(newText);}
        return true;

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String mbookname = showlist.get(position);
        Intent intent = new Intent(this,Do_Homework.class);
        intent.putExtra("name", "《"+mbookname+"》");
        startActivity(intent);
    }

}
