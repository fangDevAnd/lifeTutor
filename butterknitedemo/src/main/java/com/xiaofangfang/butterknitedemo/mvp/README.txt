

1.这个文件夹下面存放的是mvp的设计,但就目前实现的mvp架构来说.存在一些问题,一个界面只能引用一个presenter
当一个界面的实现需要多个presenter的需要的时候会出现问题

2.缺少灵活性,当出现activity与fragment的通信问题,怎么解决??

        1.方案1.使用自己包装的类和接口实现.编写的代码存放在rice2_version中
        2.使用EventBus

3.使用databind

4.使用Dagger2实现依赖注入

5.Butterknif注解简化代码

