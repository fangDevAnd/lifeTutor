package com.xiaofangfang.consumeview.RecyclerViewTest;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.xiaofangfang.consumeview.ListView.checkboxListView.CheckBoxListViewActivity;
import com.xiaofangfang.consumeview.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * 这个是recyclerview处理chechkbox点击滚动造成的错乱问题,实现了功能,但是发现有bug,在同一个界面,如果有一个被点击,再点击没有效果
 */
public class RecyclerViewTestActivity extends AppCompatActivity {


    private static final String TAG = "test";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_test);
        initVIew();
    }

    private RecyclerView recyclerView;

    private MyRecyclerAdapter myRecyclerAdapter;

    private SparseBooleanArray mCheckStates;


    SharedPreferences sp;

    private void initVIew() {
        mCheckStates = new SparseBooleanArray();

        recyclerView = findViewById(R.id.recyclerView);

        List<Address> addresses = new ArrayList<>();
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
        addresses.add(new Address("afafqfwqfq"));
        addresses.add(new Address("afafqfwqfq"));
        addresses.add(new Address("afafqfwqfq"));
        addresses.add(new Address("afafqfwqfq"));
        addresses.add(new Address("afafqfwqfq"));
        addresses.add(new Address("afafqfwqfq"));
        addresses.add(new Address("afafqfwqfq"));
        addresses.add(new Address("afafqfwqfq"));


        sp = getSharedPreferences("def", MODE_PRIVATE);
        int defaultIndex = sp.getInt("index", 0);

        mCheckStates.put(defaultIndex, true);

        myRecyclerAdapter = new MyRecyclerAdapter(addresses);
        recyclerView.setAdapter(myRecyclerAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);


    }


    class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {


        class MyViewHolder extends RecyclerView.ViewHolder {

            CheckBox checkBox;
            TextView posi;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                checkBox = itemView.findViewById(R.id.checkbox);
                posi = itemView.findViewById(R.id.posi);
            }
        }

        private List<Address> addressList;


        MyRecyclerAdapter(List<Address> addressList) {
            this.addressList = addressList;
        }


        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_checkbox, null);
            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }


        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
            final Address address = addressList.get(position);
            holder.posi.setText(address.addre);
            holder.checkBox.setTag(position);

            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int posi = (int) buttonView.getTag();

                    Log.d(TAG, "真实的位置" + posi + "\t复用的位置" + position);

                    if (isChecked) {
                        mCheckStates.clear();
                        //点击时将当前CheckBox的索引值和Boolean存入SparseBooleanArray中
                        mCheckStates.put(posi, true);
                    } else {
                        //否则将 当前CheckBox对象从SparseBooleanArray中移除
                        mCheckStates.delete(posi);
                    }

                    SharedPreferences.Editor editor = sp.edit();
                    if (isChecked) {
                        editor.putInt("index", posi);
                    } else {
                        editor.putInt("index", 0);
                    }
                    editor.commit();

                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            notifyDataSetChanged();
                        }
                    });
                }
            });

            holder.checkBox.setChecked(mCheckStates.get(position, false));


        }

        @Override
        public int getItemCount() {
            return addressList.size();
        }

    }

    class Address {
        private String addre;

        public Address(String addre) {
            this.addre = addre;
        }
    }


}
