package com.example.momomusic.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.momomusic.R;
import com.example.momomusic.activity.ParentActivity;

import androidx.annotation.DrawableRes;


/**
 * 自定义的toolbar ，是一个通用的View 实现的是
 */
public class CommandBar extends PercentLinearLayout {


    public CommandBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private View view;
    private ImageButton ibtn;
    private TextView barTitle;
    public ImageButton backupPage;

    private void init() {

        this.setOrientation(LinearLayout.VERTICAL);

        view = LayoutInflater.from(getContext()).inflate(R.layout.view_commond_head_bar, this, true);
        backupPage = view.findViewById(R.id.backupPage);
        backupPage.setOnClickListener((v) -> {
            ((ParentActivity) getContext()).finish();
        });
        ibtn = view.findViewById(R.id.menu);
        barTitle = view.findViewById(R.id.barTitle);
    }

    public void hiddenBack() {
        findViewById(R.id.backupPage).setVisibility(INVISIBLE);
    }


    public void setMenu(@DrawableRes int icon, MenuClickListener mcl) {
        ibtn.setVisibility(View.VISIBLE);
        ibtn.setBackgroundResource(icon);
        ibtn.setOnClickListener((v) -> {
            mcl.click(v);
        });
    }

    public void setTitle(String title) {
        barTitle.setText(title);
    }


    /**
     * 如果设菜单，设置的菜单点击过后在执行的操作的回调
     */
    public static interface MenuClickListener {
        void click(View view);
    }

}
