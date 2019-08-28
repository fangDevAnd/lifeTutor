package com.xiaofangfang.filterrice.Activity;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.xiaofangfang.filterrice.R;

import java.util.List;

public class ListViewTestActivity extends AppCompatActivity {


    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_test_activity);

        listView = findViewById(R.id.listView);
    }



    public abstract class MyAdapter<T> extends BaseAdapter {


        List<T> list;
        int layout;

        public MyAdapter(List<T> list, int layout) {
            this.list = list;
            this.layout = layout;
        }


        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            MyHolder myHolder = null;
            T data = list.get(position);
            if (convertView == null) {
                myHolder = new MyHolder(parent.getContext(), convertView, parent, layout);
            } else {
                myHolder = (MyHolder) convertView.getTag();
            }

            //在这里需要进行数据的绑定
            bindData(myHolder, data);

            return myHolder.getConvertView();
        }


        public abstract void bindData(MyHolder myHolder, T data);


        public class MyHolder {

            private View convertView;


            MyHolder(Context context, View convertView, ViewGroup parent, int layout) {
                convertView = LayoutInflater.from(context).inflate(layout, parent, false);
                this.convertView = convertView;
                this.convertView.setTag(this);
            }


            public View getConvertView() {
                return convertView;
            }

        }


    }


}
