package com.example.myhttp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView userId = findViewById(R.id.txtId);
        TextView userPw = findViewById(R.id.txtPw);
        TextView userNm = findViewById(R.id.txtName);
        TextView userRl = findViewById(R.id.txtRole);

        Gson gson = new Gson();

        Button btnSel = findViewById(R.id.btnoSel);
        Button btnIns = findViewById(R.id.btnIns);
        Button btnDel = findViewById(R.id.btnDel);
        Button btnUpd = findViewById(R.id.btnUpd);

        TextView tv = findViewById(R.id.txtResult);

        RequestQueue queue = Volley.newRequestQueue(this);
        //단건조회
        btnSel.setOnClickListener(v -> {
            String id = userId.getText().toString();
            System.out.println(id);
//            String url = "http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=f5eef3421c602c6cb7ea224104795888&targetDt=20211130";
            String url = "http://10.0.2.2/users?id=" + id;
            StringRequest request = new StringRequest(url, s -> {
                Map<String, Object> map = gson.fromJson(s, Map.class);
                tv.setText("ID:" + map.get("id") + "\nPW:" + map.get("password"));
                Toast.makeText(this, "조회완료", Toast.LENGTH_SHORT).show();
            }, e -> {
                Log.d("request", e.toString());
//                System.out.println(e);
            });
            queue.add(request);
        });

        //삭제
        btnDel.setOnClickListener(v -> {
            String id = userId.getText().toString();
            String url = "http://10.0.2.2/deleteUser?id=" + id;
            StringRequest request = new StringRequest(url, s -> {
                Toast.makeText(this, "삭제완료", Toast.LENGTH_SHORT).show();
            }, e -> {
                Log.d("request", e.toString());
            });
            queue.add(request);
        });

        //수정
        btnUpd.setOnClickListener(v -> {
            String id = userId.getText().toString();
            String pw = userPw.getText().toString();
            String name = userNm.getText().toString();
            String role = userRl.getText().toString();
            String url = "http://10.0.2.2/updateUser";
            StringRequest request = new StringRequest(Request.Method.POST, url, s -> {
                tv.setText(s);
                Toast.makeText(this, "수정완료", Toast.LENGTH_SHORT).show();
            }, e -> {}){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("id",id);
                    map.put("name",name);
                    map.put("password",pw);
                    map.put("role",role);
                    System.out.println("수정성공");
                    return map;
                }
            };
            queue.add(request);
        });

        //등록
        btnIns.setOnClickListener(v -> {
            String id = userId.getText().toString();
            String pw = userPw.getText().toString();
            String name = userNm.getText().toString();
            String role = userRl.getText().toString();
            String url = "http://10.0.2.2/insertUser";
            StringRequest request = new StringRequest(Request.Method.POST, url, s -> {
                tv.setText(s);
                Toast.makeText(this, "등록완료", Toast.LENGTH_SHORT).show();
            }, e -> {}){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("id",id);
                    map.put("name",name);
                    map.put("password",pw);
                    map.put("role",role);
                    System.out.println("등록성공");
                    return map;
                }
            };
            queue.add(request);
        });
    }
}