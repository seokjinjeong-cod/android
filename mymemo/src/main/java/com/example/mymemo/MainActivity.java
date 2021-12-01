package com.example.mymemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    Button btnSel, buttonIns;
    ListView listV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listV = findViewById(R.id.listV);
        btnSel = findViewById(R.id.btnSel);
        buttonIns = findViewById(R.id.buttonIns);

        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext()); //DB, table 생성

        buttonIns.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), InsertActivity.class);
            startActivityForResult(intent,0);
        });


        //메모
        List<String> list = new ArrayList<String>();
        List<Map<String,String>> list2 = new ArrayList<>();
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "홍길동"); map.put("addr", "대구");
        list2.add(map);
        map =  new HashMap<String, String>();
        map.put("name", "김길동"); map.put("addr", "서울");
        list2.add(map);
        map =  new HashMap<String, String>();
        map.put("name", "이기자"); map.put("addr", "부산");
        list2.add(map);


        SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
                list2,
                android.R.layout.simple_list_item_2,
                new String[]{"name", "addr"},
                new int[]{android.R.id.text1, android.R.id.text2});

        listV.setAdapter(adapter);
        listV.setOnItemClickListener((adapterView, view, i, l) -> {
            Toast.makeText(this, list2.get(i).get("name"), Toast.LENGTH_SHORT).show();
        });

//        ArrayAdapter adapter = new ArrayAdapter(getApplication(),
//                android.R.layout.simple_list_item_1,
//                list);
//        listV.setOnItemClickListener((adapterView, view, i, l) -> {
//            Toast.makeText(this, list.get(i), Toast.LENGTH_SHORT).show();
//        });

        //전체조회
//        btnSel.setOnClickListener(v -> {
//            SQLiteDatabase db = dbHelper.getReadableDatabase();
//            String sql = "select _id, name, age, mobile from emp order by _id desc";
//            Cursor cursor = db.rawQuery(sql, null);
//            while (cursor.moveToNext()) {
//                list.add(cursor.getString(1));
////            HashMap<String, String> map = new HashMap<String, String>();
////            map.put("_id", cursor.getString(0));
////            map.put("name", cursor.getString(1));
////            map.put("age", cursor.getString(2));
////            map.put("mobile", cursor.getString(3));
//            }
//            adapter.notifyDataSetChanged();
//            listV.setAdapter(adapter);
//        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1) {
            Toast.makeText(getApplicationContext(), "등록되었습니다.", Toast.LENGTH_LONG).show();
        } else if(resultCode == 2) {
            Toast.makeText(getApplicationContext(), "수정되었습니다.", Toast.LENGTH_LONG).show();

        }
    }
}