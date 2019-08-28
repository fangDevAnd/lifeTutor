package com.xiaofangfang.lib;

import java.awt.SystemColor;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import jdk.nashorn.internal.objects.annotations.Function;

public class JDKTest8 {

    static JDKTest8 jdkTest8 = new JDKTest8();

//    public static void main(String[] argc) throws IOException {

//        jdkTest8.test1();
//        jdkTest8.test2();
//        jdkTest8.test3();

//    }

    //1===================接口的默认方法
    interface Formula {
        double calculate(int a);

        default double sqrt(int a) {
            return Math.sqrt(a);
        }
    }


    public void test1() {
        Formula formula = new Formula() {
            @Override
            public double calculate(int a) {
                return sqrt(a * 100);
            }
        };

        System.out.print(formula.calculate(100) + "\t" + formula.sqrt(12));     // 100.0
    }

    //2.Lambda 表达式

    void test2() {
        List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");


        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return b.compareTo(a);
            }
        });

        for (String name : names) {
            System.out.println(name);
        }

        Collections.sort(names, (String a, String b) -> {
            return a.compareTo(b);
        });

        Collections.sort(names, (a, b) -> {
            return a.compareTo(b);
        });

        //对于单句,直接返回的,可以去掉大括号
        Collections.sort(names, (a, b) -> a.compareTo(b));

    }


    //3.函数式编程

    @FunctionalInterface
    interface Converter<F, T> {
        T convert(F from);
    }

    @FunctionalInterface
    interface Demo1 {
        void demo();
    }


    @FunctionalInterface
    interface Demo2 {
        Console demo();
    }


    class Person {
        String firstName;
        String lastName;


        Person() {
        }

        Person(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }
    }

    interface PersonFactory<P extends Person> {
        P create(String firstName, String lastName);
    }


    void test3() throws IOException {
        Converter<String, Integer> converter = (from) -> Integer.valueOf(from);
        Integer converted = converter.convert("123");
        System.out.println(converted);    // 123

        Converter<String, Integer> converter1 = Integer::valueOf;
        Integer converted1 = converter.convert("123");
        System.out.println(converted);   // 123　

        /**
         * 函数式接口
         * 我们知道静态方法valueOf需要传递一个参数是个整型,那么函数接口必须能够传递这个参数
         */
        Demo1 demo1 = System.out::println;//println有一个实现是没有参数的,所以可以,没有报错
        demo1.demo();

        InputStream is = System.in;
//        is.read();


        Demo2 demo11 = System::console;
        Console console = demo11.demo();

        /**
         * 方法与构造函数引用
         * 因为是接口,没有构造函数.所以可以使用这个方式进行构造函数的调用
         */
        PersonFactory<Person> personFactory = Person::new;
        Person person = personFactory.create("Peter", "Parker");
    }

    void test4() {

        int num = 1;//这里使用了匿名函数调用外不局部变量
        //局部变量需要被final 当然也可以不写,但是隐式就是final的,不能更改
        Converter<Integer, String> stringConverter =
                (from) -> String.valueOf(from + num);
        stringConverter.convert(2);     // 3
    }

    //访问对象字段与静态变量
    static class Lambda4 {

        static int outerStaticNum;
        int outerNum;


        void testScopes() {
            Converter<Integer, String> stringConverter1 = (from) -> {
                outerNum = 23;
                return String.valueOf(from);
            };

            Converter<Integer, String> stringConverter2 = (from) -> {
                outerStaticNum = 72;
                return String.valueOf(from);
            };
        }
    }

    void test5() {
        Lambda4 lambda = new Lambda4();
        lambda.testScopes();

    }

    //---------------------访问接口的默认方法
    void test6() {
        //这个是不能访问的
//        Formula formula = (a) -> sqrt(a * 100);
    }





}
