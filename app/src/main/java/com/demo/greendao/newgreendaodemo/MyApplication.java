package com.demo.greendao.newgreendaodemo;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.demo.greendao.db.DaoMaster;
import com.demo.greendao.db.DaoSession;

/**
 * @ Creator     :     chenchao
 * @ CreateDate  :     2019/1/14 0014 10:10
 * @ Description :     NewGreenDaoDemo
 */
public class MyApplication extends Application {

    public static MyApplication application;
    private DaoSession mDaoSession;

    public static MyApplication getApplication() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        initGreenDao();
    }

    private void initGreenDao() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "user.db");
        //获取数据库对象
        SQLiteDatabase db = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}
