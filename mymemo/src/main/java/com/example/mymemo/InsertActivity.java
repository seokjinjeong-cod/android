package com.example.mymemo;

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

public class InsertActivity extends AppCompatActivity {

    Button btnIns, btnDel, btnoSel, btnUpd;
    EditText txtName, txtResult2, txtPhone, txtNum, txtAge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        txtName = findViewById(R.id.txtName);
        txtResult2 = findViewById(R.id.txtResult2);
        txtPhone = findViewById(R.id.txtPhone);
        txtName = findViewById(R.id.txtName);
        txtNum = findViewById(R.id.txtNum);
        txtAge = findViewById(R.id.txtAge);
        btnIns = findViewById(R.id.btnIns);
        btnDel = findViewById(R.id.btnDel);
        btnoSel = findViewById(R.id.btnoSel);
        btnUpd = findViewById(R.id.btnUpd);

        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext()); //DB, table 생성

        //삭제
        btnDel.setOnClickListener(v -> {
            String id = txtNum.getText().toString();
            SQLiteDatabase db = dbHelper.getWritableDatabase(); //DB
            db.delete("emp", "_id=?", new String[]{id});
//            String sql = "DELETE from emp where _id = " + id;
//            db.execSQL(sql);
            Toast.makeText(this, "삭제완료", Toast.LENGTH_SHORT).show();
        });

        //단건조회
        btnoSel.setOnClickListener(v -> {
            list.removeAll(list);
            String id = txtNum.getText().toString();
            SQLiteDatabase db = dbHelper.getWritableDatabase(); //DB
            String sql = "SELECT * from emp where _id = " + id;
            Cursor cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("_id", cursor.getString(0));
                map.put("name", cursor.getString(1));
                map.put("age", cursor.getString(2));
                map.put("mobile", cursor.getString(3));
                list.add(map);
            }
            Toast.makeText(this, "조회완료", Toast.LENGTH_SHORT).show();
            String result = "";
            for(int i = 0; i < list.size(); i++) {
                result += "ID : " + list.get(i).get("_id") +
                        "\tMB : " + list.get(i).get("mobile") +
                        "\tNM : " + list.get(i).get("name") +
                        "\tAG : " + list.get(i).get("age") + "\n";
                txtName.setText(list.get(i).get("name"));
                txtPhone.setText(list.get(i).get("mobile"));
                txtAge.setText(list.get(i).get("age"));
            }
            txtResult2.setText(result);
        });

        //등록
        btnIns.setOnClickListener(v -> {
            String name = txtName.getText().toString();
            String phoneNum = txtPhone.getText().toString();
            String age = txtAge.getText().toString();
            SQLiteDatabase db = dbHelper.getWritableDatabase(); //DB
            String sqlInsert = "INSERT INTO emp " + "(name, age, mobile) VALUES ('" + name + "', '" + age + "', '" + phoneNum + "')";
            db.execSQL(sqlInsert);
//            String sqlInsert = "INSERT INTO emp " + "(name, age, mobile) VALUES (?, ?, ?)";
//            db.execSQL(sqlInsert, new Object[]{name, age, phoneNum});

//            Toast.makeText(this, "등록완료", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            setResult(1, intent);
            finish();
        });

        //수정
        btnUpd.setOnClickListener(v -> {
            String id = txtNum.getText().toString();
            String name = txtName.getText().toString();
            String phoneNum = txtPhone.getText().toString();
            String age = txtAge.getText().toString();
            SQLiteDatabase db = dbHelper.getWritableDatabase(); //DB
            String sql = "UPDATE emp SET name = '" + name + "'" + ",mobile = '" + phoneNum + "'" + ",age = '" + age + "'" + "where _id = " + id;
            db.execSQL(sql);
//            String sqlInsert = "INSERT INTO emp " + "(name, age, mobile) VALUES (?, ?, ?)";
//            db.execSQL(sqlInsert, new Object[]{name, age, phoneNum});
//            Toast.makeText(this, "수정완료", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            setResult(2, intent);
            finish();
//            startActivity(intent);
        });

    }
}