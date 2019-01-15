package com.demo.greendao.newgreendaodemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.demo.greendao.db.UserDao;
import com.demo.greendao.model.User;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private UserDao mUserDao;
    private String[] mSexArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mUserDao = MyApplication.getApplication().getDaoSession().getUserDao();
        Button btn_insert = (Button) findViewById(R.id.btn_insert);
        Button btn_update = (Button) findViewById(R.id.btn_update);
        Button btn_query = (Button) findViewById(R.id.btn_query);
        Button btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_insert.setOnClickListener(this);
        btn_update.setOnClickListener(this);
        btn_query.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        mSexArr = new String[]{"man","women"};
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //增
            case R.id.btn_insert:
                for (int i = 0; i < 10; i++) {
                    User user = new User();
                    user.setName("xiaoming"+i);
                    user.setAge(20+i);
                    user.setSex(mSexArr[i%2]);
                    user.setAddress("四川北路1666"+i+"号");
                    mUserDao.insert(user);
                }

                break;
            //改
            case R.id.btn_update:

                break;
            //查
            case R.id.btn_query:
                List<User> list = mUserDao.queryBuilder().where(UserDao.Properties.Id.gt(11)).build().list();
                for (User user : list) {
                    Log.d("xxx","userID="+user.getId());
                }
                List<User> users = mUserDao.loadAll();
                for (User user : users) {
                    Log.d("xxx","loadAll="+user.getId());
                }
               Log.d("xxx","loadByRowId="+mUserDao.loadByRowId(8).getName()) ;
                //查询数据库中性别为男性的地址
                List<User> listMan = mUserDao.queryRaw("where SEX=?", "man");
                for (User user : listMan) {
                    Log.d("xxx","man地址="+user.getAddress());

                }
                //between
                List<User> listBetween = mUserDao.queryBuilder().where(UserDao.Properties.Id.between(6, 12)).build().list();
                for (User user : listBetween) {
                    Log.d("xxx","between="+user.getAddress());
                }
                //查询一条数据
                User user = mUserDao.queryBuilder().where(UserDao.Properties.Id.eq(12)).build().unique();
                Log.d("xxx","查询一条数据="+user.getAddress());
                break;
            //删
            case R.id.btn_delete:Log.d("xxx","btn_delete");
                List<User> list1 = mUserDao.queryBuilder().where(UserDao.Properties.Id.gt(10)).build().list();
                for (User user1 : list1) {
                    Log.d("xxx","user1===="+user1.getId());

                    mUserDao.delete(user1);
                }
                break;
            default:
                break;
        }
    }
}
