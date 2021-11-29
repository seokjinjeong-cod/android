package com.example.mypet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    CheckBox cb;
    RadioGroup rdoG;
    RadioButton rdoBoxer, rdoDog, rdoGolden;
    Button btn;
    ImageView imgPet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.textView2);
        cb = findViewById(R.id.checkBox);
        rdoG = findViewById(R.id.rdoG);
        rdoBoxer = findViewById(R.id.rdoBoxer);
        rdoDog = findViewById(R.id.rdoDog);
        rdoGolden = findViewById(R.id.rdoGolden);
        btn = findViewById(R.id.button);
        imgPet = findViewById(R.id.imageView);

        tv.setVisibility(View.INVISIBLE);
        rdoG.setVisibility(View.INVISIBLE);
        btn.setVisibility(View.INVISIBLE);
        imgPet.setVisibility(View.INVISIBLE);

        cb.setOnClickListener(v -> {            //??
            if(cb.isChecked()){
                tv.setVisibility(View.VISIBLE);
                rdoG.setVisibility(View.VISIBLE);
                btn.setVisibility(View.VISIBLE);
                imgPet.setVisibility(View.VISIBLE);
            } else {
                tv.setVisibility(View.INVISIBLE);
                rdoG.setVisibility(View.INVISIBLE);
                btn.setVisibility(View.INVISIBLE);
                imgPet.setVisibility(View.INVISIBLE);
            }
        });

        btn.setOnClickListener(v -> {
            imgPet.setVisibility(View.VISIBLE);
        });
        View.OnClickListener handler = (e -> {
            switch (e.getId()){
                case R.id.rdoBoxer: imgPet.setImageResource(R.drawable.boxer); break;
                case R.id.rdoDog: imgPet.setImageResource(R.drawable.dog); break;
                case R.id.rdoGolden:imgPet.setImageResource(R.drawable.golden); break;
            }
        });
        rdoBoxer.setOnClickListener(handler);
        rdoDog.setOnClickListener(handler);
        rdoGolden.setOnClickListener(handler);


    }
}