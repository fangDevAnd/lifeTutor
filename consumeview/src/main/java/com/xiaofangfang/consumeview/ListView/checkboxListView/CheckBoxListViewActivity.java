package com.xiaofangfang.consumeview.ListView.checkboxListView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.xiaofangfang.consumeview.ListView.ZimuListView.MyAdapter;
import com.xiaofangfang.consumeview.R;
import com.xiaofangfang.consumeview.RecyclerViewTest.RecyclerViewTestActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CheckBoxListViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkboxlistview);
        /**
         R.layout.activity_checkboxlistview



         <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
         android:layout_width="match_parent"
         android:layout_height="match_parent"
         android:orientation="vertical">


         <ListView
         android:id="@+id/listView"
         android:layout_width="match_parent"
         android:layout_height="match_parent"></ListView>


         </LinearLayout>

         */

        initVIew();
    }


    ListView listView;

    MyBaseApdapter myBaseApdapter;
    SharedPreferences sp;
    List<Address> addresses;

    private Map<Integer, Boolean> map = new HashMap<>();// 存放已被选中的CheckBox


    private void initVIew() {

        listView = findViewById(R.id.listView);

        addresses = new ArrayList<>();
        addresses.add(new Address("afafqfwqfq"));
        addresses.add(new Address("afafqfwqfq"));
        addresses.add(new Address("afafqfwqfq"));
        addresses.add(new Address("afafqfwqfq"));
        addresses.add(new Address("afafqfwqfq"));
        addresses.add(new Address("afafqfwqfq"));
        addresses.add(new Address("afafqfwqfq"));
        addresses.add(new Address("afafqfwqfq"));
        addresses.add(new Address("afafqfwqfq"));
        addresses.add(new Address("afafqfwqfq"));
        addresses.add(new Address("afafqfwqfq"));
        addresses.add(new Address("afafqfwqfq"));
        addresses.add(new Address("afafqfwqfq"));
        addresses.add(new Address("afafqfwqfq"));
        addresses.add(new Address("afafqfwqfq"));
        addresses.add(new Address("afafqfwqfq"));
        addresses.add(new Address("afafqfwqfq"));
        addresses.add(new Address("afafqfwqfq"));
        addresses.add(new Address("afafqfwqfq"));
        addresses.add(new Address("afafqfwqfq"));
        addresses.add(new Address("afafqfwqfq"));
        addresses.add(new Address("afafqfwqfq"));
        addresses.add(new Address("afafqfwqfq"));
        addresses.add(new Address("afafqfwqfq"));
        addresses.add(new Address("afafqfwqfq"));
        addresses.add(new Address("afafqfwqfq"));
        addresses.add(new Address("afafqfwqfq"));


        myBaseApdapter = new MyBaseApdapter();
        listView.setAdapter(myBaseApdapter);

        sp = getSharedPreferences("def", MODE_PRIVATE);
        int defaultIndex = sp.getInt("index", 0);

        map.put(defaultIndex, true);

    }


    class MyBaseApdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return addresses.size();
        }

        @Override
        public Object getItem(int position) {
            return addresses.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            Address address = addresses.get(position);

            ViewHolder viewHolder;

            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_checkbox, null);
                viewHolder = new ViewHolder();
                viewHolder.checkBox = convertView.findViewById(R.id.checkbox);
                viewHolder.textView = convertView.findViewById(R.id.posi);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.textView.setText(address.addre);

            viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked == true) {
                        map.clear();//清空
                        map.put(position, true);
                    } else {
                        map.remove(position);
                    }

                    SharedPreferences.Editor editor = sp.edit();
                    if (isChecked) {
                        editor.putInt("index", position);
                    } else {
                        editor.putInt("index", 0);
                    }
                    editor.commit();

                    notifyDataSetChanged();
                }
            });
            if (map != null && map.containsKey(position)) {
                viewHolder.checkBox.setChecked(true);
            } else {
                viewHolder.checkBox.setChecked(false);
            }
            return convertView;
        }

        class ViewHolder {
            TextView textView;
            CheckBox checkBox;
        }
    }


    class Address {
        private String addre;

        public Address(String addre) {
            this.addre = addre;
        }
    }


}
