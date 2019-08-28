package com.xiaofangfang.opensourceframeworkdemo.MPAndroidChart;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.xiaofangfang.opensourceframeworkdemo.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MpChartActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mp_chart);
        initLineChart();
    }


    LineChart lineChart;

    public void initLineChart() {

        lineChart = findViewById(R.id.lineChart);

        List<Point> points = new ArrayList<>();
        points.add(new Point(12, 45));
        points.add(new Point(300, 231));
        points.add(new Point(456, 78));


        List<Entry> entries = new ArrayList<Entry>();
        for (Point data : points) {
            entries.add(new Entry(data.x, data.y));
        }

        LineDataSet lineDataSet = new LineDataSet(entries, "label1");
        lineDataSet.setColor(Color.GREEN);
        LineData lineData = new LineData();
        lineChart.setData(lineData);
        lineChart.invalidate(); // refresh

    }


}
