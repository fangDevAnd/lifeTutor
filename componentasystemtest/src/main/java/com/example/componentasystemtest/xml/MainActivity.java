package com.example.componentasystemtest.xml;

import android.content.res.XmlResourceParser;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.componentasystemtest.R;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<Books> list = null;
        Books books = null;
        XmlResourceParser xmlResourceParser = getResources().getXml(R.xml.test);
        try {
            int event = xmlResourceParser.getEventType();//获得节点的类型
            String tagName = null;
            list = new ArrayList();
            while (event != XmlPullParser.END_DOCUMENT) {//代表的是真个文档的结束

                switch (event) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        tagName = xmlResourceParser.getName();
                        Log.d("test", "当前的tagName=" + tagName);
                        switch (tagName) {
                            case "book":
                                books = new Books();
                                break;
                            case "author":
                                String text = xmlResourceParser.nextText();
                                Log.d("test", "text" + text);
                                books.setAuthor(text);
                                break;
                            case "price":
                                String text1 = xmlResourceParser.nextText();
                                Log.d("test", "text" + text1);
                                books.setPrice(text1);
                            case "name":
                                String text2 = xmlResourceParser.nextText();
                                Log.d("test", "text" + text2);
                                books.setName(text2);
                                break;
                            case "class":
                                String text3 = xmlResourceParser.nextText();
                                Log.d("test", "text" + text3);
                                books.setClasses(text3);
                                break;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        tagName = xmlResourceParser.getName();
                        if (tagName.equals("book")) {
                            list.add(books);
                        }
                        break;
                }
                event = xmlResourceParser.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        printBooks(list);
    }

    public void printBooks(List<Books> list) {
        for (Books books : list) {
            Log.d("test", "作者是：" + books.getAuthor());
            Log.d("test", "价格是：" + books.getPrice());
            Log.d("test", "类别是：" + books.getClasses());
            Log.d("test", "书名是：" + books.getName());
        }

        Log.d("test", "遍历结束");

    }

}
