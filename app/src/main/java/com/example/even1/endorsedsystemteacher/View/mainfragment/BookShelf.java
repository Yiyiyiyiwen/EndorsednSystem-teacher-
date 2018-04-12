package com.example.even1.endorsedsystemteacher.View.mainfragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.even1.endorsedsystemteacher.Adapter.ImageListAdapter;
import com.example.even1.endorsedsystemteacher.R;
import com.example.even1.endorsedsystemteacher.View.StackFragment.Book_Detail;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

import static android.content.Context.MODE_PRIVATE;

public class BookShelf extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    private Button manage;
    private Toolbar toolbar;
    private GridView gridview;
    private TextView mbookname;
    private boolean isShowDelete = false;
    private ImageListAdapter adapter;
    private ArrayList<HashMap<String, Object>> myList = new ArrayList<>();
    private ArrayList<Integer>mbookid = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> mybooklist = new ArrayList<>();
    private int userid;
    private int bookid;
    private String img,bookname,writer,brief,introduce;

    public int IS_FINISH;
    private ImageView keepread;
    private Bitmap bitmap;

    private android.os.Handler handler = new android.os.Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == IS_FINISH) {
                adapter = new ImageListAdapter(getContext(),mybooklist,mbookid);
                gridview.setAdapter(adapter);
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_book_shelf,container,false);

        toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        gridview = (GridView)view.findViewById(R.id.gridview);
        keepread = (ImageView)view.findViewById(R.id.keepread);
        mbookname = (TextView)view.findViewById(R.id.bookname);
        manage = (Button)view.findViewById(R.id.manage);
        init();

        return view;
    }

    private void init() {
        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("");

        SharedPreferences sp = getContext().getSharedPreferences("User", MODE_PRIVATE);
        userid = sp.getInt("id",0);
        getbookid(userid);
        manage.setOnClickListener(this);
        gridview.setOnItemClickListener(this);
    }



    private void getbookid(int uid) {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://118.25.100.167/android/bookcase.action?uid="+uid;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                try {
                    String resultDate = new String(bytes,"utf-8");
                    JSONArray array = new JSONArray(resultDate);
                    for(int j=0;j<array.length();j++){
                        JSONObject js = array.getJSONObject(j);
                        bookid = js.getInt("bookid");
                        mbookid.add(bookid);
                        System.out.println("getbookid---------------------------------------");
                        System.out.println("bookid-------------"+bookid);
                    }
                    System.out.println("bookidsize-------------"+mbookid.size());

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getbookcase();
            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
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
                        int id = js.getInt("id");
                        img = js.getString("img");
                        String imgurl = "http://118.25.100.167"+img;
                        bookname = js.getString("bookname");
                        writer = js.getString("writer");
                        brief = js.getString("brief");
                        introduce = js.getString("introduce");
                        System.out.println("getbookcase---------------------------------------");
                        System.out.println("bookname-------------"+bookname);
                        HashMap<String,Object> map = new HashMap<>();
                        map.put("image",imgurl);
                        map.put("name",bookname);
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
                Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getbook() {
        int count = 0;
        System.out.println(mbookid.size());
        for(int position=0;position<myList.size();position++){
            HashMap<String,Object> map = new HashMap<>();
            if(myList.get(position).get("id")==mbookid.get(count)){
                map.put("image",myList.get(position).get("image"));
                map.put("name",myList.get(position).get("name"));
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


    /*private void delete(int position) {//删除选中项方法
        ArrayList<HashMap<String, Object>> newList = new ArrayList<HashMap<String, Object>>();
        if (isShowDelete) {
            mybooklist.remove(position);
            isShowDelete = false;
        }
        newList.addAll(mybooklist);
        mybooklist.clear();
        mybooklist.addAll(newList);
    }*/

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences booksp = getActivity().getSharedPreferences("recentbook", MODE_PRIVATE);
        String bookname = booksp.getString("bookname",null);
        String bookimg = booksp.getString("bookimg",null);
        Glide
                .with(getActivity())
                .load(bookimg)
                .into(keepread);
        mbookname.setText("《"+bookname+"》");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent  = new Intent(getActivity(), Book_Detail.class);
        Bundle bundle = new Bundle();
        bundle.putInt("bookid",mbookid.get(position));
        intent.putExtras(bundle);
        startActivity(intent);
        SharedPreferences sp = getActivity().getSharedPreferences("recentbook", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit(); //SharedPreferences 本身不能读写数据，需要使用Editor

        for(int i=0;i<myList.size();i++){
            if(myList.get(i).get("id")==mbookid.get(position)){
                String bookname = String.valueOf(myList.get(i).get("name"));
                String bookimg = String.valueOf(myList.get(i).get("image"));
                editor.putString("bookname",bookname);
                editor.putString("bookimg",bookimg);
                editor.commit();
                break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.manage:
                if (isShowDelete) {
                    isShowDelete = false;
                } else {
                    isShowDelete = true;
                    adapter.setIsShowDelete(isShowDelete);
                    /*gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                final int position, long id) {
                            final AlertDialog.Builder builer = new AlertDialog.Builder(getActivity());
                            builer.setTitle("删除");
                            builer.setMessage("确认要删除该本书吗？");
                            builer.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builer.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //delete(position);//删除选中项
                                    adapter = new ImageListAdapter(getActivity(), mybooklist);//重新绑定一次adapter
                                    gridview.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();//刷新gridview
                                }
                            });
                            AlertDialog dialog = builer.create();
                            dialog.show();
                        }

                    });*/
                }
                adapter.setIsShowDelete(isShowDelete);//setIsShowDelete()方法用于传递isShowDelete值
                break;
        }
    }
}
