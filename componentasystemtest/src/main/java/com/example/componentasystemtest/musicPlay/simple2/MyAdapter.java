package com.example.componentasystemtest.musicPlay.simple2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import androidx.annotation.DrawableRes;

/**
 * 由于我们不在清除，内部使用什么样的bean来作为封装的数据，所以我们只能通过泛型机制进行bean测接收
 *
 * @param <T>
 */
public abstract class MyAdapter<T> extends BaseAdapter {
    private ArrayList<T> mData;
    private int mLayoutRes;

    //布局id
    public MyAdapter() {
    }

    public MyAdapter(ArrayList<T> mData, int mLayoutRes) {
        this.mData = mData;
        this.mLayoutRes = mLayoutRes;
    }

    public void setmData(ArrayList<T> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    @Override
    public T getItem(int position) {
        if (mData.size() == 0) {
            return null;
        }
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.bind(parent.getContext(), convertView, parent, mLayoutRes, position);
        bindView(holder, getItem(position));
        return holder.getItemView();
    }

    public abstract void bindView(ViewHolder holder, T obj);

    //添加一个元素
    public void add(T data) {
        if (mData == null) {
            mData = new ArrayList<>();
        }
        mData.add(data);
        notifyDataSetChanged();
    }

    //往特定位置,添加一个元素
    public void add(int position, T data) {
        if (mData == null) {
            mData = new ArrayList<>();
        }
        mData.add(position, data);
        notifyDataSetChanged();
    }

    public void remove(T data) {
        if (mData != null) {
            mData.remove(data);
        }
        notifyDataSetChanged();
    }

    public void remove(int position) {
        if (mData != null) {
            mData.remove(position);
        }
        notifyDataSetChanged();
    }

    public void clear() {
        if (mData != null) {
            mData.clear();
        }
        notifyDataSetChanged();
    }


    public static class ViewHolder {
        private SparseArray<View> mViews; //存储ListView 的 item中的
        private View item; //存放convertView
        private int position; //游标
        private Context context;//Context上下文View

        private ViewHolder(Context context, ViewGroup parent, int layoutRes) {
            mViews = new SparseArray<>();
            this.context = context;
            View convertView = LayoutInflater.from(context).inflate(layoutRes, parent, false);
            convertView.setTag(this);
            item = convertView;
        }

        public static ViewHolder bind(Context context, View convertView, ViewGroup parent, int layoutRes, int position) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder(context, parent, layoutRes);
            } else {
                holder = (ViewHolder) convertView.getTag();
                holder.item = convertView;
            }
            holder.position = position;
            return holder;
        }


        @SuppressWarnings("unchecked")
        public <T extends View> T getView(int id) {
            T t = (T) mViews.get(id);
            if (t == null) {
                t = (T) item.findViewById(id);
                mViews.put(id, t);
            }
            return t;
        }

        public View getItemView() {
            return item;
        }

        public int getItemPosition() {
            return position;
        }


        public ViewHolder setText(int id, CharSequence text) {
            View view = getView(id);
            if (view instanceof TextView) {
                ((TextView) view).setText(text);
            }
            return this;
        }

        public ViewHolder setImageResource(int id, @DrawableRes int drawableRes) {
            View view = getView(id);
            if (view instanceof ImageView) {
                ((ImageView) view).setImageResource(drawableRes);
            } else {
                view.setBackgroundResource(drawableRes);
            }
            return this;
        }


        public ViewHolder setAsyncImageResource(int id, String drawableRes) {
            View view = getView(id);
            if (view instanceof ImageView) {
                Glide.with(context).load(drawableRes).into((ImageView) view);
            }
            return this;
        }


        public ViewHolder setBitmap(int id, Bitmap bitmap) {
            View view = getView(id);
            if (view instanceof ImageView) {
                ((ImageView) view).setImageBitmap(bitmap);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] bytes = baos.toByteArray();

                Glide.with(context)
                        .load(bytes)
                        .centerCrop()
                        .into((ImageView) view);

            }
            return this;
        }


        public ViewHolder setOnClickListener(int id, View.OnClickListener listener) {
            getView(id).setOnClickListener(listener);
            return this;
        }

        public ViewHolder setVisibility(int id, int visible) {
            getView(id).setVisibility(visible);
            return this;
        }

        public ViewHolder setTag(int id, Object obj) {
            getView(id).setTag(obj);
            return this;
        }


    }

}