package com.xiaofangfang.mylibrary;

import com.google.gson.Gson;

public class GsonTest {

    public static void main(String[] argc){
        Gson gson=new Gson();

        String json="{'name':'fang','age':12}";

         json="{'name':'fang'}";


        Person person=gson.fromJson(json,Person.class);

        System.out.println("用户名"+person.age);

    }


    public static  class Person{

        private String name;

        private String age;

        public void setAge(String age) {
            this.age = age;
        }

        public String getAge() {
            return age;
        }

        public Person(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
