package com.xiaofangfang.lifetatuor.view.adapter;

import android.content.Context;
import android.graphics.Point;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.xiaofangfang.lifetatuor.R;
import com.xiaofangfang.lifetatuor.model.joke.ResultImg;
import com.xiaofangfang.lifetatuor.tools.Looger;

import java.util.List;

public class ImgListViewAdapter extends BaseAdapter {

    private Context context;
    private List<ResultImg> resultImgs;

    private static final String FLUSH_NAME = "刷新";

    public ImgListViewAdapter(Context context, List<ResultImg> resultImgs) {
        if (this.resultImgs != null) {
            //代表的是已经经历过赋值操作了,现在需要进行重置操作
            removeFlushView();
        }
        this.context = context;
        this.resultImgs = resultImgs;
        addFlushView();
    }

    /**
     * 增加一个结果到现在的视图中来
     *
     * @param resultImg
     */
    public void addResultImg(ResultImg resultImg) {
        removeFlushView();
        this.resultImgs.add(resultImg);
        addFlushView();
    }

    public void addListResultImgs(List<ResultImg> resultImgs) {
        //在增加一些数据的时候,我们需要移除刷新按钮,然后在进行添加
        if (this.resultImgs.size() >= 1) {
            removeFlushView();
        }
        this.resultImgs.addAll(resultImgs);
        addFlushView();
        notifyDataSetChanged();

    }

    /**
     * 清除所有的内容
     */
    public void removeListResultImgs() {
        //清除所有的内容
        this.resultImgs.clear();
    }

    /**
     * 移除一个刷新的界面显示
     */
    private void removeFlushView() {
        resultImgs.remove(resultImgs.size() - 1);
    }

    /**
     * 增加一个没有参数的结果集合
     */
    private void addFlushView() {
        ResultImg resultImg = new ResultImg();
        resultImg.setContent(FLUSH_NAME);
        resultImgs.add(resultImgs.size(), resultImg);
    }

    @Override
    public int getCount() {
        return resultImgs.size();
    }

    @Override
    public Object getItem(int position) {
        return resultImgs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ResultImg resultImg = resultImgs.get(position);
        ViewHolder viewHolder = null;
        if (resultImg.getContent().equals(FLUSH_NAME)) {
            //在这里我们不在使用复用的convertView,而是使用的是自定义的View
            ViewGroup linear = (ViewGroup) LayoutInflater.from(context)
                    .inflate(R.layout.listview_bottom_load_layout,
                            parent, false);
            ProgressBar pb = linear.findViewById(R.id.loadDataProgressbar);
            return linear;
        } else {
            if (convertView == null) {
                convertView = (ViewGroup) LayoutInflater.from(context)
                        .inflate(R.layout.hotimglistview_subview, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.fabulous = convertView.findViewById(R.id.hotImgListview_subview_fabulous);
                viewHolder.imageView = convertView.findViewById(R.id.hotImgListview_subview_img);
                viewHolder.stepOn = convertView.findViewById(R.id.hotImgListview_subview_stepon);
                //取消tag的设置直接设置为趣图
                //viewHolder.tag=convertView.findViewWithTag()
                viewHolder.title = convertView.findViewById(R.id.hotImgListview_subview_title);
                convertView.setTag(viewHolder);
            } else {
                if (convertView.getTag() == null) {
                    //此时这里是ImageButton的显示,我们需要重新加载视图,这里就是上面代码的翻版
                    convertView = LayoutInflater.from(context)
                            .inflate(R.layout.hotimglistview_subview, parent, false);
                    viewHolder = new ViewHolder();
                    viewHolder.fabulous = convertView.findViewById(R.id.hotImgListview_subview_fabulous);
                    viewHolder.imageView = convertView.findViewById(R.id.hotImgListview_subview_img);
                    viewHolder.stepOn = convertView.findViewById(R.id.hotImgListview_subview_stepon);
                    //取消tag的设置直接设置为趣图
                    //viewHolder.tag=convertView.findViewWithTag()
                    viewHolder.title = convertView.findViewById(R.id.hotImgListview_subview_title);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
            }

            viewHolder.title.setText(resultImg.getContent());
            //这里对图片的加载涉及到网络操作,所以我们还需要一个加载
            String imgType = resultImg.getUrl().substring(resultImg.getUrl().lastIndexOf(".") + 1, resultImg.getUrl().length());
            Looger.d("请求图片的url=" + resultImg.getUrl());
            if ("gif".equals(imgType)) {//按照gif的形式加载
                Glide.with(context).load(resultImg.getUrl()).asGif()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .placeholder(R.drawable.loading)
                        .dontAnimate()
                        .error(R.drawable.loaderror)
                        .into(viewHolder.imageView);

            } else if ("jpg".equals(imgType) || "png".equals(imgType)) {
                //按照普通图片的形式加载
                Glide.with(context).load(resultImg
                        .getUrl()).asBitmap()
                        .placeholder(R.drawable.loading)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .error(R.drawable.loaderror)
                        .dontAnimate()
                        .into(viewHolder.imageView);
            }
            //下面还有对赞以及踩的数据的实现

        }


        return convertView;
    }


    class ViewHolder {
        TextView title;
        ImageView imageView;
        TextView tag;
        TextView fabulous;
        TextView stepOn;
    }

    public static int ImageViewHeightdp = 300;

    /**
     * 获得宽高
     *
     * @return
     */
    private Point getImageViewDimension() {
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, ImageViewHeightdp, context.getResources().getDisplayMetrics());
        int width = context.getResources().getDisplayMetrics().widthPixels;
        return new Point(width, height);
    }

}
