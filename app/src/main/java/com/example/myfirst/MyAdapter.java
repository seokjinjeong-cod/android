package com.example.myfirst;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    ArrayList<MyFavoritesVO> data;

    MyAdapter(){}
    MyAdapter(ArrayList<MyFavoritesVO> data) { this.data = data; }
    public int getCount() { return data.size(); }
    public Object getItem(int i) { return data.get(i); }
    public long getItemId(int i) { return i; }
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        view = inflater.inflate(R.layout.listview_item, viewGroup, false);
        TextView txtTitle = view.findViewById(R.id.txtTitle);
        TextView txtAddress = view.findViewById(R.id.txtAddress);
        ImageView img = view.findViewById(R.id.imageView);
        txtTitle.setText(data.get(i).getTitle());
        txtAddress.setText(data.get(i).getAddress());
        switch (i){
            case 0: img.setImageResource(R.drawable.aaa); break;
            case 1: img.setImageResource(R.drawable.bbb); break;
            case 2: img.setImageResource(R.drawable.ccc); break;
            case 3: img.setImageResource(R.drawable.ddd); break;
            case 4: img.setImageResource(R.drawable.eee); break;
            case 5: img.setImageResource(R.drawable.fff); break;
            case 6: img.setImageResource(R.drawable.ggg); break;
            case 7: img.setImageResource(R.drawable.hhh); break;
            case 8: img.setImageResource(R.drawable.iii); break;
            case 9: img.setImageResource(R.drawable.jjj); break;
        }
        return view;
    }
}