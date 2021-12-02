package com.example.mylist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecycleActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);

        ArrayList<MemoVO> list = new ArrayList<MemoVO>();
        MemoVO vo = new MemoVO();
        vo.setTitle("java"); vo.setContent("혼공자바");
        list.add(vo);
        vo = new MemoVO();
        vo.setTitle("spring"); vo.setContent("스프링프레임워크");
        list.add(vo);
        vo = new MemoVO();
        vo.setTitle("vue.js"); vo.setContent("뷰");
        list.add(vo);

        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,
                        LinearLayoutManager.VERTICAL,
                        false);
//        MyRecycleAdapter adapter = new MyRecycleAdapter(list);
        recyclerView.setAdapter(new MyRecycleAdapter(list));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
            builder.setMessage("아이템 선택").create();
        });
    }
}