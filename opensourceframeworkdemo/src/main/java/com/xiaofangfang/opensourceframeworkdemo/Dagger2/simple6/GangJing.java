package com.xiaofangfang.opensourceframeworkdemo.Dagger2.simple6;

import android.app.Activity;
import android.widget.Toast;

import javax.inject.Inject;

public class GangJing {

    /**
     * 这里我们并没有进行inject,但是运行的话,不会报空指针,原因是我们在component里面实现了这个构造
     */
    SellMoe sellMoe;

    /**
     * 这个构造函数会被注入,里面传递了一个SellMoe,当我们的Dagger2碰到这个参数的时候,会去查找对应的构造函数调用,也就是构造函数被修饰了@Inject的SellMoe的构造
     * 反之如果找不到,就需要我们通过module和provndes记性修饰使用
     *
     * @param sellMoe
     */
    @Inject
    public GangJing(SellMoe sellMoe) {
        this.sellMoe = sellMoe;
    }

    public String lookAtHim() {
        return sellMoe.sellMoe();
    }

    public void gang(Activity activity) {
        Toast.makeText(activity, "这抠脚大汉天天卖萌", Toast.LENGTH_SHORT).show();
    }
}