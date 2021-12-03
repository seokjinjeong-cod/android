package com.example.mydiary;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
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

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_CALL_LOG,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_PRIVATE);

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
            String img = list.get(i).getImg();
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
                        intent.putExtra("img", img);
                        System.out.println("0000000000000000000000000000000000" + img);
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
        System.out.println("111111111111111111111111111111111");
        if(resultCode == RESULT_OK) {
            System.out.println("222222222222222222222222222");
            DBHelper dbHelper = new DBHelper(getApplicationContext());
            ArrayList<DiaryVO> list = DiaryDAO.selectAll(dbHelper);

            lv.setAdapter(new MyAdapter(list));
            Toast.makeText(getApplicationContext(),"완료!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(),"취소!", Toast.LENGTH_LONG).show();
        }
    }
}