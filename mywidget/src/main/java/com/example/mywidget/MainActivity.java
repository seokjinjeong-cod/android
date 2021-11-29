package com.example.mywidget;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button plusB, minusB, timesB, divideB;
    EditText txtNum1, txtNum2;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_calc);
        plusB = findViewById(R.id.button5);
        minusB = findViewById(R.id.button6);
        timesB = findViewById(R.id.button7);
        divideB = findViewById(R.id.button8);
        txtNum1 = findViewById(R.id.txtNum1);
        txtNum2 = findViewById(R.id.txtNum2);
        tv = findViewById(R.id.textView6);
        // 익명클래스( 클래스 선언과 생성을 동시에 )
        // 람다식( 하나의 추상메서드만 있는 인터페이스인 경우
        View.OnClickListener handler = v -> {
            double n1 = Double.parseDouble(txtNum1.getText().toString());
            double n2 = Double.parseDouble(txtNum2.getText().toString());
            double result = 0;
            switch (v.getId()){
                case R.id.button5: result = n1 + n2; break;
                case R.id.button6: result = n1 - n2; break;
                case R.id.button7: result = n1 * n2; break;
                case R.id.button8:
                    if(n2 == 0) {

                    }
                    result = n1 / n2; break;
            }
            System.out.println(result);
            tv.setText(String.valueOf(result));
        };
        plusB.setOnClickListener(handler);
        minusB.setOnClickListener(handler);
        timesB.setOnClickListener(handler);
        divideB.setOnClickListener(handler);
    }
}