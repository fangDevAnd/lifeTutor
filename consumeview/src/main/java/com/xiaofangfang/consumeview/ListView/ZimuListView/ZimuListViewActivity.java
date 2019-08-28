package com.xiaofangfang.consumeview.ListView.ZimuListView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.xiaofangfang.consumeview.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ZimuListViewActivity extends AppCompatActivity {


    private static final String TAG = "test";
    String[][] city = {{"安庆"},
            {"北京", "八达岭", "八公山", "蚌埠"},
            {"成都", "长安", "重庆", "池州"},
            {"东京", "东北", "大连"},
            {"阜阳", "福州", "福建"},
            {"广州", "广东"}};
    //这里我只是给出了部分的数据，懒得去写，太多了，只是一个例子
    String[] title = {"A", "B", "C", "D", "F", "G"};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhimu);
        maginData();
    }

    private List<String> stringList;
    private ListView listView;

    private ListAdapter myAdapter;

    private LinearLayout stickTop;

    private TextView titleItem;

    private void maginData() {

        stringList = new ArrayList<>();

        for (int i = 0; i < city.length; i++) {

            stringList.add(title[i]);

            for (int j = 0; j < city[i].length; j++) {
                stringList.add(city[i][j]);
            }
        }


        stickTop = findViewById(R.id.stickTop);

        listView = findViewById(R.id.listView);

        titleItem = findViewById(R.id.title);

        titleItem.setText(stringList.get(0));

        myAdapter = new ListAdapter();

        listView.setAdapter(myAdapter);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                String obj = (String) myAdapter.getItem(firstVisibleItem);
                if (obj.charAt(0) >= 'A' && obj.charAt(0) <= 'Z') {
                    Log.d(TAG, "onScroll: " + obj);
                    titleItem.setText(obj);
                }
            }
        });


    }


    /**
     * 定义每一项的高度
     */
    int itemHeight;


    class ListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return stringList.size();
        }

        @Override
        public Object getItem(int position) {
            return stringList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyViewHolder myViewHolder;

            String value = stringList.get(position);


            if (convertView != null) {
                myViewHolder = (MyViewHolder) convertView.getTag();
            } else {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_item, null);
                if (itemHeight == 0) {
                    View finalConvertView = convertView;
                    convertView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
                        if (itemHeight == 0) {
                            itemHeight = finalConvertView.getMeasuredHeight();
                            Log.d(TAG, "getView: +viewitem当前的高度" + itemHeight);

                            setStickTopHeight(itemHeight);

                        }
                    });
                }


                myViewHolder = new MyViewHolder();
                myViewHolder.name = convertView.findViewById(R.id.name);
                myViewHolder.title = convertView.findViewById(R.id.title);
                myViewHolder.touxiang = convertView.findViewById(R.id.touxiang);
                myViewHolder.down = convertView.findViewById(R.id.down);
                convertView.setTag(myViewHolder);
            }


            if (value.length() == 1) {//代表是字母标题
                convertView.setBackgroundColor(Color.parseColor("#cccccc"));
                myViewHolder.down.setVisibility(View.INVISIBLE);
                myViewHolder.name.setVisibility(View.INVISIBLE);
                myViewHolder.touxiang.setVisibility(View.INVISIBLE);
                myViewHolder.title.setVisibility(View.VISIBLE);
                myViewHolder.title.setText(value);

            } else {//代表的是显示列表
                convertView.setBackgroundColor(Color.parseColor("#ffffff"));
                myViewHolder.down.setVisibility(View.VISIBLE);
                myViewHolder.name.setVisibility(View.VISIBLE);
                myViewHolder.touxiang.setVisibility(View.VISIBLE);
                myViewHolder.title.setVisibility(View.INVISIBLE);
                myViewHolder.name.setText(value);
            }
            return convertView;
        }


        class MyViewHolder {
            ImageView touxiang;
            TextView name;
            TextView title;
            ImageView down;
        }
    }


    /**
     * 设置黏贴到顶部的itemm的高度
     *
     * @param itemHeight
     */
    private void setStickTopHeight(int itemHeight) {

        stickTop.getLayoutParams().height = itemHeight;

        stickTop.requestLayout();

    }


}
