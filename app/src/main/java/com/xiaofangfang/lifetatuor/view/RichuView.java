package com.xiaofangfang.lifetatuor.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.xiaofangfang.lifetatuor.Activity.fragment.weather.Fragment.weatherInfoRichuViewPagerFragment.RichuPagerFragment;

import java.util.ArrayList;
import java.util.List;

public class RichuView extends View {

    /**
     * 是否是更新当前的路径
     */
    private boolean updatePath;
    private RichuPagerFragment.DaySunTime daySunTime;

    public RichuView(Context context) {
        this(context, null);
    }

    public RichuView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RichuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //绘制虚线
    Paint linePaint;
    //绘制太阳
    Paint boildLine;
    //绘制开始点和结束点
    Paint endAndStartPoint;
    //线条的宽度
    int lineWidth = 10;
    //开始点
    Point startPoint;
    //开始点的半径
    int pointCircleRadius = 10;
    //结束点的位置
    Point endPoint;
    /**
     * 起始点到文本的距离
     */
    int distance = 60;
    //sun的半径
    //太阳的半径
    int sunCircleRadius = 30;


    //下面是贝塞尔曲线相关的参数
    Point controllerPoint;
    //虚线路径
    Path path;
    //实线路径
    Path solidPath;


    private void init() {

        linePaint = new Paint();
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(Color.RED);
        linePaint.setDither(true);
        linePaint.setStrokeWidth(7);
        linePaint.setTextSize(30);
        linePaint.setAntiAlias(true);
        //设置虚线
        linePaint.setPathEffect(new DashPathEffect(new float[]{20, 10}, 0));

        boildLine = new Paint();
        boildLine.setAntiAlias(true);
        boildLine.setDither(true);
        boildLine.setStyle(Paint.Style.FILL);
        boildLine.setColor(Color.RED);

        path = new Path();


        endAndStartPoint = new Paint();
        endAndStartPoint.setStyle(Paint.Style.STROKE);
        endAndStartPoint.setColor(Color.RED);
        endAndStartPoint.setDither(true);
        endAndStartPoint.setTextSize(30);
        endAndStartPoint.setAntiAlias(true);

        solidPath = new Path();


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        startPoint = new Point(50, getMeasuredHeight() - 50);
        endPoint = new Point(getMeasuredWidth() - 50, getMeasuredHeight() - 50);
        controllerPoint = new Point(getMeasuredWidth() / 2, 0);
        if (vdl != null) {
            List<Point> points = new ArrayList<>();
            points.add(startPoint);
            points.add(controllerPoint);
            points.add(endPoint);
            vdl.getPointList(points);
        }

        solidPath.moveTo(startPoint.x - 10, startPoint.y);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.moveTo(startPoint.x - 10, startPoint.y);
        path.quadTo(controllerPoint.x, controllerPoint.y,
                endPoint.x, endPoint.y);
        //绘制起始点
        canvas.drawCircle(startPoint.x - 10, startPoint.y
                , pointCircleRadius, boildLine);
        canvas.drawPath(path, linePaint);
        canvas.drawCircle(endPoint.x - 10, endPoint.y,
                pointCircleRadius, boildLine);

//        if(updatePath){
//            canvas.drawPath(solidPath,boildLine);
//            updatePath=false;
//        }
        //*代表当的是传递了开始时间和结束时间
        if (daySunTime != null) {
            canvas.drawText(timeStart, startPoint.x + distance,
                    startPoint.y, endAndStartPoint);
            canvas.drawText(timeEnd, endPoint.x - distance - measureEnd,
                    endPoint.y, endAndStartPoint);
        }
    }

    /**
     * 该方法用来实现动态的绘制view的实线路径
     *
     * @param point 传递的路径
     * @return
     */
    public void updatePath(Point point) {
        updatePath = true;
        solidPath.moveTo(point.x, point.y);
        postInvalidate();
    }

    float measureStart;
    float measureEnd;
    String timeStart;
    String timeEnd;

    public void setDaySunTime(RichuPagerFragment.DaySunTime daySunTime) {
        this.daySunTime = daySunTime;
        timeEnd = "结束时间 " + daySunTime.luoTime;
        timeStart = "开始时间 " + daySunTime.shengTime;
        measureStart = linePaint.measureText(timeEnd);
        measureEnd = linePaint.measureText(timeStart);
    }

    public interface ViewDeminsionListener {
        /**
         * 获得点的位置,得到的位置是  起始位置    控制位置    结束位置
         *
         * @param points
         */
        void getPointList(List<Point> points);
    }

    private ViewDeminsionListener vdl;


    public ViewDeminsionListener getViewDeminsionListener() {
        return vdl;
    }

    public void setViewDeminsionListener(ViewDeminsionListener vdl) {
        this.vdl = vdl;
    }


}
