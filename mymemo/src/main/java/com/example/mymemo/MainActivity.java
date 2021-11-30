package com.example.mymemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {


    Button btnSel, buttonIns;
    EditText txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtResult = findViewById(R.id.txtResult);
        btnSel = findViewById(R.id.btnSel);
        buttonIns = findViewById(R.id.buttonIns);

        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext()); //DB, table 생성

        buttonIns.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), InsertActivity.class);
            startActivityForResult(intent,0);
        });

        //전체조회
        btnSel.setOnClickListener(v -> {
            list.removeAll(list);
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String sql = "select _id, name, age, mobile from emp order by _id desc";
            Cursor cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("_id", cursor.getString(0));
                map.put("name", cursor.getString(1));
                map.put("age", cursor.getString(2));
                map.put("mobile", cursor.getString(3));
                list.add(map);

            }

            String result = "";
            for(int i = 0; i < list.size(); i++) {
                result += "ID : " + list.get(i).get("_id") +
                        "\tMB : " + list.get(i).get("mobile") +
                        "\tNM : " + list.get(i).get("name") +
                        "\tAG : " + list.get(i).get("age") + "\n";
            }
            txtResult.setText(result);
        });
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