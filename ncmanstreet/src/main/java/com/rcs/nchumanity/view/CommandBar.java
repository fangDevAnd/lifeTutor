package com.rcs.nchumanity.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;

import com.bumptech.glide.Glide;
import com.rcs.nchumanity.R;
import com.rcs.nchumanity.ul.ParentActivity;


/**
 * 自定义的toolbar ，是一个通用的View
 */
public class CommandBar extends PercentLinearLayout {


    public CommandBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CommandBar);
        String text = ta.getString(R.styleable.CommandBar_title);
        if (text != null) {
            setTitle(text);
        }
        ta.recycle();  //注意回收
        
    }

    private View view;
    private ImageButton ibtn;
    private TextView barTitle;
    public ImageButton backupPage;
    RelativeLayout root;

    private void init() {

        this.setOrientation(LinearLayout.VERTICAL);

        view = LayoutInflater.from(getContext()).inflate(R.layout.view_commond_head_bar, this, true);
        backupPage = view.findViewById(R.id.backupPage);
        backupPage.setOnClickListener((v) -> {
            ((ParentActivity) getContext()).finish();
        });
        ibtn = view.findViewById(R.id.menu);
        barTitle = view.findViewById(R.id.barTitle);

        root = view.findViewById(R.id.root);

    }

    public void hiddenBack() {
        findViewById(R.id.backupPage).setVisibility(INVISIBLE);
    }


    public void setMenu(@DrawableRes int icon, MenuClickListener mcl) {
        ibtn.setVisibility(View.VISIBLE);
//        ibtn.setBackgroundResource(icon);
        Glide.with(getContext()).load(icon).into(ibtn);
        ibtn.setOnClickListener((v) -> {
            mcl.click(v);
        });
    }

    public void hideMenu() {
        ibtn.setVisibility(INVISIBLE);
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


    public void setBackGroundColor1(@ColorRes int colorId) {
        root.setBackgroundResource(colorId);
    }


}
