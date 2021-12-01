package com.example.myhttp;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        System.out.println("hello");
        Gson gson = new Gson();
        EmpVO vo = new EmpVO();
        vo.setFname("seokjin");
        vo.setAge(25);

        String result = gson.toJson(vo);
        System.out.println(result);

        String response = "{\"fname\":\"홍길동\",\"age\":20}";
        EmpVO emp = gson.fromJson(response, EmpVO.class);
        System.out.println(emp.getFname() + "\n");

        Map<String, Object> map = gson.fromJson(response, Map.class);
        System.out.println(map.get("fname"));
        System.out.println(map.get("age"));

        response = "{\"data\":[{\"fname\": \"김유신\", \"age\": 40}, {\"fname\": \"홍길동\", \"age\": 35}]}";
        map = gson.fromJson(response, Map.class);
        System.out.println(((Map)((List<Object>)map.get("data")).get(0)).get("fname") + "\n");

        ListEmp listEmp = gson.fromJson(response, ListEmp.class);
        System.out.println(listEmp.data.get(0).getFname());
    }
}
