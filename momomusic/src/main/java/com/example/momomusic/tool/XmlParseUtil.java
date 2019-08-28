package com.example.momomusic.tool;

import android.content.Context;
import android.content.res.XmlResourceParser;

import com.example.momomusic.activity.PrimaryActivity;
import com.orhanobut.logger.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


/**
 * xml解析工具
 */
public class XmlParseUtil {


    public XmlParseUtil() {
    }

    public <T extends PrimaryActivity> void parseXML(T context, final String fileName) {

        try {
            //传入文件名：language.xml；用来获取流
            InputStream is = context.getAssets().open(fileName);
            //首先创造：DocumentBuilderFactory对象
            DocumentBuilderFactory dBuilderFactory = DocumentBuilderFactory.newInstance();
            //获取：DocumentBuilder对象
            DocumentBuilder dBuilder = dBuilderFactory.newDocumentBuilder();
            //将数据源转换成：document 对象
            Document document = dBuilder.parse(is);
            //获取根元素
            Element root = (Element) document.getDocumentElement();
            //获取子对象的数值 读取lan标签的内容
            NodeList nodeList = root.getElementsByTagName("fragment");

            for (int i = 0; i < nodeList.getLength(); i++) {

                //获取对应的对象
                Element fragment = (Element) nodeList.item(i);
                String fragmentPath = fragment.getTextContent();

                Logger.d("当前的路径" + fragmentPath);

                if (listener != null) {
                    boolean has = listener.parsering(fragmentPath);
                    if (has) {
                        break;
                    } else {
                        continue;
                    }
                }
            }
            listener.parseComplete();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    public interface ParseXMLListener {


        default void parseComplete() {
        }

        boolean parsering(String value);
    }

    private ParseXMLListener listener;

    public void setParseXMLListener(ParseXMLListener listener) {
        this.listener = listener;
    }


}
