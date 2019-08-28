package com.example.momomusic.fragment.person;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.momomusic.R;
import com.example.momomusic.fragment.ParentFragment;
import com.example.momomusic.tool.Tools;
import com.example.momomusic.view.Adapter.MyFragmentPageAdapter;
import com.example.momomusic.view.InterceptScrollView;
import com.example.momomusic.view.InterceptViewPager;
import com.example.momomusic.view.MeasureProgressReleativeLayout;
import com.google.android.material.tabs.TabLayout;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Response;

public class PersonalIndexPageFragment extends ParentFragment {


    private static final String TAG = "test";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_index_page, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @BindView(R.id.viewPager)
    InterceptViewPager viewPager;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.scrollView)
    InterceptScrollView scrollView;

    @BindView(R.id.head)
    MeasureProgressReleativeLayout rl;

    @BindView(R.id.mengban)
    View mengban;

    @BindView(R.id.cardHead)
    RelativeLayout cardHead;


    private List<Fragment> fragments;

    private MyFragmentPageAdapter myPageAdapter;

    private int defaultSelectIndex = 0;


    private int headHeight;

    private String[] tabTitle;

    private int startY, moveY, tabHeight, totalOffsetY;

    private boolean isShow = false;

    /**
     * 这个界面的布局存在滑动冲突，所以需要解决滑动冲突的问题
     * <p>
     * scrollView 嵌套TabLayout+viewPager 嵌套ListView
     */

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        fragments = new ArrayList<>();
        fragments.add(new PersonalZLFragment());
        fragments.add(new PersonalXCFragment());//精选

        tabTitle = getResources().getStringArray(R.array.zhuye);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        /**
         * 记住，这里只能使用getChildFragmentManager()
         */
        myPageAdapter = new MyFragmentPageAdapter(getChildFragmentManager(), fragments);
        viewPager.setAdapter(myPageAdapter);
        viewPager.setCurrentItem(defaultSelectIndex);
        for (int i = 0; i < tabTitle.length; i++) {
            tabLayout.addTab(tabLayout.newTab());
        }


        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < tabTitle.length; i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setText(tabTitle[i]);
        }


        /**
         * 布局调整
         */
        ViewTreeObserver observer = view.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(() -> {

            tabHeight = tabLayout.getMeasuredHeight();
            headHeight = rl.getMeasuredHeight();

            Logger.d("onViewCreated: " + tabHeight + "\t" + headHeight);

            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) viewPager.getLayoutParams();
            lp.height = getResources().getDisplayMetrics().heightPixels - tabHeight - Tools.getStatusBarHeight(getContext());
            viewPager.setLayoutParams(lp);


            int height = cardHead.getMeasuredHeight();
            RelativeLayout.LayoutParams lp1 = (RelativeLayout.LayoutParams) mengban.getLayoutParams();
            lp1.height = height;
            mengban.setLayoutParams(lp1);

        });


        scrollView.setOnScrollChangeListener((NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) -> {

            if (scrollY - oldScrollY > 0) {//上滑动
                if (scrollY > 100) {
                    rl.setVisibility(View.VISIBLE);
                }

                if (scrollY > 300) {
                    //执行蒙版动画
                    if (!isShow) {//设置
                        isShow = !isShow;
                        startAnim(mengban, !isShow);
                    }
                }

            } else {//下滑动
                if (scrollY < 100) {
                    rl.setVisibility(View.INVISIBLE);
                }

                if (scrollY < 300) {
                    //执行蒙版动画
                    if (isShow) {//取消
                        isShow = !isShow;
                        startAnim(mengban, !isShow);
                    }
                }
            }
        });


        /**
         *
         * 下面进行处理事件拦截的方法并不能实现,原因是内部元素进行了拦截,我们调用不到ACTION_MOVE事件的分发,所以现在修改代码,尝试使用内部拦截的方法
         *
         *
         */
//        scrollView.setEventProgress((event) -> {
//            boolean interceped = false;
//            switch (event.getAction()) {
//                case MotionEvent.ACTION_DOWN:
//                    interceped = false;
//                    startY = (int) event.getY();
//                    break;
//                case MotionEvent.ACTION_MOVE:
//                    moveY = (int) event.getY();
//                    int scrollY = scrollView.getScrollY();
//                    if (moveY < startY) {//代表的是上滑动
//                        Log.d(TAG, "scrollY:" + scrollY + "\t" + tabLayout.getTop() + "\t" + headHeight);
//                        /**
//                         * 在这里就是去判断我们在什么时候不去触发自己的滑动，也就是scrollview的滑动 ，返回true，代表的是不去执行scroll.onTouchEvent()方法，也就不能进行滑动
//                         */
//                        if (scrollY < tabLayout.getTop() - headHeight) {
//                            Log.d(TAG, "需要拦截的滑动");
//                            interceped = true;
//                        } else {
//                            interceped = false;
//                        }
//                    }
//                    break;
//                case MotionEvent.ACTION_UP:
//                    interceped = false;//不能进行拦截,否则button的点击处理不能生效
//                    break;
//            }
//            return interceped;//事件不在向上进行传递
//        });


        /**
         *
         *
         * 依旧实现不了,实际的开发要比逻辑代码难度大很多,滑动冲突处依旧比较繁琐
         *
         *
         */
//        viewPager.setEventDispatch(new InterceptViewPager.EventDispatch() {
//            @Override
//            public void eventDispatch(MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        startY = (int) event.getRawY();
//                        scrollView.requestDisallowInterceptTouchEvent(true);
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        moveY = (int) event.getRawY();
//                        totalOffsetY += Math.abs(moveY - startY);
//
//                        Log.d(TAG, "eventDispatch: " + moveY);
//
//                        if (moveY < startY) {//代表的是上滑动
//                            Log.d(TAG, "totalOffsetY=" + totalOffsetY + "\t" + tabLayout.getTop() + "\t" + headHeight);
//                            /**
//                             * 在这里就是去判断我们在什么时候不去触发自己的滑动，也就是scrollview的滑动 ，返回true，代表的是不去执行scroll.onTouchEvent()方法，也就不能进行滑动
//                             */
//                            if (totalOffsetY < tabLayout.getTop() - headHeight) {
//                                Log.d(TAG, "交给父布局处理");
//                                scrollView.requestDisallowInterceptTouchEvent(false);
//                            } else {
//
//                            }
//                        }
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        break;
//                }
//
//            }
//        });
//
//
//        scrollView.setEventProgress(new InterceptScrollView.EventProgress() {
//            @Override
//            public boolean eventProgress(MotionEvent event) {
//                int action = event.getAction();
//                Log.d(TAG, "eventProgress: ");
//                return false;
//            }
//        });

    }


    /**
     * 执行透明度动画
     *
     * @param view
     * @param isShow 是不是显示蒙版
     */
    public void startAnim(View view, boolean isShow) {
        ObjectAnimator oa = null;
        if (isShow) {
            oa = ObjectAnimator.ofFloat(view, "alpha", 0f);
        } else {
            oa = ObjectAnimator.ofFloat(view, "alpha", 1.0f);
        }
        oa.setDuration(500);
        oa.start();
    }


    @Override
    protected void loadData() {

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
