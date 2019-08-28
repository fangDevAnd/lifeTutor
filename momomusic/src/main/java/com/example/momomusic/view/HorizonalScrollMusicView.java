package com.example.momomusic.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.momomusic.R;
import com.example.momomusic.tool.ResourceUtil;
import com.example.momomusic.tool.Tools;

import java.util.List;

import androidx.cardview.widget.CardView;
import de.hdodenhof.circleimageview.CircleImageView;

public class HorizonalScrollMusicView extends HorizontalScrollView {


    public HorizonalScrollMusicView(Context context) {
        this(context, null);
    }

    public HorizonalScrollMusicView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizonalScrollMusicView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        albumHeight = Tools.px2dp(albumHeight, context);
        initView();
    }


    private LinearLayout linearLayout;
    private int albumHeight = 400;
    private int margin = 40;

    private int row = 2;

    private int padding = 4;

    private void initView() {
        linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        this.addView(linearLayout, -1, -1);
    }

    private boolean isOdd = false;


    public void setDataSource(List<? extends Album> albums) {

//        if (albums.size() < 0) {
//            try {
//                throw new Exception("歌单的大小必须大于等于0");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return;
//        }

//        if (albums.size() % 2 != 0) {
//            isOdd = true;
//        }
//        int count = albums.size() % 2 == 0 ? albums.size() / 2 : albums.size() / 2 + 1;
        int count = 5;

        for (int i = 0; i < count; i++) {

//            Album album = albums.get(i);

            LinearLayout ly = new LinearLayout(getContext());
            LinearLayout.LayoutParams conParams = new LinearLayout.LayoutParams(-2, -2);
            conParams.leftMargin = margin;
            conParams.rightMargin = margin;
            ly.setLayoutParams(conParams);

            ly.setOrientation(LinearLayout.VERTICAL);
            ly.setPadding(5, 5, 5, 5);
            if (i == (count - 1) && isOdd) {
                row = 1;
            }
            for (int j = 0; j < row; j++) {

                //实现圆角的cardView
                CardView cardView = new CardView(getContext());
                cardView.setRadius(albumHeight / 2);
                cardView.setLayoutParams(new FrameLayout.LayoutParams(albumHeight, albumHeight));
                cardView.setCardElevation(0f);

                //用于阴影的button
                Button button = new Button(getContext());
                button.setAlpha(0.1f);
                button.setBackgroundResource(R.drawable.button_ripple_1);
                cardView.addView(button, -1, -1);

                //album的背景
                ImageView img = new ImageView(getContext());
                img.setImageResource(R.drawable.gedan4);
                cardView.addView(img, -1, -1);

                //动态的添加一个ImageView  也就是播放的显示
                ImageView play = new ImageView(getContext());
                Drawable drawable = getResources().getDrawable(R.drawable.ic_bofang3);
                drawable = Tools.tintDrawable(drawable, ColorStateList.valueOf(Color.WHITE));
                play.setImageDrawable(drawable);
                FrameLayout.LayoutParams playParams = new LayoutParams(-2, -2);
                playParams.gravity = Gravity.CENTER;
                cardView.addView(play, playParams);

                //标题
                TextView title = new TextView(getContext());
                title.setGravity(Gravity.CENTER_HORIZONTAL);
                title.setText("儿童");
                title.setEllipsize(TextUtils.TruncateAt.END);
                title.setPadding(0, padding, 0, padding);

                //描述
                TextView des = new TextView(getContext());
                des.setGravity(Gravity.CENTER_HORIZONTAL);
                des.setText("精彩彩说");
                des.setPadding(0, 0, 0, padding + padding);

                ly.addView(cardView);
                ly.addView(title, -1, -1);
                ly.addView(des, -1, -1);
            }
            linearLayout.addView(ly);
        }
    }


    public static class Album {


        private String imageAddress;

        private String id;//这个作为歌单的id备用

        private String title;

        private String description;

        public Album(String imageAddress, String id, String title, String description) {
            this.imageAddress = imageAddress;
            this.id = id;
            this.title = title;
            this.description = description;
        }

        public String getImageAddress() {
            return imageAddress;
        }

        public void setImageAddress(String imageAddress) {
            this.imageAddress = imageAddress;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

    }
}
