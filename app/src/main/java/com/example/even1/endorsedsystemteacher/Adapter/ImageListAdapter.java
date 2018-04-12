package com.example.even1.endorsedsystemteacher.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.even1.endorsedsystemteacher.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Even1 on 2018/4/1.
 */

public class ImageListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private View deleteView;
    private boolean isShowDelete;// 根据这个变量来判断是否显示删除图标，true是显示，false是不显示
    private ArrayList<HashMap<String,Object>> myList;
    private ArrayList<Integer> mbookid;
    public ImageListAdapter(Context context, ArrayList<HashMap<String,Object>> myList,ArrayList<Integer>mbookid) {
        this.context = context;
        this.myList = myList;
        inflater = LayoutInflater.from(context);
        this.mbookid = mbookid;
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public Object getItem(int position) {
        return myList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setIsShowDelete(boolean isShowDelete) {
        this.isShowDelete = isShowDelete;
        notifyDataSetChanged();
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.bookshelf_item, null);
            viewHolder.deleteView = (ImageView)convertView.findViewById(R.id.delete);
            Glide
                    .with(context)
                    .load(R.mipmap.delete)
                    .into(viewHolder.deleteView);
            viewHolder.deleteView.setVisibility(isShowDelete ? View.VISIBLE : View.GONE);// 设置删除按钮是否显示
            viewHolder.pic = (ImageView)convertView.findViewById(R.id.pic);
            viewHolder.name = (TextView)convertView.findViewById(R.id.name);

        Glide
                    .with(context)
                    .load(myList.get(position).get("image"))
                    .into(viewHolder.pic);
        viewHolder.name.setText((CharSequence) myList.get(position).get("name"));

        viewHolder.deleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<HashMap<String, Object>> newList = new ArrayList<HashMap<String, Object>>();
                final AlertDialog.Builder builer = new AlertDialog.Builder(context);
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
                        ArrayList<HashMap<String, Object>> newList = new ArrayList<HashMap<String, Object>>();
                        ArrayList<Integer> newbookid = new ArrayList<Integer>();
                        if (isShowDelete) {
                            myList.remove(position);
                            isShowDelete = false;
                            mbookid.remove(position);
                        }
                        newList.addAll(myList);
                        newbookid.addAll(mbookid);
                        myList.clear();
                        mbookid.clear();
                        myList.addAll(newList);
                        mbookid.addAll(newbookid);
                        notifyDataSetChanged();//刷新gridview
                    }
                });
                AlertDialog dialog = builer.create();
                dialog.show();
            }
        });
        return convertView;

    }

    static class ViewHolder{
        TextView name;
        ImageView pic,deleteView;
    }
}
