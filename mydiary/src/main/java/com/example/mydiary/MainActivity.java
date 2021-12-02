package com.example.mydiary;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<DiaryVO> list;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnWriteForm = findViewById(R.id.btnWriteForm);
        lv = findViewById(R.id.listDiary);

        DiaryDAO dao = new DiaryDAO();
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        list = DiaryDAO.selectAll(dbHelper);

        lv.setAdapter(new MyAdapter(list));

        btnWriteForm.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), WriteActivity.class);
            startActivityForResult(intent,0);
        });

        lv.setOnItemClickListener(((adapterView, view, i, l) -> {
            Toast.makeText(getApplicationContext(), list.get(i).get_id(), Toast.LENGTH_SHORT).show();
            String id = list.get(i).get_id();
            String title = list.get(i).getTitle();
            String content = list.get(i).getContent();
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("확인")
                    .setPositiveButton("수정", (dialogInterface, i1) -> {
                        DiaryVO vo = new DiaryVO();
                        vo.set_id(id);
                        vo.setTitle(title);
                        vo.setContent(content);
                        Intent intent = new Intent(getApplicationContext(), WriteActivity.class);
                        intent.putExtra("title", title);
                        intent.putExtra("content", content);
                        intent.putExtra("id", id);
                        startActivityForResult(intent,0);
                        dao.update(dbHelper, vo);
                    })
                    .setNegativeButton("삭제", (dialogInterface, i1) -> {
                        DiaryVO vo = new DiaryVO();
                        vo.set_id(id);
                        dao.delete(dbHelper, vo);

//                        list.remove(i);
//                        ((MyAdapter)(lv.getAdapter())).notifyDataSetChanged();

                        list = DiaryDAO.selectAll(dbHelper);
                        lv.setAdapter(new MyAdapter(list));
                    })
                    .create()
                    .show();
        }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1) {
            DBHelper dbHelper = new DBHelper(getApplicationContext());
            ArrayList<DiaryVO> list = DiaryDAO.selectAll(dbHelper);

            lv.setAdapter(new MyAdapter(list));
            Toast.makeText(getApplicationContext(),"등록완료!", Toast.LENGTH_LONG).show();
        } else {
            DBHelper dbHelper = new DBHelper(getApplicationContext());
            ArrayList<DiaryVO> list = DiaryDAO.selectAll(dbHelper);

            lv.setAdapter(new MyAdapter(list));
            Toast.makeText(getApplicationContext(),"수정완료!", Toast.LENGTH_LONG).show();
        }
    }
}