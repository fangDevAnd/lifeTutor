package com.xiaofangfang.consumeview.Toolbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaofangfang.consumeview.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Toolbar extends RelativeLayout implements View.OnClickListener {
    public Toolbar(Context context) {
        this(context, null);
    }

    public Toolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Toolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    /*
       <attr name="title" format="string"></attr>
        <attr name="titleTextSize" format="dimension"></attr>    dimension对应的类型为float
        <attr name="titleTextColor" format="color"></attr>
        <attr name="leftTextColor" format="color"></attr>
        <attr name="leftBackground" format="color|reference"></attr>
        <attr name="leftText" format="string"></attr>
        <attr name="rightTextColor" format="color"></attr>               int值
        <attr name="rightBackground" format="reference|color"></attr>    drawable,原因是color也可以是drawable   colorDrawable
        <attr name="rightText" format="string"></attr>
     */

    private String title, leftText, rightText;

    private float titleTextSize;

    private int titleTextColor, rightTextColor, leftTextColor;

    private Drawable leftBackground, rightBackground;

    private Button leftButton, rightButton;

    private TextView titleText;

    private int defColor=Color.parseColor("#111111");



    public enum ButtonPosition {
        LEFT, RIGHT
    }


    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {


        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.TopBar);

        title = ta.getString(R.styleable.TopBar_title);
        leftText = ta.getString(R.styleable.TopBar_leftText);
        rightText = ta.getString(R.styleable.TopBar_rightText);

        titleTextSize = ta.getDimension(R.styleable.TopBar_titleTextSize, 10);

        titleTextColor = ta.getColor(R.styleable.TopBar_titleTextColor, defColor);

        leftTextColor = ta.getColor(R.styleable.TopBar_leftTextColor,defColor );

        rightTextColor = ta.getColor(R.styleable.TopBar_rightTextColor,defColor);

        leftBackground = ta.getDrawable(R.styleable.TopBar_leftBackground);

        rightBackground = ta.getDrawable(R.styleable.TopBar_rightBackground);

        ta.recycle();//这句一定要执行


        leftButton = new Button(context);
        rightButton = new Button(context);
        titleText = new TextView(context);

        leftButton.setText(leftText);
        leftButton.setTextColor(leftTextColor);
        leftButton.setBackground(leftBackground);

        rightButton.setText(rightText);
        rightButton.setTextColor(rightTextColor);
        rightButton.setBackground(rightBackground);


        titleText.setText(title);
        titleText.setTextColor(titleTextColor);
        titleText.setTextSize(titleTextSize);
        titleText.setGravity(Gravity.CENTER);

        LayoutParams leftLayout = new LayoutParams(-2, -1);
        leftLayout.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

        addView(leftButton, leftLayout);//代表的是wrap_content

        LayoutParams rightLayout = new LayoutParams(-2, -1);
        rightLayout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        addView(rightButton, rightLayout);//代表的是wrap_content


        LayoutParams centerLayout = new LayoutParams(-2, -1);
        centerLayout.addRule(RelativeLayout.CENTER_IN_PARENT);
        addView(titleText, centerLayout);

        leftButton.setOnClickListener(this);
        rightButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (topBarClickListener == null) {
            return;
        }

        if (v == leftButton) {
            topBarClickListener.leftClick(v);
            return;
        }
        if (v == rightButton) {
            topBarClickListener.rightClick(v);
        }
    }


    private TopBarClickListener topBarClickListener;


    public void setTopBarClickListener(TopBarClickListener topBarClickListener) {
        this.topBarClickListener = topBarClickListener;
    }


    interface TopBarClickListener {

        void leftClick(View view);

        void rightClick(View view);
    }

    public void setButtonVisible(@NonNull ButtonPosition buttonVisible, boolean visitable) {

        switch (buttonVisible) {
            case LEFT:
                if (visitable == true) {
                    leftButton.setVisibility(VISIBLE);
                } else {
                    leftButton.setVisibility(INVISIBLE);
                }
                break;
            case RIGHT:
                if (visitable == true) {
                    rightButton.setVisibility(VISIBLE);
                } else {
                    rightButton.setVisibility(INVISIBLE);
                }
                break;
        }
    }
}
