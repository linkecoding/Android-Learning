package com.codekong.litepaldemo.app;

import android.app.Application;

import org.litepal.LitePal;

/**
 * Created by 尚振鸿 on 2017/9/24. 10:35
 * mail:szh@codekong.cn
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
    }
}
