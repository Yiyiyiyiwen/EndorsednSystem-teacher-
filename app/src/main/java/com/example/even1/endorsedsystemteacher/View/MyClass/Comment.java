package com.example.even1.endorsedsystemteacher.View.MyClass;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


import com.example.even1.endorsedsystemteacher.Adapter.VpAdapter;
import com.example.even1.endorsedsystemteacher.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Comment extends AppCompatActivity implements View.OnClickListener {

    private ViewPager viewpager;
    List<View> viewlist = new ArrayList<>();

    private ScrollView scrollView;
    private LinearLayout top;
    private ListView general,think;
    private SimpleAdapter simpleAdapter;
    private Button submit;

    Comment_detail detail = new Comment_detail();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        viewpager = (ViewPager)findViewById(R.id.viewpager);

        init();
    }

    private void init() {
        viewpager.setPageMargin(80);
        viewpager.setOffscreenPageLimit(3);

        for(int i=0;i<3;i++){
            View view = LayoutInflater.from(this).inflate(R.layout.card_item,null);
            scrollView = (ScrollView)view.findViewById(R.id.scrollView);
            top = (LinearLayout)view.findViewById(R.id.top);
            general = (ListView)view.findViewById(R.id.general);
            think = (ListView)view.findViewById(R.id.think);
            submit = (Button)view.findViewById(R.id.submit);

            scrollView.scrollTo(0,0);
            top.setFocusable(true);
            top.setFocusableInTouchMode(true);
            top.requestFocus();
            simpleAdapter = new SimpleAdapter(this, getData(),R.layout.comment_item,
                    new String[]{"content","comment"},
                    new int[]{R.id.content,R.id.comment});
            general.setAdapter(simpleAdapter);
            think.setAdapter(simpleAdapter);
            submit.setOnClickListener(this);

            viewlist.add(view);
        }

        VpAdapter adapter = new VpAdapter(viewlist);
        viewpager.setCurrentItem(1);
        viewpager.setAdapter(adapter);
    }

    private List<Map<String,Object>> getData(){
        List<Map<String,Object>> list = new ArrayList<>();
        for(int i=0;i<5;i++){
            Map<String,Object> map = new HashMap<>();
            map.put("content",getString(R.string.beiying));
            map.put("comment",getString(R.string.comment));
            list.add(map);
        }
        return list;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit:
                Toast.makeText(this, "提交成功", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

}
