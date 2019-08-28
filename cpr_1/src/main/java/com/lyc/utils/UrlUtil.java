package com.lyc.utils;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * URL
 * 的工具类
 */
public class UrlUtil {
    private String urlSt;
    private URL url;

    public UrlUtil(String urlSt) {
        try {
            this.url = new URL(urlSt);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.urlSt = urlSt;
    }


    /**
     * get current url param key`s value
     * e.g <a>http://www.tao?dee=asas&fang=434</a>
     * if param name is 'dee' you can receive back value is 'asas'
     *
     * @param name
     * @return
     */
    public String getQueryString(String name) {

        String query = url.getQuery();

        query = query.substring(query.indexOf(name + "="));//www.tao?dee=asas&fang=434
        if (query.indexOf("&") > -1)
            query = query.substring(name.length() + 1, query.indexOf("&"));
        else
            query = query.substring(name.length() + 1);

        return query;
    }

    /**
     * obtain last url path
     * if url is http://www.tao/33/RWE/F
     * can return path is F
     *
     * @return
     */
    public String getPath() {
        String path = url.getPath();
        int indexOf;
        while ((indexOf = path.indexOf("/")) > -1) {
            path = path.substring(indexOf + 1);
        }
        return path;
    }
}
