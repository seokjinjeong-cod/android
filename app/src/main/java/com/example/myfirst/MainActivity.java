package com.example.myfirst;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Gson gson = new Gson();
    RequestQueue queue;
    ArrayList<MyFavoritesVO> list = new ArrayList<MyFavoritesVO>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lv = findViewById(R.id.listDiary);
        String url = "https://api.odcloud.kr/api/15066516/v1/uddi:507e01f5-76ec-42ff-96a5-8b6ff9ce554e?page=1&perPage=10&serviceKey=Q5%2FkGV%2FODcKC%2FT8IOS7rqJ7d%2Bi4UgXaDoWAimoPfNu%2FtPDiJEGI00k9D5IAKfk%2BIyHg791us%2FcRg4rQpmE5n5A%3D%3D";
        queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(url,
                s -> {
//                    System.out.println(s);
                    Map<String, Object> map = gson.fromJson(s, Map.class);
                    for(int i = 0; i < 10; i++) {
//                        tv.append(((Map<String,String>)((ArrayList)(map.get("data"))).get(i)).get("업소명") + "\n");
                        MyFavoritesVO myFav = new MyFavoritesVO();
                        myFav.setTitle(((Map<String,String>)((ArrayList)(map.get("data"))).get(i)).get("업소명"));
                        myFav.setAddress(((Map<String,String>)((ArrayList)(map.get("data"))).get(i)).get("주소1"));
                        myFav.setTel(((Map<String,String>)((ArrayList)(map.get("data"))).get(i)).get("전화번호"));
                        myFav.setCeoMsg(((Map<String,String>)((ArrayList)(map.get("data"))).get(i)).get("사장님이자랑하는내가게한마디"));
                        myFav.setKd(((Map<String,String>)((ArrayList)(map.get("data"))).get(i)).get("경도"));
                        myFav.setWd(((Map<String,String>)((ArrayList)(map.get("data"))).get(i)).get("위도"));
                        list.add(myFav);
                    }
                    System.out.println(list);
                    lv.setAdapter(new MyAdapter(list));
                },
                e -> {
                    Log.d("alert", e.toString());
                });
        queue.add(request);

        lv.setOnItemClickListener(((adapterView, view, i, l) -> {
            String title = list.get(i).getTitle();
            String address = list.get(i).getAddress();
            String tel = list.get(i).getTel();
            String ceoMsg = list.get(i).getCeoMsg();
            String kd = list.get(i).getKd();
            String wd = list.get(i).getWd();

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("사장님이 자랑하는 한마디").setMessage(ceoMsg)
                    .setPositiveButton("위치", (dialogInterface, i1) -> {
                        MyFavoritesVO vo = new MyFavoritesVO();
                        vo.setTitle(title);
                        vo.setAddress(address);
                        vo.setTel(tel);
                        vo.setCeoMsg(ceoMsg);
                        Intent intent = new Intent(getApplicationContext(), Kakaomap.class);
                        intent.putExtra("kd", kd);
                        intent.putExtra("wd", wd);
                        startActivityForResult(intent,0);
                    })
                    .setNegativeButton("즐겨찾기", (dialogInterface, i1) -> {

                    })
                    .create()
                    .show();
        }));

//        getHashKey();

    }
//    private void getHashKey(){
//        PackageInfo packageInfo = null;
//        try {
//            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        if (packageInfo == null)
//            Log.e("KeyHash", "KeyHash:null");
//
//        for (Signature signature : packageInfo.signatures) {
//            try {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                System.out.println("====================================================================================================================================");
//                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//                System.out.println("====================================================================================================================================");
//            } catch (NoSuchAlgorithmException e) {
//                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
//            }
//        }
//    }

}
