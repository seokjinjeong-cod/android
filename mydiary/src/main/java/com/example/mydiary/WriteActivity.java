package com.example.mydiary;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class WriteActivity extends AppCompatActivity {

    DBHelper dbHelper;
    DiaryDAO dao = new DiaryDAO();
    String id = "";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        EditText editTitle = findViewById(R.id.editTitle);
        EditText editContent = findViewById(R.id.editContent);
        Button btnSave = findViewById(R.id.btnSave);

        Intent intent = getIntent();
        if(intent.getExtras() != null) {
            String eTitle = intent.getExtras().getString("title");
            String eContent = intent.getExtras().getString("content");
            editTitle.setText(eTitle);
            id = intent.getExtras().getString("id");
            editContent.setText(eContent);
        }

        dbHelper = new DBHelper(getApplicationContext());

        btnSave.setOnClickListener(v -> {
            DiaryVO diary = new DiaryVO();
            String title = editTitle.getText().toString();
            String content = editContent.getText().toString();
            if(id.equals("")) {
                diary.setTitle(title);
                diary.setContent(content);
                Log.d("alert", "등록등록등록등록등록등록등록등록등록등록등록");
                dao.insert(dbHelper, diary);
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                setResult(1, intent1);
            } else {
                diary.set_id(id);
                diary.setTitle(title);
                diary.setContent(content);
                Log.d("alert", "수정수정수정수정수정수정수정수정수정수정수정");
                dao.update(dbHelper, diary);
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                setResult(2, intent1);
            }
            finish();
        });
    }
}