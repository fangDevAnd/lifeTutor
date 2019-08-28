package com.example.momomusic.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import com.example.momomusic.R;
import com.example.momomusic.adapter.MyAdapter;
import com.example.momomusic.adapter.MyCommandAdapter;
import com.example.momomusic.exception.ViewNotMatchException;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;


/**
 * 这个fragment是一个万能的List的适配器，只能用来设置单一的fragment
 * <p>
 * 这是一个实现了特定功能的Fragment,但是唯一的不足是该fragment不能是哪静态注册
 * 只能动态的注册
 * <p>
 * 更加常见的用法是用在ViewPager中
 */
public abstract class MusicListFragment<T> extends Fragment {

    /**
     * 通过这个方法实现对view的设置
     *
     * @return
     */
    public abstract int[] viewCreate();

    /**
     * 定义的默认的布局，里面仅仅存放了一个ListView
     */
    public static final int DEFAULT_VIEW = R.layout.fragment_music_list;

    public abstract List<T> getDataSource();

    public abstract void bindView(MyAdapter.ViewHolder holder, T obj);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view;
        if (viewCreate()[0] <= 0) {
            view = inflater.inflate(DEFAULT_VIEW, null);
        } else {
            view = inflater.inflate(viewCreate()[0], null);
        }

        ButterKnife.bind(this, view);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    MyAdapter<T> myAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myAdapter = new MyAdapter<T>((ArrayList<T>) getDataSource(), viewCreate()[1]) {
            @Override
            public void bindView(ViewHolder holder, T obj) {
                bindView(holder, obj);
            }

            @Override
            public int getCount() {
                return getDataSource().size();
            }
        };

        if (view instanceof ListView) {
            ((ListView) view).setAdapter((ListAdapter) myAdapter);
        } else {
            try {
                throw new ViewNotMatchException("当前的根布局必须是ListView");
            } catch (ViewNotMatchException e) {
                e.printStackTrace();
            }
        }

    }
}
