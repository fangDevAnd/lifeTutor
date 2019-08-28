package com.annotation.annotationProcessor;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.xiaofangfang.precentlayoutdemo.R;

import java.util.List;

import io.github.yuweiguocn.MyCustomAnnotation;
import io.github.yuweiguocn.annotation.CustomAnnotation;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.processor_xml);
        StringBuilder sb = new StringBuilder();
        List<String> annotations = MyCustomAnnotation.getAnnotations();
        for (int i = 0; i < annotations.size(); i++) {
            sb.append(annotations.get(i));
            sb.append("\n");
        }

        ((TextView) findViewById(R.id.tv_annotation)).setText(sb.toString());
    }

    @CustomAnnotation
    public void testAnnotation() {
        Log.d("test", "test annotation");
    }
}
