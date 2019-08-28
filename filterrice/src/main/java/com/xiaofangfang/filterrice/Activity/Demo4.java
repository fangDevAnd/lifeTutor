package com.xiaofangfang.filterrice.Activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import com.xiaofangfang.filterrice.R;

import java.io.File;

public class Demo4 extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo4);


        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 1);


        File file = new File("/storage/emulated/0", "方志月.zip");

        if (file.exists()) {

            Log.d("test", "文件的大小" + file.length());

        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        if (resultCode == RESULT_OK) {

            Log.d("test", "得到的url=" + data.getData().toString());


            ContentResolver contentResolver = getContentResolver();
            Cursor cursor = contentResolver.query(data.getData(), null, null, null, null);
            String path = null;
            if (cursor != null) {
                cursor.moveToFirst();
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }


            Log.d("test", "得到的游标的大小=" + cursor.getCount());
            Log.d("test", "文件的路径=" + path);


        }


    }
}
