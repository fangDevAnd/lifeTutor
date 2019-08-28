package com.xiaofangfang.precentlayoutdemo.consumeBindView;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.xiaofangfang.precentlayoutdemo.R;


@Bind(bindContent = R.layout.activity_main)
public class MainActivity extends AppCompatActivity {


    @Bind(bindView = R.id.textView)
    private Button textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BindProgress.bindContentView(this);
        BindProgress.bindView(this);
        BindProgress.bindClick(this);


        textView.setText("asasfasg");
    }


    @Bind(onCLick = {
            R.id.textView
    })
    public void onClick(View view) {

        Log.d("test", "onClick:==== 发生点击");

    }


}
