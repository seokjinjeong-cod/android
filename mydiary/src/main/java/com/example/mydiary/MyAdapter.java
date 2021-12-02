package com.example.mydiary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
public class MyAdapter extends BaseAdapter {
    ArrayList<DiaryVO> data;

    public MyAdapter(){}
    public MyAdapter(ArrayList<DiaryVO> data) { this.data = data; }
    public int getCount() { return data.size(); }
    public Object getItem(int i) { return data.get(i); }
    public long getItemId(int i) { return i; }
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        view = inflater.inflate(R.layout.listview_item, viewGroup, false);
        TextView txtTitle = view.findViewById(R.id.txtTitle);
        TextView txtContent = view.findViewById(R.id.txtContent);
        txtTitle.setText(data.get(i).getTitle());
        txtContent.setText(data.get(i).getContent());
        return view;
    }
}
