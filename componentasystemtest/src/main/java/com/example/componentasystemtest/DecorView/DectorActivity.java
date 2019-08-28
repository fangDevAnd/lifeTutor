package com.example.componentasystemtest.DecorView;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;

import com.example.componentasystemtest.R;


/**
 * Created by fang on 2018/6/10.
 */

public class DectorActivity extends AppCompatActivity {

    String tag = "test";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo13);

        demo1();

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    private void demo1() {
        ViewGroup dectorView = (ViewGroup) getWindow().getDecorView();
        Log.d(tag, "dectorView类型 =" + dectorView.getClass().getName());
        Log.d(tag, "dector的孩子节点数=" + dectorView.getChildCount());
        Log.d(tag, "dector的孩子的类型=" + dectorView.getChildAt(0).getClass().getName());

        for (int i = 0; i < ((ViewGroup) dectorView.getChildAt(0)).getChildCount(); i++) {
            Log.d(tag, "linearLayuot的孩子的类型=" + ((ViewGroup) dectorView.getChildAt(0)).getChildAt(i).getClass().getName());

        }


    }
}
