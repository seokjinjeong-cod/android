package com.example.mydialog;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn1, btn2, btn3, btn4;
        btn1 = findViewById(R.id.button3);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);
        btn4 = findViewById(R.id.button4);

//        Button button1 = findViewById(R.id.button1);
//        Button button2 = findViewById(R.id.button2);
//        Button button3 = findViewById(R.id.button3);
//        Button button4 = findViewById(R.id.button4);

        String[] city = new String[]{"서울","부산","대구","대전"};
        btn1.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("제목")
                   .setMessage("alert")
                   .setPositiveButton("수정", (dialogInterface, i) -> {
                       Log.d("alert", "수정버튼클릭");
                   })
                   .setNegativeButton("삭제", (dialogInterface, i) -> {
                       Log.d("alert", "수삭제버튼클릭");
                   })
                   .create()
                   .show();
        });
        ArrayList selectedItems = new ArrayList<Integer>();
        btn2.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMultiChoiceItems(city, null,
                                (dialog, which, isChecked) -> {
                                    if (isChecked) {
                                        selectedItems.add(which);
                                    } else if (selectedItems.contains(which)) {
                                        selectedItems.remove(which);
                                    }
                                })
                    .setPositiveButton("확인",(dialog, id) -> {
                        //선택된 값들을 city배열에서 찾아서 출력
                        //city.stream(city).filter().map(System.out::print);
                        for(Object o : selectedItems) {
                            int pos = ((Integer)o).intValue();
                            Log.d("alert", city[pos]);
                        }

                    }).create().show();
        });
        //커스텀 Dialog
        btn3.setOnClickListener(v -> { customModal(); });
        btn4.setOnClickListener(v -> {

        });

    }

    private void customModal() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        builder.setView(R.layout.activity_login).create().show();
    }
}