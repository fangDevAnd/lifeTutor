package com.example.componentasystemtest.sqlite;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.componentasystemtest.R;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DaoOprateActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dao_oprate);

        daoHelper = new DaoHelper(this, null, null, -1);
    }


    DaoHelper daoHelper;

    public void onClick(View view) {
        SQLiteDatabase dbO = daoHelper.getWritableDatabase();
        switch (view.getId()) {

            case R.id.button1:
                dbO.execSQL("create table t(" +
                        "name varchar(12))");
                break;
            case R.id.button2:
                dbO.execSQL("create table w(" +
                        "name varchar(12))");
                break;
            case R.id.button3:
                dbO.execSQL("create table e(" +
                        "name varchar(12))");
                break;
        }
    }
}
