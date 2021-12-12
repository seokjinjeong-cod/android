package com.example.mydiary;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WriteActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 2;
    static final int REQUEST_PHOTO_SELECTION = 3;

    DBHelper dbHelper;
    DiaryDAO dao = new DiaryDAO();
    String id = "";
    Button btnImage, btnGal;
    ImageView imageDiary;
    String currentPhotoPath;
    Uri photoURI;
    File photoFile;
    Uri selectionImg;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        EditText editTitle = findViewById(R.id.editTitle);
        EditText editContent = findViewById(R.id.editContent);
        Button btnSave = findViewById(R.id.btnSave);
        btnImage = findViewById(R.id.btnImage);
        btnGal = findViewById(R.id.btnGal);
        imageDiary = findViewById(R.id.imageDiary);

        btnGal.setOnClickListener(v -> {
            getPhoto();
        });

        dispatchTakePictureIntent();

        btnImage.setOnClickListener(v -> {

            Intent intent = getIntent();
            if (intent.getExtras() != null) {
                String eTitle = intent.getExtras().getString("title");
                String eContent = intent.getExtras().getString("content");
                editTitle.setText(eTitle);
                id = intent.getExtras().getString("id");
                editContent.setText(eContent);
                if (intent.getExtras().getString("img") != null) {
                    currentPhotoPath = intent.getExtras().getString("img");
                    System.out.println("==================================" + currentPhotoPath);
                    imageDiary.setImageURI(Uri.parse(currentPhotoPath));
                }
            }

            dbHelper = new DBHelper(getApplicationContext());

            btnSave.setOnClickListener(v -> {
                DiaryVO diary = new DiaryVO();
                String title = editTitle.getText().toString();
                String content = editContent.getText().toString();
                if (id.equals("")) {
                    diary.setTitle(title);
                    diary.setContent(content);
                    diary.setImg(currentPhotoPath);
                    Log.d("alert", "등록등록등록등록등록등록등록등록등록등록등록");
                    dao.insert(dbHelper, diary);
                    Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                    setResult(RESULT_OK, intent1);
                } else {
                    diary.set_id(id);
                    diary.setTitle(title);
                    diary.setContent(content);
                    if (currentPhotoPath != null) {
                        if (selectionImg != null) {
                            System.out.println("selectionImg = " + String.valueOf(selectionImg));
                            diary.setImg(String.valueOf(selectionImg));
                        } else {
                            diary.setImg(currentPhotoPath);
                        }
                    }
                    Log.d("alert", "수정수정수정수정수정수정수정수정수정수정수정");
                    dao.update(dbHelper, diary);
                    Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);

                    setResult(RESULT_OK, intent1);
                }
                finish();
            });
        });
    }

    private void getPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_PHOTO_SELECTION);
    }

    //파일생성
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        System.out.println("==============================" + storageDir.getAbsolutePath());
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create the File where the photo should go
        photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            // Error occurred while creating the File

        }
        // Continue only if the File was successfully created
        if (photoFile != null) {
            photoURI = FileProvider.getUriForFile(this,
                    "com.example.mydiary",
                    photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
        }
    }
0
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageDiary.setImageBitmap(imageBitmap);
        } else if(requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            imageDiary.setImageURI(photoURI);
        } else if(requestCode == REQUEST_PHOTO_SELECTION && resultCode == RESULT_OK) {
            selectionImg = data.getData();
            imageDiary.setImageURI(selectionImg);
        }
    }
}