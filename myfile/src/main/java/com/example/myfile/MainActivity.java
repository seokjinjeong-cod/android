package com.example.myfile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    EditText edtDiary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtDiary = findViewById(R.id.edtDiary);
        Button btnWrite = findViewById(R.id.button);
        Button btnRead = findViewById(R.id.button2);
        DatePicker dp = (DatePicker)findViewById(R.id.datePicker1);

        dp.init(2021,12,2,(datePicker, i, i1, i2) -> {
            //년월일로 파일명을 만들고
            String fname = String.valueOf(i)

                    + ( (i1<10) ? "0"+(i1+1) : i1+1 )
                    + ( (i2<10) ? "0"+i2 : i2 ) + ".txt";

            fileRead(fname);
        });

        btnWrite.setOnClickListener(v -> {
            try {
                String dayOfMonth = "";
                String month = "";
                if(dp.getDayOfMonth() < 10) { dayOfMonth = "0" + String.valueOf(dp.getDayOfMonth()); }
                else { dayOfMonth = String.valueOf(dp.getDayOfMonth()); }
                if(dp.getMonth() < 10) { month = "0" + String.valueOf(dp.getMonth() + 1); }
                else { month = String.valueOf(dp.getMonth() + 1); }
                String fname = String.valueOf(dp.getYear()) + month + dayOfMonth + ".txt";
                FileOutputStream outFs = openFileOutput(fname, Context.MODE_PRIVATE);
//                String str = "쿡북 안드로이드";
                String cont = edtDiary.getText().toString();
                outFs.write(cont.getBytes());
                outFs.close();
                Toast.makeText(getApplicationContext(),fname + "가 생성됨", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    edtDiary.setText("");
                    FileInputStream inFs = openFileInput("file.txt");
                    byte[] txt = new byte[30];
                    inFs.read(txt);
                    String str = new String(txt);
                    edtDiary.setText(str);
                    Toast.makeText(getApplicationContext(),str, Toast.LENGTH_LONG).show();
                    inFs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void fileRead(String filename) {
        try {
            Log.d("alert", filename);
            edtDiary.setText("");
            FileInputStream inFs = openFileInput(filename);
            byte[] txt = new byte[30];
            inFs.read(txt);
            String str = new String(txt);
            //editView
            edtDiary.setText(str);
            inFs.close();
        } catch(IOException e) {
            edtDiary.setText("파일없음");
//            Toast.makeText(getApplicationContext(), "파일없음", Toast.LENGTH_SHORT).show();
        }
    }
}