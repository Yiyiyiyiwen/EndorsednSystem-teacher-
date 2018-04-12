package com.example.even1.endorsedsystemteacher.View.MyClass;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.even1.endorsedsystemteacher.Adapter.MyClassAdapter;
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

public class Class_Member extends AppCompatActivity {

    private TextView teacher,student;
    private int[]head={R.mipmap.head};
    private String[] tname = {"李老师","王老师","赵老师"};
    private ListView tlistview,slitview;

    private Toolbar toolbar;
    private int slength;
    private int tlength = tname.length;
    List<Map<String,Object>> list = new ArrayList<>();

    private String name;

    public int IS_FINISH;

    private android.os.Handler handler = new android.os.Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == IS_FINISH) {
                MyClassAdapter adapter = new MyClassAdapter(getApplicationContext(),list);
                slitview.setAdapter(adapter);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class__member);

        teacher = (TextView)findViewById(R.id.tnumber);
        student = (TextView)findViewById(R.id.snumber);
        tlistview = (ListView)findViewById(R.id.teacher);
        slitview = (ListView)findViewById(R.id.student);
        toolbar = (Toolbar)findViewById(R.id.toolbar);

        init();
    }

    private void init() {
        SimpleAdapter tadapter = new SimpleAdapter(this,getData(tname),R.layout.class_item,
                new String[]{"pic","name"},
                new int[]{R.id.pic,R.id.name});
        tlistview.setAdapter(tadapter);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Bundle bundle=getIntent().getExtras();
        int id=bundle.getInt("id");
        initclassmember(id);
        teacher.setText(tlength+"人");
    }

    private void initclassmember(int id) {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://118.25.100.167/android/classmember.action?cid="+id;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                try {
                    String resultDate = new String(bytes,"utf-8");
                    JSONArray array = new JSONArray(resultDate);
                    student.setText(array.length()+"人");
                    for(int j=0;j<array.length();j++){
                        JSONObject js = array.getJSONObject(j);
                        name = js.getString("name");
                        Map<String,Object> map = new HashMap<>();
                        map.put("pic",R.mipmap.head);
                        map.put("name",name);
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

    private List<Map<String,Object>> getData(String[] catagory){
        List<Map<String,Object>> list = new ArrayList<>();
        for(int i=0;i<catagory.length;i++){
            Map<String,Object> map = new HashMap<>();
            map.put("pic",R.mipmap.head);
            map.put("name",catagory[i]);
            list.add(map);
        }
        return list;
    }
}
