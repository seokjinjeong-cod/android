package com.example.mydiary;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DiaryDAO {
    DBHelper dbHelper;

    //목록조회
    public static ArrayList<DiaryVO> selectAll (DBHelper dbHelper) {
        ArrayList<DiaryVO> list = new ArrayList<DiaryVO>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select _id, title, content, time, img from diary order by _id desc";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            DiaryVO vo = new DiaryVO();
            vo.set_id(cursor.getString(0));
            vo.setTitle(cursor.getString(1));
            vo.setContent(cursor.getString(2));
            vo.setTime(cursor.getString(3));
            vo.setImg(cursor.getString(4));
            list.add(vo);
        }
        db.close();
        return list;
    }

    //등록
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void insert(DBHelper dbHelper, DiaryVO vo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("title", vo.getTitle());
        contentValues.put("content", vo.getContent());
        if(vo.getImg() != null) {
            contentValues.put("img", vo.getImg());
        }

        //현재시간 설정
        LocalDate dt = LocalDate.now();
        String sdt = dt.format(DateTimeFormatter.ISO_DATE);
        contentValues.put("time", sdt);


        db.insert("diary", null, contentValues);
        db.close();
    }
    
    //삭제
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void delete(DBHelper dbHelper, DiaryVO vo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String id = vo.get_id();
        Log.d("alert", id);
        db.delete("diary", "_id=?", new String[]{id});
        db.close();
    }

    //수정
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void update(DBHelper dbHelper, DiaryVO vo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String id = vo.get_id();

        ContentValues contentValues = new ContentValues();
        contentValues.put("title", vo.getTitle());
        contentValues.put("content", vo.getContent());
        contentValues.put("img", vo.getImg());

        //현재시간 설정
        LocalDate dt = LocalDate.now();
        String sdt = dt.format(DateTimeFormatter.ISO_DATE);
        contentValues.put("time", sdt);

        db.update("diary", contentValues, "_id=?", new String[]{id});
        db.close();
    }
}
