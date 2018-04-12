package com.example.even1.endorsedsystemteacher.View.StackFragment;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.even1.endorsedsystemteacher.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Good_Readers extends AppCompatActivity {

    private String[] name = {"莲花开","紫荆幽梦","小星子","鹤顶红","玲珑筛子","花开半夏","李歌洋","知否"};
    private ListView listView;
    private SimpleAdapter simpleAdapter;
    private Toolbar toolbar;
    private TextView title;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good__readers);
        listView = (ListView)findViewById(R.id.listview);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        title = (TextView)findViewById(R.id.title);
        init();

    }

    private void init() {
        simpleAdapter = new SimpleAdapter(this,getData(),R.layout.goodreaders_item,
                new String[]{"number","head","name"},
                new int[]{R.id.number,R.id.head,R.id.name});
        listView.setAdapter(simpleAdapter);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title.setText("优秀书籍");
    }

    private List<Map<String,Object>> getData(){
        List<Map<String,Object>> list = new ArrayList<>();
        for(int i=0;i<name.length;i++){
            Map<String,Object> map = new HashMap<>();
            map.put("number",i+1+".");
            map.put("head",R.mipmap.head);
            map.put("name",name[i]);
            list.add(map);
        }
        return list;
    }
}
