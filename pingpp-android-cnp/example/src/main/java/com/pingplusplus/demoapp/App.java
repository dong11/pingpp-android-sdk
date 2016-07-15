package com.pingplusplus.demoapp;

import android.app.Application;


/**
 * @author dong {hwongrex@gmail.com}
 * @date 16/5/17
 * @time 下午5:35
 */

public class App extends Application{

    public static String deviceId;
    public static String customer;
    public static final String APP_ID = "";
    public static final String PUBLISHABLE_KEY = "";


    @Override
    public void onCreate() {
        super.onCreate();

        createCustomer();
    }

    /**
     * 模拟创建一个customer
     * @return
     */
    public void createCustomer(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //获取customer操作
            }
        }).start();
    }

}
