package com.xiaofangfang.filterrice.consumeView;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xiaofangfang.filterrice.R;
import com.xiaofangfang.filterrice.model.ImageData;
import com.xiaofangfang.filterrice.tool.ScreenDemision;

import java.util.ArrayList;
import java.util.List;

/**
 * 轮播图的图片视图的显示
 */
public class BannerImgViewContainer extends HorizontalScrollView {

    /**
     * 存放当前img的地址信息
     */
    private List<ImageData> imageDataList = new ArrayList<>();

    /**
     * 默认的构造方法，系统通过这个进行数据的构造
     *
     * @param context
     * @param imageDataList
     */
    public BannerImgViewContainer(Context context, List<ImageData> imageDataList) {
        super(context);
        this.context = context;
        this.imageDataList = imageDataList;
        initView();
    }

    public BannerImgViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private Context context;

    public BannerImgViewContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 初始化数据
     * 在这里动态的创建一些image
     * 因为我们回传的图片的大小是没有定义的，所以我们需要动态的计算图片的最大值，然后计算
     * 得到水平view的
     */
    private void initView() {

        this.setSmoothScrollingEnabled(true);

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        MarginLayoutParams mlp = new MarginLayoutParams(-1, -2);
        this.addView(linearLayout, mlp);


        //计算image的缩放，从新根据屏幕带下设置宽高
        Point screenDemision = ScreenDemision.getScreenDemision(context);
        for (ImageData imgData : imageDataList) {
            PointF rate = ScreenDemision.calculationScaleRate(screenDemision.x,
                    imgData.getWidth());
            Point point;
            point = ScreenDemision.startScale(
                    point = new Point(imgData.getWidth(), imgData.getHeight()), rate);
            imgData.setHeight(point.y);
            imgData.setWidth(point.x);
        }

        float totalHeight = ScreenDemision.getTotalDemision(imageDataList);
        if (totalHeight >= screenDemision.y / 3) {
            totalHeight = screenDemision.y / 3;
        }

        //动态创建image
        for (int i = 0; i < imageDataList.size(); i++) {

            ImageView imgView = new ImageView(context);
            MarginLayoutParams mlp1 = new MarginLayoutParams(
                    screenDemision.x, (int) totalHeight);
            imgView.setLayoutParams(mlp1);
            //设置缩放类型，放在中间
            imgView.setScaleType(ImageView.ScaleType.CENTER);
            //设置图片的url。之后使用gradle来实现动态加载
            imgView.setImageResource(imageDataList.get(i).getUrl());

            linearLayout.addView(imgView, mlp1);
        }


    }

    /**
     * 在这里面动态的添加数据，也就是动态的设置图片数据
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
