package com.xiaofangfang.lifetatuor.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xiaofangfang.lifetatuor.R;
import com.xiaofangfang.lifetatuor.model.joke.ResultImg;
import com.xiaofangfang.lifetatuor.model.joke.parent.Result;

import java.util.List;

public class JokeListViewAdapter extends BaseAdapter {

    private Context context;
    private List<Result> resultJokes;


    public JokeListViewAdapter(Context context, List<Result> resultJokes) {
        if (this.resultJokes != null) {
            //代表的是已经经历过赋值操作了,现在需要进行重置操作
            removeFlushView();
        }
        this.context = context;
        this.resultJokes = resultJokes;
        addFlushView();

    }

    private static final String FLUSH_NAME = "刷新";

    /**
     * 增加一个结果到现在的视图中来
     *
     * @param result
     */
    public void addResult(Result result) {
        removeFlushView();
        this.resultJokes.add(result);
        addFlushView();
    }

    public void addListResult(List<Result> results) {
        //在增加一些数据的时候,我们需要移除刷新按钮,然后在进行添加
        if (resultJokes==null){
            return;
        }
        if (this.resultJokes.size() >= 1) {
            removeFlushView();
        }
        this.resultJokes.addAll(results);
        addFlushView();
        notifyDataSetChanged();

    }

    /**
     * 移除一个刷新的界面显示
     */
    private void removeFlushView() {
        resultJokes.remove(resultJokes.size() - 1);
    }

    /**
     * 增加一个没有参数的结果集合
     */
    private void addFlushView() {
        Result result = new Result();
        result.setContent(FLUSH_NAME);
        resultJokes.add(resultJokes.size(), result);
    }


    @Override
    public int getCount() {
        return resultJokes.size();
    }

    @Override
    public Object getItem(int position) {
        return resultJokes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Result result = resultJokes.get(position);
        ViewHolder viewHolder;
        if (result.getContent().equals(FLUSH_NAME)) {
            //在这里我们不在使用复用的convertView,而是使用的是自定义的View
            ViewGroup linear = (ViewGroup) LayoutInflater.from(context)
                    .inflate(R.layout.listview_bottom_load_layout,
                            parent, false);
            ProgressBar pb = linear.findViewById(R.id.loadDataProgressbar);
            return linear;
        } else {
            if (convertView == null) {
                convertView = LayoutInflater.from(context)
                        .inflate(R.layout.jokelist_subview, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.fabulous = convertView.findViewById(R.id.hotImgListview_subview_fabulous);
                viewHolder.stepOn = convertView.findViewById(R.id.hotImgListview_subview_stepon);
                //取消tag的设置直接设置为趣图
                //viewHolder.tag=convertView.findViewWithTag()
                viewHolder.context = convertView.findViewById(R.id.hotImgListview_subview_title);
                convertView.setTag(viewHolder);
            } else {
                if (convertView.getTag() == null) {
                    //此时这里是ImageButton的显示,我们需要重新加载视图,这里就是上面代码的翻版
                    convertView = LayoutInflater.from(context)
                            .inflate(R.layout.jokelist_subview, parent, false);
                    viewHolder = new ViewHolder();
                    viewHolder.fabulous = convertView.findViewById(R.id.hotImgListview_subview_fabulous);
                    viewHolder.stepOn = convertView.findViewById(R.id.hotImgListview_subview_stepon);
                    //取消tag的设置直接设置为趣图
                    //viewHolder.tag=convertView.findViewWithTag()
                    viewHolder.context = convertView.findViewById(R.id.hotImgListview_subview_title);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
            }
            viewHolder.context.setText(result.getContent());

            //viewHolder.stepOn.setText();
            // 这里使用的也是一个数据平台的直接数据接受
            //需要通过网络进行异步加载
            //viewHolder.fabulous.setText();
        }
        return convertView;
    }

    public void removeListResult() {
        //清除所有的内容
        this.resultJokes.clear();
    }

    class ViewHolder {
        TextView context;
        TextView fabulous;
        TextView stepOn;
    }

}
