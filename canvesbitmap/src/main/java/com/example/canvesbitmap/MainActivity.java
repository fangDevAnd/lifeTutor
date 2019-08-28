package com.example.canvesbitmap;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {


    private ImageView img;

    private ImageView img1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = findViewById(R.id.img);


        Bitmap pic = BitmapFactory.decodeResource(
                getResources(),
                R.drawable.certificates).copy(Bitmap.Config.ARGB_8888,
                true);

        MapDraw mapDraw = new MapDraw(this);

        Bitmap just = BitmapFactory.decodeResource(getResources(),
                R.drawable.just).copy(Bitmap.Config.ARGB_8888,
                true);

        Bitmap bitmap = mapDraw.drawJustBitmap(just,
                new MapDraw.MyUserInfo(pic, "男", "方志月", "342423199707272577", "fsasasrqwe32fasr", "二O一九年十二月十二号"));
        img.setImageBitmap(bitmap);


    }
}
