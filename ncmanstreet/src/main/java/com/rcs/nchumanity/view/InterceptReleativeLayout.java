package com.rcs.nchumanity.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.rcs.nchumanity.R;
import com.rcs.nchumanity.tool.DensityConvertUtil;
import com.rcs.nchumanity.tool.Tool;

/**
 * 事件拦截的ReleativeLayout
 * <p>
 * <p>
 * 该view不能作为通用的布局，实现的功能是Map页面的 事件拦截相关的处理
 */
public class InterceptReleativeLayout extends RelativeLayout {


    /**
     * 定位列表的LIstView
     */
    private ListView pointListView;


    /**
     * View的状态
     */
    enum ViewStatus {
        INIT,//初始化状态
        FULL_SCREEN,//全屏状态
        CUTTLE//折叠状态
    }

    /**
     * 触发折叠的高度
     */
    private static int triggerCuttleHeight;


    /**
     * 高度的偏移量
     */
    public static final int OFFSET_HEIGHT = 50;


    /**
     * 初始化基本的高度
     */

    private void initScreenDimension() {
        int[] screenDimensionArray = Tool.getScreenDimension(getContext());
        triggerCuttleHeight = screenDimensionArray[1] / 2 - DensityConvertUtil.dpi2px(getContext(), OFFSET_HEIGHT);
    }


    /**
     * 初始化ListView的状态
     * 默认为INIT状态
     */
    private ViewStatus viewStatus = ViewStatus.INIT;


    public InterceptReleativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        pointListView = findViewById(R.id.searchResult);

        initScreenDimension();

        pointListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            /**
             * 滚动的布局
             * @param view
             * @param firstVisibleItem
             * @param visibleItemCount
             * @param totalItemCount
             */
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });


    }

    /**
     * 开始拦截的Y
     * 移动中的Y
     */
    private int startInterY, moveInterY;

    /**
     * 事件消费的y和移动中的Y
     */
    private int startY, moveY;


    private boolean isIntercept = false;

    /**
     * @param ev
     * @return 事件拦截方法，规定事件的拦截
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        isIntercept = false;
        /**
         * 1.判断listview的高度，如果高度过低，就不起拦截 ，因为不会产生滑动
         */

        Log.d("test", "onInterceptTouchEvent: " + pointListView.getMeasuredHeight());

//        if (pointListView.getMeasuredHeight() < triggerCuttleHeight) {
//            /**
//             * 高度值过低，不去拦截事件
//             */
//            isIntercept = false;
//            return false;
//        }


        /**
         *
         * 2.通过相关的手势进行拦截
         */

        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:

                startInterY = (int) ev.getY();
                isIntercept = false;

                break;
            case MotionEvent.ACTION_MOVE:

//                Log.d("test", "onInterceptTouchEvent: list滚动距离=" + getListScrollY());
                int scrollY = getListScrollY();

                moveInterY = (int) ev.getY();

                int offsetY = moveInterY - startInterY;

                if (offsetY < 0) {
                    //view向上滑动
                    if (scrollY == 0 && viewStatus == ViewStatus.INIT) {

                        /**
                         * 代表的是滚动的距离为0  向上滑动 外部拦截 实现对listView的移动   实现 全屏状态
                         */

                        if (pointListView.getMeasuredHeight() < triggerCuttleHeight) {
                            isIntercept = false;
                        } else {
                            isIntercept = true;
                        }

                    } else if (scrollY == 0 && viewStatus == ViewStatus.CUTTLE) {

                        /**
                         * 代表的是滚动的距离为哦 向上滑动  外部拦截 实现对ListView的移动  实现 init效果
                         */

                        isIntercept = true;
                    }


                } else {
                    //view向下滑动
                    if (scrollY == 0 && viewStatus == ViewStatus.FULL_SCREEN) {

                        /**
                         * 代表的滚动的距离为0 向下滑动 外部拦截   实现对listview的移动  实现  初始化状态
                         *
                         */


                        isIntercept = true;
                    } else if (scrollY == 0 && viewStatus == ViewStatus.INIT) {

                        /**
                         * 代表的滚动的距离为0 向下滑动 外部拦截   实现对listview的移动  实现   折叠效果
                         */

                        isIntercept = true;
                    }

                }

                break;
            case MotionEvent.ACTION_UP:

                isIntercept = false;
                break;
        }


        return isIntercept;
    }


    /**
     * 获得列表滚动的y的偏移量
     *
     * @return
     */
    public int getListScrollY() {
        View c = pointListView.getChildAt(0);
        if (c == null) {
            return 0;
        }
        int firstVisiblePosition = pointListView.getFirstVisiblePosition();
        int top = c.getTop();
        return -top + firstVisiblePosition * c.getHeight();
    }

    /**
     * 代表的是是否向上滑动
     */
    private boolean up = false;


    /**
     * 计算每一次的移动的Y值的总和
     */
    private int totalMoveY;

    /**
     * 事件处理，默认消费当前的事件
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {


        if (isIntercept) {

            Log.d("test", "onTouchEvent: ==外部拦截事件");

            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:

                    startY = (int) event.getY();

                    break;

                case MotionEvent.ACTION_MOVE:

                    moveY = (int) event.getY();

                    if (startY == 0) {
                        return true;
                    }

                    int offsetY = moveY - startY;


                    Log.d("test", "移动偏移量: " + startY + "\t" + moveY);

                    totalMoveY += offsetY;

                    if (offsetY < 0) {
                        //view向上滑动
                        up = true;

                    } else {
                        //view向下滑动
                        up = false;
                    }


                    pointListView.layout(
                            pointListView.getLeft(),
                            pointListView.getTop() + offsetY,
                            pointListView.getRight(),
                            pointListView.getBottom() + offsetY);

                    startY = moveY;

                    break;

                case MotionEvent.ACTION_UP:

                    startY = 0;
                    moveY = 0;

                    /**
                     * 进行一些动画的实现，
                     * 完成之后设置viewStatus
                     *
                     *
                     */

                    if (up) {
                        //上滑动

                        if (viewStatus == ViewStatus.INIT) {


                        } else if (viewStatus == ViewStatus.CUTTLE) {


                        }


                    } else {
                        //下滑动
                        if (viewStatus == ViewStatus.FULL_SCREEN) {


                        } else if (viewStatus == ViewStatus.INIT) {


                        }

                    }


                    break;
            }
        }
        return true;
    }
}
