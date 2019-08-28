package com.xiaofangfang.opensourceframeworkdemo;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {


    @Test
    public void decrea(){

        assertFalse("对的",14-2!=12);

    }


    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }




}