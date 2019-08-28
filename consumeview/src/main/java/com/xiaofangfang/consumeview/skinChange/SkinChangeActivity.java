package com.xiaofangfang.consumeview.skinChange;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.xiaofangfang.consumeview.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.IntentCompat;


/**
 * 简单的实现换肤的效果
 * <p>
 * <p>
 * <p>
 * 对于系统来说,定义了一堆的theme
 * 我么可以直接使用这些主题
 * <p>
 * 可以进行定制   {@link #setTheme(int)}必须在{@link #setContentView(View)}之前执行
 * <p>
 * <p>
 * 可以使用第三方的框架
 */
public class SkinChangeActivity extends BaseActivity {

    /**
     * 常用的主题资源
     * •android:theme="@android:style/Theme.Dialog"将一个Activity显示为能话框模式
     * •android:theme="@android:style/Theme.NoTitleBar"不显示应用程序标题栏
     * •android:theme="@android:style/Theme.NoTitleBar.Fullscreen"不显示应用程序标题栏，并全屏
     * •android:theme="Theme.Light"背景为白色
     * •android:theme="Theme.Light.NoTitleBar"白色背景并无标题栏
     * •android:theme="Theme.Light.NoTitleBar.Fullscreen"白色背景，无标题栏，全屏
     * •android:theme="Theme.Black"背景黑色
     * •android:theme="Theme.Black.NoTitleBar"黑色背景并无标题栏
     * •android:theme="Theme.Black.NoTitleBar.Fullscreen"黑色背景，无标题栏，全屏
     * •android:theme="Theme.Wallpaper"用系统桌面为应用程序背景
     * •android:theme="Theme.Wallpaper.NoTitleBar"用系统桌面为应用程序背景，且无标题栏
     * •android:theme="Theme.Wallpaper.NoTitleBar.Fullscreen"用系统桌面为应用程序背景，无标题栏，全屏
     * •android:theme="Translucent"背景为透明
     * •android:theme="Theme.Translucent.NoTitleBar"透明背景并无标题栏
     * •android:theme="Theme.Translucent.NoTitleBar.Fullscreen"透明背景并无标题栏，全屏
     * •android:theme="Theme.Panel"内容容器
     * •android:theme="Theme.Light.Panel"背景为白色的内容容器
     */

    private SharedPreferences.Editor editor;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        editor = sp.edit();
        setContentView(R.layout.activity_skin_change);
    }


    /**
     * 点击发现,结果是一样的,都没有更改,可能需要一些其他 的定义
     *
     * @param view
     */
    public void click(View view) {

        switch (view.getId()) {
            case R.id.btn1:
                editor.putInt("theme", R.style.appThemeDemo);
                break;
            case R.id.btn2:
                editor.putInt("theme", R.style.appThemeDemo1);
                break;
            case R.id.btn3:
                editor.putInt("theme", R.style.appThemeDemo2);
                break;

            case R.id.btn4:
                editor.putInt("theme", R.style.appThemeDemo3);
                break;
            case R.id.btn5:
                editor.putInt("theme", R.style.appThemeDemo4);
                break;
            case R.id.btn6:
                editor.putInt("theme", R.style.appThemeDemo5);
                break;
            case R.id.btn7:
                editor.putInt("theme", R.style.appThemeDemo6);
                break;
        }

        editor.commit();

        Intent intent = new Intent(SkinChangeActivity.this, SkinChangeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(0, 0);//传递0代表的是没有切换动画效果

//        onRestart();//不推荐的使用方式

    }

}
