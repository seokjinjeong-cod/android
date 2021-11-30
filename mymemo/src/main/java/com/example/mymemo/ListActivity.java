package com.example.mymemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class ListActivity extends AppCompatActivity {

    Button btnList;
    Button btnInsert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        btnList = findViewById(R.id.btnList);
        btnInsert = findViewById(R.id.btnInsert);

        btnList.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivityForResult(intent,0);
        });

        btnInsert.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), InsertActivity.class);
            startActivityForResult(intent,0);
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