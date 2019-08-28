package com.xiaofangfang.filterrice.Fragment;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.xiaofangfang.filterrice.R;
import com.xiaofangfang.filterrice.model.ImageData;
import com.xiaofangfang.filterrice.tool.ScreenDemision;

import java.util.List;

/**
 * banner部分的组件，用来实现动态的加载fragment
 */
public class BannerFragment extends Fragment {

    private List<ImageData> imageDatas;

    public BannerFragment() {
        super();
    }

    @SuppressLint("ValidFragment")
    public BannerFragment(List<ImageData> imageDatas) {
        super();
        this.imageDatas = imageDatas;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.bannerlist, container, false);


        initView(view);


        return view;
    }


    /**
     * 初始化里面的数据
     *
     * @param view
     */
    private void initView(ViewGroup view) {

        ViewFlipper viewFlipper = view.findViewById(R.id.bannerFlipper);
        ImageView imageView = null;
        Point point = ScreenDemision.getScreenDemision(getContext());
        PointF scale;
        for (int i = 0; i < imageDatas.size(); i++) {

            scale = ScreenDemision.calculationScaleRate(point.x,
                    imageDatas.get(i).getWidth());
            if (scale.x == 0) {
                //放大
                imageDatas.get(i).setHeight(
                        imageDatas.get(i).getHeight() * (int) scale.y);
            } else {
                //缩小
                imageDatas.get(i).setHeight(
                        imageDatas.get(i).getHeight() / (int) scale.y);
            }
        }

        float totalHeight = ScreenDemision.getTotalDemision(imageDatas);
        if (totalHeight > point.y / 3) {
            //代表的是图片太大了
            totalHeight = point.y / 3;
        }
        ViewGroup.MarginLayoutParams mlap =
                new ViewGroup.MarginLayoutParams(-1, (int) totalHeight);

        viewFlipper.setLayoutParams(mlap);


        for (ImageData imgData : imageDatas) {
            imageView = new ImageView(getActivity());
            ViewGroup.MarginLayoutParams mlp = new
                    ViewGroup.MarginLayoutParams(-1, (int) totalHeight);
            imageView.setLayoutParams(mlp);
            imageView.setScaleType(ImageView.ScaleType.CENTER);
            imageView.setBackgroundResource(imgData.getUrl());
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(4000);
        viewFlipper.startFlipping();

    }
}
