package com.funcList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.securityandroidpatform.R;
import com.funcList.ANR.anrTest1.ANRTestActivity;
import com.funcList.ANR.anrTest2.BlockCanaryActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProjectListActivity extends AppCompatActivity {


    ListView listView;

    Class[] array = {
            ANRTestActivity.class,
            BlockCanaryActivity.class,
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_list);


        listView = findViewById(R.id.list);

        List<String> strings = new ArrayList<>();

        for (Class classO : array) {
            strings.add(classO.getName());
        }


        listView.setAdapter(new ArrayAdapter<String>(this, R.layout.pro_list_item,
                strings));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Class aClass = array[position];
                Intent intent = new Intent(ProjectListActivity.this, aClass);
                startActivity(intent);
            }
        });

    }
}
