package com.xiaofangfang.consumeview.scrollHiddenToolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.xiaofangfang.consumeview.R;

public class ScrollHiddenToolbarActivity extends AppCompatActivity implements View.OnTouchListener {

    ListView listView;
    Toolbar toolbar;

    View viewHead;

    String[] value = {
            "爱上谁付钱", "爱上谁付钱", "爱上谁付钱", "爱上谁付钱", "爱上谁付钱", "爱上谁付钱", "爱上谁付钱", "爱上谁付钱", "爱上谁付钱",
            "爱上谁付钱", "爱上谁付钱", "爱上谁付钱", "爱上谁付钱", "爱上谁付钱", "爱上谁付钱", "爱上谁付钱", "爱上谁付钱",
            "爱上谁付钱", "爱上谁付钱", "爱上谁付钱", "爱上谁付钱", "爱上谁付钱", "爱上谁付钱", "爱上谁付钱", "爱上谁付钱",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_hidden_toolbar);
        toolbar = findViewById(R.id.toolbar);
        listView = findViewById(R.id.listView);


        viewHead = findViewById(R.id.viewHead);

        listView.setOnTouchListener(this);
        initView();
    }

    private void initView() {

        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return value.length;
            }

            @Override
            public Object getItem(int position) {
                return value[position];
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_1, null);
                TextView textView = view.findViewById(R.id.text1);
                textView.setText(value[position]);
                return view;
            }
        });

    }


    int startY;
    int moveY;

    boolean isShow = true;


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                startY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                moveY = (int) event.getY();
                int offsetY = moveY - startY;

                if (offsetY > 0) {//下滑.需要显示toolbar
                    if (!isShow) {
                        executeAnim(true);
                    }
                } else {//上滑动
                    if (isShow) {
                        executeAnim(false);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return false;
    }


    public void executeAnim(boolean hidden) {
        ObjectAnimator toolbarAnim = null;
        if (!hidden) {//显示
            toolbarAnim = ObjectAnimator.ofFloat(toolbar, "translationY", -toolbar.getHeight());//0   -40
        } else {
            toolbarAnim = ObjectAnimator.ofFloat(toolbar, "translationY", 0);//-40   0
        }
        toolbarAnim.setDuration(200);
        toolbarAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float value = (float) animation.getAnimatedValue();

                viewHead.setLayoutParams(new LinearLayout.LayoutParams(-1, toolbar.getHeight() + (int) value));
            }
        });
        toolbarAnim.start();
        isShow = !isShow;
    }


}
