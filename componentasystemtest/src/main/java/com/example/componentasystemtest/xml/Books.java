package com.example.componentasystemtest.xml;

/**
 * Created by fang on 2018/6/5.
 */

public class Books {

    /*
      <author>小鸟</author>
        <price>23.54</price>
        <name>uml建模</name>
        <class>计算机</class>
     */

    private String author;
    private String price;
    private String name;
    private String classes;

    public Books(String author, String price, String name, String classes) {
        this.author = author;
        this.price = price;
        this.name = name;
        this.classes = classes;
    }

    public Books() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }
}
