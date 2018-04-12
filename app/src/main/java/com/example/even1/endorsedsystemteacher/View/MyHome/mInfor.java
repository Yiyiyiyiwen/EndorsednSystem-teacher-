package com.example.even1.endorsedsystemteacher.View.MyHome;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.example.even1.endorsedsystemteacher.Adapter.GridViewAdapter;
import com.example.even1.endorsedsystemteacher.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class mInfor extends Fragment {

    private GridView gridView;
    private GridViewAdapter adapter;
    private String[] name = {"福尔摩斯探案集","昆虫记","家"};
    private int[] pic = {R.mipmap.bookshelfitem1,R.mipmap.bookshelfitem2,R.mipmap.bookshelfitem3};
    private ArrayList<HashMap<String, Object>> myList;

    private TextView mname,biref,phone,email,job,birth,place,school;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_m_infor,container,false);

        gridView = (GridView)view.findViewById(R.id.gridview);
        mname = (TextView)view.findViewById(R.id.name);
        biref = (TextView)view.findViewById(R.id.biref);
        phone = (TextView)view.findViewById(R.id.phone);
        email = (TextView)view.findViewById(R.id.email);
        job = (TextView)view.findViewById(R.id.job);
        birth = (TextView)view.findViewById(R.id.birth);
        place = (TextView)view.findViewById(R.id.place);
        school = (TextView)view.findViewById(R.id.school);
        init();
        return view;
    }

    private void init() {
        myList = getMenuAdapter(pic,name);
        adapter = new GridViewAdapter(getActivity(),myList,1);
        gridView.setAdapter(adapter);

        SharedPreferences sp = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        mname.setText(sp.getString("name",null));
        biref.setText(sp.getString("biref",null));
        phone.setText(sp.getString("phone",null));
        email.setText(sp.getString("email",null));
        job.setText(sp.getString("job",null));
        birth.setText(stampToDate(sp.getString("birth",null)));
        place.setText(sp.getString("place",null));
        school.setText(sp.getString("school",null));
    }
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    public static Date ChangeDate(String s){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Long time = new Long(s);
        Date date = null;
        String d = simpleDateFormat.format(time);
        try {
            date = simpleDateFormat.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    private ArrayList<HashMap<String, Object>> getMenuAdapter(//将数据装入List
                                                              int[] menuImageArray, String[] menuNameArray) {
        ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < menuImageArray.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("image", menuImageArray[i]);
            map.put("name", menuNameArray[i]);
            data.add(map);
        }
        return data;
    }
}
