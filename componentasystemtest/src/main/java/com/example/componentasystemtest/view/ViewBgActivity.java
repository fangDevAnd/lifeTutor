package com.example.componentasystemtest.view;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class ViewBgActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.view_bg);


        getResources().getConfiguration();


        Button btn = new androidx.appcompat.widget.AppCompatButton(this) {


            @Override
            public boolean onTouchEvent(MotionEvent event) {

                return false;
            }
        };
        setContentView(btn);
        btn.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    Log.i("test", "监听器的onTonch方法被调用");
                }
                return false;
            }
        });
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d("test", "dispatchTouchEvent: ");
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Log.d("test", "onTouchEvent: ");

        return true;
    }
}
