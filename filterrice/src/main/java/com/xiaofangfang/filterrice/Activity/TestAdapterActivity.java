package com.xiaofangfang.filterrice.Activity;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xiaofangfang.filterrice.R;
import com.xiaofangfang.filterrice.adapter.MyCommandAdapter;

import java.util.ArrayList;
import java.util.List;

public class TestAdapterActivity extends Activity {


    RecyclerView recyclerView;
    MyCommandAdapter<MyCommandAdapter.Person> pmca;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adapter_view);

        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        int res = R.layout.person_adapter;

        MyCommandAdapter.Person p = new MyCommandAdapter.Person("fang", "defge");
        MyCommandAdapter.Person p1 = new MyCommandAdapter.Person("xiao", "demo");

        List<MyCommandAdapter.Person> personList = new ArrayList<>();
        personList.add(p);
        personList.add(p1);

        pmca = new MyCommandAdapter(this, res, (ArrayList) personList) {

            @Override
            public void bind(MyViewHolder holder, Object o) {
                holder.setText(R.id.name, ((Person) o).getName());
                holder.setText(R.id.address, ((Person) o).getAddress());
            }
        };

        recyclerView.setAdapter(pmca);





    }
}
