package com.xiaofangfang.opensourceframeworkdemo.looger;

import android.os.Bundle;

import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoogerActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        demo1();
        demo2();
        demo3();
        demo4();
        demo5();
        try {
            demo6();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        demo7();
        demo8();


    }

    private void demo8() {

        String[] names = {"Jerry", "Emily", "小五", "hongyang", "七猫"};
        Logger.d(names);


        List<User> users = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            User user = new User(names[i], 10 + i);
            users.add(user);
        }
        Logger.d(users);

    }

    private void demo7() {

        Logger.xml("<html>" +
                "<body>" +
                "<head>你好</head>" +
                "</body>" +
                "</html>");
    }

    private void demo6() throws JSONException {

        JSONObject person = new JSONObject();
        person.put("phone", "12315");
        JSONObject address = new JSONObject();
        address.put("country", "china");
        address.put("province", "fujian");
        address.put("city", "xiamen");
        person.put("address", address);
        person.put("married", true);

        Logger.json(person.toString());

    }

    private void demo5() {
        Logger.d("hello");
        Logger.e("hello");
        Logger.w("hello");
        Logger.v("hello");
        Logger.wtf("hello");
    }

    private void demo4() {
        //提供了对字符串格式化的支持
        Logger.i("大家好，我叫%s，今年%d，很高兴大家来看我的文章！！！", "Jerry", 18);
    }

    private void demo3() {
        Logger.t("tag");//在后面追加tag
        Logger.json("{'name':'fang','age':'18'}");
    }

    private void demo2() {

        // 修改打印的tag值
        Logger.init(this.getClass().getSimpleName());
        String userName = "Jerry";
        Logger.i(userName);
    }

    private void demo1() {
        /**
         * 在没有指定tag的情况下是 :PRETTYLOGGER
         */
        String name = "fang";
        Logger.d("我是超人");

    }


    class User {
        private String name;
        private int age;

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        // 要覆写对象的toString方法才可以打印出完整的日志信息
        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

}
