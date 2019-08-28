package com.xiaofangfang.opensourceframeworkdemo.ui;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import com.xiaofangfang.opensourceframeworkdemo.R;

/**
 * 分类侧滑菜单
 */
public class SlideMenu extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SpannableString span = new SpannableString("红色打电话斜体删除线绿色下划线图片:.");
//1.设置背景色,setSpan时需要指定的flag, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE(前后都不包括)
        span.setSpan(new ForegroundColorSpan(Color.RED), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//2.用超链接标记文本
        span.setSpan(new URLSpan("tel:4155551212"), 2, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//3.用样式标记文本(斜体)
        span.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 5, 7, Spanned
                .SPAN_EXCLUSIVE_EXCLUSIVE);
//4.用删除线标记文本
        span.setSpan(new StrikethroughSpan(), 7, 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//5.用下划线标记文本
        span.setSpan(new UnderlineSpan(), 10, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//6.用颜色标记
        span.setSpan(new ForegroundColorSpan(Color.GREEN), 10, 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//7.//获取Drawable资源
        Drawable d = getResources().getDrawable(R.drawable.ic_launcher_background);
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight()
        );
//8.创建ImageSpan,然后用ImageSpan来替换文本
        ImageSpan imgspan = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
        span.setSpan(imgspan, 18, 19,
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        new TextView(this).setText(span);
    }
}
