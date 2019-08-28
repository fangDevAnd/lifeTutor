package com.example.ndk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    TextView textView;


    public native void getView(int layout);


    static {
        System.loadLibrary("ArraySum");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        Log.d("test", JNITest.get());


        textView = findViewById(R.id.textView);
//        textView.setText(new JNITest().hello());


//        new NativeLogUtil().logD();
//        new NativeLogUtil().logW();


        int[] value = new int[10];
        for (int i = 0; i < 10; i++) {
            value[i] = (i + 1);
        }


//        textView.setText("算计的结果是=" + ArraySum.getSum(value));

//        textView.setText("算计的结果是=" + ArraySum.getSum2(value));

//        int[][] value1 = ArraySum.get2DArraySum(3);
//
//
//        int result = 0;
//        for (int i = 0; i < value1.length; i++) {
//
//            for (int j = 0; j < value1[i].length; j++) {
//
//                result += value1[i][j];
//            }
//        }
//
//        textView.setText("计算的结果是=" + result);


//        int[][] arraySum = ArraySum.get2DArraySum2(3);
//
//        for (int i = 0; i < arraySum.length; i++) {
//
//            for (int j = 0; j < arraySum[i].length; j++) {
//
//                Log.d("test","获得数据=" + arraySum[i][j]);
//
//            }
//
//        }




    }
}
