package com.rcs.nchumanity.ul.basicMap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.rcs.nchumanity.R;

import java.util.ArrayList;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


    }

    public void onClick(View view) {

//115.896556	28.689505
        Intent intent = new Intent(this, BasicMapActivity.class);
        ArrayList<ILocaPoint> points = new ArrayList<>();

        points.add(new LocalPoint(28.963175,
                115.400244, "BEIJINFGASFAS", "3.5公里", "建安的爱仕达所多As"));

        points.add(new LocalPoint(28.945675,
                116.400244, "BEIJINFGASFAS", "3.第三方", "建安的爱仕达所多As"));

        points.add(new LocalPoint(28.953175,
                116.406274, "BEIJINFGASFAS", "3.爱仕达", "建安的爱仕达所多As"));

        points.add(new LocalPoint(28.962175,
                115.892356, "BEIJINFGASFAS", "3.5公里", "建安的爱仕达所多As"));

        Bundle bundle = new Bundle();
        bundle.putSerializable(ArrayList.class.getSimpleName(), points);
        bundle.putString(BasicMapActivity.TITLE, "AED分布");
        intent.putExtras(bundle);
        startActivity(intent);
    }


}
