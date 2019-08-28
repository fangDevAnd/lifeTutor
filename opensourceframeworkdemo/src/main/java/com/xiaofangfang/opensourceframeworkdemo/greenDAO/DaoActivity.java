package com.xiaofangfang.opensourceframeworkdemo.greenDAO;

import android.os.Bundle;

import com.xiaofangfang.opensourceframeworkdemo.R;
import com.xiaofangfang.opensourceframeworkdemo.greenDAO.application.BaseApplication;
import com.xiaofangfang.opensourceframeworkdemo.greenDAO.mode.User;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DaoActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dao);

        // insertData();
//        insertOrReplace();

        deleteData();
    }

    public void insertData() {
        DaoSession daoSession = ((BaseApplication) getApplication()).getDaoSession();
        for (int i = 0; i < 1000; i++) {
            User user = new User();
            user.setName("小芳芳" + i);
            user.setAge(i);
            daoSession.insert(user);
        }
    }


    public void insertOrReplace() {

        DaoSession daoSession = ((BaseApplication) getApplication()).getDaoSession();
        for (int i = 0; i < 1000; i++) {
            User user = new User();
            user.setId((long) i);
            user.setName("小月月" + i);
            user.setAge(i);
            daoSession.insertOrReplace(user);
        }

    }

    /**
     * 删除
     * 我们执行这个命令.尽管我们的name和age没有满足条件,但是依旧被删除了
     */
    public void deleteData() {
        DaoSession daoSession = ((BaseApplication) getApplication()).getDaoSession();
        daoSession.delete(new User((long) 1220, "小月月", 19));
    }

    public void deleteAll() {

        DaoSession daoSession = ((BaseApplication) getApplication()).getDaoSession();
        daoSession.deleteAll(User.class);
    }


    public void updataData() {


        User user = new User();
        user.setId((long) 12);
        user.setAge(3442);
        user.setName("达芬奇");

        DaoSession daoSession = ((BaseApplication) getApplication()).getDaoSession();
        daoSession.update(user);
    }


    public List queryAll() {
        DaoSession daoSession = ((BaseApplication) getApplication()).getDaoSession();
        List<User> students = daoSession.loadAll(User.class);
        return students;
    }


    public void queryData() {
        DaoSession daoSession = ((BaseApplication) getApplication()).getDaoSession();
        List<User> students = daoSession.queryRaw(User.class, " where id = ?", new String[]{"12"});
    }




}
