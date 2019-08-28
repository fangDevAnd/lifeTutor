package com.rcs.nchumanity.ul.detail;


import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;

import com.rcs.nchumanity.R;
import com.rcs.nchumanity.entity.model.SpecificInfo;
import com.rcs.nchumanity.fragment.VideoPlayFragment;
import com.rcs.nchumanity.ul.BasicResponseProcessHandleActivity;
import com.rcs.nchumanity.ul.ParentActivity;

/**
 * 这个界面是用来实现复合列表的详细信息界面
 * <p>
 * 这个详情信息的界面包括视频服务的基本的封装
 * <p>
 * 选修课程详情信息表
 * <p>
 * 1.需要满足的功能有  1，程序基本数据的抽象
 * 2.对视频播放模块的抽象  ，暴露基本的操作
 * <p>
 * 这个类实现的封装是 信息发布的相关，以及选修课程的详情界面的显示
 * <p>
 * <p>
 * 当前类的子类都应该设置
 * android:configChanges="orientation|screenSize|keyboard|keyboardHidden"
 */
public abstract class ComplexDetailActivity<T> extends BasicResponseProcessHandleActivity {


    private View rootView;

    /**
     * 视频播放的fragment
     */
    protected VideoPlayFragment videoPlayFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(rootView = LayoutInflater.from(this).inflate(getViewLayoutId(), null));

        videoPlayFragment = (VideoPlayFragment) getSupportFragmentManager().findFragmentById(R.id.videoFragment);
    }

    /**
     * 获得view的id
     *
     * @return
     */
    protected abstract int getViewLayoutId();

    protected T info;

    protected void bundleData() {
        if (info == null) {
            throw new RuntimeException("bundle的数据为空");
        }
        bindView(rootView, info);
    }

    /**
     * 绑定View的数据
     *
     * @param view
     * @param t
     */
    protected abstract void bindView(View view, T t);


    /**
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d("test1", "onConfigurationChanged: ===----");
        videoPlayFragment.changeLayoutParams();
    }


}
