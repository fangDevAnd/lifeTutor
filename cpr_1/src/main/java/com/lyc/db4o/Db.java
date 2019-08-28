package com.lyc.db4o;


import java.util.ArrayList;
import java.util.List;


import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.*;

/**
 * Db4o数据库 ，用来实现本地数据对象的存储
 * 采用的是异步的加载实现
 * 同步实现的是
 */
public class Db extends Thread {
    ObjectContainer db = null;
    private String dbFileName;

    public Db(String dbFileName) {
        this.dbFileName = dbFileName;
        this.start();
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        super.run();
        db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), dbFileName);
        synchronized (this) {
            notifyAll();
        }

    }

    public <T> T getFirst(T ins) {
        T res = null;

        ObjectSet obj = db.query(ins.getClass());
        if (obj.hasNext())
            res = (T) obj.next();
        else
            res = ins;
        return res;


    }

    public <T> T get(T t) {

        T res = null;
        ObjectSet<T> result = db.queryByExample(t);
        if (result.hasNext()) {
            res = (T) result.next();
        }
        return res;
    }

    public <T> List<T> getByNQ(Predicate<T> pred) {

        List<T> res = new ArrayList<T>();

        ObjectSet<T> result = db.query(pred);
        while (result.hasNext()) {
            res.add((T) result.next());
        }
        return res;
    }

    public <T> T getByNQForSingle(Predicate<T> pred) {
        T res = null;
        ObjectSet<T> result = db.query(pred);
        if (result.hasNext()) {
            res = ((T) result.next());
        }
        return res;
    }

    public <T> void add(T t) {
        synchronized (this) {
            while (db == null) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        db.store(t);
        db.commit();
    }


    public <T> void del(T t) {
        db.delete(get(t));
        db.commit();
    }

    public void close() {
        db.close();
    }
}
