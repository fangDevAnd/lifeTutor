package com.example.momomusic.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TableRow;
import android.widget.Toast;

import com.example.momomusic.R;
import com.example.momomusic.activity.ui.MusicPlayView;
import com.example.momomusic.precenter.MusicPlayPresenter;
import com.example.momomusic.view.HotCommentList;
import com.example.momomusic.view.MeasureProgressReleativeLayout;
import com.example.momomusic.view.MyLinearLayout;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

public class MusicPlayFragment extends BaseFragment<MusicPlayView, MusicPlayPresenter> {


    /**
     * 进入这个界面必须设置的播放源
     */
    public static final String SOURCE = "source";

    

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_music_play, null);
        ButterKnife.bind(this, view);
        return view;
    }


    @BindView(R.id.seekbar)
    SeekBar seekBar;

    @BindView(R.id.commentList)
    HotCommentList commentList;

    @BindView(R.id.scrollView)
    NestedScrollView scrollView;

    @BindView(R.id.tuijian)
    FrameLayout tuijian;

    @BindView(R.id.bofangmenu)
    MeasureProgressReleativeLayout bofangmenu;

    @BindView(R.id.bofang)
    ImageButton bofang;

    @BindView(R.id.xiayiqu)
    ImageButton xiayiqu;


    private int height;

    private boolean isShow = false;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        bofangmenu.setViewInflater(new MyLinearLayout.ViewInflater() {
            @Override
            public void viewInflating(View view) {
                if (view.getId() == R.id.bofangmenu) {
                    //设置播放菜单不可见
                    height = (int) bofangmenu.getMeasuredHeight();
                    Logger.d("高度" + height);
                    bofangmenu.setTranslationY(height);
                }
            }

            @Override
            public void viewInflaterComplete() {

            }
        });


        //这里设置onTouch时间的作用是避免事件向内进行分发
        bofangmenu.setOnTouchListener((v, event) -> true);

        /**
         * 这里我们分析是这儿样的
         *
         *上面我们设置了父布局进行事件的处理，不在进行事件的下一步传递
         * 但在下面的子控件中可以接收到点击事件
         * 原因：调用流程viewgroup默认都是不去拦截事件的，所以会被分发给下面的子控件进行处理，子控件设置点击事件进行事件的处理
         *     如果事件处理函数函数true 就不会调用bofangmenu的ontouch方法 ，但我们知道，view默认都是处理事件的，所以我们猜测上边的onTouch方法不会执行
         *     测试了一下，发现是正确的
         *
         *
         */
        xiayiqu.setOnClickListener((v) -> {
            Toast.makeText(getContext(), "下一曲", Toast.LENGTH_SHORT).show();
        });

        bofang.setOnClickListener((v) -> {
            Toast.makeText(getContext(), "播放", Toast.LENGTH_SHORT).show();
        });


        seekBar.setOnTouchListener((v, event) -> true);//禁用触摸事件


        scrollView.setOnScrollChangeListener((NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) -> {

            Logger.d("当前的scrollY");

            if (scrollY - oldScrollY > 0) {//上滑动

                if (scrollY > tuijian.getTop()) {
                    //动态的设置bofangmenu的显示和隐藏
                    if (!isShow) {//!false    显示
                        isShow = !isShow;//true
                        ObjectAnimator obj = ObjectAnimator.ofFloat(bofangmenu, "translationY", 0);
                        obj.setDuration(200);
                        obj.start();
                    }
                }
            } else {//下滑动

                if (scrollY < tuijian.getTop()) {
                    if (isShow) {
                        isShow = !isShow;//true
                        ObjectAnimator obj = ObjectAnimator.ofFloat(bofangmenu, "translationY", height);
                        obj.setDuration(200);
                        obj.start();
                    }
                }
            }
            Logger.d(scrollY + "\t" + tuijian.getTop());
        });

        commentList.initView(null);
    }

    @Override
    public MusicPlayPresenter createPresenter() {
        return null;
    }

    @Override
    public MusicPlayView createView() {
        return null;
    }

    @Override
    protected void loadData() {
        Logger.d("当前可见的fargment" + this.getClassName());

    }

    @Override
    public void onError(IOException e, String what) {

    }

    @Override
    public void onSucess(Response response, String what, String... backData) throws IOException {

    }

    @Override
    public Class getClassName() {
        return null;
    }
}
