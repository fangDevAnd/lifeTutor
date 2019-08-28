package com.xiaofangfang.filterrice.Activity;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;

import android.widget.ListView;

import com.xiaofangfang.filterrice.R;

public class SlipActivity extends Activity {


    ListView view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.slip);


        view = findViewById(R.id.listView);


        String[] value = {
                "放", "sgsgw", "wgwehb",
                "放", "sgsgw", "wgwehb",
                "放", "sgsgw", "wgwehb",
                "放", "sgsgw", "wgwehb"
        };

//        view.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, value));


    }

}
