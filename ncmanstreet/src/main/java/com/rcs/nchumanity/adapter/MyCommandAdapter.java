package com.rcs.nchumanity.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * 这是一个通用的RecyclerView的adapter
 *
 * @param <T>
 */
public abstract class MyCommandAdapter<T> extends RecyclerView.Adapter<MyCommandAdapter.MyViewHolder> {

    private ArrayList<T> data;
    private int resLayout;
    private Context context;
    MyViewHolder myViewHolder;

    public MyCommandAdapter(Context context, int resLayout, ArrayList<T> data) {
        this.context = context;
        this.resLayout = resLayout;
        this.data = data;
    }

    public abstract void bind(MyViewHolder holder, T t);


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(resLayout, parent, false);

        myViewHolder = new MyViewHolder<T>(view) {

            @Override
            public void bind(MyViewHolder holder, T t) {
                MyCommandAdapter.this.bind(holder, t);
            }
        };

        myViewHolder.view = view;

        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(holder, data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static abstract class MyViewHolder<T> extends RecyclerView.ViewHolder {

        View view = null;
        List<View> views = new ArrayList<>();

        public MyViewHolder(View itemView) {
            super(itemView);
        }

        public abstract void bind(MyViewHolder holder, T t);


        public void setText(int viewId, String text) {
            View viewText = this.view.findViewById(viewId);
            views.add(viewText);
            if (viewText instanceof TextView) {
                ((TextView) viewText).setText(text);
            }
        }

        /**
         * 设置图片的地址 可以设置为 integer或者是 String类型
         *
         * @param viewId
         * @param t
         * @param <T>
         */
        public <T extends Serializable> void setImageUrl(int viewId, T t) {

            View view1;

            if ((view1 = getCahceView(viewId)) == null) {
                view1 = view.findViewById(viewId);
            }

            if (view1 instanceof ImageView) {
                if (t instanceof Integer) {
                    int drawId = (Integer) t;
                    Glide.with(view.getContext()).load(drawId).into((ImageView) view1);
                } else if (t instanceof String) {
                    String url = (String) t;
                    Glide.with(view.getContext()).load(url).into((ImageView) view1);
                }
            }

        }


        /**
         * 获得缓存的view
         *
         * @param id
         * @return
         */
        private View getCahceView(int id) {
            View view = null;
            for (int i = 0; i < views.size(); i++) {
                if (views.get(i).getId() == id) {
                    view = views.get(i);
                }
            }
            return view;
        }


        public void setClickListener(int textId, View.OnClickListener clickListener) {
            View view;
            view = getCahceView(textId);
            if (view == null) {
                view = this.view.findViewById(textId);
                view.setOnClickListener(clickListener);
            }

        }

        public void setVisibility(int viewId, int visiable) {
            View view1;
            if ((view1 = getCahceView(viewId)) == null) {
                view1 = view.findViewById(viewId);
            }
            view1.setVisibility(visiable);

        }
    }


}