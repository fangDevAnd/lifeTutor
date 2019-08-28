package com.thread.unsafeTest;

import java.io.File;
import java.lang.reflect.Field;

import sun.misc.Unsafe;

public class UnSafeTest {

    static UnSafeTest unSafeTest = new UnSafeTest();

    public static void main(String[] argc) throws NoSuchFieldException {
//        unSafeTest.demo1();
        unSafeTest.demo2();
    }


    /**
     * 操纵对象属性
     *
     * @throws
     */
    void demo1() throws NoSuchFieldException {

        User user = new User();

        Unsafe unsafe = getUnSafe();
        long nameOffset = unsafe.objectFieldOffset(User.class.getDeclaredField("name"));
        unsafe.putObject(user, nameOffset, "xiaofang");
        System.out.print(user.name);
    }


    void demo2() {

        int[] arr = new int[3];
        Unsafe unsafe = getUnSafe();

        int baseOffset = unsafe.arrayBaseOffset(arr.getClass());
        int indexScale = unsafe.arrayIndexScale(arr.getClass());
//        System.out.print(baseOffset + 1 * indexScale);

        for (int i = 0; i < arr.length; i++) {
            //unsafe.putInt(array, baseOffset + i*indexScale, i);
            unsafe.getAndSetInt(arr, baseOffset + i * indexScale, i);
        }
        for (int i : arr) {
            System.out.print(i);
        }

//        unsafe.compareAndSwapObject();

    }

    //线程挂起与恢复、CAS
    void demo3() {





    }


    private static Unsafe getUnSafe() {
        Field field;
        Unsafe unsafe = null;
        try {
            field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return unsafe;
    }


    class User {
        public String name;
    }


}
