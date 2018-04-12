package com.example.even1.endorsedsystemteacher.View.MyClass;

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

public class Class_Grade extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listView;
    private TextView title;
    private String[] name = {"昌茂","晨濡","彦昌","高朗","学智","旭彬","高朗","云天","昂然","曜曦","子昂"};
    private String[] score = {"90","70","66","86","86","85","90","95","77","90","82"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class__grade);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        listView = (ListView)findViewById(R.id.listview);
        title = (TextView)findViewById(R.id.title);
        init();
    }

    private void init() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title.setText("成绩详情");
        SimpleAdapter adapter = new SimpleAdapter(this,getData(),R.layout.class_item,
                new String[]{"name","score"},
                new int[]{R.id.name,R.id.score});
        listView.setAdapter(adapter);
    }

    private List<Map<String,Object>> getData(){
        List<Map<String,Object>> list = new ArrayList<>();
        for(int i=0;i<name.length;i++){
            Map<String,Object> map = new HashMap<>();
            map.put("name",name[i]);
            map.put("score",score[i]+"分");
            list.add(map);
        }
        return list;
    }
}
