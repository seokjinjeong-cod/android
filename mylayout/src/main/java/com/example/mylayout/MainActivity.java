package com.example.mylayout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    GridLayout linear;
    int startNum = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linear = findViewById(R.id.linear);

        View.OnClickListener handler = v -> {
//            Toast.makeText(this, "클릭되버림", Toast.LENGTH_SHORT).show();
            //클릭한 버튼의 text(숫자값)을 읽어서 startNum과 같다면
            if(Integer.parseInt(((Button)v).getText().toString()) == startNum){
                ((Button)v).setText("");
                startNum++;
            }
            if(startNum == 17) {
                Toast.makeText(this, "게임완료", Toast.LENGTH_LONG).show();
            }
        };

        // 1차원배열 16개의 임의의 순서로
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16);
        Collections.shuffle(list);
        Button startB = new Button(this);
        for(int i : list) {
            Button btn = new Button(this);
            btn.setText(String.valueOf(i));
            linear.addView(btn);
            btn.setOnClickListener(handler);
        }
    }
}