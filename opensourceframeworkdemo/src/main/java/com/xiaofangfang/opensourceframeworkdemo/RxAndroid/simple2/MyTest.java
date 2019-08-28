package com.xiaofangfang.opensourceframeworkdemo.RxAndroid.simple2;

import java.util.Observable;
import java.util.Observer;

public class MyTest {

    public static void main(String[] argc) {

        SimpleObservable simpleObservable = new SimpleObservable();


        SimpleObserver observer = new SimpleObserver(simpleObservable);

        simpleObservable.setData(1);
        simpleObservable.setData(2);
        simpleObservable.setData(2);
        simpleObservable.setData(4);
        simpleObservable.setData(5);


        new MyTest().test();


    }



    public void test(){

        MyObservable myObservable=new MyObservable("sdgsdgweegwe");

        myObservable.addObserver(new MyObsever());
        myObservable.addObserver(new MyObsever());
        myObservable.addObserver(new MyObsever());
        myObservable.addObserver(new MyObsever());
        myObservable.addObserver(new MyObsever());


        myObservable.demo1("asffasfafsgqee");


    }




    public class MyObservable extends Observable{

        private String name;

        MyObservable(String name){
            this.name=name;
        }


        public void demo1(String value){

            setChanged();

            notifyObservers();

        }
    }


    public class MyObsever implements Observer {

        @Override
        public void update(Observable o, Object arg) {
           System.out.println("接收到请求"+((MyObservable)o).name+"    "+arg);
        }
    }








}
