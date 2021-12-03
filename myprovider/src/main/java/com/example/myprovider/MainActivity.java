package com.example.myprovider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements AutoPermissionsListener {

    Button btnCall, btnNumList, btnIns;
    EditText edtCall, txtPhone, txtEmail, txtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ActivityCompat.requestPermissions(this,
//                new String[]{Manifest.permission.READ_CALL_LOG,
//                             Manifest.permission.READ_CONTACTS}, MODE_PRIVATE);
        AutoPermissions.Companion.loadAllPermissions(this, 101);

        edtCall = findViewById(R.id.edtCall);
        btnCall = findViewById(R.id.btnCall);
        btnIns = findViewById(R.id.btnInsert);
        btnNumList = findViewById(R.id.button3);

        txtName = findViewById(R.id.txtName);
        txtPhone = findViewById(R.id.txtPhone);
        txtEmail = findViewById(R.id.txtEmail);

        btnCall.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                edtCall.setText(getCallHistory());
            }
        });

        btnNumList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                edtCall.setText(getContacts());
            }
        });

//        btnCall.setOnClickListener(v -> {
//
//        });

        btnIns.setOnClickListener(v -> { addContact(); });
    }

    public void addContact() {
        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, txtEmail.getText())
                .putExtra(ContactsContract.Intents.Insert.EMAIL_TYPE,
                        ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                .putExtra(ContactsContract.Intents.Insert.PHONE, txtPhone.getText())
                .putExtra(ContactsContract.Intents.Insert.PHONE_TYPE,
                        ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
                .putExtra(ContactsContract.Intents.Insert.NAME, txtName.getText());
        startActivity(intent);
    }

    public String getCallHistory() {
        String[] callSet = new String[]{CallLog.Calls.DATE,
                CallLog.Calls.TYPE, CallLog.Calls.NUMBER,
                CallLog.Calls.DURATION};                ///

        Cursor c = getContentResolver().query(CallLog.Calls.CONTENT_URI,
                callSet, null, null, null);     ///

        if (c == null)
            return "통화기록 없음";

        StringBuffer callBuff = new StringBuffer(); // 최대 100 통화 저장
        callBuff.append("\n날짜 : 구분 : 전화번호 : 통화시간\n\n");
        c.moveToFirst();
        do {
            long callDate = c.getLong(0);
            SimpleDateFormat datePattern = new SimpleDateFormat("yyyy-MM-dd");
            String date_str = datePattern.format(new Date(callDate));
            callBuff.append(date_str + ":");
            if (c.getInt(1) == CallLog.Calls.INCOMING_TYPE)
                callBuff.append("착신 :");
            else
                callBuff.append("발신 :");
            callBuff.append(c.getString(2) + ":");
            callBuff.append(c.getString(3) + "초\n");
        } while (c.moveToNext());

        c.close();
        return callBuff.toString();
    }

    public String getContacts() {
        String[] callSet =
                {
//                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
//                    ContactsContract.CommonDataKinds.Phone.NUMBER,        // 연락처
//                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                        ContactsContract.Contacts._ID,
                        ContactsContract.Contacts.LOOKUP_KEY,
                        Build.VERSION.SDK_INT
                                >= Build.VERSION_CODES.HONEYCOMB ?
                                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY :
                                ContactsContract.Contacts.DISPLAY_NAME

                };

        Cursor c = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI/*ContactsContract.CommonDataKinds.Phone.CONTENT_URI*/,
                callSet, null, null, null);

        if (c == null)
            return "통화기록 없음";

        StringBuffer callBuff = new StringBuffer(); // 최대 100 통화 저장
        callBuff.append("\n id : 검색키 : 이름\n\n");
        c.moveToFirst();
        do {
            callBuff.append(c.getString(0) + ":");
            callBuff.append(c.getString(1) + ":");
            callBuff.append(c.getString(2) + "\n");
        } while (c.moveToNext());

        c.close();
        return callBuff.toString();
    }

    @Override
    public void onDenied(int i, String[] strings) {
    }

    @Override
    public void onGranted(int i, String[] strings) {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AutoPermissions.Companion.parsePermissions(this, requestCode, permissions, this);
    }
}