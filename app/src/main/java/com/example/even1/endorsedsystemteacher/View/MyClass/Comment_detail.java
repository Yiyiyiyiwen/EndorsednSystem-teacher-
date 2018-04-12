package com.example.even1.endorsedsystemteacher.View.MyClass;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


import com.example.even1.endorsedsystemteacher.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Even1 on 2018/3/6.
 */

public class Comment_detail extends Fragment implements View.OnClickListener{
    private ScrollView scrollView;
    private LinearLayout top;
    private ListView general,think;
    private SimpleAdapter simpleAdapter;
    private Button submit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_item,container,false);
        scrollView = (ScrollView)view.findViewById(R.id.scrollView);
        top = (LinearLayout)view.findViewById(R.id.top);
        general = (ListView)view.findViewById(R.id.general);
        think = (ListView)view.findViewById(R.id.think);
        submit = (Button)view.findViewById(R.id.submit);

        init();
        return view;
    }

    private void init() {
        scrollView.scrollTo(0,0);
        top.setFocusable(true);
        top.setFocusableInTouchMode(true);
        top.requestFocus();

        simpleAdapter = new SimpleAdapter(getActivity(), getData(),R.layout.comment_item,
                new String[]{"content","comment"},
                new int[]{R.id.content,R.id.comment});
        general.setAdapter(simpleAdapter);
        think.setAdapter(simpleAdapter);
        submit.setOnClickListener(this);
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
                Toast.makeText(getActivity(), "提交成功", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
